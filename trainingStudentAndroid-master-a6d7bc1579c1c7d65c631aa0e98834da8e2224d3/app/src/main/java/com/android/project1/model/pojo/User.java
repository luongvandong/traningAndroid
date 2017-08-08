package com.android.project1.model.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @Date : 16/05/2017
 * @Author : ka
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    @JsonProperty("user_id")
    public String userId;

    public String image;

    @JsonProperty("user_name")
    public String userName;

    public String email;

    public String password;

    public User() {

    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", image='" + image + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
