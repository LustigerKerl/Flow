package com.example.lwh.project_school.Activity.Food;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.lwh.project_school.R;
import com.example.lwh.project_school.NetWork.NetWorkWorld;
import com.example.lwh.project_school.Service.GetEveryTime;
import org.hyunjun.school.SchoolMenu;
import java.util.List;


public class FoodActivity extends AppCompatActivity implements NetWorkWorld.TaskDelegate {
    private GetEveryTime gA = new GetEveryTime();
    public int year = 2018, nowMon = gA.getTime("mon"),
               day = gA.getTime("day"), time = 1,
               nowTime = gA.getTime("hour") * 100 + gA.getTime("min");   //Make the time Like this 2203, 1238
    private List<SchoolMenu> menu = null;
    private TextView timeView, tvFoodMenu;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        tvFoodMenu = findViewById(R.id.tvFoodMenu);             //Binding Section
        Button breakButton = findViewById(R.id.breakButton);
        Button lunchButton = findViewById(R.id.lunchButton);
        Button dinnerButton = findViewById(R.id.dinnerButton);
        timeView = findViewById(R.id.TimeView);
        Button getCalendar = findViewById(R.id.getCalendar);
        progressBar = findViewById(R.id.progressBar);           //End of Binding

        breakButton.setOnClickListener(btnClickListener);       //Set Listener Section
        lunchButton.setOnClickListener(btnClickListener);
        dinnerButton.setOnClickListener(btnClickListener);
        getCalendar.setOnClickListener(btnClickListener);       //End of Set Listener

        cmpTime();                                              //Initializing Methods Section
        setTimeViewText();
        startAsync();                                           //End of Initializing
    }

    private DatePickerDialog.OnDateSetListener setDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            if (nowMon != month + 1) {
                setDate(year, month + 1, day);
                startAsync();
            }
            setDate(year, month + 1, day);
            setMenu();
            setTimeViewText();
        }
    };

    public void setDate(int year, int month, int day) {
        this.year = year;
        this.nowMon = month;
        this.day = day;
    }

    public void startAsync() {
        NetWorkWorld netWorkWorld = new NetWorkWorld();
        netWorkWorld.delegate = this;
        netWorkWorld.execute(year, nowMon, day);
        tvFoodMenu.setText("불러오는중 ...");
        progressBar.setVisibility(View.VISIBLE);
    }

    public void cmpTime() {
        if (nowTime < 720) time = 1;
        else if (nowTime < 1240) time = 2;
        else if (nowTime < 1840) time = 3;
        else {
            time = 1;
            day += 1;
        }
    }

    @Override
    public void getMenu(List<SchoolMenu> success) {
        menu = success;
        progressBar.setVisibility(View.INVISIBLE);
        setMenu();
    }

    public void setMenu() {
        switch (time) {
            case 1:
                tvFoodMenu.setText(menu.get(day - 1).breakfast);
                break;

            case 2:
                tvFoodMenu.setText(menu.get(day - 1).lunch);
                break;

            case 3:
                tvFoodMenu.setText(menu.get(day - 1).dinner);
                break;
        }
    }

    private void setTimeViewText() {
        switch (time) {
            case 1:
                timeView.setText(String.format("%d년 %d월 %d일 아침", year, nowMon, day));
                break;

            case 2:
                timeView.setText(String.format("%d년 %d월 %d일 점심", year, nowMon, day));
                break;

            case 3:
                timeView.setText(String.format("%d년 %d월 %d일 저녁", year, nowMon, day));
                break;
        }
    }

    private Button.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.breakButton:
                    time = 1;
                    setTimeViewText();
                    setMenu();
                    break;
                case R.id.lunchButton:
                    time = 2;
                    setTimeViewText();
                    setMenu();
                    break;
                case R.id.dinnerButton:
                    time = 3;
                    setTimeViewText();
                    setMenu();
                    break;
                case R.id.getCalendar:
                    new DatePickerDialog(FoodActivity.this, setDate,
                            year, nowMon - 1, day).show();
                    break;
            }
        }
    };
}
