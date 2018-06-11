package com.example.lwh.project_school.Service;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService{

    @Override
    public void onTokenRefresh(){
        String refreshedToken= FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refreshedToken);
    }
    private void sendRegistrationToServer(String token){
        OkHttpClient client=new OkHttpClient();

        RequestBody body = new FormBody.Builder()
                .add("Token", token)
                .build();
        Request request= new Request.Builder()
                .url("http://flow.cafe24app.com/")
                .post(body)
                .build();
        try {
            client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
