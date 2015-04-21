package com.calebdavis.cscadvisement.Tables;

import com.orm.SugarRecord;

/**
 * Created by Caleb Davis on 4/16/15.
 */
public class Student extends SugarRecord<Student> {
    String studentId;
    String email;
    String advisor;
    String name;

    public Student(){

    }

    public Student(String name, String email, String Id, String advisor){
        this.name = name;
        this.studentId = Id;
        this.email = email;
        this.advisor = advisor;
    }

    public String getName(){
        return name;
    }

    public String getEmail(){
        return email;
    }

    public String getStudentId(){
        return studentId;
    }

    String getAdvisor(){
        return advisor;
    }

    public void setName(String name){ this.name = name; }

    public void setEmail(String email){ this.email = email; }

    public void setStudentId(String studentId){ this.studentId = studentId; }

    public void setAdvisor(String advisor){ this.advisor = advisor; }


}
