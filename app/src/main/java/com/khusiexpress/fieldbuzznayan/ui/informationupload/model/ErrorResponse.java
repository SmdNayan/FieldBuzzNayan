package com.khusiexpress.fieldbuzznayan.ui.informationupload.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ErrorResponse implements Serializable {
    @SerializedName("status_code")
    @Expose
    String statusCode;
    @SerializedName("message")
    @Expose
    String message;
    @SerializedName("success")
    @Expose
    boolean success;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
