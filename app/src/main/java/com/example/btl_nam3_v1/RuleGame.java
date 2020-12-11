package com.example.btl_nam3_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RuleGame extends AppCompatActivity {

    Button buttonBackRule;
    TextView textViewRule;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rule_game);
        setTitle("Luật chơi");

        textViewRule=findViewById(R.id.textViewRule);
        buttonBackRule=findViewById(R.id.buttonBackRule);

        textViewRule.setText("Mỗi câu hỏi có 4 đáp án , hãy chọn đáp án đúng nhất \n Mỗi lượt chơi có 5 sự trợ giúp , hãy sử dụng hợp lý");
        buttonBackRule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
