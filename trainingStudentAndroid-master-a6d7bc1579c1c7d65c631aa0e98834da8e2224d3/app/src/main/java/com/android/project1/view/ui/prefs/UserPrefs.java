package com.android.project1.view.ui.prefs;

import android.content.SharedPreferences;

import com.android.project1.ProjectApplication;
import com.android.project1.model.pojo.User;

import static android.content.Context.MODE_PRIVATE;

/**
 * @Date : 18/05/2017
 * @Author : ka
 */
public class UserPrefs {

    public static User getUser() {
        User user = new User();
        SharedPreferences preferences = ProjectApplication.getInstance().getSharedPreferences("share", MODE_PRIVATE);
        user.userName = preferences.getString("username", null);
        user.email = preferences.getString("email", null);
        user.image = preferences.getString("image", null);
        user.userId = preferences.getString("userId", null);
        return user;
    }

    public static void setUser(User user) {
        SharedPreferences pre = ProjectApplication.getInstance().getSharedPreferences("share", MODE_PRIVATE);
        SharedPreferences.Editor editor = pre.edit();

        if (user != null) {
            editor.putString("username", user.userName);
            editor.putString("email", user.email);
            editor.putString("image", user.image);
            editor.putString("userId", user.userId);
        }
        editor.apply();
    }
}