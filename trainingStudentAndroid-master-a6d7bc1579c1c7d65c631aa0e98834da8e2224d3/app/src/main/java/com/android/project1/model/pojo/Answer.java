package com.android.project1.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Date : 16/05/2017
 * @Author : ka
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Answer {

    public String id;

    public String content;

    @JsonProperty("user_id")
    public String userId;

    @JsonProperty("user_image")
    public String userImage;

    @JsonProperty("image")
    public String answerImage;

    @JsonProperty("question_id")
    public String questionId;

    public Answer() {

    }

    public Answer(String userId, String content, String answerImage) {
        this.content = content;
        this.userId = userId;
        this.answerImage = answerImage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getAnswerImage() {
        return answerImage;
    }

    public void setAnswerImage(String answerImage) {
        this.answerImage = answerImage;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
}
