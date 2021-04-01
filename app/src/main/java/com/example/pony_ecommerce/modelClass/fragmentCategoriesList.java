package com.example.pony_ecommerce.modelClass;

public class fragmentCategoriesList {
    private int categoryIcon;
    private String categoryName;

    public fragmentCategoriesList() {
    }

    public fragmentCategoriesList(int categoryIcon, String categoryName) {
        this.categoryIcon = categoryIcon;
        this.categoryName = categoryName;
    }

    public int getCategoryIcon() {
        return categoryIcon;
    }

    public void setCategoryIcon(int categoryIcon) {
        this.categoryIcon = categoryIcon;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
