package com.microservice.backend_scrapify.logic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.microservice.backend_scrapify.modelRequest.ScrapifyJobs;
import com.microservice.backend_scrapify.modelResponce.ScrapifyJobStatusResponse;
import com.microservice.backend_scrapify.modelResponce.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JobPollerService {

    @Autowired
    private ScraperService scraperService;

    @Autowired
    private RestTemplate restTemplate;

    public static final String PORTAL_NEXT_JOB_URL = "http://localhost:9001/scrapify/next-job";

    // Poll every 10 seconds
    @Scheduled(fixedDelay = 10000)
    public void pollJob() {
        try {
            // Use Unirest to GET the next job from the portal.
            HttpResponse<String> response = Unirest.get(PORTAL_NEXT_JOB_URL).asString();
            ScrapifyJobStatusResponse jobRequest = new ObjectMapper().readValue(response.getBody(), ScrapifyJobStatusResponse.class);

            if (response.getStatus() == 200) {
                if (jobRequest.getData() != null) {
                    System.out.println("JOB_QUEUE---->" + jobRequest.getData());
                    StatusResponse statusResponse = scraperService.processScrapifyJob(jobRequest.getData());
                } else {
                    System.out.println("No job returned from the portal.");
                }
            } else {
                System.out.println("Portal returned non-200 status: " + response.getStatus());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
