package com.hills.mcs_02.networkclasses.interfacesPack;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/** Get the finished task list */
public interface PostRequestRemindDone {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/task/getUserAllFinishTaskFromUT/{userId}")
    Call<ResponseBody> queryDone(@Path("userId") Integer userId);
}
