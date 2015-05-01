package com.calebdavis.cscadvisement.DatabaseHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.calebdavis.cscadvisement.SQLiteProject.Courses;
import com.calebdavis.cscadvisement.SQLiteProject.StudentCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Caleb Davis on 4/30/15.
 */
public class CoursesDbAdapter {

    public static final String KEY_ID = "_id";
    // COURSES Table - column names
    public static final String KEY_COURSE_ID = "course_id";
    public static final String KEY_STATUS = "status";

    // STUDENTS Table - column names
    public static final String KEY_STUDENT_ID = "student_id";


    // COMPLETED_COURSES Table - column names
    public static final String KEY_COMPLETED_COURSES_ID = "cc_id";
    public static final String KEY_STUDENTID = "cc_student_id";


    private static final String TAG = "CoursesDbAdapter";
    private DatabaseHelper mDbHelper;
    private SQLiteDatabase mDb;

    // Database Name
    private static final String DATABASE_NAME = "Degree_Progress";
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String COURSES_TABLE = "Courses";
    private static final String STUDENTS_TABLE = "Students";
    private static final String COMPLETED_COURSES_TABLE = "CoursesCompleted";
    private static final String STUDENT_COURSES_TABLE = "StudentCourses";


    private final Context mCtx;

    // Table Create Statements
    // COURSES table create statement
    private static final String CREATE_TABLE_COURSES = "CREATE TABLE "
            + COURSES_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_COURSE_ID
            + " TEXT," + KEY_STATUS  + " TEXT" +")";

    // STUDENTS table create statement
    private static final String CREATE_TABLE_STUDENT = "CREATE TABLE " + STUDENTS_TABLE
            + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_STUDENT_ID + " TEXT" +")";


    private static final String CREATE_TABLE_COMPLETED_COURSES = "CREATE TABLE "
            + COMPLETED_COURSES_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_COMPLETED_COURSES_ID + " INTEGER, " + KEY_STUDENTID + " INTEGER" +  KEY_STATUS  + " TEXT" +")";

    private static final String CREATE_TABLE_STUDENT_COURSES = "CREATE TABLE "
            + STUDENT_COURSES_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_STUDENT_ID + " TEXT, " + KEY_COURSE_ID + " TEXT, " +  KEY_STATUS  + " TEXT" +")";

    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, CREATE_TABLE_STUDENT);
            db.execSQL(CREATE_TABLE_STUDENT);

            Log.w(TAG, CREATE_TABLE_COURSES);
            db.execSQL(CREATE_TABLE_COURSES);

            Log.w(TAG, CREATE_TABLE_COMPLETED_COURSES);
            db.execSQL(CREATE_TABLE_COMPLETED_COURSES);

            Log.w(TAG, CREATE_TABLE_STUDENT_COURSES);
            db.execSQL(CREATE_TABLE_STUDENT_COURSES);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS " + STUDENTS_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + COURSES_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + COMPLETED_COURSES_TABLE);
            db.execSQL("DROP TABLE IF EXISTS " + STUDENT_COURSES_TABLE);

            onCreate(db);
        }
    }

    public CoursesDbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    public CoursesDbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx);
         mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
         if (mDbHelper != null) {
             mDbHelper.close();
         }
    }

    public Cursor fetchStudent(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(STUDENTS_TABLE, new String[] {KEY_ID,
                            KEY_STUDENT_ID},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, STUDENTS_TABLE, new String[] {KEY_ID,
                            KEY_STUDENT_ID},
                    KEY_STUDENT_ID + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchAllStudents() {

        Cursor mCursor = mDb.query(STUDENTS_TABLE, new String[] {KEY_ID,
                        KEY_STUDENT_ID},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public List<Courses> getAllCoursesByStudent(String student_id) {
        List<Courses> courses = new ArrayList<Courses>();

        String selectQuery = "SELECT * FROM " + COURSES_TABLE + " td, "
                + STUDENTS_TABLE + " tg, " + COMPLETED_COURSES_TABLE + " tt WHERE tg."
                + KEY_STUDENT_ID + " = '" + student_id + "'" + " AND tg." + KEY_ID
                + " = " + "tt." + KEY_STUDENTID + " AND td." + KEY_ID + " = "
                + "tt." + KEY_COMPLETED_COURSES_ID;


        Log.e(TAG, selectQuery);

        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = mDb.rawQuery(selectQuery, null);

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

        //mDb.close();
        //c.close();
        return courses;
    }

    public long createStudentCourse(String student_id, String course_id, String status){
        ContentValues values = new ContentValues();
        values.put(KEY_STUDENT_ID, student_id);
        values.put(KEY_COURSE_ID, course_id);
        values.put(KEY_STATUS, status);

        long id =  mDb.insert(STUDENT_COURSES_TABLE, null, values);
        return id;
    }

    public List<StudentCourse> testCoursesForSpecificStudent(String student_id) {
        List<StudentCourse> courses = new ArrayList<StudentCourse>();

        String selectQuery = "SELECT * FROM " + STUDENT_COURSES_TABLE + " WHERE "
                + KEY_STUDENT_ID + " = '" + student_id + "' " ;



        Log.e(TAG, selectQuery);

        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = mDb.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                StudentCourse course = new StudentCourse();
                course.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                course.setSid((c.getString(c.getColumnIndex(KEY_STUDENT_ID))));
                course.setCourseId((c.getString(c.getColumnIndex(KEY_COURSE_ID))));
                course.setTaken(c.getString(c.getColumnIndex(KEY_STATUS)));

                // adding to courses taken list
                courses.add(course);
            } while (c.moveToNext());
        }

        //mDb.close();
        //c.close();
        return courses;
    }

    public long createCourse(String course_id, String taken) {
        ContentValues values = new ContentValues();
        values.put(KEY_COURSE_ID, course_id);
        values.put(KEY_STATUS, taken);

        return mDb.insert(COURSES_TABLE, null, values);
    }

    public long createStudent(String student_id) {
        ContentValues values = new ContentValues();
        values.put(KEY_STUDENT_ID, student_id);

        return mDb.insert(STUDENTS_TABLE, null, values);
    }

    public boolean updateItem(long rowId, String status) {
        ContentValues values = new ContentValues();

        values.put(KEY_STATUS, status);

        return mDb.update(STUDENT_COURSES_TABLE, values, KEY_ID + "=" + rowId, null) > 0;
    }

    public long addCourseToStudentsSchedule(long course_id, long student_id, String taken) {

        ContentValues values = new ContentValues();
        values.put(KEY_COMPLETED_COURSES_ID, course_id);
        values.put(KEY_STUDENTID, student_id);
        values.put(KEY_STATUS, taken);
        Log.w(TAG, String.valueOf(values));

        return mDb.insert(COMPLETED_COURSES_TABLE, null, values);
    }



    public boolean deleteAllCourses(){
        int doneDelete = 0;
        doneDelete = mDb.delete(COURSES_TABLE, null, null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;
    }

    public boolean deleteAllStudentCourses(){
        int doneDelete = 0;
        doneDelete = mDb.delete(STUDENT_COURSES_TABLE, null, null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;
    }

    public boolean deleteAllCompletedCourses(){
        int doneDelete = 0;
        doneDelete = mDb.delete(COMPLETED_COURSES_TABLE, null, null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;
    }

    public Cursor fetchAllCourses() {

        Cursor mCursor = mDb.query(COURSES_TABLE, new String[] {KEY_ID,
                        KEY_COURSE_ID, KEY_STATUS},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor fetchAllStudentCourses(String inputText) {

        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(STUDENT_COURSES_TABLE, new String[] {KEY_ID,
                            KEY_STUDENT_ID, KEY_COURSE_ID, KEY_STATUS},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, STUDENT_COURSES_TABLE, new String[] {KEY_ID,
                            KEY_STUDENT_ID, KEY_COURSE_ID, KEY_STATUS},
                    KEY_STUDENT_ID + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor fetchCourseByCourseId(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(COURSES_TABLE, new String[] {KEY_ID,
                            KEY_COURSE_ID, KEY_STATUS},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, COURSES_TABLE, new String[] {KEY_ID,
                            KEY_COURSE_ID, KEY_STATUS},
                    KEY_COURSE_ID + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public Cursor fetchStudentCourseByCourseId(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  {
            mCursor = mDb.query(STUDENT_COURSES_TABLE, new String[] {KEY_ID,
                            KEY_STUDENT_ID, KEY_COURSE_ID, KEY_STATUS},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, STUDENT_COURSES_TABLE, new String[] {KEY_ID,
                            KEY_STUDENT_ID, KEY_COURSE_ID, KEY_STATUS},
                    KEY_COURSE_ID + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }



    public Cursor fetchCoursesByRowId(long row_id) throws SQLException {
        Log.w(TAG, String.valueOf(row_id));
        Cursor mCursor = null;
        if (row_id == 0)  {
            mCursor = mDb.query(COURSES_TABLE, new String[] {KEY_ID,
                            KEY_COURSE_ID, KEY_STATUS},
                    null, null, null, null, null);

        }
        else {
            mCursor = mDb.query(true, COURSES_TABLE, new String[] {KEY_ID,
                            KEY_COURSE_ID, KEY_STATUS},
                    KEY_ID + " like '%" + row_id + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public void insertInitialCourses() {

        createCourse("CSC101", "false");
        createCourse("CSC102", "false");
        createCourse("CSC103", "false");
        createCourse("CSC104", "false");
        createCourse("CSC105", "false");
        createCourse("CSC106", "false");
        createCourse("CSC107", "false");

    }

    public void insertStudentCourses(String studentId){
        createStudentCourse(studentId, "CSC101", "false");
        createStudentCourse(studentId, "CSC102", "false");
        createStudentCourse(studentId, "CSC103", "false");
        createStudentCourse(studentId, "CSC104", "false");
        createStudentCourse(studentId, "CSC105", "false");
        createStudentCourse(studentId, "CSC106", "false");
        createStudentCourse(studentId, "CSC107", "false");
    }
}

