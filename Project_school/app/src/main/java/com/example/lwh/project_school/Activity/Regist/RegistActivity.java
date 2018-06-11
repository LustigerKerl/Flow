package com.example.lwh.project_school.Activity.Regist;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lwh.ProjectSchool.R;
import com.example.lwh.project_school.Activity.Login.LoginActivity;
import com.example.lwh.project_school.Activity.Regist.Request.RequestBody;
import com.example.lwh.project_school.Activity.Regist.Response.ResponseBody;
import com.example.lwh.project_school.NetWork.RetroService;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistActivity extends AppCompatActivity{


    private Button btnDone;
    private Button btnCancel;
    private EditText etEmail;
    Pattern ptEmail=Pattern.compile("(dgsw.hs.kr)");
    Pattern ptPassword= Pattern.compile("[a-zA-Z0-9]{8,16}(?=.*[~`!@#$%^&*()-])");
    Matcher m;
    public int code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        etEmail = (EditText) findViewById(R.id.etEmail);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etPasswordConfirm = (EditText) findViewById(R.id.etPasswordConfirm);
        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etMobile = (EditText) findViewById(R.id.etMobile);
        final Spinner spClass=(Spinner) findViewById(R.id.spClass);
        final Spinner spNumber=(Spinner) findViewById(R.id.spNumber);
        final Spinner spZender=(Spinner) findViewById(R.id.spZender);
        btnDone = (Button) findViewById(R.id.btnDone);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        // 비밀번호 일치 검사
        etPasswordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = etPassword.getText().toString();
                String confirm = etPasswordConfirm.getText().toString();

                if( password.equals(confirm) ) {
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
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                m=ptEmail.matcher(etEmail.getText().toString());
                // 이메일 입력 확인
                if( etEmail.getText().toString().length() == 0 ) {
                    Toast.makeText(RegistActivity.this, "Email을 입력하세요!", Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }

                if(m.find()){
                    m=ptPassword.matcher(etPassword.getText().toString());
                }
                else {
                    Toast.makeText(RegistActivity.this, "DGSW 이메일이 아닙니다!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 비밀번호 입력 확인
                if( etPassword.getText().toString().length() == 0 ) {
                    Toast.makeText(RegistActivity.this, "비밀번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }

                if(m.find()){
                }
                else {
                    Toast.makeText(RegistActivity.this, "비밀번호 양식을 지켜주세요!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 비밀번호 확인 입력 확인
                if( etPasswordConfirm.getText().toString().length() == 0 ) {
                    Toast.makeText(RegistActivity.this, "비밀번호 확인을 입력하세요!", Toast.LENGTH_SHORT).show();
                    etPasswordConfirm.requestFocus();
                    return;
                }
                // 이름 입력 확인
                if( etName.getText().toString().length() == 0 ) {
                    Toast.makeText(RegistActivity.this, "이름을 입력하세요!", Toast.LENGTH_SHORT).show();
                    etName.requestFocus();
                    return;
                }
                //전화번호 입력 확인
                if( etMobile.getText().toString().length() == 0 ) {
                    Toast.makeText(RegistActivity.this, "전화번호를 입력하세요!", Toast.LENGTH_SHORT).show();
                    etMobile.requestFocus();
                    return;
                }
                // 비밀번호 일치 확인
                if( !etPassword.getText().toString().equals(etPasswordConfirm.getText().toString()) ) {
                    Toast.makeText(RegistActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_SHORT).show();
                    etPassword.setText("");
                    etPasswordConfirm.setText("");
                    etPassword.requestFocus();
                    return;
                }
                final RequestBody requestBody = new RequestBody();
                requestBody.setClass_idx(Integer.parseInt(spClass.getSelectedItem().toString()));
                requestBody.setClass_number(Integer.parseInt(spNumber.getSelectedItem().toString()));
                requestBody.setEmail(etEmail.getText().toString());
                requestBody.setGender(spZender.getSelectedItem().toString());
                requestBody.setMobile(etMobile.getText().toString());
                requestBody.setName(etName.getText().toString());
                requestBody.setPw(SHA256(etPassword.getText().toString()));

                RetroService retroService=new RetroService(getApplicationContext(),false);
                Log.d("Registration Test",requestBody.toString());
                retroService.getSignUpService().signUp(requestBody).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        ResponseBody responseBody=response.body();
                        Log.d("test2",responseBody.toString());
                        exceptionCatcher(responseBody.getStatus());
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                        Toast.makeText(RegistActivity.this, "서버에서 응답을 받지 못했습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void exceptionCatcher(int code){
        if(code==200){
            Toast.makeText(getApplicationContext(), "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, LoginActivity.class);
            intent.putExtra("email",etEmail.getText().toString());
            startActivity(intent);
            finish();
        }
        else if(code==409){
            Toast.makeText(getApplicationContext(), "해당 이메일로 계정이 존재합니다.", Toast.LENGTH_SHORT).show();
        }
        else if(code==500){
            Toast.makeText(getApplicationContext(), "서버 오류.", Toast.LENGTH_SHORT).show();
        }
    }
    public String SHA256(String str){

        String SHA = "";

        try{

            MessageDigest sh = MessageDigest.getInstance("SHA-256");

            sh.update(str.getBytes());

            byte byteData[] = sh.digest();

            StringBuffer sb = new StringBuffer();

            for(int i = 0 ; i < byteData.length ; i++){

                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));

            }

            SHA = sb.toString();



        }catch(NoSuchAlgorithmException e){

            e.printStackTrace();

            SHA = null;

        }

        return SHA;

    }
}