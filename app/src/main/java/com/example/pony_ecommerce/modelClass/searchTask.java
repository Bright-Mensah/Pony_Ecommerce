package com.example.pony_ecommerce.modelClass;

public class searchTask {
    private String catName;
    private int catIcon;

    public searchTask() {
    }

    public searchTask(String catName, int catIcon) {
        this.catName = catName;
        this.catIcon = catIcon;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public int getCatIcon() {
        return catIcon;
    }

    public void setCatIcon(int catIcon) {
        this.catIcon = catIcon;
    }
}

