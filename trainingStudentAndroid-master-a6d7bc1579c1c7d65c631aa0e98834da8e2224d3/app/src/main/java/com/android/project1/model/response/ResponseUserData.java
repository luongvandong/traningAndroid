package com.android.project1.model.response;

import com.android.project1.model.pojo.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Date : 17/05/2017 10:30
 * @Author : ka
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseUserData {

    @JsonProperty("data")
    public User user;

    @JsonProperty("status_code")
    public int statusCode;

    @JsonProperty("error_message")
    public String errorMessage;

    public ResponseUserData() {

    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
