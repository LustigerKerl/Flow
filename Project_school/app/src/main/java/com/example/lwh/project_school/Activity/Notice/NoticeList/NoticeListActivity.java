package com.example.lwh.project_school.Activity.Notice.NoticeList;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.lwh.project_school.Activity.Notice.NoticeList.Response.ResponseBody;
import com.example.lwh.project_school.Adapter.RecyclerAdapter;
import com.example.lwh.project_school.Adapter.RecyclerItem;
import com.example.lwh.project_school.NetWork.RetroService;
import com.example.lwh.project_school.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    ResponseBody responseBody;
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);


        final SwipeRefreshLayout refreshLayout = findViewById(R.id.swifeLayout);
        initLayout();
        setRecyclerView();
        networkWork();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                networkWork();
                refreshLayout.setRefreshing(false);
            }
        });


    }

    private void initLayout() {
        recyclerView = findViewById(R.id.recyclerView);
    }

    private ArrayList<RecyclerItem> initData() {

        ArrayList<RecyclerItem> items = new ArrayList<>();
        for (int i = 0; i < responseBody.getData().getList().length; i++) {
            RecyclerItem recyclerItem = new RecyclerItem();
            recyclerItem.setContent(responseBody.getData().getList()[i].getContent());
            recyclerItem.setIdx(responseBody.getData().getList()[i].getIdx());
            recyclerItem.setWriter(responseBody.getData().getList()[i].getWriter());
            items.add(recyclerItem);
        }
        return items;
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(getApplicationContext(),"notice");
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void networkWork() {
        RetroService retroService = new RetroService(getApplicationContext(), true);
        retroService.getNoticeService().noticeList().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                responseBody = response.body();
                Log.d("hi", responseBody.toString());
                adapter.clear();
                adapter.addRecyclerItems(initData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(NoticeListActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


}
