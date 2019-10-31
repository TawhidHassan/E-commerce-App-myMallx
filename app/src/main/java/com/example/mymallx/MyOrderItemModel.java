package com.example.mymallx;

public class MyOrderItemModel {

    private int productImage;
    private int rating;
    private String productTitle;
    private String deliveryStatas;

    public MyOrderItemModel(int productImage, int rating, String productTitle, String deliveryStatas) {
        this.productImage = productImage;
        this.rating = rating;
        this.productTitle = productTitle;
        this.deliveryStatas = deliveryStatas;
    }

    public int getProductImage() {
        return productImage;
    }

    public void setProductImage(int productImage) {
        this.productImage = productImage;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getDeliveryStatas() {
        return deliveryStatas;
    }

    public void setDeliveryStatas(String deliveryStatas) {
        this.deliveryStatas = deliveryStatas;
    }
}
