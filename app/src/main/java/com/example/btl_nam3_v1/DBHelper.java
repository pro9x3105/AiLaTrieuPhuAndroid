package com.example.btl_nam3_v1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DBHelper extends SQLiteOpenHelper {

    private static final String dbName = "myQuestion1";
    private static final int VERSION= 1;

    private static final String table_name="Question";
    private static final String STT="sTT";
    private static final String CauHoi="cauHoi";
    private static final String AnswerTrue = "answerTrue";
    private static final String AnswerFake1 = "answerFake1";
    private static final String AnswerFake2 = "answerFake2";
    private static final String AnswerFake3 = "answerFake3";
    private static final String Suggest = "suggest";

    private SQLiteDatabase myQuestion;

    public DBHelper(Context context) {
        super(context, dbName , null, VERSION);
    }

    public static String getSTT() {
        return STT;
    }

    public static String getCauHoi() {
        return CauHoi;
    }

    public static String getAnswerTrue() {
        return AnswerTrue;
    }

    public static String getAnswerFake1() {
        return AnswerFake1;
    }

    public static String getAnswerFake2() {
        return AnswerFake2;
    }

    public static String getAnswerFake3() {
        return AnswerFake3;
    }

    public static String getSuggest() {
        return Suggest;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query1 = "CREATE TABLE " + table_name + "( " + STT + " INTEGER PRIMARY KEY, " + CauHoi + " TEXT NOT NULL, " + AnswerTrue + " TEXT NOT NULL, " + AnswerFake1 + " TEXT NOT NULL, "+ AnswerFake2 + " TEXT NOT NULL, "+ AnswerFake3 + " TEXT NOT NULL, "+ Suggest + " TEXT NOT NULL " + " )";
        db.execSQL(query1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        db.execSQL("DROP TABLE IF EXISTS "+table_name);
        this.onCreate(db);
        */
    }

    public void DeleteTable(){
        myQuestion.execSQL("DROP TABLE "+table_name);
        this.onCreate(myQuestion);
    }

    public void openDB(){
        myQuestion = getWritableDatabase();
    }

    public void closeDB(){
        if (myQuestion!=null &&myQuestion.isOpen())	{
            myQuestion.close();
        }
    }

    public long Insert(int sTT,String cauHoi, String answerTrue, String answerFake1, String answerFake2, String answerFake3, String suggest){
        ContentValues values =new ContentValues();
        values.put(STT,sTT);
        values.put(CauHoi,cauHoi);
        values.put(AnswerTrue,answerTrue);
        values.put(AnswerFake1,answerFake1);
        values.put(AnswerFake2,answerFake2);
        values.put(AnswerFake3,answerFake3);
        values.put(Suggest,suggest);
        return myQuestion.insert(table_name,null,values);
    }

    public long Update(int sTT,String cauHoi, String answerTrue, String answerFake1, String answerFake2, String answerFake3, String suggest){
        ContentValues values =new ContentValues();
        values.put(STT,sTT);
        values.put(CauHoi,cauHoi);
        values.put(AnswerTrue,answerTrue);
        values.put(AnswerFake1,answerFake1);
        values.put(AnswerFake2,answerFake2);
        values.put(AnswerFake3,answerFake3);
        values.put(Suggest,suggest);
        String where = STT + " = " + sTT;
        return myQuestion.update(table_name,values,where,null);
    }

    public long Delete(int sTT){
        String where = STT + " = " + sTT;
        return myQuestion.delete(table_name,where,null);
    }

    public Cursor getAllRecord (){
        String query = "SELECT * FROM " + table_name + " ORDER BY STT ASC";
        return myQuestion.rawQuery(query, null);
    }

    public ArrayList<Question> getQuestionbySTT(int stt) {
        ArrayList<Question> listQuestion = new ArrayList<>();
        String query = "SELECT * FROM " + table_name + " WHERE STT = " + stt;
        Cursor cursor = myQuestion.rawQuery(query, null); //getReadableDatabase()
        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setSTT(cursor.getString(cursor.getColumnIndex(DBHelper.getSTT())));
                question.setCauHoi(cursor.getString(cursor.getColumnIndex(DBHelper.getCauHoi())));
                question.setAnswerTrue(cursor.getString(cursor.getColumnIndex(DBHelper.getAnswerTrue())));
                question.setAnswerFake1(cursor.getString(cursor.getColumnIndex(DBHelper.getAnswerFake1())));
                question.setAnswerFake2(cursor.getString(cursor.getColumnIndex(DBHelper.getAnswerFake2())));
                question.setAnswerFake3(cursor.getString(cursor.getColumnIndex(DBHelper.getAnswerFake3())));
                question.setSuggest(cursor.getString(cursor.getColumnIndex(DBHelper.getSuggest())));
                listQuestion.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listQuestion;
    }
    //Lay toan bo cau hoi theo ArrayList
    public ArrayList<Question> getAllQuestions() {
        ArrayList<Question> listQuestion = new ArrayList<>();
        String query = "SELECT * FROM " + table_name + " ORDER BY STT ASC";
    Cursor cursor = myQuestion.rawQuery(query, null); //getReadableDatabase()
        if (cursor.moveToFirst()) {
            do {
                Question question = new Question();
                question.setSTT(cursor.getString(cursor.getColumnIndex(DBHelper.getSTT())));
                question.setCauHoi(cursor.getString(cursor.getColumnIndex(DBHelper.getCauHoi())));
                question.setAnswerTrue(cursor.getString(cursor.getColumnIndex(DBHelper.getAnswerTrue())));
                question.setAnswerFake1(cursor.getString(cursor.getColumnIndex(DBHelper.getAnswerFake1())));
                question.setAnswerFake2(cursor.getString(cursor.getColumnIndex(DBHelper.getAnswerFake2())));
                question.setAnswerFake3(cursor.getString(cursor.getColumnIndex(DBHelper.getAnswerFake3())));
                question.setSuggest(cursor.getString(cursor.getColumnIndex(DBHelper.getSuggest())));
                listQuestion.add(question);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listQuestion;
    }

    //Đếm số bản ghi
    public int getQuestionsCount() {
        String countQuery = "SELECT * FROM " + table_name;
        Cursor cursor = myQuestion.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    //Kiểm tra stt có bị trùng không , có thì là 1 , không thì là 0
    public int checkIfExist(int stt) {
        String countQuery = "SELECT * FROM " + table_name + " WHERE " + STT + " = " + stt;
        Cursor cursor = myQuestion.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }




}
