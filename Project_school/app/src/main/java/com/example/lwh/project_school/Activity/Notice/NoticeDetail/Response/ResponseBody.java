package com.example.lwh.project_school.Activity.Notice.NoticeDetail.Response;

public class ResponseBody {
    private Integer status;
    private String message;
    private data data;

    @Override
    public String toString() {
        return "ResponseBody{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public com.example.lwh.project_school.Activity.Notice.NoticeDetail.Response.data getData() {
        return data;
    }

    public void setData(com.example.lwh.project_school.Activity.Notice.NoticeDetail.Response.data data) {
        this.data = data;
    }
}
