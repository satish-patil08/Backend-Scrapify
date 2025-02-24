package com.microservice.backend_scrapify.modelResponce;


import com.microservice.backend_scrapify.modelRequest.ScrapifyJobs;

public class ScrapifyJobStatusResponse {
    public Boolean success;
    public String message;
    public ScrapifyJobs data;

    public ScrapifyJobStatusResponse() {
    }

    public ScrapifyJobStatusResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ScrapifyJobStatusResponse(Boolean success, String message, ScrapifyJobs data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ScrapifyJobs getData() {
        return data;
    }

    public void setData(ScrapifyJobs data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ScrapifyJobStatusResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
