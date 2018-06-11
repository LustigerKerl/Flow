package com.example.lwh.project_school.Service;

import java.text.SimpleDateFormat;
import java.util.Date;



public class GetAnything {
    int result=0;
    public int getTime(String what) {

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        SimpleDateFormat hour = new SimpleDateFormat("HH");
        SimpleDateFormat min = new SimpleDateFormat("mm");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        switch (what) {
            case "mon":
                result= Integer.parseInt(month.format(date));
                break;
            case "day":
                result= Integer.parseInt(day.format(date));
                break;
            case "hour":
                result=Integer.parseInt(hour.format(date));
                break;
            case "min":
                result=Integer.parseInt(min.format(date));
                break;
            case "year":
                result=Integer.parseInt(year.format(date));

        }
        return result;
    }
}
