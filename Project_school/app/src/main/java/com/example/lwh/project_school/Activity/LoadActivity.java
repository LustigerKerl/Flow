package com.example.lwh.project_school.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lwh.project_school.Activity.Food.FoodActivity;
import com.example.lwh.project_school.Activity.Notice.NoticeList.NoticeListActivity;
import com.example.lwh.project_school.Activity.Out.OutActivity;
import com.example.lwh.project_school.Activity.Result.ResultActivity;
import com.example.lwh.project_school.R;

public class LoadActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        Intent intent = getIntent();
        TextView tvNameView = findViewById(R.id.tvNameView);
        Button btnShowOut = findViewById(R.id.btnShowOut);
        Button btnShowFood = findViewById(R.id.btnShowFood);
        Button btnShowOutRes = findViewById(R.id.btnShowOutRes);
        Button btnShowNotice = findViewById(R.id.btnShowNotice);
        tvNameView.setText(intent.getStringExtra("name"));
        btnShowOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OutActivity.class);
                startActivity(intent);
            }
        });

        btnShowFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FoodActivity.class);
                startActivity(intent);
            }
        });

        btnShowOutRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                startActivity(intent);
            }
        });
        btnShowNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NoticeListActivity.class);
                startActivity(intent);
            }
        });


    }
}
