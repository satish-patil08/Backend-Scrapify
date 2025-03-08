package com.microservice.backend_scrapify.logic.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.backend_scrapify.commons.SequenceGeneratorService;
import com.microservice.backend_scrapify.logic.entity.ScrapifyData;
import com.microservice.backend_scrapify.logic.repository.ScrapifyRepository;
import com.microservice.backend_scrapify.modelRequest.ChatGPTResponseData;
import com.microservice.backend_scrapify.modelRequest.ScrapifyJobs;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.List;


@Service
public class ScraperService {

    @Autowired
    private ScrapifyRepository scrapifyRepository;

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private WebDriver webDriver;

    private static final int MAX_RETRIES = 3;
    private static final int RESPONSE_WAIT_TIME = 20000;
    private static final int RETRY_WAIT_TIME = 5000;

    public boolean scrapeChatGPT(ScrapifyJobs scrapifyJobs) {
        int retryCount = 0;

        while (retryCount < MAX_RETRIES) {
            WebDriver webDriver = null;
            try {
                // Initialize WebDriver only if required
                webDriver = initializeWebDriver();

                System.out.println("Navigating to URL: " + scrapifyJobs.getUrl());
                webDriver.get(URLDecoder.decode(scrapifyJobs.getUrl(), StandardCharsets.UTF_8));

                // Wait for page load
                WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(30));
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("prompt-textarea")));

                // Submit the prompt
                WebElement inputField = webDriver.findElement(By.tagName("textarea"));
                inputField.clear();
                simulateHumanTyping(inputField, scrapifyJobs.getFinalPrompt());
                inputField.submit();

                // Wait for the ChatGPT response
                Thread.sleep(RESPONSE_WAIT_TIME);

                // Extract the JSON content
                WebElement jsonElement = webDriver.findElement(By.cssSelector("div.overflow-y-auto.p-4 code"));
                String jsonText = jsonElement.getText();

                if (jsonText.isEmpty()) {
                    throw new RuntimeException("Empty JSON response.");
                }

                // Parse and save the data
                saveScrapedData(scrapifyJobs, jsonText);

                System.out.println("✅ Scraping successful: " + scrapifyJobs.getFinalPrompt());
                return true;

            } catch (Exception e) {
                System.err.println("❌ Error during scraping attempt " + (retryCount + 1) + ": " + e.getMessage());
                retryCount++;
                try {
                    Thread.sleep(RETRY_WAIT_TIME);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            } finally {
                if (webDriver != null) {
                    webDriver.quit(); // Ensure Chrome is closed after each attempt
                }
            }
        }

        System.err.println("❌ Scraping failed after " + MAX_RETRIES + " attempts.");
        return false;
    }

    // Initialize WebDriver (Lazy loading)
    private WebDriver initializeWebDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--start-maximized",
                "--disable-gpu",
                "--no-sandbox",
                "--disable-dev-shm-usage",
                "--disable-blink-features=AutomationControlled"
        );
        options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));
        return new ChromeDriver(options);
    }

    // Simulate human-like typing
    private void simulateHumanTyping(WebElement inputField, String text) throws InterruptedException {
        for (char c : text.toCharArray()) {
            inputField.sendKeys(String.valueOf(c));
            Thread.sleep(100); // Human-like delay
        }
    }

    // Parse JSON and save the scraped data
    private void saveScrapedData(ScrapifyJobs scrapifyJobs, String jsonText) {
        ChatGPTResponseData jsonData = parseChatGPTResponse(jsonText);
        ScrapifyData data = new ScrapifyData(
                sequenceGeneratorService.getSequenceNumber(ScrapifyData.DATA_SCRAPPING_SEQUENCE),
                jsonText,
                scrapifyJobs.getCategory(),
                jsonData
        );
        scrapifyRepository.save(data);
    }

    // Parse JSON from ChatGPT response
    private ChatGPTResponseData parseChatGPTResponse(String jsonText) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonText, ChatGPTResponseData.class);
        } catch (Exception e) {
            System.err.println("❌ Failed to parse JSON: " + e.getMessage());
            return null;
        }
    }
}