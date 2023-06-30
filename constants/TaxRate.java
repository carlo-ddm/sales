package constants;

public enum TaxRate {
    BASIC_SALES(0.10),
    IMPORT_DUTY(0.05);

    private final double rate;

    TaxRate(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return this.rate;
    }
}

