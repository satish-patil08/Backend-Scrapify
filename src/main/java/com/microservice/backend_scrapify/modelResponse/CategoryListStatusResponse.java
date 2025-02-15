package com.microservice.backend_scrapify.modelResponse;

import com.microservice.backend_scrapify.logic.category.entity.Category;

import java.util.List;

public class CategoryListStatusResponse {
    public Boolean success;
    public String message;
    public List<Category> data;

    public CategoryListStatusResponse() {
    }

    public CategoryListStatusResponse(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public CategoryListStatusResponse(Boolean success, String message, List<Category> data) {
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

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CategoryListStatusResponse{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
