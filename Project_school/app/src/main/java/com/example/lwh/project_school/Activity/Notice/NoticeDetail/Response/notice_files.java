package com.example.lwh.project_school.Activity.Notice.NoticeDetail.Response;

public class notice_files {
    private Integer idx;
    private String upload_name;
    private String upload_dir;

    @Override
    public String toString() {
        return "notice_files{" +
                "idx=" + idx +
                ", upload_name='" + upload_name + '\'' +
                ", upload_dir='" + upload_dir + '\'' +
                ", notice_idx=" + notice_idx +
                '}';
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getUpload_name() {
        return upload_name;
    }

    public void setUpload_name(String upload_name) {
        this.upload_name = upload_name;
    }

    public String getUpload_dir() {
        return upload_dir;
    }

    public void setUpload_dir(String upload_dir) {
        this.upload_dir = upload_dir;
    }

    public Integer getNotice_idx() {
        return notice_idx;
    }

    public void setNotice_idx(Integer notice_idx) {
        this.notice_idx = notice_idx;
    }

    private Integer notice_idx;


}
