package psbd.models;

public class Company {
    private int nip;
    private String name;
    private double discountValue;

    public Company(int nip, String name, double discountValue)
    {
        this.nip = nip;
        this.name = name;
        this.discountValue = discountValue;
    }

    public String getName() {
        return name;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public int getNip() {
        return nip;
    }
}
