package psbd.models;

import java.util.ArrayList;

public class Recipe {
    private ArrayList<Ingredient> ingredientsList;
    private double price;
    private String name;
    private boolean available;

    public Recipe(String name, double price, ArrayList<Ingredient> ingredientsList, boolean available)
    {
        this.name = name;
        this.price = price;
        this.ingredientsList = ingredientsList;
        this.available = available;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ArrayList<Ingredient> getIngredientsList() {
        return ingredientsList;
    }

    public boolean isAvailable() {
        return available;
    }
}
