package com.microservice.backend_scrapify.logic.service;

import com.microservice.backend_scrapify.commons.SequenceGeneratorService;
import com.microservice.backend_scrapify.logic.entity.ScrapifyData;
import com.microservice.backend_scrapify.logic.repository.ScrapifyRepository;
import com.microservice.backend_scrapify.modelRequest.ScrapifyJobs;
import com.microservice.backend_scrapify.modelResponce.StatusResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class ScraperService {

    @Autowired
    private ScrapifyRepository scrapifyRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    private static final int MAX_RETRIES = 3;
    private static final int MIN_RESULT_LENGTH = 10; // Minimum length for valid data


    public StatusResponse processScrapifyJob(ScrapifyJobs jobRequest) {
        try {
            // Extract chromedriver from classpath to a temporary file
            ClassPathResource resource = new ClassPathResource("chromedriver");
            File chromeDriverFile = File.createTempFile("chromedriver", "");
            try (InputStream is = resource.getInputStream()) {
                Files.copy(is, chromeDriverFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
            chromeDriverFile.setExecutable(true);
            System.setProperty("webdriver.chrome.driver", chromeDriverFile.getAbsolutePath());
        } catch (Exception e) {
            return new StatusResponse(false, "Failed to set chromedriver: " + e.getMessage(), null);
        }

        // Setup ChromeOptions for headless mode.
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);
        String result = "";
        boolean success = false;
        int attempts = 0;

        try {
            // Open the target URL.
            driver.get(jobRequest.getUrl());

            while (attempts < MAX_RETRIES && !success) {
                attempts++;
                try {
                    // Locate the input field (update locator if necessary).
                    WebElement inputBox = driver.findElement(By.id("searchBox"));
                    inputBox.clear();
                    inputBox.sendKeys(jobRequest.getFinalPrompt());
                    inputBox.submit();

                    // Wait for results to load.
                    Thread.sleep(2000);

                    // Extract the result (update locator if necessary).
                    WebElement resultElement = driver.findElement(By.className("result-text"));
                    result = resultElement.getText();

                    if (result != null && result.length() >= MIN_RESULT_LENGTH) {
                        success = true;
                    }
                } catch (Exception e) {
                    // Optionally handle individual attempt errors here.
                }
            }
        } catch (Exception e) {
            driver.quit();
            return new StatusResponse(false, "Error processing job: " + e.getMessage(), null);
        } finally {
            driver.quit();
        }

        if (success) {
            // Save the scraped data.
            ScrapifyData data = new ScrapifyData(
                    sequenceGeneratorService.getSequenceNumber(ScrapifyData.DATA_SCRAPPING_SEQUENCE),
                    jobRequest.getCategory(),
                    jobRequest.getUrl(),
                    jobRequest.getFinalPrompt(),
                    result
            );
            scrapifyRepository.save(data);
            return new StatusResponse(true, "Scraping successful", data);
        } else {
            return new StatusResponse(false, "Scraping failed after " + attempts + " attempts", null);
        }
    }

}
