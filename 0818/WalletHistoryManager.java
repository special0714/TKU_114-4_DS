class Transaction {
    private int sequence;     
    private String type;      
    private double amount;    
    private double balanceAfter; 

    public Transaction(int sequence, String type, double amount, double balanceAfter) {
        this.sequence = sequence;
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
    }

    public int getSequence() {
        return sequence;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    @Override
    public String toString() {
        return String.format("序號 #%02d | 類型: %-12s | 金額: %8.2f 元 | 結餘: %8.2f 元",
                sequence, type, amount, balanceAfter);
    }
}

class DigitalWallet {
    private String walletId;
    private String owner;
    private double balance;
    private Transaction[] history; 
    private int txCount;          
    private int sequenceCounter;    

    public DigitalWallet(String walletId, String owner, double initialBalance, int maxCapacity) {
        this.walletId = (walletId == null || walletId.isBlank()) ? "Unknown" : walletId.trim();
        this.owner = (owner == null || owner.isBlank()) ? "Unknown" : owner.trim();
        this.balance = Math.max(0.0, initialBalance);
        this.history = new Transaction[Math.max(1, maxCapacity)];
        this.txCount = 0;
        this.sequenceCounter = 1;
    }

    public boolean isFull() {
        return txCount >= history.length;
    }

    private void recordTransaction(String type, double amount) {
        history[txCount] = new Transaction(sequenceCounter++, type, amount, this.balance);
        txCount++;
    }

    public boolean topUp(double amount) {
        if (amount <= 0) {
            System.out.printf("[%s 儲值失敗] 金額必須大於 0%n", owner);
            return false;
        }
        if (isFull()) {
            System.out.printf("[%s 儲值失敗] 交易紀錄陣列已滿，不進行餘額修改%n", owner);
            return false;
        }
        this.balance += amount;
        recordTransaction("TOPUP", amount);
        return true;
    }

    public boolean pay(double amount) {
        if (amount <= 0 || amount > balance) {
            System.out.printf("[%s 付款失敗] 金額不合法或餘額不足%n", owner);
            return false;
        }
        if (isFull()) {
            System.out.printf("[%s 付款失敗] 交易紀錄陣列已滿，不進行餘額修改%n", owner);
            return false;
        }
        this.balance -= amount;
        recordTransaction("PAY", amount);
        return true;
    }

    public boolean transferTo(DigitalWallet target, double amount) {
        if (target == null || target == this) {
            System.out.println("[轉帳失敗] 目標帳戶無效或為同一帳戶");
            return false;
        }
        if (amount <= 0) {
            System.out.println("[轉帳失敗] 轉帳金額必須大於 0");
            return false;
        }
        if (this.balance < amount) {
            System.out.printf("[轉帳失敗] %s 餘額不足 (欲轉出 %.2f 元, 餘額 %.2f 元)%n", owner, amount, balance);
            return false;
        }

        if (this.isFull() || target.isFull()) {
            System.out.println("[轉帳失敗] 來源或目標錢包交易紀錄已滿，取消轉帳以維護資料一致性");
            return false;
        }

        this.balance -= amount;
        this.recordTransaction("TRANSFER_OUT", amount);

        target.balance += amount;
        target.recordTransaction("TRANSFER_IN", amount);

        System.out.printf("[轉帳成功] %s 轉帳 %.2f 元至 %s%n", owner, amount, target.owner);
        return true;
    }

    public Transaction findTransaction(int sequence) {
        for (int i = 0; i < txCount; i++) {
            if (history[i].getSequence() == sequence) {
                return history[i];
            }
        }
        return null;
    }

    public double totalByType(String type) {
        if (type == null) return 0.0;
        double total = 0.0;
        for (int i = 0; i < txCount; i++) {
            if (history[i].getType().equalsIgnoreCase(type)) {
                total += history[i].getAmount();
            }
        }
        return total;
    }

    public void printStatement() {
        System.out.println("==========================================================================");
        System.out.printf("  錢包完整對帳單 (Statement) | ID: %-6s | 持有者: %s%n", walletId, owner);
        System.out.println("==========================================================================");
        System.out.printf(" 當前餘額: %10.2f 元 | 容量進度: %d / %d%n", balance, txCount, history.length);
        System.out.println("--------------------------------------------------------------------------");
        if (txCount == 0) {
            System.out.println(" (尚無交易紀錄)");
        } else {
            for (int i = 0; i < txCount; i++) {
                System.out.println(" " + history[i]);
            }
        }
        System.out.println("==========================================================================\n");
    }

    public String getWalletId() { return walletId; }
    public String getOwner() { return owner; }
    public double getBalance() { return balance; }
}

public class WalletHistoryManager {
    public static void main(String[] args) {
        DigitalWallet walletA = new DigitalWallet("W101", "張小明", 1000.0, 4);
        DigitalWallet walletB = new DigitalWallet("W202", "李大華", 200.0, 4);

        System.out.println("=== 1. 執行基礎交易與轉帳 ===");
        walletA.topUp(500.0);                 
        walletA.pay(300.0);                     
        walletA.transferTo(walletB, 400.0);        
        System.out.println();

        System.out.println("=== 2. 測試交易陣列滿載防護機制 ===");
        walletA.topUp(100.0);                  

        System.out.println("-> 嘗試對滿載的 walletA 進行儲值：");
        walletA.topUp(200.0);

        System.out.println("-> 嘗試由 walletB 轉帳給已滿載的 walletA：");
        walletB.transferTo(walletA, 50.0);
        System.out.println();

        System.out.println("=== 3. 測試 findTransaction(sequence) ===");
        int searchSeq = 3;
        Transaction foundTx = walletA.findTransaction(searchSeq);
        if (foundTx != null) {
            System.out.printf("walletA 找到序號 #%d 的交易: %s%n", searchSeq, foundTx);
        } else {
            System.out.printf("walletA 找不到序號 #%d 的交易%n", searchSeq);
        }

        Transaction notFoundTx = walletA.findTransaction(99);
        System.out.println("walletA 尋找不存在序號 #99 結果: " + notFoundTx);
        System.out.println();

        System.out.println("=== 4. 測試 totalByType(type) ===");
        System.out.printf("walletA 的 'TOPUP' 累積金額: %.2f 元%n", walletA.totalByType("TOPUP"));
        System.out.printf("walletA 的 'TRANSFER_OUT' 累積金額: %.2f 元%n", walletA.totalByType("TRANSFER_OUT"));
        System.out.printf("walletB 的 'TRANSFER_IN' 累積金額: %.2f 元%n", walletB.totalByType("TRANSFER_IN"));
        System.out.println();

        System.out.println("=== 5. 輸出兩個錢包的完整對帳單 ===");
        walletA.printStatement();
        walletB.printStatement();
    }
}