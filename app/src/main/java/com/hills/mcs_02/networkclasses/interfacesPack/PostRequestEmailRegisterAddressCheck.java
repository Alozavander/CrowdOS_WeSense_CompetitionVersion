package com.hills.mcs_02.networkclasses.interfacesPack;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/** Register the account using Email */
public interface PostRequestEmailRegisterAddressCheck {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("user/checkLogin")
    Call<ResponseBody> userRegister(@Body RequestBody userInfo);
}
