package com.hills.mcs_02.networkclasses.interfacesPack;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/** Check the new version of the app */
public interface PostRequestMineMinor7Update {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/version_updating/checkVersion/{versionCode}")
    Call<ResponseBody> queryPublished(@Path("versionCode") int versionCode);
}


