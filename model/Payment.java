package model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Payment {

    private Integer id;
    private List<Item> itemsList;

    public Payment(Integer id, List<Item> items) {
        setId(id);
        setItemsList(items);
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    // Imposto copia profonda in setter per buona pratica
    public void setItemsList (List<Item> items) {
        this.itemsList = new ArrayList<>();
        items.forEach(item -> itemsList.add(new Item(item)));
    }

    // calcolo tot tasse per tutti items di questo pagamento
    public BigDecimal getTotalSalesTax() {
        BigDecimal totalSalesTax = BigDecimal.ZERO;
        for (Item item : itemsList) {
            totalSalesTax = totalSalesTax.add(item.getSalesTax());
        }
        return totalSalesTax;
    }

    // calcola prezzo totale (comprensivo di tasse) per tutti gli articoli in questo pagamento
    public BigDecimal getTotalAmount() {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Item item : itemsList) {
            totalAmount = totalAmount.add(item.getTotalPrice());
        }
        return totalAmount;
    }

    @Override
    public Payment clone() {
        List<Item> clonedItemsList = new ArrayList<>();
        for (Item item : this.itemsList) {
            clonedItemsList.add(new Item(item));
        }
        return new Payment(this.id, clonedItemsList);
    }
}
