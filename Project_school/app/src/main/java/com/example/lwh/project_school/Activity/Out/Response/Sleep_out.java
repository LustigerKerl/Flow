package com.example.lwh.project_school.Activity.Out.Response;

import java.util.Date;

public class Sleep_out {
    @Override
    public String toString() {
        return "Sleep_out{" +
                "accept=" + accept +
                ", idx=" + idx +
                ", start_time='" + start_time + '\'' +
                ", end_time='" + end_time + '\'' +
                ", reason='" + reason + '\'' +
                ", class_idx=" + class_idx +
                ", student_email='" + student_email + '\'' +
                '}';
    }

    private int accept;
    private int idx;
    private Date start_time;
    private Date end_time;
    private String reason;
    private int class_idx;
    private String student_email;

    public int getAccept() {
        return accept;
    }

    public int getIdx() {
        return idx;
    }

    public Date getStart_time() {
        return start_time;
    }

    public Date getEnd_time() {
        return end_time;
    }

    public String getReason() {
        return reason;
    }

    public int getClass_idx() {
        return class_idx;
    }

    public String getStudent_email() {
        return student_email;
    }
}
