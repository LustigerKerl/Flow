package com.example.lwh.project_school.NetWork.Interfaces;

import com.example.lwh.project_school.Activity.Out.Request.RequestBody;
import com.example.lwh.project_school.Activity.Out.Response.ResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface OutService {
    @POST("/out/go")
    Call<ResponseBody>outGo(@Body RequestBody requestBody);

    @POST("out/sleep")
    Call<ResponseBody>outSleep(@Body RequestBody requestBody);

}
