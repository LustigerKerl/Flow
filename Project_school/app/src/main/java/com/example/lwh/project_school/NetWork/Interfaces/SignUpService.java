package com.example.lwh.project_school.NetWork.Interfaces;

import com.example.lwh.project_school.Activity.Regist.Request.RequestBody;
import com.example.lwh.project_school.Activity.Regist.Response.ResponseBody;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpService {
    @POST("auth/signup")
    Call<ResponseBody> signUp(@Body RequestBody requestBody);

}
