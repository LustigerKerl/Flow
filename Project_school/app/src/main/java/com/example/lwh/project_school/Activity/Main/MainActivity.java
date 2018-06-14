package com.example.lwh.project_school.Activity.Main;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lwh.project_school.Activity.Food.FoodActivity;
import com.example.lwh.project_school.Activity.Login.LoginActivity;
import com.example.lwh.project_school.Activity.Notice.NoticeList.NoticeListActivity;
import com.example.lwh.project_school.Activity.Out.OutActivity;
import com.example.lwh.project_school.Activity.Result.ResultActivity;
import com.example.lwh.project_school.DataBase.DatabaseHelper;
import com.example.lwh.project_school.R;

public class MainActivity extends AppCompatActivity {
    private long backPressedTime = 0;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);                            //Instantiation

        Toolbar toolbar = findViewById(R.id.toolbar);                       //Binding Section
        TextView tvNameView = findViewById(R.id.tvNameView);
        Button btnShowOut = findViewById(R.id.btnShowOut);
        Button btnShowFood = findViewById(R.id.btnShowFood);
        Button btnShowOutRes = findViewById(R.id.btnShowOutRes);
        Button btnShowNotice = findViewById(R.id.btnShowNotice);            //End of Binding

        btnShowOut.setOnClickListener(btnClickListener);                    //Initializing Section
        btnShowFood.setOnClickListener(btnClickListener);
        btnShowOutRes.setOnClickListener(btnClickListener);
        btnShowNotice.setOnClickListener(btnClickListener);
        tvNameView.setText(setNameText());
        toolbar.setTitleTextColor(Color.parseColor("#d7d7d7"));
        setSupportActionBar(toolbar);                                       //End of Initializing
    }

    @Override
    public void onBackPressed() {
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;
        if (0 <= intervalTime && 2000 >= intervalTime) {
            super.onBackPressed();
        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "뒤로가기를 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                myDb.delete("token_table");
                Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    private Button.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId()) {
                case R.id.btnShowOut:
                    intent = new Intent(getApplicationContext(), OutActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btnShowFood:
                    intent = new Intent(getApplicationContext(), FoodActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btnShowOutRes:
                    intent = new Intent(getApplicationContext(), ResultActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btnShowNotice:
                    intent = new Intent(getApplicationContext(), NoticeListActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };
    private String setNameText(){
        Cursor res=myDb.getAllData("token_table");
        res.moveToLast();
        return String.format("%s님 로그인을 환영합니다.", res.getString(2));
    }
}
