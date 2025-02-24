package com.microservice.backend_scrapify.modelRequest;

public class ScrapifyJobs {
    public String finalPrompt;
    public Long category;
    public String url;

    public ScrapifyJobs() {
    }

    public ScrapifyJobs(String finalPrompt, Long category, String url) {
        this.finalPrompt = finalPrompt;
        this.category = category;
        this.url = url;
    }

    public String getFinalPrompt() {
        return finalPrompt;
    }

    public void setFinalPrompt(String finalPrompt) {
        this.finalPrompt = finalPrompt;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ScrapifyJobs{" +
                "finalPrompt='" + finalPrompt + '\'' +
                ", category=" + category +
                ", url='" + url + '\'' +
                '}';
    }
}
