package com.hills.mcs_02.networkclasses.interfacesPack;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface PostRequestLivenessLogin {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("liveness/enterLiveness")
    Call<ResponseBody> livenessLogin(@Body RequestBody livenessInfo);
}
