public class MoMoTransaction {
    private static String franchiseName = " Campany Global";
    private static double feeCap = 15.00;

    private static int nextTranscationNumber = 1001;
    private static int transcationCount = 0;
    private static double totalFeeRevenue = 0.0;

    public static final double MIN_AMOUNT = 2.00;
    public static final double MAX_AMOUNT = 5000.00;

    private String transcationCode;
    private String phoneNumber;
    private String transcationType;
    private double amount;
    private double fee;

    public MoMoTransaction(String phone, String type, double amount) {
        this.transcationCode = "TX -" + nextTranscationNumber++;
        this.phoneNumber = isValidPhone(phone) ? cleanPhone(phone) : "0000000000";
        this.transcationType = type;
        this.amount = amount;
        this.fee = calculateFee(type, amount);
        transcationCount++;
        totalFeeRevenue += this.fee;
    }

    public MoMoTransaction(String phone, double amount) {
        this(phone, "TRANSFER", amount);
    }

    public static String cleanPhone(String raw) {
        return raw == null ? "" : raw.replace(" ", "").replace("-", "");
    }

    public static boolean isValidPhone(String raw) {
        String cleaned = cleanPhone(raw);
        if (cleaned.length() != 10) return false;
        if (!(cleaned.startsWith("02") || cleaned.startsWith("05"))) return false;
        for (char c : cleaned.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    public static double calculateFee(String type, double amount) {
        switch (type) {
            case "CASH_IN":
                return 0.00;
            case "CASH_OUT":
                return Math.min(amount * 0.01, feeCap);
            case "TRANSFER":
                if (amount <= 100.00) {
                    return 1.50;
                } else {
                    return 5.00;
                }
            default:
                return 0.00;
        }
    }

    public static double calculateFee(String type, double amount, double discountRate) {
        double standardFee = calculateFee(type, amount);
        return standardFee * (1.0 - discountRate);
    }

    public static void updateFeeCap(double newCap) {
        feeCap = newCap;
    }

    public static void updateFranchiseName(String newName) {
        franchiseName = newName;
    }

    public static void updatefranchiseName(String newName) {
        updateFranchiseName(newName);
    }

    public void printReceipt() {
        System.out.printf("%s | %s | %-8s | Amount : GHC %.2f | Fee : GHC %.2f%n",
                transcationCode, phoneNumber, transcationType, amount, fee);
    }

    public static int getTransactionCount() {
        return transcationCount;
    }

    public static double getTotalFeeRevenue() {
        return totalFeeRevenue;
    }

    public static double getFeeCap() {
        return feeCap;
    }
}
