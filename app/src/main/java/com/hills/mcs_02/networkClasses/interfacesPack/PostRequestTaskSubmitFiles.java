package com.hills.mcs_02.networkClasses.interfacesPack;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/** Upload Image */
public interface PostRequestTaskSubmitFiles {
    @Multipart
    @POST("/user_task/uploadImage")                                                //Post目标地址
    Call<ResponseBody> taskSubmit(@Part("utask") RequestBody userTaskInfo,@Part MultipartBody.Part file);
}
