package com.calebdavis.cscadvisement.SQLiteProject;

/**
 * Created by user on 4/23/15.
 */

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.calebdavis.cscadvisement.R;

import java.util.List;

public class SQLiteActivity extends Activity {

    // Database Helper
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        db = new DatabaseHelper(getApplicationContext());

        // Creating students
        Student student1 = new Student("student1");
        Student student2 = new Student("student2");
        Student student3 = new Student("student3");
        Student student4 = new Student("student4");

        // Inserting students in db
        long student1_id = db.createStudent(student1);
        long student2_id = db.createStudent(student2);
        long student3_id = db.createStudent(student3);
        long student4_id = db.createStudent(student4);


        Log.d("Tag Count", "Tag Count: " + db.getAllStudents().size());

        // Creating courses
        Courses course1 = new Courses("CSC101", "true");
        Courses course2 = new Courses("CSC102", "true");
        Courses course3 = new Courses("CSC103", "true");

        Courses course4 = new Courses("CSC104", "false");
        Courses course5 = new Courses("CSC105", "false");
        Courses course6 = new Courses("CSC106", "false");
        Courses course7 = new Courses("CSC107", "false");

        // Inserting courses in db
        long course1_id = db.createCourse(course1);
        long course2_id = db.createCourse(course2);
        long course3_id = db.createCourse(course3);
        long course4_id = db.createCourse(course4);
        long course5_id = db.createCourse(course5);
        long course6_id = db.createCourse(course6);
        long course7_id = db.createCourse(course7);


        // Inserting courses in db
        // Inserting 1-3courses under "Student1"
        //long course1_id = db.createCourseCompleted(course1, new long[]{student1_id});
        //long course2_id = db.createCourseCompleted(course2, new long[]{student1_id});
        //long course3_id = db.createCourseCompleted(course3, new long[]{student1_id});

        // Inserting courses under "Student2"
        //long course4_id = db.createCourseCompleted(course4, new long[]{student2_id});
        //long course5_id = db.createCourseCompleted(course5, new long[]{student2_id});
        //long course6_id = db.createCourseCompleted(course6, new long[]{student2_id});
        //long course7_id = db.createCourseCompleted(course7, new long[]{student2_id});

        // Inserting courses under "Student3" Tag
        //long course8_id = db.createCourseCompleted(course4, new long[]{student3_id});
        //long course9_id = db.createCourseCompleted(course5, new long[]{student3_id});

        // Inserting courses under "Student4" Tag
        //long course10_id = db.createCourseCompleted(course2, new long[]{student4_id});
        //long course11_id = db.createCourseCompleted(course3, new long[]{student4_id});




        db.addCourseToStudentsSchedule(course1_id, student1_id);
        db.addCourseToStudentsSchedule(course2_id, student1_id);
        db.addCourseToStudentsSchedule(course3_id, student1_id);
        db.addCourseToStudentsSchedule(course4_id, student2_id);
        db.addCourseToStudentsSchedule(course5_id, student2_id);
        db.addCourseToStudentsSchedule(course6_id, student2_id);
        db.addCourseToStudentsSchedule(course7_id, student3_id);

        List<Courses> test = db.getAllCoursesByStudent(student1.getSudentId());
        Log.e("Courses Count for student 1", "Courses count: " + db.getAllCoursesByStudent(student1.getSudentId()));
        for (Courses i: test ){
            Log.e("Course for student 1:", "Course for student 1: " + i.getCourseId());

        }
        Log.e("Courses Count for student 2", "Courses count: " + db.getAllCoursesByStudent(student2.getSudentId()).size());
        Log.e("Courses Count for student 3", "Courses count: " + db.getAllCoursesByStudent(student3.getSudentId()).size());
        Log.e("Courses Count for student 4", "Courses count: " + db.getAllCoursesByStudent(student4.getSudentId()).size());



        List<Student> allStudents = db.getAllStudents();
        for (Student tag : allStudents) {
            Log.d("Student ID:", tag.getSudentId());
        }


        // Don't forget to close database connection
        db.closeDB();

    }

}