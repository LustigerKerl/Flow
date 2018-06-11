package com.example.lwh.project_school.Adapter;

public class RecyclerItem {
    private String content;
    private Integer idx;
    private String writer;
    private String creDate;

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
