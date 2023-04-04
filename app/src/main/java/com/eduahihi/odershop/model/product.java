package com.eduahihi.odershop.model;

public class product implements java.io.Serializable{
private String id;
    private String name;
    private String price;
    private byte[] image;

    private String quantity;
    private String description;
    private String id_category;

    public product() {
    }

    public product(String name, String price, byte[] image, String quantity, String description) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.description = description;
    }

    public product(String id, String name, String price, byte[] image, String quantity, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.description = description;
    }

    public product(String name, String price, byte[] image, String quantity, String description, String id_category) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.description = description;
        this.id_category = id_category;
    }

    public product(String id, String name, String price, byte[] image, String quantity, String description, String id_category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.description = description;
        this.id_category = id_category;
    }

    public String getId_category() {
        return id_category;
    }

    public void setId_category(String id_category) {
        this.id_category = id_category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
