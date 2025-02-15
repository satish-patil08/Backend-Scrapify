package com.microservice.backend_scrapify.modelResponse;

public class StatusResponse {
    public Boolean success;
    public String message;
    public Object data;

    public StatusResponse() {
    }

    public StatusResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public StatusResponse(Boolean success, String message, Object data) {
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StatusResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
