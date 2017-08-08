package com.android.project1.model.pojo;

/**
 * @Date : 09/05/2017
 * @Author : ka
 */
public class Notify {

    private long id;

    private String name;

    private String content;

    public Notify(String name, String content) {
        this.name = name;
        this.content = content;
    }

    public Notify() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
