package com.calebdavis.cscadvisement;

import java.util.List;

/**
 * Created by Caleb Davis on 4/19/15.
 */
public class CourseCreater {


    List<Course> courseList;

    public CourseCreater(){}

    public List<Course> createDegreePlan(){

        Course course = new Course(1L, "CSC 101", "Intro to C++", false);
        course.save();

        Course course2 = new Course(2L, "CSC 102", "Advanced C++", false);
        course2.save();

        Course course3 = new Course(3L, "CSC 307", "Data Structures", false);
        course3.save();

        Course course4 = new Course(4L, "CSC 317", "Object Oriented", false);
        course4.save();

        Course course5 = new Course(5L, "CSC 413", "Intro to Algorithms", false);
        course5.save();

        Course course6 = new Course(6L, "CSC 414", "Software Engineering I", false);
        course6.save();

        Course course7 = new Course(7L, "CSC 424", "Software Engineering II", false);
        course7.save();


        return this.courseList;
    }


    public List<Course> getCourseList(){
        return this.courseList;
    }


}
