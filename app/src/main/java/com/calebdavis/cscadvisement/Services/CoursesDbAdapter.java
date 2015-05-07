package com.calebdavis.cscadvisement.Services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.calebdavis.cscadvisement.SQLiteTableClasses.StudentCourse;

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
    public static final String KEY_STUDENT_ID = "student_id";
    public static final String KEY_SEMESTER = "semester";
    public static final String KEY_CODE = "code";
    public static final String KEY_INSTRUCTOR = "instructor";



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
            + KEY_STUDENT_ID + " TEXT, " + KEY_COURSE_ID + " TEXT, " +  KEY_STATUS  + " TEXT, " + KEY_SEMESTER + " TEXT, " + KEY_CODE + " INTEGER, "  + KEY_INSTRUCTOR + " TEXT"+")";

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

    public List<StudentCourse> getAllCoursesNotTakenByStudent(String student_id, String notTaken) {
        List<StudentCourse> courses = new ArrayList<StudentCourse>();

        String selectQuery = "SELECT * FROM " + STUDENT_COURSES_TABLE + " WHERE "
                + KEY_STUDENT_ID + " = '" + student_id + "'" + " AND " + KEY_STATUS
                + " = '" + notTaken + "'";


        Log.e(TAG, selectQuery);

        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = mDb.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                StudentCourse course = new StudentCourse();
                course.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                course.setSid(c.getString(c.getColumnIndex(KEY_STUDENT_ID)));
                course.setCourseId((c.getString(c.getColumnIndex(KEY_COURSE_ID))));
                course.setTaken(c.getString(c.getColumnIndex(KEY_STATUS)));
                course.setSemester(c.getString(c.getColumnIndex(KEY_SEMESTER)));
                course.setCode(c.getInt(c.getColumnIndex(KEY_CODE)));
                course.setInstructor(c.getString(c.getColumnIndex(KEY_INSTRUCTOR)));


                // adding to courses taken list
                courses.add(course);
            } while (c.moveToNext());
        }

        //mDb.close();
        //c.close();
        return courses;
    }

    public List<StudentCourse> getAllFallCoursesNotTakenByStudent(String student_id, String notTaken, String fall) {
        List<StudentCourse> courses = new ArrayList<StudentCourse>();

        String selectQuery = "SELECT * FROM " + STUDENT_COURSES_TABLE + " WHERE "
                + KEY_STUDENT_ID + " = '" + student_id + "'" + " AND " + KEY_STATUS
                + " = '" + notTaken + "'" + " AND " + KEY_SEMESTER + " = '" + fall + "'";


        Log.e(TAG, selectQuery);

        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = mDb.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                StudentCourse course = new StudentCourse();
                course.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                course.setSid(c.getString(c.getColumnIndex(KEY_STUDENT_ID)));
                course.setCourseId((c.getString(c.getColumnIndex(KEY_COURSE_ID))));
                course.setTaken(c.getString(c.getColumnIndex(KEY_STATUS)));
                course.setCode(c.getInt(c.getColumnIndex(KEY_CODE)));
                course.setSemester(c.getString(c.getColumnIndex(KEY_SEMESTER)));
                course.setInstructor(c.getString(c.getColumnIndex(KEY_INSTRUCTOR)));

                // adding to courses taken list
                courses.add(course);
            } while (c.moveToNext());
        }

        //mDb.close();
        //c.close();
        return courses;
    }

    public List<StudentCourse> getAllBothSemesterCoursesNotTakenByStudent(String student_id, String notTaken, String both) {
        List<StudentCourse> courses = new ArrayList<StudentCourse>();


        String selectQuery = "SELECT * FROM " + STUDENT_COURSES_TABLE + " WHERE "
                + KEY_STUDENT_ID + " = '" + student_id + "'" + " AND " + KEY_STATUS
                + " = '" + notTaken + "'" + " AND " + KEY_SEMESTER + " = '" + both + "'" ;


        Log.e(TAG, selectQuery);

        //SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = mDb.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                StudentCourse course = new StudentCourse();
                course.setId(c.getInt((c.getColumnIndex(KEY_ID))));
                course.setSid(c.getString(c.getColumnIndex(KEY_STUDENT_ID)));
                course.setCourseId((c.getString(c.getColumnIndex(KEY_COURSE_ID))));
                course.setTaken(c.getString(c.getColumnIndex(KEY_STATUS)));
                course.setCode(c.getInt(c.getColumnIndex(KEY_CODE)));
                course.setSemester(c.getString(c.getColumnIndex(KEY_SEMESTER)));
                course.setInstructor(c.getString(c.getColumnIndex(KEY_INSTRUCTOR)));

                // adding to courses taken list
                courses.add(course);
            } while (c.moveToNext());
        }

        //mDb.close();
        //c.close();
        return courses;
    }

    public long createStudentCourse(String student_id, String course_id, String status, String semester, int code, String instructor){
        ContentValues values = new ContentValues();
        values.put(KEY_STUDENT_ID, student_id);
        values.put(KEY_COURSE_ID, course_id);
        values.put(KEY_STATUS, status);
        values.put(KEY_SEMESTER, semester);
        values.put(KEY_CODE, code);
        values.put(KEY_INSTRUCTOR, instructor);


        long id =  mDb.insert(STUDENT_COURSES_TABLE, null, values);
        return id;
    }

    public long createStudent(String student_id) {
        ContentValues values = new ContentValues();
        values.put(KEY_STUDENT_ID, student_id);

        return mDb.insert(STUDENTS_TABLE, null, values);
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
                course.setSemester(c.getString(c.getColumnIndex(KEY_SEMESTER)));
                course.setCode(c.getInt(c.getColumnIndex(KEY_CODE)));
                course.setInstructor(c.getString(c.getColumnIndex(KEY_INSTRUCTOR)));

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

    public boolean updateItem(long row_id, String status) {
        ContentValues values = new ContentValues();
        values.put(KEY_STATUS, status);


        return mDb.update(STUDENT_COURSES_TABLE, values, KEY_ID + "=" + row_id, null) > 0;
    }

    public boolean deleteAllStudentCourses(){
        int doneDelete = 0;
        doneDelete = mDb.delete(STUDENT_COURSES_TABLE, null, null);
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0;
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

    public void insertStudentCourses(String studentId){


        createStudentCourse(studentId, "CSC 101 - Intro to Programming", "false", "Both" , 101, "Glenn Bond");
        createStudentCourse(studentId, "CSC 102 - Advanced Computer Programming", "false", "Both", 102, "Glenn Bond");
        createStudentCourse(studentId, "CSC 203 - Intro to Computer Systems", "false", "Fall", 203, "Wonryull Koh");
        createStudentCourse(studentId, "CSC 204 - Computer Organizations", "false", "Spring", 204, "TBA");
        createStudentCourse(studentId, "CSC 300 - Foundations of Computer Science", "false", "Fall", 300, "Bikramjit Banerjee");
        createStudentCourse(studentId, "CSC 306 - Operating Systems", "false", "Fall", 306, "Glover George");
        createStudentCourse(studentId, "CSC 307 - Data Structures", "false", "Both", 307, "Beddhu Murali");
        createStudentCourse(studentId, "CSC 309 - Computers and Society", "false", "Both", 309, "Glenn Bond");
        createStudentCourse(studentId, "CSC 317 - Object Oriented Programming", "false", "Spring", 317, "Wonryull Koh");
        createStudentCourse(studentId, "CSC 320 - Intro to Linear Programming", "false", "Fall", 320, "Bikramjit Banerjee");
        createStudentCourse(studentId, "CSC 408 - Organization of Programming Languages", "false", "Fall", 408, "Dia Ali");
        createStudentCourse(studentId, "CSC 411 - Relational Database Management Systems", "false", "Fall", 411, "Nan Wang");
        createStudentCourse(studentId, "CSC 412 - Intro to Artificial Intelligence", "false", "Spring", 412, "Bikramjit Banerjee");
        createStudentCourse(studentId, "CSC 413 - Algorithms", "false", "Fall", 413, "Wonryull Koh");
        createStudentCourse(studentId, "CSC 414 - Software Design and Development", "false", "Fall", 414, "Dia Ali");
        createStudentCourse(studentId, "CSC 415 - Theory of Programming Languages", "false", "Spring", 415, "Dia Ali");
        createStudentCourse(studentId, "CSC 424 - Software Engineering II", "false", "Spring", 424, "Nan Wang");

    }
}