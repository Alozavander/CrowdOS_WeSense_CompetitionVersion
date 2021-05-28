package com.hills.mcs_02.networkClasses.interfacesPack;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/** Get the all accepted tasks of the user */
public interface PostRequestMineMinor2Accepted {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/task/getUserAllAcceptTaskFromUT/{userId}")
    Call<ResponseBody> queryAccepted(@Path("userId") Integer userId);
}
