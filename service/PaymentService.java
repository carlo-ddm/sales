package service;

import java.math.BigDecimal;

import model.Payment;
import repository.PaymentsStorage;

public class PaymentService implements TaxCalculatorService {
  private PaymentsStorage repository;

  public PaymentService(PaymentsStorage repository) {
    this.repository = repository;
  }

  public void createPayment(Payment payment) {
    this.repository.createPayment(payment.clone());
  }

  public Payment retrievePayment(Integer id) {
    Payment payment = this.repository.retrievePayment(id).clone();
    return payment;
  }

  public void updatePayment(Payment payment) {
    this.repository.updatePayment(payment.clone());
  }

  public void deletePayment(Integer id) {
    this.repository.deletePayment(id);
  }

  @Override
  public BigDecimal getSalesTax(Integer paymentId) {
    Payment payment = this.repository.retrievePayment(paymentId);
    return payment.getTotalSalesTax();
  }

  @Override
  public BigDecimal getTotalPrice(Integer paymentId) {
    Payment payment = this.repository.retrievePayment(paymentId);
    return payment.getTotalAmount();
  }

}
