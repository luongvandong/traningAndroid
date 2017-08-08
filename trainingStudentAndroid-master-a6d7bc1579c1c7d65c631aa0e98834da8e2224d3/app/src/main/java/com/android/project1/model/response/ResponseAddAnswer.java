package com.android.project1.model.response;

import com.android.project1.model.pojo.Answer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @Date : 17/05/2017 13:49
 * @Author : ka
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseAddAnswer {

    @JsonProperty("data")
    public List<Answer> answer;

    @JsonProperty("status_code")
    public int statusCode;

    @JsonProperty("error_message")
    public String errorMessage;

    public ResponseAddAnswer() {

    }

    public List<Answer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Answer> answer) {
        this.answer = answer;
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
