package service;

import java.math.BigDecimal;

public interface TaxCalculatorService {
  public BigDecimal getSalesTax(Integer paymentId);
  public BigDecimal getTotalPrice(Integer paymentId);
}
