package com.example.lwh.project_school.Activity.Food;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lwh.ProjectSchool.R;
import com.example.lwh.project_school.NetWork.NetWorkWorld;
import com.example.lwh.project_school.Service.GetAnything;

import org.hyunjun.school.SchoolMenu;

import java.util.List;


public class FoodActivity extends AppCompatActivity implements NetWorkWorld.TaskDelegate{
    private GetAnything gA=new GetAnything();
    public int year= 2018, nowMon =gA.getTime("mon");
    private int day=gA.getTime("day"),time=1;
    private int nowTime=gA.getTime("hour")*100+gA.getTime("min");//시간을 24시간 형태로 만듦
    private List<SchoolMenu> menu=null;
    private TextView timeView,text;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        cmpTime();
        text= findViewById(R.id.tvUserEmail);
        Button breakButton= findViewById(R.id.breakButton);
        Button lunchButton= findViewById(R.id.lunchButton);
        Button dinnerButton= findViewById(R.id.dinnerButton);
        timeView= findViewById(R.id.TimeView);
        Button getCalendar= findViewById(R.id.getCalendar);
        progressBar=findViewById(R.id.progressBar);

        setTimeViewText();

        startAsync(null);

        breakButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time=1; setTimeViewText(); setMenu();
            }
        });
        lunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time=2; setTimeViewText(); setMenu();

            }
        });
        dinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time=3; setTimeViewText(); setMenu();

            }
        });
        getCalendar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                new DatePickerDialog(FoodActivity.this, setDate,
                        year, nowMon -1,day).show();
            }
        });
    }

    private DatePickerDialog.OnDateSetListener setDate=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            if(nowMon!=month+1){
                setDate(year,month+1,day);
                startAsync(null);
            }
            setDate(year,month+1,day);
            setMenu();
            setTimeViewText();
        }
    };

    public void setDate(int year,int month,int day){
        this.year=year;
        this.nowMon =month;
        this.day=day;
    }
    public void startAsync(View view){
        NetWorkWorld netWorkWorld=new NetWorkWorld();
        netWorkWorld.delegate=this;
        netWorkWorld.execute(year, nowMon,day);
        progressBar.setVisibility(View.VISIBLE);

    }
    public void cmpTime(){
      if(nowTime < 720) time=1;
      else if(nowTime < 1240) time = 2;
      else if(nowTime<1840) time= 3;
      else {time=1;day+=1;}
    }

    @Override
    public void getMenu(List<SchoolMenu> success) {
        menu=success;
        progressBar.setVisibility(View.INVISIBLE);
        setMenu();
    }
     public  void setMenu(){
        switch (time){
            case 1:
                text.setText(menu.get(day-1).breakfast);
            break;

            case 2:
                text.setText(menu.get(day-1).lunch);
            break;

            case 3:
                text.setText(menu.get(day-1).dinner);
            break;
        }
    }
    private  void setTimeViewText(){
        switch (time){
            case 1:
                timeView.setText(year+"년 "+ nowMon +"월 "+day+"일 아침");
                break;

            case 2:
                timeView.setText(year+"년 "+ nowMon +"월 "+day+"일 점심");
                break;

            case 3:
                timeView.setText(year+"년 "+ nowMon +"월 "+day+"일 저녁");
                break;
        }
    }

}
