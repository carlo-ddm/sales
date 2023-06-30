package repository;
import java.util.HashMap;
import java.util.Map;
import model.Payment;

public class PaymentsStorage {

  Map<Integer, Payment> paymentsList = new HashMap<>();

  public void createPayment(Payment payment) {
        this.paymentsList.put(payment.getId(), payment.clone());
    }

    public Payment retrievePayment(Integer id) {
        return this.paymentsList.get(id).clone();
    }

    public void updatePayment(Payment payment) {
        this.paymentsList.put(payment.getId(), payment.clone());
    }

    public void deletePayment(Integer id) {
        this.paymentsList.remove(id);
    }
}
