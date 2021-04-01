package com.example.pony_ecommerce.modelClass;

public class CategoriesList {
    private String cartImage;
    private String cartName;
    private String cartPrice;

    public CategoriesList() {
    }

    public CategoriesList(String  cartImage, String cartName, String cartPrice) {
        this.cartImage = cartImage;
        this.cartName = cartName;
        this.cartPrice = cartPrice;
    }

    public String  getCartImage() {
        return cartImage;
    }

    public void setCartImage(String  cartImage) {
        this.cartImage = cartImage;
    }

    public String getCartName() {
        return cartName;
    }

    public void setCartName(String cartName) {
        this.cartName = cartName;
    }

    public String getCartPrice() {
        return cartPrice;
    }

    public void setCartPrice(String cartPrice) {
        this.cartPrice = cartPrice;
    }
}
