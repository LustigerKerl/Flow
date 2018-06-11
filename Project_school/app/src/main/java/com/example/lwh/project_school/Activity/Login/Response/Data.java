package com.example.lwh.project_school.Activity.Login.Response;

public class Data {
    @Override
    public String toString() {
        return "data{" +
                "token='" + token + '\'' +
                ", user=" + user +
                '}';
    }
    private String token;
    private User user;
    public String getToken() {
        return token;
    }

    public void setToken(String value) {
        this.token = value;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User value) {
        this.user = value;
    }
}
