package com.calebdavis.cscadvisement.SQLiteTableClasses;

/**
 * Created by user on 4/23/15.
 */
public class Student {

    String sid;

    int id;

    public Student(){}
    public Student(String sid){

        this.sid = sid;


    }

    public long getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getSudentId() {
        return sid;
    }
    public void setStudentId(String sid) {
        this.sid = sid;
    }



}