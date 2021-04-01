package com.example.pony_ecommerce.modelClass;

public class shoeDisplayModelClass {
    private String shoeImage;
    private String shoeName,shoePrice;

    public shoeDisplayModelClass() {
    }

    public shoeDisplayModelClass(String shoeImage, String shoeName,String shoePrice) {
        this.shoeImage = shoeImage;
        this.shoeName = shoeName;
        this.shoePrice = shoePrice;
    }

    public String getShoeImage() {
        return shoeImage;
    }

    public void setShoeImage(String shoeImage) {
        this.shoeImage = shoeImage;
    }

    public String getShoeName() {
        return shoeName;
    }

    public void setShoeName(String shoeName) {
        this.shoeName = shoeName;
    }

    public String getShoePrice() {
        return shoePrice;
    }

    public void setShoePrice(String shoePrice) {
        this.shoePrice = shoePrice;
    }
}
