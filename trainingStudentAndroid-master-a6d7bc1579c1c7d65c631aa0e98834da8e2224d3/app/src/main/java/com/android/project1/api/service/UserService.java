package com.android.project1.api.service;

import com.android.project1.model.response.ResponseUserData;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @Date : 16/05/2017
 * @Author : ka
 */
public interface UserService {

    @FormUrlEncoded
    @POST("user/login")
    Call<ResponseUserData> login(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("user/register")
    Call<ResponseBody> register(@FieldMap Map<String, String> params);

    @Multipart
    @POST("user/updateImgProfile/")
    Call<ResponseBody> updateImageProfile(@Part("id") RequestBody userId,
                                          @Part MultipartBody.Part part);
}