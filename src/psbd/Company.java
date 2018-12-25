package psbd;

public class Company {
    private int nip;
    private String name;
    private int discountValue;

    public Company(int nip, String name, int discountValue)
    {
        this.nip = nip;
        this.name = name;
        this.discountValue = discountValue;
    }

    public String getName() {
        return name;
    }

    public int getDiscountValue() {
        return discountValue;
    }

    public int getNip() {
        return nip;
    }
}
