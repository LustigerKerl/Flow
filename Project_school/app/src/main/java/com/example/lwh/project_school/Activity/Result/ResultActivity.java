package com.example.lwh.project_school.Activity.Result;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.lwh.ProjectSchool.R;
import com.example.lwh.project_school.DataBase.DatabaseHelper;

public class ResultActivity extends AppCompatActivity {
    DatabaseHelper da;
    TextView tvResTitle;
    Cursor res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Button btnClear=findViewById(R.id.btnClear);
        da = new DatabaseHelper(this);
        res = da.selectQuery("go_out_table","ACCEPT is 1");
        tvResTitle = findViewById(R.id.tvResTitle);
        abc();
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
                res = da.getAllData("out_table4");
                textSetting();
            }
        });

    }

    private void abc() {
       /* textView3.setText("");
        res.moveToFirst();
        if (res.getCount() == 0) {
            textView3.setText("데이터가 없습니다!");
        } else {
            do {
                textView3.setText(textView3.getText() +
                        res.getString(1)
                        + res.getString(2)
                        + res.getString(3));
            }
            while (res.moveToNext());
        }*/
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
        res=da.selectQuery("sleep_out_table","ACCEPT is 1");
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
        if(sb.length()==0) {
            tvResTitle.setText("외출, 외박증이 없어요 ㅠㅠ");
            return;
        }
        tvResTitle.setText(sb);
    }

    private void textSetting() {
        if (res.getCount() == 0) {
            tvResTitle.setText("외출,외박증이 없어요 ㅠㅠ");
            return;
        }
        res.moveToLast();
        do {
            tvResTitle.setText(tvResTitle.getText() + res.getString(1) + "증 \n\n" +
                    res.getString(4) + " 부터\n" +
                    res.getString(5) + " 까지\n\n---------------------------------------\n");
        } while (res.moveToPrevious());

    }

    private void delete() {
        da.delete("out_table4");
    }
}
