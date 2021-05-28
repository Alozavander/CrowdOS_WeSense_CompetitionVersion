package com.hills.mcs_02.networkClasses.interfacesPack;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

/** Get task list whose tag is taskKingTag */
public interface PostRequestGridPageTaskList {
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("/task/getOrderTaskFromKind/{taskKind}")
    Call<ResponseBody> getCall(@Path("taskKind") int taskKindTag);
}
