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

    private static List<SchoolMenu> result=null;

    @Override
    protected void onPostExecute(Integer integer) { //네트워크 작업이 끝난후
        super.onPostExecute(integer);
        delegate.getMenu(result);       //delegate 패턴을 써서 전달
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


