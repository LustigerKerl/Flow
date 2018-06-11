package com.example.lwh.project_school.Activity.Notice.NoticeList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.lwh.ProjectSchool.R;
import com.example.lwh.project_school.Activity.Notice.NoticeList.Response.ResponseBody;
import com.example.lwh.project_school.Adapter.RecyclerAdapter;
import com.example.lwh.project_school.Adapter.RecyclerItem;
import com.example.lwh.project_school.NetWork.RetroService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    ResponseBody responseBody;
    ArrayList<RecyclerItem> items=new ArrayList<>();
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        initLayout();
        RetroService retroService = new RetroService(getApplicationContext(), true);
        retroService.getNoticeService().noticeList().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                responseBody = response.body();
                Log.d("hi", responseBody.toString());
                initData();
                setRecyclerView();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(NoticeListActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }
    private void initLayout(){
        recyclerView=findViewById(R.id.recyclerView);
    }

    private void initData(){

        for(int i=0;i<responseBody.getData().getList().length;i++){
            RecyclerItem recyclerItem=new RecyclerItem();
            recyclerItem.setContent(responseBody.getData().getList()[i].getContent());
            recyclerItem.setIdx(responseBody.getData().getList()[i].getIdx());
            recyclerItem.setWriter(responseBody.getData().getList()[i].getWriter());
            items.add(recyclerItem);
        }


    }
    private void setRecyclerView(){
        LinearLayoutManager layoutManager=new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new RecyclerAdapter(getApplicationContext(),items);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }



}
