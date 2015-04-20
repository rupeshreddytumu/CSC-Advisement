package com.calebdavis.cscadvisement;

import java.util.List;

/**
 * Created by Caleb Davis on 4/19/15.
 */
public class CourseCreater {


    List<Course> courseList;

    public CourseCreater(){}

    public List<Course> createDegreePlan(){

        Course course = new Course(1L, "CSC 101", "Intro to C++ Programming",false);
        course.save();

        Course course2 = new Course(2L, "CSC 102", "Advanced C++ Programming", false);
        course2.save();

        Course course3 = new Course(3L, "CSC 203", "Assembly Language",false);
        course3.save();

        Course course4 = new Course(4L, "CSC 204", "Computer Organizations",false);
        course4.save();

        Course course5 = new Course(5L, "CSC 306", "Operating Systems",false);
        course5.save();

        Course course6 = new Course(6L, "CSC 307", "Data Structures", false);
        course6.save();

        Course course7 = new Course(7L, "CSC 309", "Computers and Society", false);
        course7.save();

        Course course8 = new Course(8L, "CSC 317", "Object Oriented Programming", false);
        course8.save();

        Course course9 = new Course(9L, "CSC 320", "Linear Programming", false);
        course9.save();

        Course course10 = new Course(10L, "CSC 408", "Theory of Programming Languages", false);
        course10.save();

        Course course11 = new Course(11L, "CSC 411", "Relational Database Management Systems", false);
        course11.save();

        Course course12 = new Course(12L, "CSC 412", "Intro to Artificial Intelligence", false);
        course12.save();

        Course course13 = new Course(13L, "CSC 413", "Intro to Algorithms",false);
        course13.save();

        Course course14 = new Course(14L, "CSC 414", "Software Engineering I",false);
        course14.save();

        Course course15 = new Course(15L, "CSC 415", "Compiler Theory", false);
        course15.save();

        Course course16 = new Course(16L, "CSC 424", "Software Engineering II",false);
        course16.save();



        return this.courseList;
    }


    public List<Course> getCourseList(){
        return this.courseList;
    }


}
