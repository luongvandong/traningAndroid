package com.android.project1.model.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * @Date : 16/05/2017
 * @Author : ka
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Question implements Parcelable {

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
    public String id;
    public String content;
    @JsonProperty("user_id")
    public String userId;
    @JsonProperty("user_image")
    public String userImage;
    @JsonProperty("user_name")
    public String userName;
    @JsonProperty("list_answers")
    public List<Answer> answers;
    @JsonProperty("question_image")
    public String questionImage;

    public Question() {

    }

    protected Question(Parcel in) {
        id = in.readString();
        content = in.readString();
        userId = in.readString();
        userImage = in.readString();
        userName = in.readString();
        questionImage = in.readString();
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public String getQuestionImage() {
        return questionImage;
    }

    public void setQuestionImage(String questionImage) {
        this.questionImage = questionImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(content);
        dest.writeString(userId);
        dest.writeString(userImage);
        dest.writeString(userName);
        dest.writeString(questionImage);
    }
}
