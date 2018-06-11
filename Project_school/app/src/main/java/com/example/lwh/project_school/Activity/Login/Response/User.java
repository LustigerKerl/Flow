package com.example.lwh.project_school.Activity.Login.Response;

public class User {
    private String email;
    private String name;
    private String gender;

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", mobile='" + mobile + '\'' +
                ", my_class=" + my_class +
                '}';
    }

    private String mobile;
    private my_class my_class;

    public String getEmail() {
        return email;
    }

    public void setEmail(String value) {
        this.email = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String value) {
        this.gender = value;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String value) {
        this.mobile = value;
    }

    public my_class getmy_class() {
        return my_class;
    }

    public void setmy_class(my_class value) {
        this.my_class = value;
    }
}
