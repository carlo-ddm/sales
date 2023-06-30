package model;
import constants.TaxRate;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Item {
    private String name;
    private BigDecimal price;
    private boolean isExemptFromTax;
    private boolean isImported;
    private int quantity;

    public Item(String name, BigDecimal price, boolean isExemptFromTax, boolean isImported, int quantity) {
        this.name = name;
        this.price = price;
        this.isExemptFromTax = isExemptFromTax;
        this.isImported = isImported;
        this.quantity = quantity;
    }

    public Item(Item source) {
        setName(source.name);
        setPrice(source.price);
        setIsExemptFromTax(source.isExemptFromTax);
        setIsImported(source.isImported);
        setQuantity(source.quantity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price cannot be null or negative");
        }
        this.price = price;
    }

    public boolean isExemptFromTax() {
        return isExemptFromTax;
    }

    public void setIsExemptFromTax(boolean isExemptFromTax) {
        this.isExemptFromTax = isExemptFromTax;
    }

    public boolean isImported() {
        return isImported;
    }

    public void setIsImported(boolean isImported) {
        this.isImported = isImported;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Calcola le tasse di vendita per questo articolo
    public BigDecimal getSalesTax() {
        BigDecimal salesTax = BigDecimal.ZERO;

        if (!isExemptFromTax) {
            salesTax = salesTax.add(price.multiply(BigDecimal.valueOf(TaxRate.BASIC_SALES.getRate())));
        }

        if (isImported) {
            salesTax = salesTax.add(price.multiply(BigDecimal.valueOf(TaxRate.IMPORT_DUTY.getRate())));
        }

        // Arrotondamento al più vicino 0.05
        salesTax = salesTax.multiply(BigDecimal.valueOf(20));
        salesTax = salesTax.setScale(0, RoundingMode.UP);
        salesTax = salesTax.divide(BigDecimal.valueOf(20), RoundingMode.HALF_UP);

        return salesTax.multiply(BigDecimal.valueOf(quantity));
    }

    // Calcola il prezzo totale (comprensivo di tasse) per questo articolo
    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity)).add(getSalesTax());
    }
}
