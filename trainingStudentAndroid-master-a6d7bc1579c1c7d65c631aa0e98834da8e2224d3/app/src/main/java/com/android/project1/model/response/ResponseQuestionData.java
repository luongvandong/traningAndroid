package com.android.project1.model.response;

import com.android.project1.model.pojo.Question;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @Date : 17/05/2017 13:01
 * @Author : ka
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseQuestionData {

    @JsonProperty("data")
    public List<Question> questions;

    @JsonProperty("status_code")
    public int statusCode;

    @JsonProperty("error_message")
    public String errorMessage;

    public ResponseQuestionData() {

    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
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
