package com.example.lwh.project_school.Activity.Notice.NoticeList.Response;

import java.util.Date;

public class List {
    @Override
    public String toString() {
        return "List{" +
                "idx=" + idx +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                ", writeDate='" + writeDate + '\'' +
                ", modifyDate='" + modifyDate + '\'' +
                ", NoticeFiles=" + NoticeFiles +
                '}';
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Date getWriteDate() {
        return writeDate;
    }

    public void setWriteDate(Date writeDate) {
        this.writeDate = writeDate;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public com.example.lwh.project_school.Activity.Notice.NoticeList.Response.NoticeFiles getNoticeFiles() {
        return NoticeFiles;
    }

    public void setNoticeFiles(com.example.lwh.project_school.Activity.Notice.NoticeList.Response.NoticeFiles noticeFiles) {
        NoticeFiles = noticeFiles;
    }

    private Integer idx;
    private String content;
    private String writer;
    private Date writeDate;
    private Date modifyDate;
    private NoticeFiles NoticeFiles;
}
