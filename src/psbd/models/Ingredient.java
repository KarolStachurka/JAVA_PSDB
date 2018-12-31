package psbd.models;

import psbd.utils.IngredientsEnum;

import java.sql.Date;
import java.time.LocalDate;

public class Ingredient {
    private String name;
    private IngredientsEnum type;
    private double price;
    private double amount;
    private Date expiration_time;
    private int warehouse;

    public Ingredient(String name, IngredientsEnum type, double price, double amount, Date expiration_time, int warehouse)
    {
        this.name = name;
        this.type = type;
        this.price = price;
        this.amount = amount;
        this.expiration_time = expiration_time;
        this.warehouse = warehouse;
    }

    public String getName() {
        return name;
    }

    public double getAmount() {
        return amount;
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
