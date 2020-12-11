package com.example.btl_nam3_v1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Path;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class OptionGame extends AppCompatActivity {
    TextView editTextSoCauHoiOption,editTextTienKhoiDauOption,editTextTienThuongOption,editTextHelp5050Option,editTextGoiDTOption,editTextKhanGiaOption,editTextAsk3Option,editTextSkipOption,editTextGiaLuot;
    Button buttonSaveOption,buttonDefaultOption,buttonBackOption;
    CheckBox checkBoxNeverLose;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_game);
        setTitle("Tuỳ chọn");

        editTextSoCauHoiOption=findViewById(R.id.editTextSoCauHoiOption);
        editTextTienKhoiDauOption=findViewById(R.id.editTextTienKhoiDauOption);
        editTextTienThuongOption=findViewById(R.id.editTextTienThuongOption);
        editTextHelp5050Option=findViewById(R.id.editTextHelp5050Option);
        editTextGoiDTOption=findViewById(R.id.editTextGoiDTOption);
        editTextKhanGiaOption=findViewById(R.id.editTextKhanGiaOption);
        editTextAsk3Option=findViewById(R.id.editTextAsk3Option);
        editTextSkipOption=findViewById(R.id.editTextSkipOption);
        editTextGiaLuot=findViewById(R.id.editTextGiaLuot);
        buttonSaveOption=findViewById(R.id.buttonSaveOption);
        buttonDefaultOption=findViewById(R.id.buttonDefaultOption);
        buttonBackOption=findViewById(R.id.buttonBackOption);
        checkBoxNeverLose=findViewById(R.id.checkBoxNeverLose);

        if(MainActivity.checkboxNeverLose==true){
            checkBoxNeverLose.setChecked(true);
        }
        else{
            checkBoxNeverLose.setChecked(false);
        }

        setTextOption();

        buttonSaveOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Kiểm tra thông tin nhập và cảnh báo
                String mes = "";
                if(TextUtils.isEmpty(editTextSoCauHoiOption.getText().toString().trim())){
                    mes += " Số câu hỏi";
                }
                if(TextUtils.isEmpty(editTextTienKhoiDauOption.getText().toString().trim())){
                    mes += " Số tiền khởi đầu";
                }
                if(TextUtils.isEmpty(editTextTienThuongOption.getText().toString().trim())){
                    mes += " Số tiền thưởng";
                }
                if(TextUtils.isEmpty(editTextHelp5050Option.getText().toString().trim())){
                    mes += " Trợ giúp 1";
                }
                if(TextUtils.isEmpty(editTextGoiDTOption.getText().toString().trim())){
                    mes += " Trợ giúp 2";
                }
                if(TextUtils.isEmpty(editTextKhanGiaOption.getText().toString().trim())){
                    mes += " Trợ giúp 3";
                }
                if(TextUtils.isEmpty(editTextAsk3Option.getText().toString().trim())){
                    mes += " Trợ giúp 4";
                }
                if(TextUtils.isEmpty(editTextSkipOption.getText().toString().trim())){
                    mes += " Trợ giúp 5";
                }
                if(TextUtils.isEmpty(editTextGiaLuot.getText().toString().trim())){
                    mes += " Giá lượt mua";
                }
                if (!TextUtils.isEmpty(mes)) {
                    Toast.makeText(OptionGame.this,"Vui lòng nhập giá trị" +mes,Toast.LENGTH_SHORT).show();
                    return;
                }
                if(Integer.parseInt(editTextSoCauHoiOption.getText().toString())>MainActivity.dbHelper.getQuestionsCount()){
                    Toast.makeText(OptionGame.this, "Số câu hỏi phải nhỏ hơn hoặc bằng "+MainActivity.dbHelper.getQuestionsCount(), Toast.LENGTH_SHORT).show();
                    return;
                }
                MainActivity.SoCauHoiOption=Integer.parseInt(editTextSoCauHoiOption.getText().toString());
                MainActivity.TienKhoiDauOption=Integer.parseInt(editTextTienKhoiDauOption.getText().toString());
                MainActivity.TienThuongOption=Integer.parseInt(editTextTienThuongOption.getText().toString());
                MainActivity.Help5050Option=Integer.parseInt(editTextHelp5050Option.getText().toString());
                MainActivity.GoiDTOption=Integer.parseInt(editTextGoiDTOption.getText().toString());
                MainActivity.KhanGiaOption=Integer.parseInt(editTextKhanGiaOption.getText().toString());
                MainActivity.Ask3Option=Integer.parseInt(editTextAsk3Option.getText().toString());
                MainActivity.SkipOption=Integer.parseInt(editTextSkipOption.getText().toString());
                MainActivity.SkipOption=Integer.parseInt(editTextSkipOption.getText().toString());
                MainActivity.GiaLuot=Integer.parseInt(editTextGiaLuot.getText().toString());
                if(checkBoxNeverLose.isChecked()){
                    MainActivity.checkboxNeverLose=true;
                }
                else{
                    MainActivity.checkboxNeverLose=false;
                }

                Toast.makeText(OptionGame.this, "Thiết lập thành công", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        buttonDefaultOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DefaultGame();
                setTextOption();
                Toast.makeText(OptionGame.this, "Đã về giá trị mặc định , cần ấn SAVE ", Toast.LENGTH_SHORT).show();
            }
        });

        buttonBackOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void DefaultGame(){
        MainActivity.SoCauHoiOption=15;
        MainActivity.TienKhoiDauOption=-100;
        MainActivity.TienThuongOption=100;
        MainActivity.Help5050Option=1;
        MainActivity.GoiDTOption=1;
        MainActivity.KhanGiaOption=1;
        MainActivity.Ask3Option=1;
        MainActivity.SkipOption=1;
        MainActivity.GiaLuot=300;
    }

    public void setTextOption(int soCauHoiOption,int tienKhoiDauOption,int tienThuongOption,int help5050Option,int goiDTOption,int khanGiaOption,int ask3Option,int skipOption,int giaLuot){
        editTextSoCauHoiOption.setText(""+soCauHoiOption);
        editTextTienKhoiDauOption.setText(""+tienKhoiDauOption);
        editTextTienThuongOption.setText(""+tienThuongOption);
        editTextHelp5050Option.setText(""+help5050Option);
        editTextGoiDTOption.setText(""+goiDTOption);
        editTextKhanGiaOption.setText(""+khanGiaOption);
        editTextAsk3Option.setText(""+ask3Option);
        editTextSkipOption.setText(""+skipOption);
        editTextGiaLuot.setText(""+giaLuot);
    }
    public void setTextOption(){
        editTextSoCauHoiOption.setText(""+MainActivity.SoCauHoiOption);
        editTextTienKhoiDauOption.setText(""+MainActivity.TienKhoiDauOption);
        editTextTienThuongOption.setText(""+MainActivity.TienThuongOption);
        editTextHelp5050Option.setText(""+MainActivity.Help5050Option);
        editTextGoiDTOption.setText(""+MainActivity.GoiDTOption);
        editTextKhanGiaOption.setText(""+MainActivity.KhanGiaOption);
        editTextAsk3Option.setText(""+MainActivity.Ask3Option);
        editTextSkipOption.setText(""+MainActivity.SkipOption);
        editTextGiaLuot.setText(""+MainActivity.GiaLuot);
    }
}
