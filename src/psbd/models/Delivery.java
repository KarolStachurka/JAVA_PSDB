package psbd.models;

import java.sql.Date;

public class Delivery {

    private String ingredient;
    private String id;
    private int warehouseId;
    private Date expirationDate;
    private double quantity;
    private double availableQuantity;
    private Date dateOfOrder;
    private Date dateOfReceiving;
    private boolean received;

    public Delivery(String ingredient, int warehouseId, double quantity, Date expirationDate)
    {
        this.ingredient = ingredient;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
        this.availableQuantity = quantity;
        this.dateOfOrder = null;
        this.dateOfReceiving = null;
        this.expirationDate = expirationDate;
        this.received = false;
    }

    public Delivery(String ingredient, int warehouseId, double quantity, Date expirationDate, Date orderDate)
    {
        this.ingredient = ingredient;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
        this.availableQuantity = quantity;
        this.dateOfOrder = orderDate;
        this.dateOfReceiving = null;
        this.expirationDate = expirationDate;
        this.received = false;
    }

    public Delivery(String ingredient, int warehouseId, double quantity, double availableQuantity, Date expirationDate,
                    Date dateOfOrder, Date dateOfReceiving, boolean received)
    {
        this.ingredient = ingredient;
        this.warehouseId = warehouseId;
        this.quantity = quantity;
        this.availableQuantity = availableQuantity;
        this.expirationDate = expirationDate;
        this.dateOfOrder = dateOfOrder;
        this.dateOfReceiving = dateOfReceiving;
        this.received = received;
    }

    public String getIngredient() {
        return ingredient;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public Date getDateOfReceiving() {
        return dateOfReceiving;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public int getWarehouseId() {
        return warehouseId;
    }

    public double getQuantity() {
        return quantity;
    }

    public double getAvailableQuantity() {
        return availableQuantity;
    }

    public boolean isReceived() {
        return received;
    }

    public void setReceived(boolean received) {
        this.received = received;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setAvailableQuantity(double availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public void setDateOfReceiving(Date dateOfReceiving) {
        this.dateOfReceiving = dateOfReceiving;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}

