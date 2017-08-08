package com.android.project1.model.response;

import com.android.project1.model.pojo.Question;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @Date : 17/05/2017 13:15
 * @Author : ka
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseQuestionHistory {

    @JsonProperty("data")
    public List<Question> question;

    @JsonProperty("status_code")
    public int statusCode;

    @JsonProperty("error_message")
    public String errorMessage;

    public ResponseQuestionHistory() {

    }

    public List<Question> getQuestion() {
        return question;
    }

    public void setQuestion(List<Question> question) {
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
