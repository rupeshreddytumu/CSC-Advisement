package com.calebdavis.cscadvisement.SQLiteTableClasses;

/**
 * Created by user on 4/30/15.
 */
public class StudentCourse implements Comparable{
    String sid;
    String cid;
    String taken;
    String semester;
    int id;
    int code;


    public StudentCourse(){}
    public StudentCourse(String sid, String cid, String taken, String semester, int code){
        this.sid = sid;
        this.cid = cid;
        this.taken = taken;
        this.semester = semester;
        this.code = code;
    }

    public long getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getCourseId() {
        return cid;
    }
    public void setCourseId(String courseId) {
        this.cid = courseId;
    }

    public String getSid() {
        return sid;
    }
    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTaken() {
        return taken;
    }
    public void setTaken(String taken) {
        this.taken = taken;
    }

    public String getSemester() {
        return semester;
    }
    public void setSemester(String semester) {
        this.semester = semester;
    }

    public int getCode() {return code;}
    public void setCode(int code) { this.code = code;}

    @Override
    public int compareTo(Object another) {
        int compare_course_number = ((StudentCourse)another).getCode();
        return this.code-compare_course_number;
    }
}
