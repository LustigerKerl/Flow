package com.example.lwh.project_school.Activity.Login.Request;

public class RequestBody {
    private String email;
    private String pw;

    @Override
    public String toString() {
        return "RequestBody{" +
                "email='" + email + '\'' +
                ", pw='" + pw + '\'' +
                ", registration_token='" + registration_token + '\'' +
                '}';
    }

    private String registration_token;

    public void setRegistration_token(String registration_token) {
        this.registration_token = registration_token;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }
}
