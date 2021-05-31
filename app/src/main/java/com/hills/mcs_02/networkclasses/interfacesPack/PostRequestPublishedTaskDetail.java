package com.hills.mcs_02.networkclasses.interfacesPack;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/** Get the  published task detail */
public interface PostRequestPublishedTaskDetail {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/user_task/getTaskIdToUserName/{taskId}")
    Call<ResponseBody> checkUserTaskWithUsername(@Path("taskId") Integer taskId);
}
