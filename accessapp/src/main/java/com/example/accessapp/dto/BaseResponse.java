package com.example.accessapp.dto;

/**
 * Base response class for API responses.
 * Provides a standardized format for all API responses.
 */
public class BaseResponse {
    private boolean success;
    private String message;
    private Object data;

    // Constructors
    public BaseResponse() {
    }

    public BaseResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public BaseResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    // Static factory methods
    public static BaseResponse success(String message) {
        return new BaseResponse(true, message);
    }

    public static BaseResponse success(String message, Object data) {
        return new BaseResponse(true, message, data);
    }

    public static BaseResponse error(String message) {
        return new BaseResponse(false, message);
    }

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
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
}