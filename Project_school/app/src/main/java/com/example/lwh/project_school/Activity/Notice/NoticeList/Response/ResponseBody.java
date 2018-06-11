package com.example.lwh.project_school.Activity.Notice.NoticeList.Response;

public class ResponseBody {
    private Integer status;
    private String message;
    private Data data;

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

    public Data getData() {

        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
