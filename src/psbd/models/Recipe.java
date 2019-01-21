package psbd.models;

import java.util.ArrayList;

public class Recipe {
    private ArrayList<Ingredient> ingredientsList;
    private double price;
    private String name;
    private boolean available;
    private boolean realized;
    private int id;

    public Recipe(String name, double price, ArrayList<Ingredient> ingredientsList, boolean available)
    {
        this.name = name;
        this.price = price;
        this.ingredientsList = ingredientsList;
        this.available = available;
        this.realized = false;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public ArrayList<Ingredient> getIngredientsList() {
        return ingredientsList;
    }

    public boolean isAvailable() {
        return available;
    }

    public boolean isRealized() {
        return realized;
    }

    public void setRealized(boolean realized) {
        this.realized = realized;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIngredientList(ArrayList<Ingredient> ingredientList)
    {
        this.ingredientsList = ingredientList;
    }

    public ArrayList<Object> getRecipeModelToClientList()
    {
        ArrayList<Object> list = new ArrayList<>();
        list.add(name);
        list.add(price);
        return list;
    }

    public ArrayList<Object> getRecipeModelToCookList()
    {
        ArrayList<Object> list = new ArrayList<>();
        list.add(name);
        list.add(realized);
        return list;
    }

    public void setIngredientsList(ArrayList<Ingredient> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }
}
