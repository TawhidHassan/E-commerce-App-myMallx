package com.example.mymallx;

public class CategoryModel {
    private String categoryIconlink;
    private String categoryName;

    public CategoryModel(String categoryIconlink, String categoryName) {
        this.categoryIconlink = categoryIconlink;
        this.categoryName = categoryName;
    }

    public String getCategoryIconlink() {
        return categoryIconlink;
    }

    public void setCategoryIconlink(String categoryIconlink) {
        this.categoryIconlink = categoryIconlink;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
