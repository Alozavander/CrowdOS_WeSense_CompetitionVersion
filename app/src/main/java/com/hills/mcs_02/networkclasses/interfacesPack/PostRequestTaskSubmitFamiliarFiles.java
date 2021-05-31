package com.hills.mcs_02.networkclasses.interfacesPack;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/** Upload the sensor data */
public interface PostRequestTaskSubmitFamiliarFiles {
    @Multipart
    @POST("/sensor/uploadSensorFiles")
    Call<ResponseBody> taskSubmit(@Part("familiar_sensor") RequestBody familiarSensorInfo, @Part MultipartBody.Part file);
}
