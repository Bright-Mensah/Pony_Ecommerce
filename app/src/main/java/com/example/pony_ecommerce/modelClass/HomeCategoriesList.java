package com.example.pony_ecommerce.modelClass;

public class HomeCategoriesList {
    private String catName;
    private int catIcon;

    public HomeCategoriesList(int catIcon, String catName) {
        this.catIcon = catIcon;
        this.catName = catName;
    }

    public HomeCategoriesList() {
    }

    public int getCatIcon() {
        return catIcon;
    }

    public void setCatIcon(int iconLink) {
        this.catIcon = iconLink;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}
