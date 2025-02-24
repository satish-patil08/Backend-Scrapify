package com.microservice.backend_scrapify.logic.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "scrapped_data")
public class ScrapifyData {

    @Transient
    public static final String DATA_SCRAPPING_SEQUENCE = "data_scrapping_sequence";

    @Id
    public Long id;
    public Long categoryId;
    public String url;
    public String prompt;
    public String data;

    public ScrapifyData() {
    }

    public ScrapifyData(Long id, Long categoryId, String url, String prompt, String data) {
        this.id = id;
        this.categoryId = categoryId;
        this.url = url;
        this.prompt = prompt;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ScrapedData{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", url='" + url + '\'' +
                ", prompt='" + prompt + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
