package com.eduahihi.odershop.database;

public class constant {
    public static final String TABLE_USER = "user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_PASSWORD = "password";

    public static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_PHONE + " TEXT,"
            + COLUMN_PASSWORD + " TEXT"
            + ")";

    public static final String DROP_TABLE_USER = "DROP TABLE IF EXISTS " + TABLE_USER;

    public static final String TABLE_PRODUCT = "product";
    public static final String COLUMN_ID_PRODUCT = "id";
    public static final String COLUMN_NAME_PRODUCT = "name";
    public static final String COLUMN_PRICE_PRODUCT = "price";
    public static final String COLUMN_QUANTITY_PRODUCT = "quantity";
    public static final String COLUMN_IMAGE_PRODUCT = "image";
    public static final String COLUMN_DESCRIPTION_PRODUCT = "description";
    public static final String COLUMN_ID_CATEGORY_PRODUCT = "id_category";

    public static final String CREATE_TABLE_PRODUCT = "CREATE TABLE " + TABLE_PRODUCT + "("
            + COLUMN_ID_PRODUCT + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME_PRODUCT + " TEXT,"
            + COLUMN_PRICE_PRODUCT + " TEXT,"
            + COLUMN_IMAGE_PRODUCT + " BLOB,"
            + COLUMN_QUANTITY_PRODUCT + " INTEGER,"
            + COLUMN_DESCRIPTION_PRODUCT + " TEXT,"
            + COLUMN_ID_CATEGORY_PRODUCT + " INTEGER"
            + ")";

    public static final String DROP_TABLE_PRODUCT = "DROP TABLE IF EXISTS " + TABLE_PRODUCT;

    public static final String TABLE_CATEGORY = "category";
    public static final String COLUMN_ID_CATEGORY = "id";
    public static final String COLUMN_NAME_CATEGORY = "name";

    public static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY + "("
            + COLUMN_ID_CATEGORY + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_NAME_CATEGORY + " TEXT"
            + ")";

    public static final String DROP_TABLE_CATEGORY = "DROP TABLE IF EXISTS " + TABLE_CATEGORY;


    public static final String TABLE_CART = "cart";
    public static final String COLUMN_ID_CART = "id";
    public static final String COLUMN_ID_PRODUCT_CART = "id_product";
    public static final String COLUMN_ID_USER_CART = "id_user";
    public static final String COLUMN_QUANTITY_CART = "quantity";

    public static final String COLUMN_DATE_CART = "date";

    public static final String COLUMN_STATUS_CART = "status";

    public static final String CREATE_TABLE_CART = "CREATE TABLE " + TABLE_CART + "("
            + COLUMN_ID_CART + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_ID_PRODUCT_CART + " TEXT,"
            + COLUMN_ID_USER_CART + " TEXT,"
            + COLUMN_QUANTITY_CART + " INTEGER,"
            + COLUMN_DATE_CART + " TEXT,"
            + COLUMN_STATUS_CART + " INTEGER"
            + ")";

    public static final String DROP_TABLE_CART = "DROP TABLE IF EXISTS " + TABLE_CART;

    public static final String TABLE_ORDER = "orders";
    public static final String COLUMN_ID_ORDER = "id";
    public static final String COLUMN_ID_USER_ORDER = "id_user";
    public static final String COLUMN_ID_CART_ORDER = "id_cart";
    public static final String COLUMN_DATE_ORDER = "date";
    //is status
    public static final String COLUMN_STATUS_ORDER = "status";

    public static final String CREATE_TABLE_ORDER = "CREATE TABLE " + TABLE_ORDER + "("
            + COLUMN_ID_ORDER + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_ID_USER_ORDER + " TEXT,"
            + COLUMN_ID_CART_ORDER + " TEXT,"
            + COLUMN_DATE_ORDER + " TEXT,"
            + COLUMN_STATUS_ORDER + " INTEGER"
            + ")";

    public static final String DROP_TABLE_ORDER = "DROP TABLE IF EXISTS " + TABLE_ORDER;
}
