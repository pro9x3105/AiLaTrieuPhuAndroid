package com.example.btl_nam3_v1;

public class HighScore {
    public HighScore(String id_HighScore, String hoTen_HighScore, String score_HighScore, String money_HighScore) {
        Id_HighScore = id_HighScore;
        HoTen_HighScore = hoTen_HighScore;
        Score_HighScore = score_HighScore;
        Money_HighScore = money_HighScore;
        this.selected = false;

    }

    public HighScore(){}

    public String getId_HighScore() {
        return Id_HighScore;
    }

    public void setId_HighScore(String id_HighScore) {
        Id_HighScore = id_HighScore;
    }

    public String getHoTen_HighScore() {
        return HoTen_HighScore;
    }

    public void setHoTen_HighScore(String hoTen_HighScore) {
        HoTen_HighScore = hoTen_HighScore;
    }

    public String getScore_HighScore() {
        return Score_HighScore;
    }

    public void setScore_HighScore(String score_HighScore) {
        Score_HighScore = score_HighScore;
    }

    public String getMoney_HighScore() {
        return Money_HighScore;
    }

    public void setMoney_HighScore(String money_HighScore) {
        Money_HighScore = money_HighScore;
    }

    public boolean isSelected() { return selected;}

    public void setSelected(boolean selected) { this.selected = selected;}

    public String Id_HighScore;
    public String HoTen_HighScore;
    public String Score_HighScore;
    public String Money_HighScore;
    private boolean selected=false;
}
