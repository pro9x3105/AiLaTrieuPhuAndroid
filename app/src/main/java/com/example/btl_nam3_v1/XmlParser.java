package com.example.btl_nam3_v1;

import android.content.Intent;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlParser {
    //Khai báo
    public String text;
    public int dem=0;

    public XmlParser(InputStream file){
        try{
            //Khai báo XmlPullParser
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(file, null);

            //Duyệt
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT ) {      //duyệt đến cuối xml
                String tagname = parser.getName();
                if ( XmlPullParser.START_TAG ==eventType ) {        //duyệt tag từ đầu
                    // create a new instance of employee
                    if (tagname.equalsIgnoreCase("question")) {     //tìm tag là question
                        Question listXml=new Question();

                        //Đọc trong tag question
                        for (int i = 0; i < parser.getAttributeCount(); i++) {
                            text = parser.getAttributeValue(i);
                            switch (i) {
                                case 0:
                                    //đáp án sai 1
                                    listXml.setAnswerFake1(text);
                                    break;
                                case 1:
                                    //đáp án sai 2
                                    listXml.setAnswerFake2(text);
                                    break;
                                case 2:
                                    //đáp án sai 3
                                    listXml.setAnswerFake3(text);
                                    break;
                                case 3:
                                    //Đáp án đúng
                                    listXml.setAnswerTrue(text);
                                    break;
                                case 4:
                                    //Câu hỏi
                                    listXml.setCauHoi(text);
                                    break;
                                case 5:
                                    //Gợi ý
                                    listXml.setSuggest(text);
                                    break;
                            }

                        }

                        //Tự thêm STT nếu ko trùng
                        int i=0;
                        while(i>=0){
                            if(MainActivity.dbHelper.checkIfExist(i)==1){
                                i++;
                            }
                            else{
                                break;
                            }
                        }

                        //Thêm vào db
                        long resultAdd = MainActivity.dbHelper.Insert(i,
                                listXml.getCauHoi(),
                                listXml.getAnswerTrue(),
                                listXml.getAnswerFake1(),
                                listXml.getAnswerFake2(),
                                listXml.getAnswerFake3(),
                                listXml.getSuggest());
                        if (resultAdd!=-1){
                            dem++;
                        }
                    }
                }
                eventType = parser.next();
            }
        }
        catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        //return dem;
    }
}
