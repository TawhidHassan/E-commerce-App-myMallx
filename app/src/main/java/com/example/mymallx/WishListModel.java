package com.example.mymallx;

import java.util.ArrayList;

public class WishListModel {

    private String  productId;
    private String  productImg;
    private String productTitle;
    private long freeCoupens;
    private String rating;
    private String  totalRating;
    private String productPrice;
    private String cuttedproductPrice;
    private Boolean COD;
   private ArrayList<String>tags;

    public WishListModel(String productId, String productImg, String productTitle, long freeCoupens, String rating, String totalRating, String productPrice, String cuttedproductPrice, Boolean COD) {
        this.productId = productId;
        this.productImg = productImg;
        this.productTitle = productTitle;
        this.freeCoupens = freeCoupens;
        this.rating = rating;
        this.totalRating = totalRating;
        this.productPrice = productPrice;
        this.cuttedproductPrice = cuttedproductPrice;
        this.COD = COD;
    }


    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImg() {
        return productImg;
    }

    public void setProductImg(String productImg) {
        this.productImg = productImg;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public long getFreeCoupens() {
        return freeCoupens;
    }

    public void setFreeCoupens(long freeCoupens) {
        this.freeCoupens = freeCoupens;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(String totalRating) {
        this.totalRating = totalRating;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedproductPrice() {
        return cuttedproductPrice;
    }

    public void setCuttedproductPrice(String cuttedproductPrice) {
        this.cuttedproductPrice = cuttedproductPrice;
    }

    public Boolean getCOD() {
        return COD;
    }

    public void setCOD(Boolean COD) {
        this.COD = COD;
    }
}
