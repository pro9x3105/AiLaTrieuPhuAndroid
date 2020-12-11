package com.example.btl_nam3_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Path;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {
    //Khai báo
    Button buttonPlay,buttonOption,buttonAbout,buttonQuestion,buttonHighScore,buttonRule;
    public static DBHelper dbHelper;
    public static DBHelper_forHighScore dbHelper_forHighScore;
    public static int SoCauHoiOption=15,TienKhoiDauOption=-100,TienThuongOption=100,Help5050Option=1,GoiDTOption=1,KhanGiaOption=1,Ask3Option=1,SkipOption=1,GiaLuot=300;
    public static boolean checkboxNeverLose=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Main Menu");

        //Ánh xạ
        buttonPlay=findViewById(R.id.buttonPlay);
        buttonOption=findViewById(R.id.buttonOption);
        buttonAbout=findViewById(R.id.buttonAbout);
        buttonQuestion=findViewById(R.id.buttonQuestion);
        buttonHighScore=findViewById(R.id.buttonHighScore);
        buttonRule=findViewById(R.id.buttonRule);


        dbHelper = new DBHelper(MainActivity.this);
        dbHelper.openDB();

        dbHelper_forHighScore = new DBHelper_forHighScore(MainActivity.this);
        dbHelper_forHighScore.openDB();

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,PlayGame.class);
                startActivity(intent);
            }
        });

        buttonOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,OptionGame.class);
                startActivity(intent);

            }
        });
        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,about_us.class);
                startActivity(intent);
            }
        });
        buttonQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,show_all.class);
                startActivity(intent);
            }
        });
        buttonHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,showHighScore.class);
                startActivity(intent);
            }
        });
        buttonRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RuleGame.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.closeDB();
        dbHelper_forHighScore.closeDB();
    }
}
