package com.example.mymallx;

public class SliderModel {
    private String baner;
    private String bannerBackgrounCoolor;

    public SliderModel(String baner, String bannerBackgrounCoolor) {
        this.baner = baner;
        this.bannerBackgrounCoolor = bannerBackgrounCoolor;
    }

    public String  getBaner() {
        return baner;
    }

    public void setBaner(String  baner) {
        this.baner = baner;
    }

    public String getBannerBackgrounCoolor() {
        return bannerBackgrounCoolor;
    }

    public void setBannerBackgrounCoolor(String bannerBackgrounCoolor) {
        this.bannerBackgrounCoolor = bannerBackgrounCoolor;
    }
}
