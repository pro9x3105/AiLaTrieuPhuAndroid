package com.example.btl_nam3_v1;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;

public class XmlParser_HighScore {
    //Khai báo
    public String text;
    private InputStream file;

    public XmlParser_HighScore(InputStream file){
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
                    if (tagname.equalsIgnoreCase("highscore")) {     //tìm tag là highscore
                        HighScore listXml=new HighScore();

                        //Đọc trong tag question
                        for (int i = 0; i < parser.getAttributeCount(); i++) {
                            text = parser.getAttributeValue(i);
                            switch (i) {
                                case 0:
                                    //Họ tên
                                    listXml.setHoTen_HighScore(text);
                                    break;
                                case 1:
                                    //Tiền
                                    listXml.setMoney_HighScore(text);
                                    break;
                                case 2:
                                    //Điểm
                                    listXml.setScore_HighScore(text);
                                    break;
                            }

                        }

                        //Tự thêm STT nếu ko trùng
                        int i=0;
                        while(i>=0){
                            if(MainActivity.dbHelper_forHighScore.checkIfExist(i)==1){
                                i++;
                            }
                            else{
                                break;
                            }
                        }

                        //Thêm vào db
                        long resultAdd = MainActivity.dbHelper_forHighScore.Insert(i,
                                listXml.getHoTen_HighScore(),
                                Integer.parseInt(listXml.getScore_HighScore()),
                                Integer.parseInt(listXml.getMoney_HighScore()));
                    }
                }
                eventType = parser.next();
            }
        }
        catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
    }
}
