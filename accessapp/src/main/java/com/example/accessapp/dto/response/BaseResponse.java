package com.example.accessapp.dto.response;

import java.time.Instant;

/**
 * Base response class for all API responses
 */
public class BaseResponse {
    private String status;
    private String message;
    private Instant timestamp;

    public BaseResponse() {
        this.timestamp = Instant.now();
    }

    public BaseResponse(String status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = Instant.now();
    }

    public static BaseResponse success(String message) {
        return new BaseResponse("success", message);
    }

    public static BaseResponse error(String message) {
        return new BaseResponse("error", message);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}