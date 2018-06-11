package com.example.lwh.project_school.NetWork.Interfaces;

import com.example.lwh.project_school.Activity.Login.Request.RequestBody;
import com.example.lwh.project_school.Activity.Login.Response.ResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignInService {
    @POST("/auth/signin")
    Call<ResponseBody> signIn(@Body RequestBody requestBody);

}
