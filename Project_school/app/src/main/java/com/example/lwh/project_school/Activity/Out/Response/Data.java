package com.example.lwh.project_school.Activity.Out.Response;

public class Data {
    private Go_Out go_out;

    public Sleep_out getSleep_out() {
        return sleep_out;
    }

    public void setSleep_out(Sleep_out sleep_out) {
        this.sleep_out = sleep_out;
    }

    private Sleep_out sleep_out;
    public Go_Out getGo_out() {
        return go_out;
    }

    public void setGo_out(Go_Out go_out) {
        this.go_out = go_out;
    }

    @Override
    public String toString() {
        return "data{" +
                "go_out=" + go_out +
                '}';
    }
}
