package com.example.pony_ecommerce.modelClass;

public class purchaseItemModelClass {
    private String price;
    private String image;
    private String name;


    public purchaseItemModelClass() {
    }

    public purchaseItemModelClass(String price, String image,String name) {
        this.price = price;
        this.image = image;
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
