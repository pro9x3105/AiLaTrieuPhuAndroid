package com.example.btl_nam3_v1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class show_all extends AppCompatActivity {

    //Khai báo
    private ListView listViewShowAll;
    private final ArrayList<Question> QuestionList= new ArrayList<Question>();
    private Button buttonInsert1, buttonDelete1,buttonBack1,buttonDataDefault;
    DBHelper dbHelper = MainActivity.dbHelper;
    private QuestionCustomAdapter adapter;
    private EditText editTextTimKiem;
    private int editPosition;

    //Trả về kết quả và refresh adapter , phục vụ cho listView
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100 && resultCode==RESULT_OK) {    //Insert trả về code 100
            Bundle bundle = data.getExtras();
            if (bundle!=null) {
                Question c = new Question(
                        bundle.getString("STT"),
                        bundle.getString("Question"));
                c.setSelected(false);
                QuestionList.add(c);
            }
            adapter.notifyDataSetChanged();
        }
        if (requestCode==200 && resultCode==RESULT_OK) {    //Edit trả về code 200
            Bundle bundle = data.getExtras();
            if (bundle!=null) {
                Question c = new Question(
                        bundle.getString("STT"),
                        bundle.getString("Question"));
                QuestionList.set(editPosition,c);
            }
            adapter.notifyDataSetChanged();
        }
    }

    //ContextMenu
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_main,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case R.id.edit_id:
                editPosition = info.position;
                Intent myIntent = new Intent(show_all.this, QuestionInsert.class);
                myIntent.putExtra("Mode", "Edit");
                myIntent.putExtra("STT",QuestionList.get(editPosition).getSTT());
                show_all.this.startActivityForResult(myIntent, 200);
                break;
            case R.id.delete_id:
                AlertDialog.Builder alert = new AlertDialog.Builder(show_all.this);
                alert.setTitle(" Xoa danh ba");
                alert.setMessage(" Ban co muon xoa contact nay khong?");
                alert.setPositiveButton(" Có", new DialogInterface.OnClickListener() {
                    @Override public void onClick(DialogInterface dialoginterface, int which) {
                        // Delete clicked members of arraylist
                        long resultDelete = dbHelper.Delete(Integer.parseInt(QuestionList.get(editPosition).getSTT()));
                if (resultDelete == 0) {
                    Toast.makeText(show_all.this, " Lỗi khi xoá câu hỏi", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(show_all.this, " Câu hỏi đã được xoá ", Toast.LENGTH_SHORT).show();
                    QuestionList.remove(info.position);
                }
                adapter.notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton(" Không ",null);
                alert.show();
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all);
        setTitle("Câu hỏi");

        //Ánh xạ
        listViewShowAll=findViewById(R.id.listViewShowAll);
        buttonInsert1=findViewById(R.id.buttonInsert1);
        buttonDelete1=findViewById(R.id.buttonDelete1);
        buttonDataDefault=findViewById(R.id.buttonDataDefault);
        buttonBack1=findViewById(R.id.buttonBack1);
        editTextTimKiem=findViewById(R.id.editTextTimKiem);

        //Mở DBhelper
        //dbHelper =new DBHelper(show_all.this);
        //dbHelper.openDB();

        ArrayList<Question> list= dbHelper.getAllQuestions() ;
        QuestionList.addAll(list);
        adapter =new QuestionCustomAdapter(show_all.this, QuestionList);
        listViewShowAll.setAdapter(adapter);

        //Đăng ký contextmenu cho listview để sử dụng
        registerForContextMenu(listViewShowAll);

        buttonInsert1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(show_all.this,QuestionInsert.class);
                myIntent.putExtra("Mode","Insert");
                show_all.this.startActivityForResult(myIntent,100);
            }
        });

        buttonDelete1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(show_all.this); //Tạo alert
                alert.setTitle("Thông báo ?");
                alert.setMessage("Bạn có muốn xoá các câu hỏi đã chọn ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {      //Nút có
                    @Override public void onClick(DialogInterface dialoginterface, int which) {
                        //Xoá các lựa chọn
                        for (int i=QuestionList.size()-1;i>=0;i--){
                            Question c = QuestionList.get(i);
                            if ( c.isSelected() ) {
                                long resultDelete = dbHelper.Delete(Integer.parseInt(QuestionList.get(i).getSTT()));
                                QuestionList.remove(i);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
                alert.setNegativeButton("No",null);
                alert.show();
            }
        });

        buttonDataDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(show_all.this); //Tạo alert
                alert.setTitle("Thông báo ?");
                alert.setMessage("Bạn có muốn sử dụng dữ liệu mặc định ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {      //Nút có
                    @Override public void onClick(DialogInterface dialoginterface, int which) {
                        AlertDialog.Builder alert1 = new AlertDialog.Builder(show_all.this); //Tạo alert
                        alert1.setTitle("Thông báo ?");
                        alert1.setMessage("Bạn có muốn xoá và ghi mỡi dữ liệu hiện tại ( chọn No nếu muốn giữ lại dữ liệu cũ nhưng vẫn muốn thêm mới ) ?");
                        alert1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override public void onClick(DialogInterface dialoginterface, int which) {
                                //Nút có thì xoá và thêm mới từ đầu
                                //lấy xml và thêm mới
                                dbHelper.DeleteTable();
                                new XmlParser(getResources().openRawResource(R.raw.questions));

                                //Refresh listview ( ko dung dc notifyDataSetChanged() )
                                ArrayList<Question> list= dbHelper.getAllQuestions() ;
                                QuestionList.clear();
                                QuestionList.addAll(list);
                                adapter =new QuestionCustomAdapter(show_all.this, QuestionList);
                                listViewShowAll.setAdapter(adapter);

                                adapter.notifyDataSetChanged();
                                Toast.makeText(show_all.this,"Đã thêm dữ liệu mặc định",Toast.LENGTH_SHORT).show();
                            }
                        });
                        alert1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Nút không thì giữ nguyên và thêm mới
                                //lấy xml và thêm mới
                                new XmlParser(getResources().openRawResource(R.raw.questions));

                                //Refresh listview ( ko dung dc notifyDataSetChanged() )
                                ArrayList<Question> list= dbHelper.getAllQuestions() ;
                                QuestionList.clear();
                                QuestionList.addAll(list);
                                adapter =new QuestionCustomAdapter(show_all.this, QuestionList);
                                listViewShowAll.setAdapter(adapter);

                                adapter.notifyDataSetChanged();
                                Toast.makeText(show_all.this,"Đã thêm dữ liệu mặc định",Toast.LENGTH_SHORT).show();
                            }
                        });
                        alert1.show();
                    }
                });
                alert.setNegativeButton("No",null);
                alert.show();
            }
        });

        buttonBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editTextTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                adapter.getFilter().filter(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
