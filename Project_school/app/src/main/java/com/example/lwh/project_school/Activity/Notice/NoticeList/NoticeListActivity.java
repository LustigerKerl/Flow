package com.example.lwh.project_school.Activity.Notice.NoticeList;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
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
    ProgressBar bar;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);

        bar = findViewById(R.id.progressBar2);                   //Binding Section
        refreshLayout = findViewById(R.id.swifeLayout);          //End of Binding

        initLayout();                                            //Initializing Section
        setRecyclerView();
        doNetwork();
        refreshLayout.setOnRefreshListener(onRefreshListener);   //End of Initializing
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
        adapter = new RecyclerAdapter(getApplicationContext(), "notice");
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void doNetwork() {
        RetroService retroService = new RetroService(getApplicationContext(), true);
        retroService.getNoticeService().noticeList().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if(response.body()==null){
                    Toast.makeText(NoticeListActivity.this, "데이터를 받아오지 못했습니다! 스크롤하여 새로고침 하세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                responseBody = response.body();
                bar.setVisibility(View.INVISIBLE);
                adapter.clear();
                adapter.addRecyclerItems(initData());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(NoticeListActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private SwipeRefreshLayout.OnRefreshListener onRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            bar.setVisibility(View.VISIBLE);
            doNetwork();
            refreshLayout.setRefreshing(false);
        }
    };


}
