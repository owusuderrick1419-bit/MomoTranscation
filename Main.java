
import java.util.Scanner;

public class Main {

   
    public static double readValidAmount(Scanner sc) {

        double amount;

        do {
            System.out.print("Enter transaction amount: ");
            amount = sc.nextDouble();

            if (amount < MoMoTransaction.MIN_AMOUNT
                    || amount > MoMoTransaction.MAX_AMOUNT) {

                System.out.println("Invalid amount. Enter between GH₵ 2.00 and GH₵ 5000.00.");
            }

        } while (amount < MoMoTransaction.MIN_AMOUNT|| amount > MoMoTransaction.MAX_AMOUNT);

        return amount;
    }

  
    public static double calculateTotal(double[] amounts) {

        double total = 0.0;

        for (int i = 0; i < amounts.length; i++) {
            total = total + amounts[i];
        }

        return total;
    }

    public static int countHighValue(double[] amounts, double threshold) {

        int count = 0;

        for (int i = 0; i < amounts.length; i++) {

            if (amounts[i] >= threshold) {
                count++;
            }
        }

        return count;
    }

    public static double findLargest(double[] amounts) {

        if (amounts.length == 0) {
            return 0.0;
        }

        double largest = amounts[0];

        for (int i = 1; i < amounts.length; i++) {

            if (amounts[i] > largest) {
                largest = amounts[i];
            }
        }

        return largest;
    }

    // Simulate cash depletion
    public static int simulateCashDepletion(double startingCash, double[] queue) {

        double remainingCash = startingCash;
        int k = 0;
        int served = 0;

        while (k < queue.length && queue[k] <= remainingCash) {

            remainingCash -= queue[k];

            served++;
            k++;
        }

        return served;
    }

    public static void main(String[] args) {

      
        System.out.println("--- 1. Phone Sanitizer Verification ---");

        String rawPhone = "\u00A0 024-555-1234\u00A0 ";
        String cleanedPhone = MoMoTransaction.cleanPhone(rawPhone);

        System.out.println("Cleaned '" + rawPhone + "' -> " + cleanedPhone + " (Valid)");

        String invalidPhone1 = "030-999-0000";
        String invalidPhone2 = "024-ABC-1234";

        System.out.println("Testing '" + invalidPhone1 + "' -> " + (MoMoTransaction.isValidPhone(MoMoTransaction.cleanPhone(invalidPhone1))
                        ? "Valid" : "Invalid (Rejected)")
        );

        System.out.println("Testing '" + invalidPhone2 + "' -> " + (MoMoTransaction.isValidPhone(MoMoTransaction.cleanPhone(invalidPhone2))
                        ? "Valid" : "Invalid (Rejected)")
        );

        MoMoTransaction t1 = new MoMoTransaction(" 024-555-1234 ", "CASH_IN", 400.00);

        MoMoTransaction t2 = new MoMoTransaction("055-123-4567", "CASH_OUT", 2000.00);

        MoMoTransaction t3 = new MoMoTransaction("020-888-9999", 75.00);

        MoMoTransaction t4 = new MoMoTransaction("054-000-1122", "TRANSFER", 250.00);

    
        System.out.println("\n--- 2. Batch Transaction Analytics ---");

        double[] amounts = {400.0, 2000.0, 75.0, 250.0};

        System.out.println("Daily Transaction Count: " + MoMoTransaction.getTransactionCount());

        System.out.printf("Total Processed Volume: GHC %.2f%n", calculateTotal(amounts));

        System.out.println("Transactions >= GHC 200.00: " + countHighValue(amounts, 200.00));

        System.out.printf("Largest Transaction Amount: GHC %.2f%n", findLargest(amounts));

       
        System.out.println("\n--- 3. Cash Float Depletion Simulation ---");

        double[] queue = {600.0, 1200.0, 800.0, 900.0, 400.0};

        System.out.printf("Starting Cash Float: GHC %.2f%n", 2500.00);
        System.out.print("Queue of withdrawals: [");

            for (int i = 0; i < queue.length; i++) {
        System.out.printf("GHC %.1f", queue[i]);

            if (i < queue.length - 1) {
        System.out.print(", ");
            }
        }
        System.out.println("]");

        System.out.println("Withdrawals serviced before cash float exhausted: "+ simulateCashDepletion(2500.00, queue));

        System.out.println( "\n--- 4. Regulatory Tariff Update ---");

        MoMoTransaction.updateFeeCap(20.00);

        System.out.printf("Updating nationwide fee cap to: GHC %.2f%n", MoMoTransaction.getFeeCap());MoMoTransaction.calculateFee("CASH_OUT", 3000.00);
        
        double standardFee = MoMoTransaction.calculateFee("CASH_OUT", 3000.00);

        double discountedFee = standardFee * (1 - 0.20);

        System.out.printf("Standard fee on GHC 3000.00 withdrawal (capped): GHC %.2f%n", standardFee);

        System.out.printf("Discounted fee with 20%% campus promo: GHC %.2f%n", discountedFee);

                


     
        MoMoTransaction.updateFranchiseName("CampusPay Africa");

        System.out.println(
                "\n--- 5. Transaction Receipts (CampusPay Africa) ---"
        );

        t1.printReceipt();
        t2.printReceipt();
        t3.printReceipt();
        t4.printReceipt();

        System.out.println( "-------------------------------------------------------------------------");

        System.out.println("System Status: " + MoMoTransaction.getTransactionCount() + " transactions processed across network");

        System.out.printf("Total Fee Revenue Collected: GHC %.2f%n", MoMoTransaction.getTotalFeeRevenue());
    }
}