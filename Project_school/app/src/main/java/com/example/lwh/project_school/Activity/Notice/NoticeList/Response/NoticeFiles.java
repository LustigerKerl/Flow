package com.example.lwh.project_school.Activity.Notice.NoticeList.Response;

public class NoticeFiles {
    private Integer idx;
    private String uploadName;
    private String uploadDir;
    private Integer noticeIdx;

    @Override
    public String toString() {
        return "NoticeFiles{" +
                "idx=" + idx +
                ", uploadName='" + uploadName + '\'' +
                ", uploadDir='" + uploadDir + '\'' +
                ", noticeIdx=" + noticeIdx +
                '}';
    }

    public Integer getIdx() {
        return idx;
    }

    public void setIdx(Integer idx) {
        this.idx = idx;
    }

    public String getUploadName() {
        return uploadName;
    }

    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }

    public String getUploadDir() {
        return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
        this.uploadDir = uploadDir;
    }

    public Integer getNoticeIdx() {
        return noticeIdx;
    }

    public void setNoticeIdx(Integer noticeIdx) {
        this.noticeIdx = noticeIdx;
    }
}
