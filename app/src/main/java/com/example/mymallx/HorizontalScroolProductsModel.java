package com.example.mymallx;

public class HorizontalScroolProductsModel {

    private String productId;
    private String  productImag;
    private String productName;
    private String productDescription;
    private String productPrice;
    public HorizontalScroolProductsModel(String productId,String productImag, String productName, String productDescription, String productPrice) {
        this.productImag = productImag;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productPrice = productPrice;
        this.productId = productId;
    }

    public String getProductImag() {
        return productImag;
    }

    public void setProductImag(String productImag) {
        this.productImag = productImag;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

}
