package com.calebdavis.cscadvisement;

import com.orm.SugarRecord;

/**
 * Created by Caleb Davis on 4/16/15.
 */
public class Student extends SugarRecord<Student> {
    String studentId;
    String name;
    String email;

    public Student(){

    }

    public Student(String Id, String name, String email){
        this.studentId = Id;
        this.name = name;
        this.email = email;
    }
}
