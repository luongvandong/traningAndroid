package com.android.project1.presenter;

import com.android.project1.model.pojo.User;

/**
 * @Date : 17/05/2017 15:47
 * @Author : ka
 */
public interface UserResultImpl {

    void onRegisterSuccess(User user);

    void onLoadUserFail(String msg);

    void onLoginSuccess(User user);
}
