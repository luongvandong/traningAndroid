package com.android.project1.view;

import com.android.project1.model.pojo.User;

/**
 * @Date : 17/05/2017 15:45
 * @Author : ka
 */
public interface UserView {

    void registerSuccess(User u);

    void loginFail(String msg);

    void loginSuccess(String msg);

    void registerFail(String msg);
}
