package com.example.btl_nam3_v1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class showHighScore extends AppCompatActivity {

    EditText editTextTimKiemHighScore;
    ListView listViewHighScore;
    Button buttonXoaHighScore,buttonDefaultHighScore,buttonBackHighScore;

    DBHelper_forHighScore dbHelper_forHighScore = MainActivity.dbHelper_forHighScore;
    private HighScoreCustomAdapter adapter;
    private final ArrayList<HighScore> HighScoreList= new ArrayList<HighScore>();
    boolean checkdoubleclickID=false,checkdoubleclickMoney=false,checkdoubleclickScore=false,checkdoubleclickHoTen=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_high_score);
        setTitle("High Score");

        editTextTimKiemHighScore=findViewById(R.id.editTextTimKiemHighScore);
        listViewHighScore=findViewById(R.id.listViewHighScore);
        buttonXoaHighScore=findViewById(R.id.buttonXoaHighScore);
        buttonDefaultHighScore=findViewById(R.id.buttonDefaultHighScore);
        buttonBackHighScore=findViewById(R.id.buttonBackHighScore);

        ArrayList<HighScore> list= dbHelper_forHighScore.getAllHighScores() ;
        HighScoreList.addAll(list);
        adapter =new HighScoreCustomAdapter(showHighScore.this, HighScoreList);
        listViewHighScore.setAdapter(adapter);

        buttonXoaHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(showHighScore.this); //Tạo alert
                alert.setTitle("Thông báo ?");
                alert.setMessage("Bạn có muốn xoá các bản lưu đã chọn ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {      //Nút có
                    @Override public void onClick(DialogInterface dialoginterface, int which) {
                        //Xoá các lựa chọn
                        for (int i=HighScoreList.size()-1;i>=0;i--){
                            HighScore c = HighScoreList.get(i);
                            if ( c.isSelected() ) {
                                long resultDelete = dbHelper_forHighScore.Delete(Integer.parseInt(HighScoreList.get(i).getId_HighScore()));
                                HighScoreList.remove(i);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton("No",null);
                alert.show();
            }
        });

        buttonDefaultHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(showHighScore.this); //Tạo alert
                alert.setTitle("Thông báo ?");
                alert.setMessage("Bạn có muốn sử dụng dữ liệu mặc định ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {      //Nút có
                    @Override public void onClick(DialogInterface dialoginterface, int which) {
                        AlertDialog.Builder alert1 = new AlertDialog.Builder(showHighScore.this); //Tạo alert
                        alert1.setTitle("Thông báo ?");
                        alert1.setMessage("Bạn có muốn xoá và ghi mỡi dữ liệu hiện tại ( chọn No nếu muốn giữ lại dữ liệu cũ nhưng vẫn muốn thêm mới ) ?");
                        alert1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialoginterface, int which) {
                                //Nút có thì xoá và thêm mới từ đầu
                                //lấy xml và thêm mới
                                dbHelper_forHighScore.DeleteTable();
                                new XmlParser_HighScore(getResources().openRawResource(R.raw.highscores));

                                //Refresh listview ( ko dung dc notifyDataSetChanged() )
                                ArrayList<HighScore> list= dbHelper_forHighScore.getAllHighScores() ;
                                HighScoreList.clear();
                                HighScoreList.addAll(list);
                                adapter =new HighScoreCustomAdapter(showHighScore.this, HighScoreList);
                                listViewHighScore.setAdapter(adapter);

                                adapter.notifyDataSetChanged();
                                Toast.makeText(showHighScore.this,"Đã thêm dữ liệu mặc định",Toast.LENGTH_SHORT).show();
                            }
                        });
                        alert1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Nút không thì giữ nguyên và thêm mới
                                //lấy xml và thêm mới
                                new XmlParser_HighScore(getResources().openRawResource(R.raw.highscores));

                                //Refresh listview ( ko dung dc notifyDataSetChanged() )
                                ArrayList<HighScore> list= dbHelper_forHighScore.getAllHighScores() ;
                                HighScoreList.clear();
                                HighScoreList.addAll(list);
                                adapter =new HighScoreCustomAdapter(showHighScore.this, HighScoreList);
                                listViewHighScore.setAdapter(adapter);

                                adapter.notifyDataSetChanged();
                                Toast.makeText(showHighScore.this,"Đã thêm dữ liệu mặc định",Toast.LENGTH_SHORT).show();
                            }
                        });
                        alert1.show();
                        adapter.notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton("No",null);
                alert.show();
            }
        });

        buttonBackHighScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(showHighScore.this,MainActivity.class);
                startActivity(myIntent);
            }
        });


    }

    //Sự kiện ấn vào ID textview
    public void onClickID(View v){
        String s;
        if(checkdoubleclickID==false){
            s=" ORDER BY Id_HighScore ASC";
            checkdoubleclickID=true;
        }
        else{
            s=" ORDER BY Id_HighScore DESC";
            checkdoubleclickID=false;
        }
        ArrayList<HighScore> list= dbHelper_forHighScore.getAllHighScoreswithQuery(s) ;
        HighScoreList.clear();
        HighScoreList.addAll(list);
        adapter =new HighScoreCustomAdapter(showHighScore.this, HighScoreList);
        listViewHighScore.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    public void onClickMoney(View v){
        String s;
        if(checkdoubleclickMoney==false){
            s=" ORDER BY Money_HighScore ASC";
            checkdoubleclickMoney=true;
        }
        else{
            s=" ORDER BY Money_HighScore DESC";
            checkdoubleclickMoney=false;
        }
        ArrayList<HighScore> list= dbHelper_forHighScore.getAllHighScoreswithQuery(s) ;
        HighScoreList.clear();
        HighScoreList.addAll(list);
        adapter =new HighScoreCustomAdapter(showHighScore.this, HighScoreList);
        listViewHighScore.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void onClickScore(View v){
        String s;
        if(checkdoubleclickScore==false){
            s=" ORDER BY Score_HighScore ASC";
            checkdoubleclickScore=true;
        }
        else{
            s=" ORDER BY Score_HighScore DESC";
            checkdoubleclickScore=false;
        }
        ArrayList<HighScore> list= dbHelper_forHighScore.getAllHighScoreswithQuery(s) ;
        HighScoreList.clear();
        HighScoreList.addAll(list);
        adapter =new HighScoreCustomAdapter(showHighScore.this, HighScoreList);
        listViewHighScore.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void onClickHoTen(View v){
        String s;
        if(checkdoubleclickHoTen==false){
            s=" ORDER BY HoTen_HighScore ASC";
            checkdoubleclickHoTen=true;
        }
        else{
            s=" ORDER BY HoTen_HighScore DESC";
            checkdoubleclickHoTen=false;
        }
        ArrayList<HighScore> list= dbHelper_forHighScore.getAllHighScoreswithQuery(s) ;
        HighScoreList.clear();
        HighScoreList.addAll(list);
        adapter =new HighScoreCustomAdapter(showHighScore.this, HighScoreList);
        listViewHighScore.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
