package constants;

public enum ExemptProduct {
    BOOK("book"),
    CHOCOLATE_BAR("chocolate bar"),
    BOX_OF_CHOCOLATES("box of chocolates"),
    BOX_OF_IMPORTED_CHOCOLATES("box of imported chocolates"),
    PACKET_OF_HEADACHE_PILLS("packet of headache pills");

    private final String productName;

    ExemptProduct(String productName) {
        this.productName = productName;
    }

    public String getProductName() {
        return this.productName;
    }
}

