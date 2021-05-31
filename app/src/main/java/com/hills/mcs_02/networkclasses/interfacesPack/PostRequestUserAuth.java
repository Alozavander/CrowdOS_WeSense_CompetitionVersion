package com.hills.mcs_02.networkclasses.interfacesPack;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/** Authentication */
public interface PostRequestUserAuth {
    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("user/enterUser")                                                //Post目标地址
    Call<ResponseBody> userLogin(@Body RequestBody userInfo);
}
