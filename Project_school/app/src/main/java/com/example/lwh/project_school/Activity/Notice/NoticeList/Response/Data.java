package com.example.lwh.project_school.Activity.Notice.NoticeList.Response;

public class Data {
    @Override
    public String toString() {
        return "data{" +
                "list=" + list +
                '}';
    }

    public List[] getList() {
        return list;
    }

    public void setList(List[] list) {
        this.list = list;
    }

    private List[] list;
}
