package com.example.lwh.project_school.Activity.Regist.Request;

public class RequestBody {
    private String email=null;
    private String pw=null;
    private String name=null;
    private String gender=null;
    private String mobile=null;
    private Integer class_idx=null;
    private Integer class_number=null;

    @Override
    public String toString() {
        return "RequestBody{" +
                "email='" + email + '\'' +
                ", pw='" + pw + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", mobile='" + mobile + '\'' +
                ", class_idx=" + class_idx +
                ", class_number=" + class_number +
                '}';
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setClass_idx(Integer class_idx) {
        this.class_idx = class_idx;
    }

    public void setClass_number(Integer class_number) {
        this.class_number = class_number;
    }
}
