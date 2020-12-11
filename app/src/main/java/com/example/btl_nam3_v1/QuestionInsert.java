package com.example.btl_nam3_v1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionInsert extends AppCompatActivity {
    EditText editTextSTTInsert,editTextCauHoiInsert,editTextAnswerTrueInsert,editTextAnswerFake1Insert,editTextAnswerFake2Insert,editTextAnswerFake3Insert,editTextSuggestInsert;
    Button buttonInsertInsert,buttonResetInsert,buttonBackInsert;
    DBHelper dbHelper;
    int check_Insert_or_Edit ; //Insert = false(0) , Edit = true(1)
    String s="";
    int STT_for_Edit;

    private String getValue(EditText editText){
        return editText.getText().toString().trim();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_insert);



        //Anh xa
        editTextSTTInsert=findViewById(R.id.editTextSTTInsert);
        editTextCauHoiInsert=findViewById(R.id.editTextCauHoiInsert);
        editTextAnswerTrueInsert=findViewById(R.id.editTextAnswerTrueInsert);
        editTextAnswerFake1Insert=findViewById(R.id.editTextAnswerFake1Insert);
        editTextAnswerFake2Insert=findViewById(R.id.editTextAnswerFake2Insert);
        editTextAnswerFake3Insert=findViewById(R.id.editTextAnswerFake3Insert);
        editTextSuggestInsert=findViewById(R.id.editTextSuggestInsert);
        buttonInsertInsert=findViewById(R.id.buttonInsertInsert);
        buttonResetInsert=findViewById(R.id.buttonResetInsert);
        buttonBackInsert=findViewById(R.id.buttonBackInsert);

        dbHelper=MainActivity.dbHelper;

        //Lấy dữ liệu từ bên show_all về
        Intent myIntent = getIntent();
        String valueMode = myIntent.getStringExtra("Mode");
        if (valueMode.equals("Insert")) {  //Insert mode
            check_Insert_or_Edit = 0;
            editTextSTTInsert.setEnabled(true);
            s="Thêm";
            buttonInsertInsert.setText(s);
        }
        if (valueMode.equals("Edit")) {  //Edit mode
            check_Insert_or_Edit = 1;
            editTextSTTInsert.setEnabled(false);
            STT_for_Edit=Integer.parseInt(myIntent.getStringExtra("STT"));
            s="Sửa";
            buttonInsertInsert.setText(s);

            ArrayList<Question> editQuestion = dbHelper.getQuestionbySTT(STT_for_Edit);
            //thêm dữ liệu vào các edittext
            editTextSTTInsert.setText(editQuestion.get(0).getSTT());
            editTextCauHoiInsert.setText(editQuestion.get(0).getCauHoi());
            editTextAnswerTrueInsert.setText(editQuestion.get(0).getAnswerTrue());
            editTextAnswerFake1Insert.setText(editQuestion.get(0).getAnswerFake1());
            editTextAnswerFake2Insert.setText(editQuestion.get(0).getAnswerFake2());
            editTextAnswerFake3Insert.setText(editQuestion.get(0).getAnswerFake3());
            editTextSuggestInsert.setText(editQuestion.get(0).getSuggest());
        }

        //Sự kiện click insert button
        buttonInsertInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Kiểm tra thông tin nhập và cảnh báo
                String mes = "";
                if(TextUtils.isEmpty(editTextSTTInsert.getText().toString().trim())){
                    mes += " STT";
                }
                if(TextUtils.isEmpty(editTextCauHoiInsert.getText().toString().trim())){
                    mes += " tên câu hỏi";
                }
                if(TextUtils.isEmpty(editTextAnswerTrueInsert.getText().toString().trim())){
                    mes += " đáp án đúng";
                }
                if(TextUtils.isEmpty(editTextAnswerFake1Insert.getText().toString().trim())){
                    mes += " đáp án khác 1";
                }
                if(TextUtils.isEmpty(editTextAnswerFake2Insert.getText().toString().trim())){
                    mes += " đáp án khác 2";
                }
                if(TextUtils.isEmpty(editTextAnswerFake3Insert.getText().toString().trim())){
                    mes += " đáp án khác 3";
                }
                if(TextUtils.isEmpty(editTextSuggestInsert.getText().toString().trim())){
                    mes += " gợi ý";
                }
                if (!TextUtils.isEmpty(mes)) {
                    Toast.makeText(QuestionInsert.this,"Vui long nhap gia tri" +mes,Toast.LENGTH_SHORT).show();
                    return;
                }
                //Kiểm tra trùng STT
                int stt = Integer.parseInt(editTextSTTInsert.getText().toString().trim());
                int checkIfExist = dbHelper.checkIfExist(stt);
                if ((checkIfExist==1 && check_Insert_or_Edit==1)) {     //Nếu = 1 là có tồn tại => Edit , nếu = 0 thì Insert
                    //Edit mode
                    long resultUpdate = dbHelper.Update(Integer.parseInt(
                            getValue(editTextSTTInsert)),
                            getValue(editTextCauHoiInsert),
                            getValue(editTextAnswerTrueInsert),
                            getValue(editTextAnswerFake1Insert),
                            getValue(editTextAnswerFake2Insert),
                            getValue(editTextAnswerFake3Insert),
                            getValue(editTextSuggestInsert));
                    if (resultUpdate==-1){
                        Toast.makeText(QuestionInsert.this," Lỗi ",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //Đưa bundle về show_all
                    else if (check_Insert_or_Edit==0){
                        Intent myIntent= new Intent();
                        myIntent.putExtra("STT",editTextSTTInsert.getText().toString());
                        myIntent.putExtra("Question",editTextCauHoiInsert.getText().toString());
                        Toast.makeText(QuestionInsert.this,s+" thành công ", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK,myIntent);
                        finish();
                    }
                }
                else{
                    //Insert mode
                    //Thêm vào sqlite
                    Cursor cursor = dbHelper.getAllRecord();
                    ArrayList<Question> listQuestion = new ArrayList<>();
                    cursor.moveToFirst();
                    while (!cursor.isAfterLast()) { //Đẩy xuống cuối và kiểm tra STT
                        listQuestion.add(new Question(cursor.getString(cursor.getColumnIndex(DBHelper.getSTT())))); //add the item
                        cursor.moveToNext();
                    }

                    long resultAdd = dbHelper.Insert(Integer.parseInt(
                            getValue(editTextSTTInsert)),
                            getValue(editTextCauHoiInsert),
                            getValue(editTextAnswerTrueInsert),
                            getValue(editTextAnswerFake1Insert),
                            getValue(editTextAnswerFake2Insert),
                            getValue(editTextAnswerFake3Insert),
                            getValue(editTextSuggestInsert));
                    if (resultAdd==-1){
                        Toast.makeText(QuestionInsert.this," Lỗi ",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    //Đưa bundle về show_all
                    else{
                        Intent myIntent= new Intent();
                        myIntent.putExtra("STT",editTextSTTInsert.getText().toString());
                        myIntent.putExtra("Question",editTextCauHoiInsert.getText().toString());
                        Toast.makeText(QuestionInsert.this,s+" thành công ", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK,myIntent);
                        finish();
                    }
                }
            }
        });

        buttonResetInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check_Insert_or_Edit==0){
                    editTextSTTInsert.setText("");
                }
                editTextCauHoiInsert.setText("");
                editTextAnswerTrueInsert.setText("");
                editTextAnswerFake1Insert.setText("");
                editTextAnswerFake2Insert.setText("");
                editTextAnswerFake3Insert.setText("");
                editTextSuggestInsert.setText("");
            }
        });


        buttonBackInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



}
