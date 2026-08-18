class DigitalWallet {
    private String walletId;
    private String owner;
    private double balance;
    private int transactionCount;

    public DigitalWallet(String walletId, String owner, double initialBalance) {
        this.walletId = (walletId == null || walletId.isBlank()) ? "Unknown" : walletId.trim();
        this.owner = (owner == null || owner.isBlank()) ? "Unknown" : owner.trim();
        this.balance = Math.max(0.0, initialBalance);
        this.transactionCount = 0;
    }

    public boolean topUp(double amount) {
        if (amount <= 0) {
            System.out.printf("[儲值失敗] 金額必須大於 0 (傳入: %.2f)%n", amount);
            return false;
        }
        this.balance += amount;
        this.transactionCount++;
        System.out.printf("[儲值成功] +%.2f 元 | 當前餘額: %.2f 元%n", amount, this.balance);
        return true;
    }

    public boolean pay(double amount) {
        if (amount <= 0) {
            System.out.printf("[付款失敗] 金額必須大於 0 (傳入: %.2f)%n", amount);
            return false;
        }
        if (amount > this.balance) {
            System.out.printf("[付款失敗] 餘額不足 (欲扣除: %.2f 元, 當前餘額: %.2f 元)%n", amount, this.balance);
            return false;
        }
        this.balance -= amount;
        this.transactionCount++;
        System.out.printf("[付款成功] -%.2f 元 | 當前餘額: %.2f 元%n", amount, this.balance);
        return true;
    }

    public boolean refund(double amount) {
        if (amount <= 0) {
            System.out.printf("[退款失敗] 金額必須大於 0 (傳入: %.2f)%n", amount);
            return false;
        }
        this.balance += amount;
        this.transactionCount++;
        System.out.printf("[退款成功] +%.2f 元 | 當前餘額: %.2f 元%n", amount, this.balance);
        return true;
    }

    public String getWalletId() {
        return walletId;
    }

    public String getOwner() {
        return owner;
    }

    public double getBalance() {
        return balance;
    }

    public int getTransactionCount() {
        return transactionCount;
    }

    @Override
    public String toString() {
        return String.format("錢包 ID: %s | 持有者: %s | 餘額: %.2f 元 | 成功交易次數: %d 次", 
                walletId, owner, balance, transactionCount);
    }
}

public class DigitalWalletSystem {
    public static void main(String[] args) {
        DigitalWallet wallet = new DigitalWallet("W-1001", "陳小明", 100.0);
        System.out.println("=== 初始狀態 ===");
        System.out.println(wallet);
        System.out.println();

        System.out.println("=== 1. 正常儲值測試 ===");
        wallet.topUp(500.0);
        System.out.println();

        System.out.println("=== 2. 正常付款測試 ===");
        wallet.pay(200.0);
        System.out.println();

        System.out.println("=== 3. 餘額不足測試 ===");
        wallet.pay(1000.0); 
        System.out.println();

        System.out.println("=== 4. 負數與不合法金額測試 ===");
        wallet.topUp(-50.0);
        wallet.pay(-30.0);
        wallet.refund(0.0);
        System.out.println();

        System.out.println("=== 5. 退款測試 ===");
        wallet.refund(150.0);
        System.out.println();

        System.out.println("=== 最終狀態與交易統計 ===");
        System.out.println(wallet);
    }
}