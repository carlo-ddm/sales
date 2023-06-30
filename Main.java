import model.Item;
import model.Payment;
import repository.PaymentsStorage;
import service.PaymentService;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import utils.TaxUtil;

public class Main {

    static Path[] paths = new Path[] { Paths.get("data/input.txt"), Paths.get("data/input2.txt"), Paths.get("data/input3.txt") };

    public static void main(String[] args) {

        // Inizializza il repository e il servizio
        PaymentsStorage paymentsStorage = new PaymentsStorage();
        PaymentService paymentService = new PaymentService(paymentsStorage);

        try {
            int paymentId = 1;
            for (Path path : paths) {
                processFile(path, paymentService, paymentId);
                paymentId++;
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

   private static void processFile(Path filePath, PaymentService paymentService, int paymentId) throws IOException {
    System.out.println("\nProcessing file: " + filePath.getFileName());

    List<Item> items = new ArrayList<>();
    Files.lines(filePath).forEach(line -> {
        Item item = createItemFromLine(line);
        items.add(item);
    });

    // Crea un nuovo Payment e aggiungi gli articoli
    Payment payment = new Payment(paymentId, items);

    // SALVA il pagamento nel repository
    paymentService.createPayment(payment);

    // Stampa i dettagli della ricevuta per ogni articolo
    for (Item item : items) {
        BigDecimal itemTotalPrice = item.getTotalPrice();
        System.out.printf("%d %s: %.2f\n", item.getQuantity(), item.getName(), itemTotalPrice);
    }

    // Utilizza il servizio per calcolare i totali
    BigDecimal totalSalesTax = paymentService.getSalesTax(payment.getId());
    BigDecimal totalPrice = paymentService.getTotalPrice(payment.getId());

    // Stampa i totali
    System.out.printf("Sales Taxes: %.2f\n", totalSalesTax);
    System.out.printf("Total: %.2f\n", totalPrice);
}


    private static Item createItemFromLine(String inputLine) {
        String[] parts = inputLine.split(" ");
        int quantity = Integer.parseInt(parts[0]);
        BigDecimal price = new BigDecimal(parts[parts.length - 1]);
        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 1; i < parts.length - 2; i++) {
            nameBuilder.append(parts[i]).append(" ");
        }
        String name = nameBuilder.toString().trim();
        boolean isImported = inputLine.contains("imported")
                && (inputLine.indexOf("imported") < inputLine.indexOf(parts[1]));

        boolean isExemptFromTax = TaxUtil.isExemptFromTax(name);
        Item item = new Item(name, price, isExemptFromTax, isImported, quantity);
        return item;
    }
}
