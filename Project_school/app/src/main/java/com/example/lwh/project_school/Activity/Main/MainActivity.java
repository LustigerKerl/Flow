package com.example.lwh.project_school.Activity.Main;

import android.content.Context;
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

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private long backPressedTime = 0;
    DatabaseHelper myDb;
    public static Context mContext;
    public static TextView badge_noti1,badge_noti2;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.tvNameView)
    TextView tvNameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();

    }

    public void init() {
        myDb = new DatabaseHelper(this);                            //Instantiation

        badge_noti1 = findViewById(R.id.badge_noti1);
        badge_noti2 = findViewById(R.id.badge_noti2);         //End of Binding

        tvNameView.setText(setNameText());
        toolbar.setTitleTextColor(Color.parseColor("#d7d7d7"));

        mContext = this;
        badge_noti2.setVisibility(View.INVISIBLE);
        badge_noti1.setVisibility(View.INVISIBLE);
        setSupportActionBar(toolbar);
        LoginActivity.InitializationOnDemandHolderIdiom.getInstance();
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
                changeIntent(this, LoginActivity.class);
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

    @OnClick({R.id.btnShowFood, R.id.btnShowOut, R.id.btnShowOutRes, R.id.btnShowNotice})
    public void btnClick(View view){
        switch (view.getId()) {
            case R.id.btnShowOut:
                changeIntent(getApplicationContext(), OutActivity.class);
                break;
            case R.id.btnShowFood:
                changeIntent(getApplicationContext(), FoodActivity.class);
                break;
            case R.id.btnShowOutRes:
                changeIntent(getApplicationContext(), ResultActivity.class, badge_noti2);
                break;
            case R.id.btnShowNotice:
                changeIntent(getApplicationContext(), NoticeListActivity.class, badge_noti1);
                break;
        }
    }

    private void changeIntent(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }

    private void changeIntent(Context context, Class cls, TextView tv) {
        changeIntent(context, cls);
        tv.setText("0");
        tv.setVisibility(View.INVISIBLE);
    }

    private String setNameText() {
        Cursor res = myDb.getAllData("token_table");
        res.moveToLast();
        return String.format("%s님 환영합니다.", res.getString(2));
    }
}
