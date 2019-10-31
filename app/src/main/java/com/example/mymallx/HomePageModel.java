package com.example.mymallx;

import java.util.List;

public class HomePageModel {

    public static  final int BannerSlider=0;
    public static  final int StripAddBaneer=1;
    public static  final int HorizontalProductView=2;
    public static  final int GridProductsView=3;

    private int type;
    private String backgroundColor;

    // *********************************************************************************************************
//-----------------------------------------------------{
    //baner slider strat
    private List<SliderModel> sliderModelList;

    public HomePageModel(int type, List<SliderModel> sliderModelList) {
        this.type = type;
        this.sliderModelList = sliderModelList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<SliderModel> getSliderModelList() {
        return sliderModelList;
    }

    public void setSliderModelList(List<SliderModel> sliderModelList) {
        this.sliderModelList = sliderModelList;
    }
    //baner slider end
//-----------------------------------------------------}
    // *********************************************************************************************************
// -----------------------------------------------------{
    //Strip Add baner strat
    private String imagResource;

    public HomePageModel(int type, String  imagResource, String backgroundColor) {
        this.type = type;
        this.imagResource = imagResource;
        this.backgroundColor = backgroundColor;
    }

    public String  getImagResource() {
        return imagResource;
    }

    public void setImagResource(String  imagResource) {
        this.imagResource = imagResource;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
    //Strip Add baner end
//-----------------------------------------------------}


    // *********************************************************************************************************
// -----------------------------------------------------{
    //horizontal/@@@and Gridproducts@@ View scrool products layout start
    private String title;
    private List<HorizontalScroolProductsModel> horizontalScroolProductsModelList;
    private List<WishListModel> viewAllProductList;

    //horizontel product viewall btn click and show All product List start
    public HomePageModel(int type, String title,String backgroundColor, List<HorizontalScroolProductsModel> horizontalScroolProductsModelList,List<WishListModel>viewAllProductList) {
        this.type = type;
        this.title = title;
        this.backgroundColor=backgroundColor;

        this.horizontalScroolProductsModelList = horizontalScroolProductsModelList;
        this.viewAllProductList = viewAllProductList;
    }

    public List<WishListModel> getViewAllProductList() {
        return viewAllProductList;
    }

    public void setViewAllProductList(List<WishListModel> viewAllProductList) {
        this.viewAllProductList = viewAllProductList;
    }
    //horizontel product viewall btn click and show All product List start


    //---------------------------------------------------------------------------------------------
    //horizontel product
    public HomePageModel(int type, String title, String backgroundColor, List<HorizontalScroolProductsModel> horizontalScroolProductsModelList) {
        this.type = type;
        this.title = title;
        this.backgroundColor=backgroundColor;

        this.horizontalScroolProductsModelList = horizontalScroolProductsModelList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<HorizontalScroolProductsModel> getHorizontalScroolProductsModelList() {
        return horizontalScroolProductsModelList;
    }

    public void setHorizontalScroolProductsModelList(List<HorizontalScroolProductsModel> horizontalScroolProductsModelList) {
        this.horizontalScroolProductsModelList = horizontalScroolProductsModelList;
    }
    //horizontel product
    //horizontal/and @@Gridproducts@@ View scrool products layout end

// -----------------------------------------------------}



}
