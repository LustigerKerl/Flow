package com.example.lwh.project_school.NetWork;

import android.os.AsyncTask;

import org.hyunjun.school.School;
import org.hyunjun.school.SchoolException;
import org.hyunjun.school.SchoolMenu;

import java.util.List;

public  class NetWorkWorld extends AsyncTask<Integer, Integer, Integer> {

    public TaskDelegate delegate=null;

    public interface TaskDelegate{
        void getMenu(List<SchoolMenu> success);
    }

    static List<SchoolMenu> result=null;

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        delegate.getMenu(result);
    }

    @Override
    protected Integer doInBackground(Integer... integers) {//year, nowMon, day
        School api=new School(School.Type.HIGH, School.Region.DAEGU,"D100000282");
        try {
            result=api.getMonthlyMenu(integers[0],integers[1]);
        } catch (SchoolException e) {
            e.printStackTrace();
        }
        return integers[2];
    }
}


