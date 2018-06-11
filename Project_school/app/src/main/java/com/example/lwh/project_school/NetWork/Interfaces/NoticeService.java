package com.example.lwh.project_school.NetWork.Interfaces;


import com.example.lwh.project_school.Activity.Notice.NoticeDetail.Response.ResponseBody;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NoticeService {
    @GET("/notice/{idx}")
    Call<ResponseBody> noticeDetail(@Path("idx")String idx);

    @GET("/notice")
    Call<com.example.lwh.project_school.Activity.Notice.NoticeList.Response.ResponseBody> noticeList();
}
