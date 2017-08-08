package com.android.project1.model;

import android.content.Context;

import com.android.project1.AppConfig;
import com.android.project1.api.ApiError;
import com.android.project1.api.ServiceGenerator;
import com.android.project1.api.service.UserService;
import com.android.project1.model.pojo.User;
import com.android.project1.model.response.ResponseUserData;
import com.android.project1.presenter.UserResultImpl;
import com.android.project1.view.ui.prefs.UserPrefs;
import com.android.project1.view.ui.util.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @Date : 17/05/2017 15:17
 * @Author : ka
 */
public class UserHelper {

    private static final Logger LOGGER = Logger.getLogger(UserHelper.class);

    private UserResultImpl result;

    private UserService service = ServiceGenerator.create(UserService.class);

    private Context context;

    public UserHelper(Context context) {
        this.context = context;
    }

    public void setResult(UserResultImpl result) {
        this.result = result;
    }

    public void register(User user) {
        Map<String, String> param = new HashMap<>();
        param.put("user_name", user.userName);
        param.put("email", user.email);
        param.put("password", user.password);
        service.register(param).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    LOGGER.warn(ApiError.parseFromResponse(response).message());
                    result.onLoadUserFail(response.errorBody().toString());
                    return;
                }
                JSONObject object;
                try {
                    object = new JSONObject(response.body().string());
                    JSONObject data = object.getJSONObject("data");
                    User user = new User();
                    user.image = AppConfig.URL + data.getString("image");
                    user.userId = data.getString("user_id");
                    user.email = data.getString("email");
                    user.userName = data.getString("user_name");
                    LOGGER.info(user);
                    result.onRegisterSuccess(user);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                    result.onLoadUserFail(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                result.onLoadUserFail(t.getMessage());
                LOGGER.error(t);
            }
        });
    }

    public void login(User user) {
        Map<String, String> param = new HashMap<>();
        param.put("user_name", user.userName);
        param.put("password", user.password);
        service.login(param).enqueue(new Callback<ResponseUserData>() {
            @Override
            public void onResponse(Call<ResponseUserData> call, Response<ResponseUserData> response) {
                if (!response.isSuccessful()) {
                    LOGGER.warn(ApiError.parseFromResponse(response).message());
                    result.onLoadUserFail(response.errorBody().toString());
                    return;
                }
                UserPrefs.setUser(response.body().user);
                result.onLoginSuccess(response.body().user);
            }

            @Override
            public void onFailure(Call<ResponseUserData> call, Throwable t) {
                LOGGER.error(t);
                result.onLoadUserFail(t.toString());
            }
        });
    }
}

