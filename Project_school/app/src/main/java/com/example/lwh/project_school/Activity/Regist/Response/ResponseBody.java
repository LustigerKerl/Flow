package com.example.lwh.project_school.Activity.Regist.Response;

public class ResponseBody {
    private int status;
    private String message;

    @Override
    public String toString() {
        return "ResponseBody{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
