package com.example.lwh.project_school.NetWork;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.lwh.project_school.DataBase.DatabaseHelper;
import com.example.lwh.project_school.NetWork.Interfaces.NoticeService;
import com.example.lwh.project_school.NetWork.Interfaces.OutService;
import com.example.lwh.project_school.NetWork.Interfaces.SignInService;
import com.example.lwh.project_school.NetWork.Interfaces.SignUpService;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroService {

    private Retrofit retrofit = null;

    private static final String SERVER_URL = "http:/flow.cafe24app.com/";

    private String token="";

    public RetroService(Context context,Boolean needToken) {
        DatabaseHelper myDb = new DatabaseHelper(context);
        if(needToken) {
            token = myDb.getToken();
            myDb.close();
            Log.d("test32423423",token);
        }
        else token="";
    }
    private Retrofit getClient() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request request = chain
                        .request()
                        .newBuilder()
                        .addHeader("x-access-token", token)
                        .build();
                return chain.proceed(request);
            }
        });

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(SERVER_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.build())
                    .build();
        }

        return retrofit;
    }

    public SignInService getSignInService() {
        return getClient().create(SignInService.class);
    }

    public SignUpService getSignUpService() {
        return getClient().create(SignUpService.class);
    }

    public OutService getOutService() {
        return getClient().create(OutService.class);
    }

    public NoticeService getNoticeService() {
        return getClient().create(NoticeService.class);
    }
}
