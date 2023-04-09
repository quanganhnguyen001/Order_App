package com.eduahihi.odershop.model;

public class order {
    private int id;
    private int id_user;
    private int id_cart;
    private long date;
    private boolean status;


    public order() {
    }

    public order(int id, int id_user, int id_cart, long date, boolean status) {
        this.id = id;
        this.id_user = id_user;
        this.id_cart = id_cart;
        this.date = date;
        this.status = status;
    }

    public order(int id_user, int id_cart, long date, boolean status) {
        this.id_user = id_user;
        this.id_cart = id_cart;
        this.date = date;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_cart() {
        return id_cart;
    }

    public void setId_cart(int id_cart) {
        this.id_cart = id_cart;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
