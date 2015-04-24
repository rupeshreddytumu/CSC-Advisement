package com.calebdavis.cscadvisement.SQLiteProject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 4/23/15.
 */
public class DatabaseHelper extends SQLiteOpenHelper{
    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Degree_Progress";

    // Table Names
    private static final String COURSES_TABLE = "Courses";
    private static final String STUDENTS_TABLE = "Students";
    private static final String COMPLETED_COURSES_TABLE = "CoursesCompleted";

    // Common column names
    private static final String KEY_ID = "id";

    // COURSES Table - column names --- > TABLE_TODO
    private static final String KEY_COURSE_ID = "course_id";// --- > KEY_TODO
    private static final String KEY_STATUS = "status";// --- > KEY_CREATED_AT

    // STUDENTS Table - column names --- > TABLE_TAG
    private static final String KEY_STUDENT_ID = "student_id";// --- > KEY_TAG_NAME


    // COMPLETED_COURSES Table - column names --- > TABLE_TODO_TAG
    private static final String KEY_COMPLETED_COURSES_ID = "cc_id";// --- > KEY_TODO_ID
    private static final String KEY_STUDENTID = "cc_student_id";// --- > KEY_TAG_ID

    // Table Create Statements
    // COURSES table create statement
    private static final String CREATE_TABLE_COURSES = "CREATE TABLE "
            + COURSES_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_COURSE_ID
            + " TEXT," + KEY_STATUS  + " TEXT" +")";

    // STUDENTS table create statement
    private static final String CREATE_TABLE_STUDENT = "CREATE TABLE " + STUDENTS_TABLE
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_STUDENT_ID + " TEXT" +")";

    // todo_tag table create statement
    private static final String CREATE_TABLE_COMPLETED_COURSES = "CREATE TABLE IF NOT EXISTS "
            + COMPLETED_COURSES_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_COMPLETED_COURSES_ID + " INTEGER, " + KEY_STUDENTID + " INTEGER" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // creating required tables
        db.execSQL(CREATE_TABLE_COURSES);
        db.execSQL(CREATE_TABLE_STUDENT);
        db.execSQL(CREATE_TABLE_COMPLETED_COURSES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + STUDENTS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + COMPLETED_COURSES_TABLE);

        // create new tables
        onCreate(db);
    }

    /*
 * Creating a COMPLETED COURSE
 */
    public long createCourseCompleted(Courses course, long[] student_ids) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COURSE_ID, course.getCourseId());
        values.put(KEY_STATUS, course.getTaken());

        // insert row
        long course_id = db.insert(COURSES_TABLE, null, values);

        // assigning tags to course
        for (long student_id : student_ids) {
            addCourseToStudentsSchedule(course_id, student_id);
        }

        db.close();
        return course_id;
    }

    /*
 * get single COMPLETED COURSE
 */
    public Courses getCourse(long course_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + COURSES_TABLE + " WHERE "
                + KEY_ID + " = " + course_id;

        Log.e(LOG, selectQuery);

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Courses course = new Courses();
        course.setId(c.getInt(c.getColumnIndex(KEY_ID)));
        course.setCourseId((c.getString(c.getColumnIndex(KEY_COURSE_ID))));
        course.setTaken(c.getString(c.getColumnIndex(KEY_STATUS)));
        c.close();

        return course;
    }

    /*
    * getting all COURSES under single STUDENT
 * */
    public List<Courses> getAllCoursesByStudent(String student_id) {
        List<Courses> courses = new ArrayList<Courses>();

        String selectQuery = "SELECT * FROM " + COURSES_TABLE + " td, "
                + STUDENTS_TABLE + " tg, " + COMPLETED_COURSES_TABLE + " tt WHERE tg."
                + KEY_STUDENT_ID + " = '" + student_id + "'" + " AND tg." + KEY_ID
                + " = " + "tt." + KEY_STUDENTID + " AND td." + KEY_ID + " = "
                + "tt." + KEY_COMPLETED_COURSES_ID;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Courses course = new Courses();
                course.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                course.setCourseId((c.getString(c.getColumnIndex(KEY_COURSE_ID))));
                course.setTaken(c.getString(c.getColumnIndex(KEY_STATUS)));

                // adding to courses taken list
                courses.add(course);
            } while (c.moveToNext());
        }

        db.close();
        c.close();
        return courses;
    }

    /*
* Creating a COURSE
*/
    public long createCourse(Courses course) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COURSE_ID, course.getCourseId());
        values.put(KEY_STATUS, course.getTaken());

        long course_id = db.insert(COURSES_TABLE, null, values);

        return course_id;

    }

    /*
 * Updating a COURSE
 */
    public int updateCourse(Courses course) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COURSE_ID, course.getCourseId());
        values.put(KEY_STATUS, course.getTaken());

        // updating row
        return db.update(COURSES_TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(course.getId()) });
    }

    /*
 * Deleting a COURSE
 */
    public void deleteCourse(long course_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(COURSES_TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(course_id) });
    }

    /*
 * Creating a STUDENT
 */

    public long createStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STUDENT_ID, student.getSudentId());

        // insert row
        long student_id = db.insert(STUDENTS_TABLE, null, values);

        return student_id;
    }
    /**
     * getting all STUDENTS
     * */
    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<Student>();
        String selectQuery = "SELECT  * FROM " + STUDENTS_TABLE;

        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Student student = new Student();
                student.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                student.setStudentId(c.getString(c.getColumnIndex(KEY_STUDENT_ID)));

                // adding to tags list
                students.add(student);
            } while (c.moveToNext());
        }
        c.close();
        return students;
    }

    /*
 * Updating a STUDENT
 */
    public int updateStudent(Student student) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STUDENT_ID, student.getSudentId());

        // updating row
        return db.update(STUDENTS_TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(student.getId()) });
    }

    /*
 * Deleting a student
 */
    public void deleteStudentsDegreeProgress(Student student, boolean should_delete_all_completed_courses) {
        SQLiteDatabase db = this.getWritableDatabase();

        // before deleting student
        // check if courses under this student should also be deleted
        if (should_delete_all_completed_courses) {
            // get all courses under this student
            List<Courses> allCourses = getAllCoursesByStudent(student.getSudentId());

            // delete all courses
            for (Courses c : allCourses) {
                // delete each course
                deleteCourse(c.getId());
            }
        }

        // now delete the tag
        db.delete(STUDENTS_TABLE, KEY_ID + " = ?",
                new String[] { String.valueOf(student.getId()) });
    }

    /*
     * Assigning a course to a student
     */
    public long addCourseToStudentsSchedule(long course_id, long student_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_COMPLETED_COURSES_ID, course_id);
        values.put(KEY_STUDENTID, student_id);


        long id = db.insert(COMPLETED_COURSES_TABLE, null, values);

        db.close();
        return id;
    }

    /*
 * Updating a student's current course
 */
    public int updateStudentsCurrentCourse(long id, long student_id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_STUDENT_ID, student_id);

         db.close();

        // updating row
        return db.update(COURSES_TABLE, values, KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}