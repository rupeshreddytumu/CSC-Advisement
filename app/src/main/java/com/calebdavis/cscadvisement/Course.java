package com.calebdavis.cscadvisement;


import com.orm.SugarRecord;

/**
 * Created by Caleb Davis on 4/16/15.
 */
public class Course extends SugarRecord<Course> {


    Long ID;
    boolean selected = false;
    String code = null;
    String name = null;

    public Course(){
    }

    public Course(Long ID, String code, String name, boolean selected){


        this.ID = ID;
        this.code = code;
        this.name = name;
        this.selected = selected;
    }

    public void setID(Long Id){
        this.ID = Id;
    }

    public Long getId(){
        return this.ID;

    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

