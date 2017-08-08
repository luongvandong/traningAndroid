package com.android.project1.model;

/**
 * Created by Pc on 5/14/2017.
 */

public class ListQuestion {
    String name;
    String time;
    String title;
    String content;
    String imgAvatar;
    String caigido;

    public ListQuestion() {
    }

    public ListQuestion(String name, String time, String title, String content, String imgAvatar, String caigido) {
        this.name = name;
        this.time = time;
        this.title = title;
        this.content = content;
        this.imgAvatar = imgAvatar;
        this.caigido = caigido;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImgAvatar() {
        return imgAvatar;
    }

    public void setImgAvatar(String imgAvatar) {
        this.imgAvatar = imgAvatar;
    }

    public String getCaigido() {
        return caigido;
    }

    public void setCaigido(String caigido) {
        this.caigido = caigido;
    }
}
