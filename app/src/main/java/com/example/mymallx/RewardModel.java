package com.example.mymallx;

public class RewardModel {

    private String title;
    private String expirDate;
    private String coupenBody;

    public RewardModel(String title, String expirDate, String coupenBody) {
        this.title = title;
        this.expirDate = expirDate;
        this.coupenBody = coupenBody;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExpirDate() {
        return expirDate;
    }

    public void setExpirDate(String expirDate) {
        this.expirDate = expirDate;
    }

    public String getCoupenBody() {
        return coupenBody;
    }

    public void setCoupenBody(String coupenBody) {
        this.coupenBody = coupenBody;
    }
}
