package com.android.project1;

import android.app.Application;

/**
 * @Date : 11/05/2017
 * @Author : ka
 */
public class ProjectApplication extends Application {

    static volatile ProjectApplication instance;

    public static ProjectApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
