package com.example.pony_ecommerce.modelClass;

public class Users {

    private String name, password,image,email;

    public Users(String name, String password, String image,String email) {
        this.name = name;
        this.password = password;
        this.image = image;
        this.email = email;
    }

    public Users() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
