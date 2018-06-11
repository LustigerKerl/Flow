package com.example.lwh.project_school.Activity.Login.Response;

public class my_class {
    private Integer grade;
    private Integer class_room;
    private Integer class_number;

    @Override
    public String toString() {
        return "my_class{" +
                "grade=" + grade +
                ", classRoom=" + class_room +
                ", classNumber=" + class_number +
                '}';
    }


    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer value) {
        this.grade = value;
    }

    public Integer getClassRoom() {
        return class_room;
    }

    public void setClassRoom(Integer value) {
        this.class_room = value;
    }

    public Integer getClassNumber() {
        return class_number;
    }

    public void setClassNumber(Integer value) {
        this.class_number = value;
    }
}
