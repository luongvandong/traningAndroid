package com.android.project1.view.ui.util;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Date : 16/05/2017
 * @Author : ka
 */
public class Factory {

    public static MultipartBody.Part prepareFileAsPart(String name, String fileUrl) {
        File file = new File(fileUrl);
        RequestBody body = RequestBody.create(MediaType.parse("image/jpeg"), file);
        return MultipartBody.Part.createFormData(name, file.getName(), body);
    }

    public static List<MultipartBody.Part> prepareMultiFileAsPart(String name, String... fileUrls) {
        List<MultipartBody.Part> parts = new ArrayList<>();
        for (String url : fileUrls) {
            parts.add(prepareFileAsPart(name, url));
        }
        return parts;
    }

    public static Map<String, RequestBody> requestBodyAnswer(String userId, String questionId, String content) {
        Map<String, RequestBody> map = new HashMap<>();
        map.put("user_id", RequestBody.create(null, userId));
        map.put("question_id", RequestBody.create(null, questionId));
        map.put("content", RequestBody.create(null, content));
        return map;
    }

    public static Map<String, RequestBody> requestBodyQuestion(String userId, String content) {
        Map<String, RequestBody> map = new HashMap<>();
        map.put("user_id", RequestBody.create(null, userId));
        map.put("content", RequestBody.create(null, content));
        return map;
    }
}
