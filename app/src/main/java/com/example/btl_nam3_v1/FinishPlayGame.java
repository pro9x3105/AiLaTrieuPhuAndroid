package com.example.btl_nam3_v1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FinishPlayGame extends AppCompatActivity {

    TextView textViewInfoFinish;
    Button buttonSave,buttonBackFinish,buttonShowHighScore;

    private LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_play_game);

        textViewInfoFinish=findViewById(R.id.textViewInfoFinish);
        buttonBackFinish=findViewById(R.id.buttonBackFinish);
        buttonSave=findViewById(R.id.buttonSave);
        buttonShowHighScore=findViewById(R.id.buttonShowHighScore);

        Intent myIntent = getIntent();
        final int cau = myIntent.getIntExtra("cau",0);
        final int money = myIntent.getIntExtra("money",0);
        textViewInfoFinish.setText("Chúc mừng bạn đã trả lời đúng "+ cau + " câu và nhận được "+ money +" $");

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Đặt View view
                inflater = LayoutInflater.from(FinishPlayGame.this);
                View view = inflater.inflate(R.layout.activity_input_ho_ten, null);
                //Lấy edittext từ bên input_ho_ten
                final EditText userInput = (EditText) view.findViewById(R.id.editTextInputHoTen);
                AlertDialog.Builder alert = new AlertDialog.Builder(FinishPlayGame.this);
                alert.setTitle("Lưu kết quả");
                alert.setView(view);
                alert.setPositiveButton("Lưu", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Yes :
                        //lấy STT nếu ko trùng
                        int i=0;
                        while(i>=0){
                            if(MainActivity.dbHelper_forHighScore.checkIfExist(i)==1){
                                i++;
                            }
                            else{
                                break;
                            }
                        }
                        MainActivity.dbHelper_forHighScore.Insert(i, userInput.getText().toString(),cau,money);
                        Toast.makeText(FinishPlayGame.this,"Đã lưu",Toast.LENGTH_SHORT).show();
                    }
                });
                alert.setNegativeButton("Không",null);
                alert.show();
            }
        });
        buttonBackFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(FinishPlayGame.this,MainActivity.class);
                startActivity(myIntent);
            }
        });
        buttonShowHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(FinishPlayGame.this,showHighScore.class);
                startActivity(myIntent);
            }
        });
    }
}
