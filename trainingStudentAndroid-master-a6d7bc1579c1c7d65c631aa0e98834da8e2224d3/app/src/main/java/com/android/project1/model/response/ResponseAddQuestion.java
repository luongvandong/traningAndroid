package com.android.project1.model.response;

import com.android.project1.model.pojo.Question;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Date : 17/05/2017 13:42
 * @Author : ka
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseAddQuestion {

    @JsonProperty("data")
    public Question question;

    @JsonProperty("status_code")
    public int statusCode;

    @JsonProperty("error_message")
    public String errorMessage;

    public ResponseAddQuestion() {

    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
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
