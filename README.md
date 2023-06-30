# sales

# Sales Taxes Problem Solution

Questo repository contiene la mia soluzione al problema delle tasse di vendita.

## Struttura del Progetto

Il progetto è strutturato nel seguente modo:

- `Main.java`: Questo è il file principale che avvia l'applicazione.
- `constants`: Questa cartella contiene le classi `ExemptProduct` e `TaxRate` che definiscono i prodotti esenti da tasse e le aliquote fiscali.
- `data`: Questa cartella contiene i file di input `input.txt`, `input2.txt` e `input3.txt` che vengono utilizzati per testare l'applicazione.
- `model`: Questa cartella contiene le classi `Item` e `Payment` che rappresentano un articolo e un pagamento.
- `repository`: Questa cartella contiene la classe `PaymentsStorage` che gestisce la memorizzazione dei pagamenti.
- `service`: Questa cartella contiene le classi `PaymentService` e `TaxCalculatorService` che gestiscono il calcolo delle tasse e il processo di pagamento.
- `utils`: Questa cartella contiene la classe `TaxUtil` che fornisce metodi di utilità per il calcolo delle tasse.

## Come eseguire il progetto

Per eseguire il progetto, compila e avvia il file `Main.java`. L'applicazione leggerà i dati di input dai file nella cartella `data` e stamperà i dettagli del ricevuto, compresi gli articoli acquistati, le tasse di vendita pagate e il costo totale.

## Nota

E' presente un bug che non sono riuscito a risolvere. Il bug è relativo al calcolo sulla tassa di importazione sull'input 3. Il codice è commentato in quello specifico punto, lì offro una soluzione ragionevole al problema. Tuttavia il problema persiste.

## Licenza

Questo progetto è rilasciato sotto la licenza MIT.
