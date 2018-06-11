package com.example.lwh.project_school.Activity.Out;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lwh.project_school.R;
import com.example.lwh.project_school.Activity.Out.Request.RequestBody;
import com.example.lwh.project_school.Activity.Out.Response.ResponseBody;
import com.example.lwh.project_school.DataBase.DatabaseHelper;
import com.example.lwh.project_school.NetWork.RetroService;
import com.example.lwh.project_school.Service.GetAnything;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OutActivity extends AppCompatActivity {

    private GetAnything getAnything = new GetAnything();
    private RetroService retroService = null;
    private EditText etWHy;
    private RequestBody requestBody = new RequestBody();
    private int mode = 0; //0 = mode OutGo 1= mode OutSleep
    private DatabaseHelper myDb;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"),
            formatDay = new SimpleDateFormat("yyyy-MM-dd");
    private Integer now[] = new Integer[5];
    private Date cmpDate[] = new Date[2];
    private TextView tvStartTime, tvEndTime;
    private Integer start_or_end = 0;
    private StringBuilder sb;
    TimePickerDialog timePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out);

        retroService = new RetroService(getApplicationContext(), true);
        getNowTime();
        Button btnStartTime = findViewById(R.id.btnStartTime),
                btnEndTime = findViewById(R.id.btnEndTime),
                btnGetAllow = findViewById(R.id.btnGetAllow);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndTime = findViewById(R.id.tvEndTime);
        etWHy = findViewById(R.id.etWhy);
        final RadioButton rbGoOut = findViewById(R.id.rbGoOut);
        rbGoOut.toggle();
        RadioGroup group = findViewById(R.id.rgGoSleep);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbGoOut:
                        mode = 0;
                        break;
                    case R.id.rbSleepOut:
                        mode = 1;
                        break;
                }
            }
        });


        btnStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_or_end = 0;
                tvStartTime.setText("");
                getNowTime();
                new DatePickerDialog(OutActivity.this, setDate, now[0], now[1] - 1, now[2]).show();
            }
        });

        btnEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_or_end = 1;
                tvEndTime.setText("");
                getNowTime();
                new DatePickerDialog(OutActivity.this, setDate, now[0], now[1] - 1, now[2]).show();
            }
        });

        btnGetAllow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("test-32344", cmpDate[0] + "\n" + cmpDate[1]);
                if (tvEndTime.getText().length() == 0 || tvStartTime.getText().length() == 0) {
                    Toast.makeText(OutActivity.this, "시간을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (cmpDate[0].equals(cmpDate[1])) {
                    Toast.makeText(OutActivity.this, "ㅋ 몇초동안나가게 ", Toast.LENGTH_SHORT).show();
                    return;
                } else if (etWHy.getText().toString().equals("")) {
                    Toast.makeText(OutActivity.this, "사유를 말하지않는자... 죽음을 면치 못할것이다.", Toast.LENGTH_SHORT).show();
                    return;
                } else if (cmpDate[0].after(cmpDate[1])) {
                    Toast.makeText(OutActivity.this, "과거로 가시게요??", Toast.LENGTH_SHORT).show();
                    return;
                } else if (tvEndTime.getText().length() == 0 || tvStartTime.getText().length() == 0) {
                    Toast.makeText(OutActivity.this, "시간을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                requestBody.setReason(etWHy.getText().toString());
                requestBody.setStart_time(format.format(cmpDate[0]));
                requestBody.setEnd_time(format.format(cmpDate[1]));
                if (mode == 0) {
                    retroService.getOutService().outGo(requestBody).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.body() == null) {
                                Toast.makeText(OutActivity.this, "서버 오류입니다!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ResponseBody responseBody = response.body();
                            assert responseBody != null;
                            switch (responseBody.getStatus()) {
                                case 200:
                                    Toast.makeText(OutActivity.this, "외출을 신청했습니다.", Toast.LENGTH_SHORT).show();
                                    createDatabase(getApplicationContext());
                                    myDb.insertData("go_out_table", null, responseBody);
                                    myDb.close();
                                    Log.d("ㅇㄹㄴㅇㄹ", "저장됨");
                                    break;
                                default:
                                    Toast.makeText(OutActivity.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(OutActivity.this, "서버와 통신에 실패했습니다. ERROR CODE : 999", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    retroService.getOutService().outSleep(requestBody).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.body() == null) {
                                Toast.makeText(OutActivity.this, "서버 오류입니다!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            ResponseBody responseBody = response.body();
                            assert responseBody != null;
                            switch (responseBody.getStatus()) {
                                case 200:
                                    Toast.makeText(OutActivity.this, "외박을 신청했습니다.", Toast.LENGTH_SHORT).show();
                                    createDatabase(getApplicationContext());
                                    myDb.insertData("sleep_out_table", null, responseBody);
                                    myDb.close();
                                    break;
                                default:
                                    Toast.makeText(OutActivity.this, responseBody.getMessage(), Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(OutActivity.this, "서버와 통신에 실패했습니다. ERROR CODE : 999", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
    private DatePickerDialog.OnDateSetListener setDate = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            switch (start_or_end) {
                case 0:
                    tvStartTime.setText(String.format("%d년 %d월 %d일 ", year, month + 1, dayOfMonth));
                    break;
                case 1:
                    tvEndTime.setText(String.format("%d년 %d월 %d일 ", year, month + 1, dayOfMonth));
                    break;
            }
            sb = new StringBuilder();
            sb.append(year + "-" + (month + 1) + "-" + dayOfMonth + " ");
            Date wantDate = null;
            Date nowDate = null;
            try {
                wantDate = formatDay.parse(sb.toString());
                nowDate = formatDay.parse(now[0] + "-" + now[1] + "-" + now[2]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            //Log.d("test332",wantDate.toString());
            if (wantDate.before(nowDate)) {
                Toast.makeText(OutActivity.this, "과거로 갈수는 없어요", Toast.LENGTH_SHORT).show();
                return;
            }
            timePickerDialog = new TimePickerDialog(OutActivity.this, setTime, now[3], now[4], true);
            timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == DialogInterface.BUTTON_NEGATIVE) {
                        switch (start_or_end) {
                            case 0:
                                tvStartTime.setText("");
                                break;
                            case 1:
                                tvEndTime.setText("");
                                break;
                        }
                        Toast.makeText(OutActivity.this, "취소되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            timePickerDialog.show();
        }
    };
    private TimePickerDialog.OnTimeSetListener setTime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            sb.append(hourOfDay + ":" + minute);
            Date wantDate = null;
            Date nowDate = null;
            try {
                wantDate = format.parse(sb.toString());
                nowDate = format.parse(now[0] + "-" + now[1] + "-" + now[2] + " " + now[3] + ":" + now[4]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (wantDate.before(nowDate)) {
                Toast.makeText(OutActivity.this, "과거로 갈수는 없어요", Toast.LENGTH_SHORT).show();
                return;
            }
            switch (start_or_end) {
                case 0:
                    tvStartTime.append(hourOfDay + "시 " + minute + "분 부터");
                    try {
                        cmpDate[0] = format.parse(sb.toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
                case 1:
                    tvEndTime.append(hourOfDay + "시 " + minute + "분 까지");
                    try {
                        cmpDate[1] = format.parse(sb.toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    };

    private void createDatabase(Context context) {
        myDb = new DatabaseHelper(context);
    }
    private void getNowTime(){
        now[0] = getAnything.getTime("year");
        now[1] = getAnything.getTime("mon");
        now[2] = getAnything.getTime("day");
        now[3] = getAnything.getTime("hour");
        now[4] = getAnything.getTime("min");
    }

}
