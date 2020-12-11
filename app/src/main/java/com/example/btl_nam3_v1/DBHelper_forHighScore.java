package com.example.btl_nam3_v1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DBHelper_forHighScore extends SQLiteOpenHelper {

    private static final String dbName = "myHighScore";
    private static final int VERSION= 1;

    private static final String table_name="HighScore";
    private static final String Id_HighScore="id_HighScore";
    private static final String HoTen_HighScore="hoTen_HighScore";
    private static final String Score_HighScore = "score_HighScore";
    private static final String Money_HighScore = "money_HighScore";

    private SQLiteDatabase myHighScore;

    public DBHelper_forHighScore(Context context) {
        super(context, dbName , null, VERSION);
    }

    public static String getId_HighScore() {
        return Id_HighScore;
    }

    public static String getHoTen_HighScore() {
        return HoTen_HighScore;
    }

    public static String getScore_HighScore() {
        return Score_HighScore;
    }

    public static String getMoney_HighScore() {
        return Money_HighScore;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name + "( " + Id_HighScore + " INTEGER PRIMARY KEY, " + HoTen_HighScore  + " TEXT NOT NULL, " + Score_HighScore + " INTEGER NOT NULL, " + Money_HighScore + " INTEGER NOT NULL )";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*
        db.execSQL("DROP TABLE IF EXISTS "+table_name);
        this.onCreate(db);
        */
    }

    public void DeleteTable(){
        myHighScore.execSQL("DROP TABLE "+table_name);
        this.onCreate(myHighScore);
    }

    public void openDB(){
        myHighScore = getWritableDatabase();
    }

    public void closeDB(){
        if (myHighScore!=null &&myHighScore.isOpen())	{
            myHighScore.close();
        }
    }

    public long Insert(int id_HighScore, String hoTen_HighScore, int score_HighScore, int money_HighScore){
        ContentValues values =new ContentValues();
        values.put(Id_HighScore,id_HighScore);
        values.put(HoTen_HighScore,hoTen_HighScore);
        values.put(Score_HighScore,score_HighScore);
        values.put(Money_HighScore,money_HighScore);
        return myHighScore.insert(table_name,null,values);
    }

    public long Update(int id_HighScore, String hoTen_HighScore, int score_HighScore, int money_HighScore){
        ContentValues values =new ContentValues();
        values.put(Id_HighScore,id_HighScore);
        values.put(HoTen_HighScore,hoTen_HighScore);
        values.put(Score_HighScore,score_HighScore);
        values.put(Money_HighScore,money_HighScore);
        String where = Id_HighScore + " = " + id_HighScore;
        return myHighScore.update(table_name,values,where,null);
    }

    public long Delete(int id_HighScore){
        String where = Id_HighScore + " = " + id_HighScore;
        return myHighScore.delete(table_name,where,null);
    }

    public Cursor getAllRecord (){
        String query = "SELECT * FROM " + table_name + " ORDER BY Score_HighScore DESC";
        return myHighScore.rawQuery(query, null);
    }

    //Lay toan bo nguoi choi theo ArrayList
    public ArrayList<HighScore> getAllHighScores() {
        ArrayList<HighScore> listHighScore = new ArrayList<HighScore>();
        String query = "SELECT * FROM " + table_name + " ORDER BY Score_HighScore DESC";
        Cursor cursor = myHighScore.rawQuery(query, null); //getReadableDatabase()
        if (cursor.moveToFirst()) {
            do {
                HighScore highScore = new HighScore();
                highScore.setId_HighScore(cursor.getString(cursor.getColumnIndex(DBHelper_forHighScore.getId_HighScore())));
                highScore.setHoTen_HighScore(cursor.getString(cursor.getColumnIndex(DBHelper_forHighScore.getHoTen_HighScore())));
                highScore.setScore_HighScore(cursor.getString(cursor.getColumnIndex(DBHelper_forHighScore.getMoney_HighScore())));
                highScore.setMoney_HighScore(cursor.getString(cursor.getColumnIndex(DBHelper_forHighScore.getScore_HighScore())));
                listHighScore.add(highScore);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listHighScore;
    }

    //Lay danh sách theo query
    public ArrayList<HighScore> getAllHighScoreswithQuery(String queryplus) {
        ArrayList<HighScore> listHighScore = new ArrayList<HighScore>();
        String query = "SELECT * FROM " + table_name + queryplus;       //ORDER BY ... ASC/DESC
        Cursor cursor = myHighScore.rawQuery(query, null); //getReadableDatabase()
        if (cursor.moveToFirst()) {
            do {
                HighScore highScore = new HighScore();
                highScore.setId_HighScore(cursor.getString(cursor.getColumnIndex(DBHelper_forHighScore.getId_HighScore())));
                highScore.setHoTen_HighScore(cursor.getString(cursor.getColumnIndex(DBHelper_forHighScore.getHoTen_HighScore())));
                highScore.setScore_HighScore(cursor.getString(cursor.getColumnIndex(DBHelper_forHighScore.getMoney_HighScore())));
                highScore.setMoney_HighScore(cursor.getString(cursor.getColumnIndex(DBHelper_forHighScore.getScore_HighScore())));
                listHighScore.add(highScore);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listHighScore;
    }

    //Kiểm tra id có bị trùng không , có thì là 1 , không thì là 0
    public int checkIfExist(int id) {
        String countQuery = "SELECT * FROM " + table_name + " WHERE " + Id_HighScore + " = " + id;
        Cursor cursor = myHighScore.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }




}
