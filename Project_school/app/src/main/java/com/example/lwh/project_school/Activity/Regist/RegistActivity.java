package com.example.lwh.project_school.Activity.Regist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lwh.project_school.Activity.Login.LoginActivity;
import com.example.lwh.project_school.Activity.Regist.Request.RequestBody;
import com.example.lwh.project_school.Activity.Regist.Response.ResponseBody;
import com.example.lwh.project_school.NetWork.RetroService;
import com.example.lwh.project_school.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistActivity extends AppCompatActivity {


    private EditText etEmail, etPassword, etPasswordConfirm, etName, etMobile;
    private Pattern ptEmail = Pattern.compile("(dgsw.hs.kr)");
    private Pattern ptPassword = Pattern.compile("[a-zA-Z0-9]{8,16}(?=.*[~`!@#$%^&*()-])");
    private Spinner spClass, spNumber, spZender;
    RequestBody requestBody = new RequestBody();
    RetroService retroService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        retroService = new RetroService(getApplicationContext(), false);    //Instantiation

        etEmail = findViewById(R.id.etEmail);                                         //Binding Section
        etPassword = findViewById(R.id.etPassword);
        etPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        spClass = findViewById(R.id.spClass);
        spNumber = findViewById(R.id.spNumber);
        spZender = findViewById(R.id.spZender);
        Button btnDone = findViewById(R.id.btnDone);
        Button btnCancel = findViewById(R.id.btnCancel);                              //End of Binding

        etPasswordConfirm.addTextChangedListener(checkSame);                          //Initializing Section
        btnDone.setOnClickListener(btnClickListener);
        btnCancel.setOnClickListener(btnClickListener);                               //End of Initializing
    }

    private void exceptionCatcher(int code) {
        if (code == 200) {
            Toast.makeText(getApplicationContext(), "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra("email", etEmail.getText().toString());
            startActivity(intent);
            finish();
        } else if (code == 409) {
            Toast.makeText(getApplicationContext(), "해당 이메일로 계정이 존재합니다.", Toast.LENGTH_SHORT).show();
        } else if (code == 500) {
            Toast.makeText(getApplicationContext(), "서버 오류.", Toast.LENGTH_SHORT).show();
        }
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

    private boolean checkFormatException() {
        Matcher m = ptEmail.matcher(etEmail.getText().toString());

        if (etEmail.getText().toString().startsWith("@")||etEmail.getText().length()==0) {
            Toast.makeText(RegistActivity.this, "Email을 입력하세요!", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return false;
        } else if (m.find()) {
            m = ptPassword.matcher(etPassword.getText().toString());
        } else {
            Toast.makeText(RegistActivity.this, "DGSW 이메일이 아닙니다!", Toast.LENGTH_SHORT).show();
            etEmail.requestFocus();
            return false;
        }

        // 비밀번호 입력 확인
        if (etPassword.getText().toString().length() == 0) {
            Toast.makeText(RegistActivity.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return false;
        } else if (!m.find()) {
            Toast.makeText(RegistActivity.this, "비밀번호 양식을 맞춰주세요!\n8~16자리, 영문 대소문자, 숫자, 특수문자 필수포함\n" +
                    "특수문자는 !@#$%^&*()만 가능", Toast.LENGTH_LONG).show();
            return false;
        }

        // 비밀번호 확인 입력 확인
        else if (etPasswordConfirm.getText().toString().length() == 0) {
            Toast.makeText(RegistActivity.this, "비밀번호 확인을 입력하세요!", Toast.LENGTH_SHORT).show();
            etPasswordConfirm.requestFocus();
            return false;
        }
        // 이름 입력 확인
        else if (etName.getText().toString().length() == 0) {
            Toast.makeText(RegistActivity.this, "이름을 입력하세요!", Toast.LENGTH_SHORT).show();
            etName.requestFocus();
            return false;
        }
        //전화번호 입력 확인
        else if (etMobile.getText().toString().length() == 0) {
            Toast.makeText(RegistActivity.this, "전화번호를 입력하세요!", Toast.LENGTH_SHORT).show();
            etMobile.requestFocus();
            return false;
        }
        // 비밀번호 일치 확인
        else if (!etPassword.getText().toString().equals(etPasswordConfirm.getText().toString())) {
            Toast.makeText(RegistActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
            etPassword.setText("");
            etPasswordConfirm.setText("");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void doNetwork() {
        retroService.getSignUpService().signUp(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.body() == null) {
                    Toast.makeText(RegistActivity.this, "데이터를 받아오지 못했습니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                exceptionCatcher(response.body().getStatus());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(RegistActivity.this, "서버에서 응답을 받지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private Button.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnDone:
                    if (!checkFormatException()) return;
                    requestBody.setClass_idx(Integer.parseInt(spClass.getSelectedItem().toString()));
                    requestBody.setClass_number(Integer.parseInt(spNumber.getSelectedItem().toString()));
                    requestBody.setEmail(etEmail.getText().toString());
                    requestBody.setGender(spZender.getSelectedItem().toString());
                    requestBody.setMobile(etMobile.getText().toString());
                    requestBody.setName(etName.getText().toString());
                    requestBody.setPw(SHA256(etPassword.getText().toString()));
                    doNetwork();
                    break;
                case R.id.btnCancel:
                    finish();
                    break;
            }
        }
    };
    private TextWatcher checkSame = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String password = etPassword.getText().toString();
            String confirm = etPasswordConfirm.getText().toString();

            if (password.equals(confirm)) {
                etPassword.setBackgroundColor(Color.GREEN);
                etPasswordConfirm.setBackgroundColor(Color.GREEN);
            } else {
                etPassword.setBackgroundColor(Color.RED);
                etPasswordConfirm.setBackgroundColor(Color.RED);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
        }
    };
}