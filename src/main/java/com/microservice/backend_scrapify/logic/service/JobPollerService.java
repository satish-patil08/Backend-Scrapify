package com.microservice.backend_scrapify.logic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.backend_scrapify.modelRequest.ScrapifyJobs;
import com.microservice.backend_scrapify.modelResponce.ScrapifyJobsListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.Lifecycle;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class JobPollerService implements Lifecycle {

    @Autowired
    private ScraperService scraperService;

    @Autowired
    private RestTemplate restTemplate;

    private volatile boolean running = false;
    private Thread pollerThread;
    public static final String PORTAL_JOBS_URL = "http://localhost:9001/scrapify/get-jobs";

    // Local job queue holding pending jobs from the portal.
    private final Queue<ScrapifyJobs> jobQueue = new ConcurrentLinkedQueue<>();


    // Start the poller when the application starts
    @Override
    public void start() {
        running = true;
        System.out.println("JobPollerService started.");
    }

    // Stop the poller
    @Override
    public void stop() {
        running = false;
        System.out.println("JobPollerService stopped.");
    }

    // Check if the poller is running
    @Override
    public boolean isRunning() {
        return running;
    }

    // Fetch jobs from Scrapify Portal and add to the queue
    @Scheduled(fixedDelay = 60000) // Every minute
    public void fetchJobsFromPortal() {
        try {
            String response = restTemplate.getForObject(PORTAL_JOBS_URL, String.class);
            ScrapifyJobsListResponse jobsListResponse = new ObjectMapper().readValue(response, ScrapifyJobsListResponse.class);

            if (Boolean.TRUE.equals(jobsListResponse.success) && jobsListResponse.getData() != null) {
                jobQueue.addAll(jobsListResponse.data);
                System.out.println("Added " + jobsListResponse.getData().size() + " jobs to the queue.");
            }
        } catch (Exception e) {
            System.err.println("Error fetching jobs: " + e.getMessage());
        }
    }

    // Process jobs from the queue
    @Scheduled(fixedDelay = 10000) // Every 10 seconds
    public void processJobQueue() {
        while (!jobQueue.isEmpty()) {
            ScrapifyJobs job = jobQueue.poll();
            if (job != null) {
                boolean success = scraperService.scrapeChatGPT(job);
                if (!success) {
                    jobQueue.add(job); // Requeue failed jobs
                }
            }
        }
    }
}