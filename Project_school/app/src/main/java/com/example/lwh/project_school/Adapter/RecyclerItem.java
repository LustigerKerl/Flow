package com.example.lwh.project_school.Adapter;

public class RecyclerItem {
    private String content;
    private Integer idx;
    private String writer;
    private String creDate;
    private String type;
    private String start_date;
    private String end_date;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    private String reason;

    public void setContent(String content) {
        this.content = content;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public void setCreDate(String creDate) {
        this.creDate = creDate;
    }

    public String getContent() {
        return content;
    }

    public Integer getIdx() {
        return idx;
    }

    public String getWriter() {
        return writer;
    }

    public String getCreDate() {
        return creDate;
    }
}
