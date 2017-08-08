package com.android.project1.presenter;

import com.android.project1.model.UserHelper;
import com.android.project1.model.pojo.User;
import com.android.project1.view.UserView;
import com.android.project1.view.ui.util.Logger;

/**
 * @Date : 17/05/2017 15:44
 * @Author : ka
 */
public class UserPresenter implements UserResultImpl {

    private static final Logger LOGGER = Logger.getLogger(UserPresenter.class);

    private UserView userView;
    private UserHelper userHelper;

    public UserPresenter(UserView userView, UserHelper userHelper) {
        this.userView = userView;
        this.userHelper = userHelper;
        this.userHelper.setResult(this);
    }

    public void login(User user) {
        userHelper.login(user);
    }

    public void register(User user) {
        userHelper.register(user);
    }

    @Override
    public void onRegisterSuccess(User user) {
        LOGGER.info(user.email);
        userView.registerSuccess(user);
    }

    @Override
    public void onLoadUserFail(String msg) {
        LOGGER.info(msg);
        userView.loginFail(msg);
        userView.registerFail(msg);
    }

    @Override
    public void onLoginSuccess(User user) {
        LOGGER.info(user.email);
        userView.loginSuccess(user.email);
    }
}
