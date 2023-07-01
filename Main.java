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

    static Path[] paths = new Path[] { Paths.get("data/input.txt"), Paths.get("data/input2.txt"),
            Paths.get("data/input3.txt") };

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

    /**
     * 1) creo item con metodo createItemFromLine che estrae parti di line di input x creare item
     * 2) aggiungo item a list
     * 3) essendo processFile metodo ciclato creo tanti pagamenti quanti file di testo
     * 4) a ogni pagamento associo lista di items
     * 5) con servizio di pagamento salvo l'oggetto pagamento in repo (3 files --> 3 cicli --> 3 pagamenti)
     * 6) stampo ricevuta utilizzando metodi oggetto item per calcoli
     * 7) calcolo tasse con metodi servizio di pagamento (Stampo totali)
     * @param filePath
     * @param paymentService
     * @param paymentId
     * @throws IOException
     */
    private static void processFile(Path filePath, PaymentService paymentService, int paymentId) throws IOException {
        System.out.println("\nProcessing file: " + filePath.getFileName());

        List<Item> items = new ArrayList<>();
        Files.lines(filePath).forEach(line -> {
            Item item = createItemFromLine(line);
            items.add(item);
        });

        // Creo pagamento e aggiungo item
        Payment payment = new Payment(paymentId, items);

        // Salvataggio pagamento in repo
        paymentService.createPayment(payment);

        // Stampo ricevuta
        for (Item item : items) {
            BigDecimal itemTotalPrice = item.getTotalPrice();
            System.out.printf("%d %s: %.2f\n", item.getQuantity(), item.getName(), itemTotalPrice);
        }

        // Utilizzo servizio per calcolo tasse
        BigDecimal totalSalesTax = paymentService.getSalesTax(payment.getId());
        BigDecimal totalPrice = paymentService.getTotalPrice(payment.getId());

        // Stampo totali
        System.out.printf("Sales Taxes: %.2f\n", totalSalesTax);
        System.out.printf("Total: %.2f\n", totalPrice);
    }

    
    /**
     *                  ** Estrazione di parti per linea di input ** capolavoro che non funziona
     * estraggo parti per creare item 
     * @param inputLine
     * @return item
     */
    private static Item createItemFromLine(String inputLine) {
        String[] parts = inputLine.split(" ");
        int quantity = Integer.parseInt(parts[0]);
        BigDecimal price = new BigDecimal(parts[parts.length - 1]);
        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 1; i < parts.length - 2; i++) {
            nameBuilder.append(parts[i]).append(" ");
        }
        String name = nameBuilder.toString().trim();
        boolean isImported = inputLine.contains("imported");

        // La linea di codice appena sotto corregge l'output 2 ma sballa completamente l'output 3  L'errore è in questo punto ma non riesco a correggerlo!!! L'errore ha a che fare con "imported" che all'ultima voce dell'outout tre deve essere considerato come un nome e non come realmente importato e quindi tassabile.

        // Controllo nel caso la parola "importd" fa parte del nome. Se così fosse non verrebbe considerata tassabile per importazione. (Input3, ultima voce.)
                //    && !(inputLine.indexOf("imported") > inputLine.indexOf(parts[1])) ?  true :  false;

        // Ennesimo tentativo di correzione prima e ultima voce di Output 3, fallito. L'approccio di cui sopra funzionava nella versione semplificata. Nel refactoring incontro un problema che non sono riuscito a risolvere. Di seguito un tentativo di correzione che non ha funzionato, ma che reputo valido (arroccato ma valido).

        // if (inputLine.contains("imported box of chocolates")) {
        //     isImported = false;
        // }

        // Controllo se è esente da tasse con classe TaxUtil che utilizza enum (ExemptProduct)
        boolean isExemptFromTax = TaxUtil.isExemptFromTax(name);
        Item item = new Item(name, price, isExemptFromTax, isImported, quantity);
        return item;
    }
}

// NOTA: pozione "imported" non c'entra