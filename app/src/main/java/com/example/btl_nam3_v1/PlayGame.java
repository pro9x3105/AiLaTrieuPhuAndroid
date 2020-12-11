package com.example.btl_nam3_v1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class PlayGame extends AppCompatActivity {

    //Khai báo
    DBHelper dbHelper = MainActivity.dbHelper;
    TextView textViewNumberQuestion, textViewQuestion,textViewMoney;
    Button buttonAnswerA, buttonAnswerB, buttonAnswerC, buttonAnswerD,buttonBackPlay;
    FloatingActionButton fabHelp,fab50_50,fabCall,fabAskPercent,fabAsk3,fabSkipQuestion;
    TextView textView50_50,textViewCall,textViewAskPercent,textViewAsk3,textViewSwitchQuestion;
    TextView textViewHelp1,textViewHelp2,textViewHelp3,textViewHelp4;
    GifImageView gifCall;

    Boolean anhienFAB = false,checklose=true;
    String AnswerTrue="";
    ArrayList<Question> listQuestion = dbHelper.getAllQuestions();
    ArrayList<String> Answers = new ArrayList<String>();;

    //Ingame
    int numberQuestion = -1,money=MainActivity.TienKhoiDauOption,numberQuestionMax=MainActivity.SoCauHoiOption,moneyBonus=MainActivity.TienThuongOption,gialuot=MainActivity.GiaLuot;
    int Number50_50=MainActivity.Help5050Option,NumberCall=MainActivity.GoiDTOption,NumberAskPercent=MainActivity.KhanGiaOption,NumberAsk3=MainActivity.Ask3Option,NumberSkipQuestion=MainActivity.SkipOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_game);
        setTitle("Play Game");

        //Ánh xạ
        textViewNumberQuestion = findViewById(R.id.textViewNumberQuestion);
        textViewQuestion = findViewById(R.id.textViewQuestion);
        textViewMoney=findViewById(R.id.textViewMoney);
        buttonAnswerA = findViewById(R.id.buttonAnswerA);
        buttonAnswerB = findViewById(R.id.buttonAnswerB);
        buttonAnswerC = findViewById(R.id.buttonAnswerC);
        buttonAnswerD = findViewById(R.id.buttonAnswerD);
        buttonBackPlay = findViewById(R.id.buttonBackPlay);
        fabHelp = findViewById(R.id.fabHelp);
        fab50_50= findViewById(R.id.fab50_50);
        fabCall= findViewById(R.id.fabCall);
        fabAskPercent= findViewById(R.id.fabAskPercent);
        fabAsk3= findViewById(R.id.fabAsk3);
        fabSkipQuestion= findViewById(R.id.fabSkipQuestion);
        textView50_50=findViewById(R.id.textView50_50);
        textViewCall=findViewById(R.id.textViewCall);
        textViewAskPercent=findViewById(R.id.textViewAskPercent);
        textViewAsk3=findViewById(R.id.textViewAsk3);
        textViewSwitchQuestion=findViewById(R.id.textViewSkipQuestion);
        textViewHelp1=findViewById(R.id.textViewHelp1);
        textViewHelp2=findViewById(R.id.textViewHelp2);
        textViewHelp3=findViewById(R.id.textViewHelp3);
        textViewHelp4=findViewById(R.id.textViewHelp4);
        gifCall=findViewById(R.id.gifCall);

        textView50_50.setText("50:50 : "+Number50_50+" lần");
        textViewCall.setText("Gọi ĐT : "+NumberCall+" lần");
        textViewAskPercent.setText("Hỏi khán giả : "+NumberAskPercent+" lần");
        textViewAsk3.setText("Hỏi 3 người : "+NumberAsk3+" lần");
        textViewSwitchQuestion.setText("Bỏ qua : "+NumberSkipQuestion+" lần");
        AnFAB();
        NextQuestion();
        //Sự kiện cho button và kiểm soát câu trả lời
        buttonAnswerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (buttonAnswerA.getText().equals(AnswerTrue)) {
                        Toast.makeText(PlayGame.this, "Đúng", Toast.LENGTH_SHORT).show();
                        listQuestion.remove(0);
                        NextQuestion();
                    }
                    else {
                        Toast.makeText(PlayGame.this, "Sai", Toast.LENGTH_SHORT).show();
                        if(MainActivity.checkboxNeverLose==false){
                            FinishGame();
                        }
                    }

            }
        });
        buttonAnswerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (buttonAnswerB.getText().equals(AnswerTrue)) {
                        Toast.makeText(PlayGame.this, "Đúng", Toast.LENGTH_SHORT).show();
                        listQuestion.remove(0);
                        NextQuestion();
                    }
                    else {
                        Toast.makeText(PlayGame.this, "Sai", Toast.LENGTH_SHORT).show();
                        if(MainActivity.checkboxNeverLose==false){
                            FinishGame();
                        }
                    }

            }
        });
        buttonAnswerC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (buttonAnswerC.getText().equals(AnswerTrue)) {
                        Toast.makeText(PlayGame.this, "Đúng", Toast.LENGTH_SHORT).show();
                        listQuestion.remove(0);
                        NextQuestion();
                    }
                    else {
                        Toast.makeText(PlayGame.this, "Sai", Toast.LENGTH_SHORT).show();
                        if(MainActivity.checkboxNeverLose==false){
                            FinishGame();
                        }
                    }

            }
        });
        buttonAnswerD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (buttonAnswerD.getText().equals(AnswerTrue)) {
                        Toast.makeText(PlayGame.this, "Đúng", Toast.LENGTH_SHORT).show();
                        listQuestion.remove(0);
                        NextQuestion();
                    }
                    else {
                        Toast.makeText(PlayGame.this, "Sai", Toast.LENGTH_SHORT).show();
                        if(MainActivity.checkboxNeverLose==false){
                            FinishGame();
                        }
                    }

            }
        });
        buttonBackPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Floating Action Button cho Trợ giúp
        fabHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(anhienFAB==false){
                    HienFAB();
                    anhienFAB=true;
                }
                else{
                    AnFAB();
                    anhienFAB=false;
                }
            }
        });

        fab50_50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnFAB();
                anhienFAB=false;
                if(Number50_50>0){
                    Toast.makeText(PlayGame.this,"Đã sử dụng 50:50",Toast.LENGTH_SHORT).show();
                    Help_50_50();
                    Number50_50--;
                    textView50_50.setText("50:50 : "+Number50_50+" lần");
                }
                else{
                    Toast.makeText(PlayGame.this,"Đã hết sự trợ giúp",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(PlayGame.this); //Tạo alert
                    alert.setTitle("Mua lượt trợ giúp ?");
                    alert.setMessage("Bạn có chắc chắn mua trợ giúp ?\n(Giá là "+gialuot+" $ )");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {      //Nút có
                        @Override public void onClick(DialogInterface dialoginterface, int which) {
                            if(money<gialuot){
                                Toast.makeText(PlayGame.this,"Bạn không đủ tiền",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Number50_50++;
                                textView50_50.setText("50:50 : "+Number50_50+" lần");
                                money-=gialuot;
                                textViewMoney.setText("Nhận được : "+money+" $ ");

                            }
                        }
                    });
                    alert.setNegativeButton("No",null);
                    alert.show();

                }
            }
        });
        fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnFAB();
                anhienFAB=false;
                if(NumberCall>0){
                    Toast.makeText(PlayGame.this,"Đã sử dụng gọi điện thoại cho người thân",Toast.LENGTH_SHORT).show();
                    Call();
                    NumberCall--;
                    textViewCall.setText("Gọi ĐT : "+NumberCall+" lần");
                }
                else{
                    Toast.makeText(PlayGame.this,"Đã hết sự trợ giúp",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(PlayGame.this); //Tạo alert
                    alert.setTitle("Mua lượt trợ giúp ?");
                    alert.setMessage("Bạn có chắc chắn mua trợ giúp ?\n(Giá là "+gialuot+" $ )");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {      //Nút có
                        @Override public void onClick(DialogInterface dialoginterface, int which) {
                            if(money<gialuot){
                                Toast.makeText(PlayGame.this,"Bạn không đủ tiền",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                NumberCall++;
                                textViewCall.setText("Gọi ĐT : "+NumberCall+" lần");
                                money-=gialuot;
                                textViewMoney.setText("Nhận được : "+money+" $ ");
                            }
                        }
                    });
                    alert.setNegativeButton("No",null);
                    alert.show();
                }
            }
        });
        fabAskPercent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnFAB();
                anhienFAB=false;
                if(NumberAskPercent>0){
                    Toast.makeText(PlayGame.this,"Đã sử dụng hỏi ý kiến khán giả",Toast.LENGTH_SHORT).show();
                    AskPercent();
                    NumberAskPercent--;
                    textViewAskPercent.setText("Hỏi khán giả : "+NumberAskPercent+" lần");
                }
                else{
                    Toast.makeText(PlayGame.this,"Đã hết sự trợ giúp",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(PlayGame.this); //Tạo alert
                    alert.setTitle("Mua lượt trợ giúp ?");
                    alert.setMessage("Bạn có chắc chắn mua trợ giúp ?\n(Giá là "+gialuot+" $ )");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {      //Nút có
                        @Override public void onClick(DialogInterface dialoginterface, int which) {
                            if(money<gialuot){
                                Toast.makeText(PlayGame.this,"Bạn không đủ tiền",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                NumberAskPercent++;
                                textViewAskPercent.setText("Hỏi khán giả : "+NumberAskPercent+" lần");
                                money-=gialuot;
                                textViewMoney.setText("Nhận được : "+money+" $ ");
                            }
                        }
                    });
                    alert.setNegativeButton("No",null);
                    alert.show();
                }
            }
        });
        fabAsk3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnFAB();
                anhienFAB=false;
                if(NumberAsk3>0){
                    Toast.makeText(PlayGame.this,"Đã sử dụng hỏi đáp 3 khán giả",Toast.LENGTH_SHORT).show();
                    Ask3();
                    NumberAsk3--;
                    textViewAsk3.setText("Hỏi 3 người : "+NumberAsk3+" lần");
                }
                else{
                    Toast.makeText(PlayGame.this,"Đã hết sự trợ giúp",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(PlayGame.this); //Tạo alert
                    alert.setTitle("Mua lượt trợ giúp ?");
                    alert.setMessage("Bạn có chắc chắn mua trợ giúp ?\n(Giá là "+gialuot+" $ )");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {      //Nút có
                        @Override public void onClick(DialogInterface dialoginterface, int which) {
                            if(money<gialuot){
                                Toast.makeText(PlayGame.this,"Bạn không đủ tiền",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                NumberAsk3++;
                                textViewAsk3.setText("Hỏi 3 người : "+NumberAsk3+" lần");
                                money-=gialuot;
                                textViewMoney.setText("Nhận được : "+money+" $ ");
                            }
                        }
                    });
                    alert.setNegativeButton("No",null);
                    alert.show();
                }
            }
        });
        fabSkipQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnFAB();
                anhienFAB=false;
                if(NumberSkipQuestion>0){
                    Toast.makeText(PlayGame.this,"Đã sử dụng bỏ qua câu hỏi",Toast.LENGTH_SHORT).show();
                    SkipQuestion();
                    NumberSkipQuestion--;
                    textViewSwitchQuestion.setText("Bỏ qua : "+NumberSkipQuestion+" lần");
                }
                else{
                    Toast.makeText(PlayGame.this,"Đã hết sự trợ giúp",Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(PlayGame.this); //Tạo alert
                    alert.setTitle("Mua lượt trợ giúp ?");
                    alert.setMessage("Bạn có chắc chắn mua trợ giúp ?\n(Giá là "+gialuot+" $ )");
                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {      //Nút có
                        @Override public void onClick(DialogInterface dialoginterface, int which) {
                            if(money<gialuot){
                                Toast.makeText(PlayGame.this,"Bạn không đủ tiền",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                NumberSkipQuestion++;
                                textViewSwitchQuestion.setText("Bỏ qua : "+NumberSkipQuestion+" lần");
                                money-=gialuot;
                                textViewMoney.setText("Nhận được : "+money+" $ ");
                            }
                        }
                    });
                    alert.setNegativeButton("No",null);
                    alert.show();
                }
            }
        });
    }
    //bật Alert cho đáp án ( skip )
    /*Boolean ThongBao(){
        final Boolean[] check = {false};
        AlertDialog.Builder alert = new AlertDialog.Builder(PlayGame.this); //Tạo alert
        alert.setTitle("Thông báo ?");
        alert.setMessage("Bạn có chắc chắn với đáp án đã chọn ?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {      //Nút có
            @Override public void onClick(DialogInterface dialoginterface, int which) {
                check[0] =true;
            }
        });
        alert.setNegativeButton("No",null);
        alert.show();
        return check[0];
    }
    */

    //Câu hỏi tiếp
    public void NextQuestion(){
        //Khai báo lại ( reset )
        buttonAnswerA.setVisibility(View.VISIBLE);
        buttonAnswerB.setVisibility(View.VISIBLE);
        buttonAnswerC.setVisibility(View.VISIBLE);
        buttonAnswerD.setVisibility(View.VISIBLE);
        textViewHelp1.setVisibility(View.INVISIBLE);
        textViewHelp2.setVisibility(View.INVISIBLE);
        textViewHelp3.setVisibility(View.INVISIBLE);
        textViewHelp4.setVisibility(View.INVISIBLE);
        gifCall.setVisibility(View.INVISIBLE);

        //Khai báo biến
        numberQuestion++;
        checklose=true;
        money+=moneyBonus;
        if((numberQuestion)==numberQuestionMax || dbHelper.getQuestionsCount()==numberQuestionMax){
            FinishGame();
        }

        //Số câu hỏi/tiền
        textViewNumberQuestion.setText("Câu hỏi số : " + (numberQuestion+1));
        textViewMoney.setText("Nhận được : "+money+" $ ");
        //Trộn bộ câu hỏi
        Collections.shuffle(listQuestion);
        //Lấy câu hỏi đầu
        textViewQuestion.setText(listQuestion.get(0).getCauHoi());
        AnswerTrue=listQuestion.get(0).getAnswerTrue();
        //Trộn đáp án
        Answers = new ArrayList<String>();  //Reset gói đáp án
        Answers.add(listQuestion.get(0).getAnswerTrue());
        Answers.add(listQuestion.get(0).getAnswerFake1());
        Answers.add(listQuestion.get(0).getAnswerFake2());
        Answers.add(listQuestion.get(0).getAnswerFake3());
        Collections.shuffle(Answers);
        //Gán vào button
        buttonAnswerA.setText(Answers.get(0));
        buttonAnswerB.setText(Answers.get(1));
        buttonAnswerC.setText(Answers.get(2));
        buttonAnswerD.setText(Answers.get(3));
        //Xoá câu hỏi tránh bị trùng lặp
        //listQuestion.remove(0)
    }



    //Out
    public void FinishGame(){
        Intent myIntent = new Intent(PlayGame.this,FinishPlayGame.class);
        myIntent.putExtra("cau",numberQuestion);
        myIntent.putExtra("money",money);
        startActivity(myIntent);
    }
    //FAB support
    public void HienFAB(){
        fab50_50.show();
        fabCall.show();
        fabAskPercent.show();
        fabAsk3.show();
        fabSkipQuestion.show();
        textView50_50.setVisibility(View.VISIBLE);
        textViewCall.setVisibility(View.VISIBLE);
        textViewAskPercent.setVisibility(View.VISIBLE);
        textViewAsk3.setVisibility(View.VISIBLE);
        textViewSwitchQuestion.setVisibility(View.VISIBLE);
    }
    public void AnFAB(){
        fab50_50.hide();
        fabCall.hide();
        fabAskPercent.hide();
        fabAsk3.hide();
        fabSkipQuestion.hide();
        textView50_50.setVisibility(View.INVISIBLE);
        textViewCall.setVisibility(View.INVISIBLE);
        textViewAskPercent.setVisibility(View.INVISIBLE);
        textViewAsk3.setVisibility(View.INVISIBLE);
        textViewSwitchQuestion.setVisibility(View.INVISIBLE);
    }

    //Trợ giúp
    //50:50
    //Tìm đáp án đúng và tạm thời bỏ ra để random 3 đáp án sai
    public void Help_50_50(){
        ArrayList<String> list_50_50 = Answers;
        for(int i=0;i<4;i++){
            if(list_50_50.get(i).equals(AnswerTrue)){
                list_50_50.remove(i);
                break;
            }
        }
        Collections.shuffle(list_50_50);
        //lọc 2 đáp án sai / đúng
        if(buttonAnswerA.getText().equals(AnswerTrue) || buttonAnswerA.getText().equals(list_50_50.get(0))){
            buttonAnswerA.setVisibility(View.VISIBLE);
        }
        else{
            buttonAnswerA.setVisibility(View.INVISIBLE);
        }

        if(buttonAnswerB.getText().equals(AnswerTrue) || buttonAnswerB.getText().equals(list_50_50.get(0))){
            buttonAnswerB.setVisibility(View.VISIBLE);
        }
        else{
            buttonAnswerB.setVisibility(View.INVISIBLE);
        }

        if(buttonAnswerC.getText().equals(AnswerTrue) || buttonAnswerC.getText().equals(list_50_50.get(0))){
            buttonAnswerC.setVisibility(View.VISIBLE);
        }
        else{
            buttonAnswerC.setVisibility(View.INVISIBLE);
        }

        if(buttonAnswerD.getText().equals(AnswerTrue) || buttonAnswerD.getText().equals(list_50_50.get(0))){
            buttonAnswerD.setVisibility(View.VISIBLE);
        }
        else{
            buttonAnswerD.setVisibility(View.INVISIBLE);
        }

    }

    //Gọi ĐT
    public void Call(){
        gifCall.setVisibility(View.VISIBLE);
        gifCall.refreshDrawableState();
        textViewHelp1.setVisibility(View.VISIBLE);
        textViewHelp2.setVisibility(View.VISIBLE);
        textViewHelp3.setVisibility(View.VISIBLE);
        textViewHelp4.setVisibility(View.VISIBLE);
        textViewHelp1.setText("Tôi : ");
        textViewHelp2.setText("Người lạ : ");
        textViewHelp3.setText("Tôi : ");
        textViewHelp4.setText("Người lạ : ");

        //Đồng hồ đếm ngược
        new CountDownTimer(30000, 1000) {
            public void onTick(long mil) {
                if(mil<30000){
                    textViewHelp1.setText("Tôi : Calling.....");
                }
                if(mil<25000){
                    textViewHelp2.setText("Người lạ : Alo, Tôi nghe");
                }
                if(mil<22000){
                    textViewHelp3.setText("Tôi : "+listQuestion.get(0).getCauHoi());
                }
                if(mil<15000){
                    textViewHelp4.setText("Người lạ : "+listQuestion.get(0).getSuggest());
                    cancel();
                }
            }

            @Override
            public void onFinish() {
                gifCall.setVisibility(View.INVISIBLE);
            }
        }.start();
    }

    //Hỏi khán giả
    public void AskPercent(){
        textViewHelp1.setVisibility(View.VISIBLE);
        textViewHelp2.setVisibility(View.VISIBLE);
        textViewHelp3.setVisibility(View.VISIBLE);
        textViewHelp4.setVisibility(View.VISIBLE);

        int True0,Fake1,Fake2,Fake3;
        Random rd = new Random();

        Fake1=rd.nextInt(25);
        Fake2=rd.nextInt(25);
        Fake3=rd.nextInt(25);
        True0=100-Fake1-Fake2-Fake3;

        if(buttonAnswerA.getText().equals(AnswerTrue)){
            textViewHelp1.setText("A : "+True0+" % ");
            textViewHelp2.setText("B : "+Fake1+" % ");
            textViewHelp3.setText("C : "+Fake2+" % ");
            textViewHelp4.setText("D : "+Fake3+" % ");
        }
        if(buttonAnswerB.getText().equals(AnswerTrue)){
            textViewHelp1.setText("A : "+Fake1+" % ");
            textViewHelp2.setText("B : "+True0+" % ");
            textViewHelp3.setText("C : "+Fake2+" % ");
            textViewHelp4.setText("D : "+Fake3+" % ");
        }
        if(buttonAnswerC.getText().equals(AnswerTrue)){
            textViewHelp1.setText("A : "+Fake1+" % ");
            textViewHelp2.setText("B : "+Fake2+" % ");
            textViewHelp3.setText("C : "+True0+" % ");
            textViewHelp4.setText("D : "+Fake3+" % ");
        }
        if(buttonAnswerD.getText().equals(AnswerTrue)){
            textViewHelp1.setText("A : "+Fake1+" % ");
            textViewHelp2.setText("B : "+Fake2+" % ");
            textViewHelp3.setText("C : "+Fake3+" % ");
            textViewHelp4.setText("D : "+True0+" % ");
        }
    }

    //Hỏi 3 người chơi
    public void Ask3(){
        textViewHelp1.setVisibility(View.VISIBLE);
        textViewHelp2.setVisibility(View.VISIBLE);
        textViewHelp3.setVisibility(View.VISIBLE);
        textViewHelp4.setVisibility(View.VISIBLE);

        ArrayList<String> rateAnswerTrue = new ArrayList<String>();
        textViewHelp1.setText("Xin mời 3 bạn trả lời : ");

        //Rate 40%-20%-20%-20% , Sure 1
        for(int i=0;i<2;i++){
            rateAnswerTrue.add(AnswerTrue);
            rateAnswerTrue.add(AnswerTrue);
            rateAnswerTrue.add(listQuestion.get(0).getAnswerFake1());
            rateAnswerTrue.add(listQuestion.get(0).getAnswerFake2());
            rateAnswerTrue.add(listQuestion.get(0).getAnswerFake3());
        }
        Collections.shuffle(rateAnswerTrue);

        //Chắc chắn ra 1 đáp án đúng hoặc hơn
        if(!AnswerTrue.equals(rateAnswerTrue.get(0)) && !AnswerTrue.equals(rateAnswerTrue.get(1)) && !AnswerTrue.equals(rateAnswerTrue.get(2))){
            ArrayList<Integer> number = new ArrayList<Integer>();
            number.add(0);
            number.add(1);
            number.add(2);
            Collections.shuffle(number);
            rateAnswerTrue.set(number.get(0),AnswerTrue);
        }

        //Lấy 3 kết quả đầu tiên sau khi trộn
        textViewHelp2.setText("Bạn 1 : "+rateAnswerTrue.get(0));
        textViewHelp3.setText("Bạn 2 : "+rateAnswerTrue.get(1));
        textViewHelp4.setText("Bạn 3 : "+rateAnswerTrue.get(2));

    }

    //Đổi câu hỏi
    public void SkipQuestion(){
        listQuestion.remove(0);
        NextQuestion();
    }


}
