package psbd.models;

import psbd.utils.IngredientsEnum;

import java.sql.Date;

public class Ingredient {
    private String name;
    private IngredientsEnum type;
    private double price;
    private double quantity;
    private Date expiration_time;
    private int warehouse;

    public Ingredient(String name, IngredientsEnum type, double price, double amount, Date expiration_time, int warehouse)
    {
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = amount;
        this.expiration_time = expiration_time;
        this.warehouse = warehouse;
    }

    public Ingredient(String name, IngredientsEnum type, double price)
    {
        this.name = name;
        this.type = type;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public IngredientsEnum getType() {
        return type;
    }

    public Date getExpiration_time() {
        return expiration_time;
    }

    public int getWarehouse() {
        return warehouse;
    }
}
