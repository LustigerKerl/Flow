package com.example.lwh.project_school.Activity.Login;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lwh.project_school.Activity.Login.Request.RequestBody;
import com.example.lwh.project_school.Activity.Login.Response.ResponseBody;
import com.example.lwh.project_school.Activity.Main.MainActivity;
import com.example.lwh.project_school.Activity.Notice.NoticeDetail.NoticeDetailActivity;
import com.example.lwh.project_school.Activity.Regist.RegistActivity;
import com.example.lwh.project_school.Activity.Result.ResultActivity;
import com.example.lwh.project_school.DataBase.DatabaseHelper;
import com.example.lwh.project_school.NetWork.RetroService;
import com.example.lwh.project_school.R;
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
    private EditText etEmail,
            etPassword;
    private Pattern ptEmail = Pattern.compile("(dgsw.hs.kr)"),
            ptPassword = Pattern.compile("[a-zA-Z0-9]{8,16}(?=.*[~`!@#$%^&*()-])");
    private RequestBody requestBody;
    private boolean activityChanged = false;
    private RetroService retroService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myDb = new DatabaseHelper(this);                                    //Instantiation Section
        retroService = new RetroService(getApplicationContext(), false);
        requestBody = new RequestBody();                                            //End of Instantiation

        etEmail = findViewById(R.id.etEmail);                                       //Binding Section
        Button btnLogin = findViewById(R.id.btnLogin);
        Button btnRegist = findViewById(R.id.btnRegist);
        etPassword = findViewById(R.id.etPassword);                                 //End of Binding
        preActivityStart();                                                         //Initializing Section
        btnRegist.setOnClickListener(btnClickListener);
        btnLogin.setOnClickListener(btnClickListener);                              //End of Initializing
    }
    public static class InitializationOnDemandHolderIdiom {
        private InitializationOnDemandHolderIdiom () {}
        private static class Singleton {
            private static final InitializationOnDemandHolderIdiom instance = new InitializationOnDemandHolderIdiom();
        }

        public static InitializationOnDemandHolderIdiom getInstance () {
            System.out.println("create instance");
            return Singleton.instance;
        }
    }

    private void exceptionCatcher(int code, String token) {
        if (code == 200 && token != null) {
            Toast.makeText(getApplicationContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();
            delTokenTableData();
            if (myDb.insertData("token_table", token, requestBody.getEmail(), null)) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("name", requestBody.getEmail());
                startActivity(intent);
                myDb.close();
                finish();
            } else Toast.makeText(this, "DB에 토큰을 저장하지 못했습니다.", Toast.LENGTH_SHORT).show();
        } else if (code == 400)
            Toast.makeText(getApplicationContext(), "입력 양식을 지켜주세요", Toast.LENGTH_SHORT).show();
        else if (code == 401)
            Toast.makeText(getApplicationContext(), "아이디 또는 비밀번호가 틀립니다.", Toast.LENGTH_SHORT).show();
        else if (code == 500)
            Toast.makeText(getApplicationContext(), "서버 오류.", Toast.LENGTH_SHORT).show();
    }

    private void delTokenTableData() {
        myDb.delete("token_table");
        myDb.close();
    }

    public String SHA256(String str) {
        String SHA;
        try {
            MessageDigest sh = MessageDigest.getInstance("SHA-256");
            sh.update(str.getBytes());
            byte byteData[] = sh.digest();
            StringBuilder sb = new StringBuilder();
            for (byte aByteData : byteData) {
                sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
            }
            SHA = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            SHA = null;
        }
        return SHA;
    }

    private void fcmBackgroundControl(Intent getIntent) {
        switch (Objects.requireNonNull(getIntent.getStringExtra("type"))) {
            case "sleep_out":
                myDb.updateData("sleep_out_table", "1", getIntent.getStringExtra("idx"));
                activityChanged = true;
                startActivity(changeIntent(this, ResultActivity.class));
                finish();
                break;
            case "go_out":
                myDb.updateData("go_out_table", "1", getIntent.getStringExtra("idx"));
                activityChanged = true;
                startActivity(changeIntent(this, ResultActivity.class));
                finish();
                break;
            case "notice":
                changeIntent(this, NoticeDetailActivity.class, getIntent.getStringExtra("idx"));
                activityChanged = true;
                finish();
                break;
        }
    }

    private Intent changeIntent(Context context, Class cls) {
        return new Intent(context, cls);
    }

    private void changeIntent(Context context, Class cls, String idx) {
        startActivity(changeIntent(context, cls).putExtra("idx", idx));
    }

    private Button.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnRegist:
                    Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                    startActivity(intent);
                    break;
                case R.id.btnLogin:
                    if (!checkFormatException()) return;
                    requestBody.setEmail(etEmail.getText().toString());
                    requestBody.setPw(SHA256(etPassword.getText().toString()));
                    requestBody.setRegistration_token(FirebaseInstanceId.getInstance().getToken());
                    doNetwork();
                    break;
            }
        }
    };

    private boolean checkFormatException() {
        Matcher m = ptEmail.matcher(etEmail.getText().toString());
        if (etEmail.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "Email을 입력하세요!", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return false;
        } else if (m.find()) {
            m = ptPassword.matcher(etPassword.getText().toString());
        } else {
            Toast.makeText(getApplicationContext(), "DGSW 이메일이 아닙니다!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (etPassword.getText().toString().length() == 0) {
            Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return false;
        } else if (!m.find()) {
            Toast.makeText(getApplicationContext(), "비밀번호 양식을 맞춰주세요", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void doNetwork() {
        retroService.getSignInService().signIn(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.body() == null)
                    Toast.makeText(LoginActivity.this, "데이터를 받아오지 못했습니다.", Toast.LENGTH_SHORT).show();
                else if (response.body().getStatus() == 200)
                    exceptionCatcher(response.body().getStatus(), response.body().getData().getToken());
                else
                    exceptionCatcher(response.body().getStatus(), null);
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(LoginActivity.this, "서버에서 응답을 받지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void preActivityStart() {
        Intent getIntent = getIntent();
        Cursor res = myDb.getAllData("token_table");
        res.moveToLast();

        if (getIntent != null) {
            if (getIntent.getStringExtra("type") != null && getIntent.getStringExtra("idx") != null) {
                fcmBackgroundControl(getIntent);
            } else if (getIntent.getStringExtra("email") != null) {
                etEmail.setText(getIntent.getStringExtra("email"));
            }
        }
        if (res.getCount() != 0 && !activityChanged) {      //Auto Login Section
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}