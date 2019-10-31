package com.example.mymallx;

public class CartItemModel {

    //for 2 view inflate
    public static final int CART_ITEM=0;
    public static final int CART_TOTAL_AMOUNT=1;

    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    //for 2 view inflate
    //-----------------------------------
    //for cart item start
    private  String productId;



    private String productTitle;
    private String productPrice;
    private String productCutedPrice;
    private String  productImage;
    private long freeCoupen;
    private long productQuntity;
    private long productOfferApplyed;
    private long productCoupenApplyed;
    private boolean inStock;

    public CartItemModel(int type, String productId, String productTitle, String productPrice, String productCutedPrice, String productImage, long freeCoupen, long productQuntity, long productOfferApplyed, long productCoupenApplyed,boolean inStock) {
        this.type = type;
        this.productId = productId;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productCutedPrice = productCutedPrice;
        this.productImage = productImage;
        this.freeCoupen = freeCoupen;
        this.productQuntity = productQuntity;
        this.productOfferApplyed = productOfferApplyed;
        this.productCoupenApplyed = productCoupenApplyed;
        this.inStock=inStock;

    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCutedPrice() {
        return productCutedPrice;
    }

    public void setProductCutedPrice(String productCutedPrice) {
        this.productCutedPrice = productCutedPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public long getFreeCoupen() {
        return freeCoupen;
    }

    public void setFreeCoupen(long freeCoupen) {
        this.freeCoupen = freeCoupen;
    }

    public long getProductQuntity() {
        return productQuntity;
    }

    public void setProductQuntity(long productQuntity) {
        this.productQuntity = productQuntity;
    }

    public long getProductOfferApplyed() {
        return productOfferApplyed;
    }

    public void setProductOfferApplyed(long productOfferApplyed) {
        this.productOfferApplyed = productOfferApplyed;
    }

    public long getProductCoupenApplyed() {
        return productCoupenApplyed;
    }

    public void setProductCoupenApplyed(int productCoupenApplyed) {
        this.productCoupenApplyed = productCoupenApplyed;
    }
    //for cart item end
    //-----------------------------------------------------------------------------------

    //for cart total ammount start

    public CartItemModel(int type) {
        this.type = type;
    }

    //for cart total ammount end
}
