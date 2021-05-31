package com.hills.mcs_02.networkclasses.interfacesPack;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/** Get the doing task list */
public interface PostRequestRemindDoing {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/task/getUserAllUnfinishTaskFromUT/{userId}")
    Call<ResponseBody> queryDoing(@Path("userId") Integer userId);
}
