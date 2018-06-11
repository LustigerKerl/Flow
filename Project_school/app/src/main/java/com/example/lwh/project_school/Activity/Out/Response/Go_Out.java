package com.example.lwh.project_school.Activity.Out.Response;

import java.util.Date;

public class Go_Out {
    private int accept;
    private int idx;
    private Date start_time;
    private Date end_time;
    private String reason;
    private int class_idx;
    private String student_email;

    @Override
    public String toString() {
        return "Go_Out{" +
                "accept=" + accept +
                ", idx=" + idx +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", reason='" + reason + '\'' +
                ", class_idx=" + class_idx +
                ", student_email='" + student_email + '\'' +
                '}';
    }

    public int getAccept() {
        return accept;
    }

    public void setAccept(int accept) {
        this.accept = accept;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public Date getStart_time() {
        return start_time;
    }

    public void setStart_time(Date start_time) {
        this.start_time = start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public void setEnd_time(Date end_time) {
        this.end_time = end_time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getClass_idx() {
        return class_idx;
    }

    public void setClass_idx(int class_idx) {
        this.class_idx = class_idx;
    }

    public String getStudent_email() {
        return student_email;
    }

    public void setStudent_email(String student_email) {
        this.student_email = student_email;
    }
}
