package com.hills.mcs_02.networkclasses.interfacesPack;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/** Get the location of task */
public interface GetRequestMapTaskLoc {
	@GET("task/getLoc") Call<ResponseBody> getCall();
}