package com.android.project1.api.service;

import com.android.project1.model.response.ResponseQuestionData;
import com.android.project1.model.response.ResponseQuestionDetail;
import com.android.project1.model.response.ResponseQuestionHistory;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * @Date : 16/05/2017
 * @Author : ka
 */
public interface QuestionService {

    @GET("question/getlistquestions")
    Call<ResponseQuestionData> getListQuestion(@Query("page") int page);

    @GET("question/getquestiondetail")
    Call<ResponseQuestionDetail> getQuestionDetail(@Query("id") String id);

    @GET("question/getlisthistory")
    Call<ResponseQuestionHistory> getListQuestionHistory(@Query("user_id") String userId,
                                                         @Query("page") int page);

    @Multipart
    @POST("question/addquestion")
    Call<ResponseBody> postQuestion(@PartMap Map<String, RequestBody> params,
                                    @Part MultipartBody.Part image);
}
