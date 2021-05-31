package com.hills.mcs_02.networkclasses.interfacesPack;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface QueryRequestTaskDetail {
    /** Get task status: 1. Not Accept. 2.Accept. 3.Finish */
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/user_task/getStatus")
    Call<ResponseBody> checkUserTask(@Body RequestBody userTask);
}
