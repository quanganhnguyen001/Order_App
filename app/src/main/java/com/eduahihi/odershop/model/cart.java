package com.eduahihi.odershop.model;

public class cart {

    private int id;
    private int id_product;
    private int id_user;
    private int quantity;
    private long date;
    private int status;

    private int isCart;

    public cart() {
    }

    public cart(int id, int id_product, int id_user, int quantity, long date) {
        this.id = id;
        this.id_product = id_product;
        this.id_user = id_user;
        this.quantity = quantity;
        this.date = date;
    }

    public cart(int id_product, int id_user, int quantity, long date) {
        this.id_product = id_product;
        this.id_user = id_user;
        this.quantity = quantity;
        this.date = date;
    }

    public cart(int id_product, int id_user, int quantity, long date, int status, int isCart) {
        this.id_product = id_product;
        this.id_user = id_user;
        this.quantity = quantity;
        this.date = date;
        this.status = status;
        this.isCart = isCart;
    }

    public cart(int id, int id_product, int id_user, int quantity, long date, int status, int isCart) {
        this.id = id;
        this.id_product = id_product;
        this.id_user = id_user;
        this.quantity = quantity;
        this.date = date;
        this.status = status;
        this.isCart = isCart;
    }


    public int getStatus() {
        return status;
    }

    public int getIsCart() {
        return isCart;
    }

    public void setIsCart(int isCart) {
        this.isCart = isCart;
    }

    public int isStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_product() {
        return id_product;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
