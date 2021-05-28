package com.hills.mcs_02.networkClasses.interfacesPack;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PostRequestUserCoin {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/user/getUser/{userId}")
    Call<ResponseBody> userCoins(@Path("userId") Integer userId);
}
