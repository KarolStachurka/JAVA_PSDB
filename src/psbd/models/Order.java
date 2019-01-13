package psbd.models;

import java.sql.Timestamp;
import java.util.ArrayList;

public class Order {
    private String courierId;
    private String login;
    private ArrayList<Recipe> recipeList;
    private double price;
    private double discount;
    private double companyDiscount;
    private boolean realized;
    private boolean delivered;
    private Timestamp orderTime;
    private Timestamp realizationTime;
    private Timestamp deliveryTime;

    public Order(String login, ArrayList<Recipe> recipeList, double price, double discount, double companyDiscount, Timestamp orderTime)
    {
        this.recipeList = recipeList;
        this.login = login;
        this.price = price;
        this.discount = discount;
        this.companyDiscount = companyDiscount;
        this.orderTime = orderTime;
    }

    public Order(String login, ArrayList<Recipe> recipeList, String courierId, double price, double discount, double companyDiscount,
                 Timestamp orderTime, Timestamp realizationTime, Timestamp deliveryTime, boolean realized, boolean delivered)
    {
        this.recipeList = recipeList;
        this.login = login;
        this.courierId = courierId;
        this.price = price;
        this.discount = discount;
        this.companyDiscount = companyDiscount;
        this.orderTime = orderTime;
        this.realizationTime = realizationTime;
        this.deliveryTime = deliveryTime;
        this.realized = realized;
        this.delivered = delivered;
    }

    public ArrayList<Recipe> getRecipeList() {
        return recipeList;
    }
    public double getCompanyDiscount() {
        return companyDiscount;
    }

    public double getDiscount() {
        return discount;
    }

    public double getPrice() {
        return price;
    }

    public String getLogin() {
        return login;
    }

    public String getCourierId() {
        return courierId;
    }

    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public Timestamp getRealizationTime() {
        return realizationTime;
    }

    public boolean isDelivered() {
        return delivered;
    }

    public boolean isRealized() {
        return realized;
    }

    public void setRealized(boolean realized) {
        this.realized = realized;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public void setRealizationTime(Timestamp realizationTime) {
        this.realizationTime = realizationTime;
    }

    public void setCourierId(String courierId) {
        this.courierId = courierId;
    }


}
