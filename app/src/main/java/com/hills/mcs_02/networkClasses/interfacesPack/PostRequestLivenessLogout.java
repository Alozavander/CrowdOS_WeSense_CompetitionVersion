package com.hills.mcs_02.networkClasses.interfacesPack;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PostRequestLivenessLogout {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("liveness/logoutUser")
    Call<ResponseBody> livenessLogout(@Body RequestBody livenessInfo);
}
