package com.calebdavis.cscadvisement.SQLiteTableClasses;

/**
 * Created by user on 4/30/15.
 */
public class StudentCourse {
    String sid;
    String cid;
    String taken;
    int id;

    public StudentCourse(){}
    public StudentCourse(String sid, String cid, String taken){
        this.sid = sid;
        this.cid = cid;
        this.taken = taken;
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


}
