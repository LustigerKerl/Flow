package com.example.lwh.project_school.Activity.Notice.NoticeDetail.Response;

import java.util.Arrays;
import java.util.Date;

public class data {
    private Integer idx;
    private String content;
    private String writer;
    private Date write_date;
    private Date modify_date;

    public com.example.lwh.project_school.Activity.Notice.NoticeDetail.Response.notice_files[] getNotice_files() {
        return notice_files;
    }

    public void setNotice_files(com.example.lwh.project_school.Activity.Notice.NoticeDetail.Response.notice_files[] notice_files) {
        this.notice_files = notice_files;
    }

    @Override
    public String toString() {
        return "data{" +
                "idx=" + idx +
                ", content='" + content + '\'' +
                ", writer='" + writer + '\'' +
                ", write_date='" + write_date + '\'' +
                ", modify_date='" + modify_date + '\'' +
                ", notice_files=" + Arrays.toString(notice_files) +
                '}';
    }

    private notice_files[] notice_files=null;

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

    public Date getWrite_date() {
        return write_date;
    }

    public void setWrite_date(Date write_date) {
        this.write_date = write_date;
    }

    public Date getModify_date() {
        return modify_date;
    }

    public void setModify_date(Date modify_date) {
        this.modify_date = modify_date;
    }

}
