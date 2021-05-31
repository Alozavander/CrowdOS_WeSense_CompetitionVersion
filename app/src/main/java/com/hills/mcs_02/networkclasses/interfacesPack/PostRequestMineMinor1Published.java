package com.hills.mcs_02.networkclasses.interfacesPack;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/** Get the all published tasks of the user */
public interface PostRequestMineMinor1Published {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/task/getUserAllTask/{userId}")
    Call<ResponseBody> queryPublished(@Path("userId") Integer userId);
}
