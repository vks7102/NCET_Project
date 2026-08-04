package com.capstone.backend.exception;

public class ApiResponse<T> {
    private int statusCode;
    private String message;
    private T data;
    private boolean success;

    public ApiResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.success = statusCode >= 200 && statusCode < 300;
    }

    public ApiResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        this.success = statusCode >= 200 && statusCode < 300;
    }

    public int getStatusCode() { return statusCode; }
    public String getMessage() { return message; }
    public T getData() { return data; }
    public boolean isSuccess() { return success; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }
    public void setMessage(String message) { this.message = message; }
    public void setData(T data) { this.data = data; }
    public void setSuccess(boolean success) { this.success = success; }
}
