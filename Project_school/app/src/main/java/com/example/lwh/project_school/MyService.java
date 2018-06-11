package com.example.lwh.project_school;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.example.lwh.project_school.Activity.Out.Request.RequestBody;
import com.example.lwh.project_school.Activity.Out.Response.ResponseBody;
import com.example.lwh.project_school.DataBase.DatabaseHelper;
import com.example.lwh.project_school.NetWork.RetroService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyService extends Service {
    RetroService retroService = null;
    private int mode = 0;
    RequestBody request = null;
    DatabaseHelper myDb;

    public MyService(Intent intent, RequestBody requestBody) {
        mode = intent.getIntExtra("mode", 999);
        request = requestBody;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myDb = new DatabaseHelper(this);
        retroService = new RetroService(getApplicationContext(),true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("test-332", "started");
        if (mode == 0) {
            retroService.getOutService().outGo(request).enqueue(
                    new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            ResponseBody responseBody = response.body();
                            Log.d("test-333", responseBody.toString());
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    }
            );
        } else if (mode == 1) {
            retroService.getOutService().outSleep(request).enqueue(
                    new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            ResponseBody responseBody = response.body();
                            Log.d("test-334", responseBody.toString());
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {

                        }
                    }
            );
        }
        return super.onStartCommand(intent, flags, startId);
    }
}
