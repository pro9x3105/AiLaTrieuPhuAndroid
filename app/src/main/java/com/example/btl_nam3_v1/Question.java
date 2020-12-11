package com.example.btl_nam3_v1;

public class Question {

    private String STT;
    private String CauHoi;
    private String AnswerTrue;
    private String AnswerFake1;
    private String AnswerFake2;
    private String AnswerFake3;
    private String Suggest;
    private boolean selected=false;

    public Question() {
    }

    public String getSTT() {
        return STT;
    }

    public void setSTT(String sTT) {
        STT = sTT;
    }

    public String getCauHoi() {
        return CauHoi;
    }

    public void setCauHoi(String cauHoi) {
        CauHoi = cauHoi;
    }

    public String getAnswerTrue() {
        return AnswerTrue;
    }

    public void setAnswerTrue(String answerTrue) {
        AnswerTrue = answerTrue;
    }

    public String getAnswerFake1() {
        return AnswerFake1;
    }

    public void setAnswerFake1(String answerFake1) {
        AnswerFake1 = answerFake1;
    }

    public String getAnswerFake2() {
        return AnswerFake2;
    }

    public void setAnswerFake2(String answerFake2) {
        AnswerFake2 = answerFake2;
    }

    public String getAnswerFake3() {
        return AnswerFake3;
    }

    public void setAnswerFake3(String answerFake3) {
        AnswerFake3 = answerFake3;
    }

    public String getSuggest() {
        return Suggest;
    }

    public void setSuggest(String suggest) {
        Suggest = suggest;
    }

    public Question(String sTT){
        STT=sTT;
    }
    public boolean isSelected() { return selected;}
    public void setSelected(boolean selected) { this.selected = selected;}


    public Question(String sTT, String cauHoi, String answerTrue, String answerFake1, String answerFake2, String answerFake3, String suggest) {
        STT = sTT;
        CauHoi = cauHoi;
        AnswerTrue = answerTrue;
        AnswerFake1 = answerFake1;
        AnswerFake2 = answerFake2;
        AnswerFake3 = answerFake3;
        Suggest = suggest;
        this.selected = false;
    }

    public Question(String sTT, String cauHoi){
        STT = sTT;
        CauHoi = cauHoi;
        this.selected = false;
    }
}
