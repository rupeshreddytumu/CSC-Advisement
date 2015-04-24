package com.calebdavis.cscadvisement.SQLiteProject;

/**
 * Created by user on 4/23/15.
 */
public class Courses {

    String cid;
    String name;
    String taken;
    String description;
    int id;

    public Courses(){}

    public Courses( String cid, String taken){
        //this.name = name;
        this.cid = cid;
        //this.description = description;
        this.taken = taken;
        //this.id = id;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTaken() {
        return taken;
    }
    public void setTaken(String taken) {
        this.taken = taken;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


}