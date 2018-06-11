package com.example.lwh.project_school.Activity.Result;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.lwh.project_school.Adapter.RecyclerAdapter;
import com.example.lwh.project_school.Adapter.RecyclerItem;
import com.example.lwh.project_school.DataBase.DatabaseHelper;
import com.example.lwh.project_school.R;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    private DatabaseHelper da;
    private Cursor res;
    private RecyclerView recyclerView;
    RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        da = new DatabaseHelper(this);
        res = da.selectQuery("go_out_table", "ACCEPT is 1");
        initLayout();
        setRecyclerView();
        adapter.clear();
        adapter.addRecyclerItems(initData());
        adapter.notifyDataSetChanged();
    }

    private void initLayout() {
        recyclerView = findViewById(R.id.resultRecycle);
    }

    private void setRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapter(getApplicationContext(), "result");
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    private ArrayList<RecyclerItem> initData() {

        ArrayList<RecyclerItem> items = new ArrayList<>();
        if(res.getCount()!=0){
            res.moveToLast();
            do {
                RecyclerItem recyclerItem=new RecyclerItem();
                recyclerItem.setType("외출");
                recyclerItem.setStart_date(res.getString(3));
                recyclerItem.setEnd_date(res.getString(4));
                recyclerItem.setReason(res.getString(5));
                items.add(recyclerItem);
            } while (res.moveToPrevious());
        }
        res=da.selectQuery("sleep_out_table","ACCEPT is 1");
        if(res.getCount()!=0){
            res.moveToLast();
            do {
                RecyclerItem recyclerItem=new RecyclerItem();
                recyclerItem.setType("외박");
                recyclerItem.setStart_date(res.getString(3));
                recyclerItem.setEnd_date(res.getString(4));
                recyclerItem.setReason(res.getString(5));
                items.add(recyclerItem);
            } while (res.moveToPrevious());
        }
        return items;
    }

    /*private void abc() {
        StringBuilder sb = new StringBuilder();
        if (res.getCount() != 0) {
            res.moveToLast();
            do {
                sb.append("외출증 \n\n" + "번호 " + res.getInt(2) + "\n");
                sb.append(res.getString(3) + " 부터\n");
                sb.append(res.getString(4) + " 까지\n");
                sb.append("사유" + res.getString(5) + "\n\n---------------------------------------\n");
            } while (res.moveToPrevious());//외출 표시 DB 완료 여기까지함.
        }
        res = da.selectQuery("sleep_out_table", "ACCEPT is 1");
        if (res.getCount() != 0) {
            sb.append("\n\n\n");
            res.moveToLast();
            do {
                sb.append("외박증 \n\n" + "번호 " + res.getInt(2) + "\n");
                sb.append(res.getString(3) + " 부터\n");
                sb.append(res.getString(4) + " 까지\n");
                sb.append("사유" + res.getString(5) + "\n\n---------------------------------------\n");
            } while (res.moveToPrevious());
        }
        if (sb.length() == 0) {
            tvResTitle.setText("외출, 외박증이 없어요 ㅠㅠ");
            return;
        }
        tvResTitle.setText(sb);
    }*/ //Text design Ver.

}
