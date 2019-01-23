package psbd.models;

import psbd.utils.IngredientsEnum;

import java.sql.Date;
import java.util.ArrayList;

public class Ingredient {
    private String name;
    private IngredientsEnum type;
    private double price;
    private double quantity;
    private Date expiration_time;
    private int warehouse;
    private boolean optional;
    private boolean included;

    public Ingredient(String name, IngredientsEnum type, double price, double quantity, Date expiration_time, int warehouse)
    {
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.expiration_time = expiration_time;
        this.warehouse = warehouse;
        this.included = true;
    }

    public Ingredient(String name, IngredientsEnum type, double price, double quantity, boolean optional)
    {
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.optional = optional;
        this.included = true;
    }

    public Ingredient(String name, IngredientsEnum type, double price)
    {
        this.name = name;
        this.type = type;
        this.price = price;
        this.included = true;
    }

    public Ingredient(Ingredient ingredient)
    {
        this.name = ingredient.name;
        this.type = ingredient.type;
        this.quantity = ingredient.quantity;
        this.price = ingredient.price;
        this.included = ingredient.included;
    }

    public ArrayList<Object> getIngredientModelToList()
    {
        ArrayList<Object> list = new ArrayList<>();
        list.add(name);
        list.add(type);
        list.add(price);
        list.add(quantity);
        list.add(included);
        return list;
    }
    public ArrayList<Object> getIngredientModelToClientList()
    {
        ArrayList<Object> list = new ArrayList<>();
        list.add(name);
        list.add(type);
        list.add(quantity);
        list.add(included);
        return list;
    }

    public ArrayList<Object> getIngredientModelToCookList()
    {
        ArrayList<Object> list = new ArrayList<>();
        list.add(name);
        list.add(quantity);
        return list;
    }

    public ArrayList<Object> getIngredientModelSupplierList()
    {
        ArrayList<Object> list = new ArrayList<>();
        list.add(name);
        list.add(quantity);
        return list;
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

    public boolean isOptional() {
        return optional;
    }

    public boolean isIncluded() {
        return included;
    }

    public void setIncluded(boolean included) {
        this.included = included;
    }
}
