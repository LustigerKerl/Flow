package com.example.lwh.project_school.Activity.Login;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lwh.ProjectSchool.R;
import com.example.lwh.project_school.Activity.LoadActivity;
import com.example.lwh.project_school.Activity.Login.Request.RequestBody;
import com.example.lwh.project_school.Activity.Login.Response.ResponseBody;
import com.example.lwh.project_school.Activity.Notice.NoticeDetail.NoticeDetailActivity;
import com.example.lwh.project_school.Activity.Regist.RegistActivity;
import com.example.lwh.project_school.Activity.Result.ResultActivity;
import com.example.lwh.project_school.DataBase.DatabaseHelper;
import com.example.lwh.project_school.NetWork.RetroService;
import com.google.firebase.iid.FirebaseInstanceId;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private DatabaseHelper myDb;
    private EditText etEmail;
    private EditText etPassword;
    private Pattern ptEmail = Pattern.compile("(dgsw.hs.kr)");
    private Pattern ptPassword = Pattern.compile("[a-zA-Z0-9]{8,16}(?=.*[~`!@#$%^&*()-])");
    private Matcher m;
    private RequestBody requestBody;
    private boolean activityChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent getIntent = getIntent();
        requestBody = new RequestBody();
        myDb = new DatabaseHelper(this);
        if (getIntent != null && getIntent.getStringExtra("type") != null) {
            fcmBackgroundControl(getIntent);
        }

        etEmail = findViewById(R.id.etEmail);
        if (getIntent.getStringExtra("email") != null) {
            Log.d("test", getIntent.getStringExtra("email"));
            etEmail.setText(getIntent.getStringExtra("email"));
        }
        etPassword = findViewById(R.id.etPassword);
        Cursor res = myDb.getAllData("token_table2");
        res.moveToLast();

        if (res.getCount() != 0 && !activityChanged) {
            Intent intent = new Intent(getApplicationContext(), LoadActivity.class);
            intent.putExtra("name", requestBody.getEmail());
            startActivity(intent);
            finish();
        }
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegist = findViewById(R.id.btnRegist);
        btnRegist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                m = ptEmail.matcher(etEmail.getText().toString());
                if (etEmail.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Email을 입력하세요!", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }

                if (m.find()) {
                    m = ptPassword.matcher(etPassword.getText().toString());
                } else {
                    Toast.makeText(getApplicationContext(), "DGSW 이메일이 아닙니다!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (etPassword.getText().toString().length() == 0) {
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }
                if (m.find() != true) {
                    Toast.makeText(getApplicationContext(), "비밀번호 양식을 맞춰주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                requestBody.setEmail(etEmail.getText().toString());
                requestBody.setPw(SHA256(etPassword.getText().toString()));
                requestBody.setRegistration_token(FirebaseInstanceId.getInstance().getToken());
                RetroService retroService = new RetroService(getApplicationContext(), false);
                retroService.getSignInService().signIn(requestBody).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody responseBody = response.body();
                        Log.d("Login TEst", responseBody.toString());
                        if (responseBody.getStatus() == 200)
                            exceptionCatcher(responseBody.getStatus(), responseBody.getData().getToken());
                        else
                            exceptionCatcher(responseBody.getStatus(), null);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "서버에서 응답을 받지 못했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void exceptionCatcher(int code, String token) {
        if (code == 200 && token != null) {
            Toast.makeText(getApplicationContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();
            if (myDb.insertData("token_table2", token, null)) {
                Intent intent = new Intent(getApplicationContext(), LoadActivity.class);
                deleteLastToken();
                intent.putExtra("name", requestBody.getEmail());
                startActivity(intent);
                myDb.close();
                finish();
                Toast.makeText(this, "DB에 토큰을 저장했습니다.", Toast.LENGTH_SHORT).show();
            } else Toast.makeText(this, "DB에 토큰을 저장하지 못했습니다.", Toast.LENGTH_SHORT).show();
        } else if (code == 400)
            Toast.makeText(getApplicationContext(), "입력 양식을 지켜주세요", Toast.LENGTH_SHORT).show();
        else if (code == 401)
            Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
        else if (code == 500)
            Toast.makeText(getApplicationContext(), "서버 오류.", Toast.LENGTH_SHORT).show();
    }

    private void deleteLastToken() {
        Cursor res = myDb.getAllData("token_table2");
        res.moveToFirst();
        int i = res.getInt(0);
        res.moveToLast();
        for (; i < res.getInt(0); i++) {
            myDb.deleteData(Integer.toString(i), "token_table2");
        }
        myDb.close();
    }

    private void viewAllToken() {
        Cursor res = myDb.getAllData("token_table2");
        if (res.getCount() == 0) {
            return;
        }
        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("ID :" + res.getInt(0) + "\n");
            buffer.append("TOKEN :" + res.getString(1) + "\n");
        }
        Log.d("DATA", buffer.toString());
        myDb.close();
    }

    public String SHA256(String str) {

        String SHA = "";

        try {

            MessageDigest sh = MessageDigest.getInstance("SHA-256");

            sh.update(str.getBytes());

            byte byteData[] = sh.digest();

            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < byteData.length; i++) {

                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));

            }

            SHA = sb.toString();


        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();

            SHA = null;

        }

        return SHA;

    }

    private void fcmBackgroundControl(Intent getIntent) {
        Intent intent;
        Log.d("test-type", getIntent.getStringExtra("type"));
        Log.d("test-idx", getIntent.getStringExtra("idx"));
        switch (Objects.requireNonNull(getIntent.getStringExtra("type"))) {
            case "sleep_out":
                intent = new Intent(getApplicationContext(), ResultActivity.class);
                Log.d("test-type3", getIntent.getStringExtra("type"));
                myDb.updateData("sleep_out_table", "1", getIntent.getStringExtra("idx"));
                startActivity(intent);
                Log.d("test-debug=hi", "hi");
                activityChanged = true;
                finish();
                break;
            case "go_out":
                intent = new Intent(getApplicationContext(), ResultActivity.class);
                Log.d("test-type3", getIntent.getStringExtra("type"));
                myDb.updateData("go_out_table", "1", getIntent.getStringExtra("idx"));
                startActivity(intent);
                Log.d("test-debug=hi", "hi");
                activityChanged = true;
                finish();
                break;
            case "notice":
                intent = new Intent(getApplicationContext(), NoticeDetailActivity.class);
                Log.d("test-type3", getIntent.getStringExtra("type"));
                Log.d("test-idx23", getIntent.getStringExtra("idx"));
                intent.putExtra("idx", getIntent.getStringExtra("idx"));
                startActivity(intent);
                Log.d("test-debug=hi", "hi");
                activityChanged = true;
                finish();
                break;
        }
    }
}
