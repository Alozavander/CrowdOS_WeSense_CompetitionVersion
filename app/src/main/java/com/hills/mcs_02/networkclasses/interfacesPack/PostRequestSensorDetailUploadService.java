package com.hills.mcs_02.networkclasses.interfacesPack;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/** Upload File */
public interface PostRequestSensorDetailUploadService {
    @Multipart
    @POST("/uploadSensorFileMessageDetail")
    Call<ResponseBody> uploadSensorMessage(@Part("sensor_detail") RequestBody sensorDetail, @Part MultipartBody.Part file);
}
