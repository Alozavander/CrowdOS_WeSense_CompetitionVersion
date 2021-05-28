package com.hills.mcs_02.networkClasses.interfacesPack;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/** Get the coins rank level of the user */
public interface GetRequestUserCoinsRankList {
    //Retrofit 网络请求接口，GET里的为服务器的URL
    @GET("/user/getUserRank") Call<ResponseBody> getCall();
}
