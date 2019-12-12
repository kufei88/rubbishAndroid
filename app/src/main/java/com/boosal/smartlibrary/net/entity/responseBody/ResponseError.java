package com.boosal.smartlibrary.net.entity.responseBody;

import java.io.Serializable;

public class ResponseError implements Serializable {
    private String error_code;
    private String error_message;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }
}
