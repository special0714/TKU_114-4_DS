class Account {
    private String id;
    private String name;
    private int balance;

    public Account(String id, String name, int balance) {
        this.id = (id == null || id.isBlank()) ? "Unknown" : id.trim();
        this.name = (name == null || name.isBlank()) ? "Unknown" : name.trim();
        this.balance = Math.max(0, balance); 
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBalance() {
        return balance;
    }

    public void withdraw(int amount) {
        this.balance -= amount;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }

    @Override
    public String toString() {
        return String.format("帳號: %s (%s) | 餘額: %,d 元", id, name, balance);
    }
}

class TransferService {
    
    public static boolean transfer(Account source, Account target, int amount) {
        if (source == null || target == null) {
            System.out.println("[轉帳失敗] 交易帳戶不可為 null (來源或目標帳戶缺失)");
            return false;
        }

        if (source == target) {
            System.out.println("[轉帳失敗] 來源帳戶與目標帳戶不能為同一物件");
            return false;
        }

        if (amount <= 0) {
            System.out.printf("[轉帳失敗] 轉帳金額必須大於 0 (傳入: %,d 元)%n", amount);
            return false;
        }

        if (source.getBalance() < amount) {
            System.out.printf("[轉帳失敗] 餘額不足 (欲轉出: %,d 元, 當前餘額: %,d 元)%n", 
                    amount, source.getBalance());
            return false;
        }

        source.withdraw(amount);
        target.deposit(amount);
        System.out.printf("[轉帳成功] 從 %s 轉出 %,d 元至 %s%n", 
                source.getName(), amount, target.getName());
        return true;
    }
}

public class AccountTransferService {
    public static void main(String[] args) {
        Account accA = new Account("A101", "張小明", 1000);
        Account accB = new Account("B202", "李大華", 500);

        System.out.println("=== 初始帳戶狀態 ===");
        System.out.println(accA);
        System.out.println(accB);
        System.out.println();

        System.out.println("=== 1. 測試成功轉帳 (張小明轉 300 給 李大華) ===");
        TransferService.transfer(accA, accB, 300);
        System.out.println(accA);
        System.out.println(accB);
        System.out.println();

        System.out.println("=== 2. 測試餘額不足 (張小明欲轉 1000，當前餘額僅 700) ===");
        TransferService.transfer(accA, accB, 1000);
        System.out.println("驗證狀態未改變: " + accA);
        System.out.println();

        System.out.println("=== 3. 測試同一帳戶轉帳 (張小明轉給自己) ===");
        TransferService.transfer(accA, accA, 100);
        System.out.println("驗證狀態未改變: " + accA);
        System.out.println();

        System.out.println("=== 4. 測試 Null 目標帳戶 ===");
        TransferService.transfer(accA, null, 200);
        System.out.println("驗證狀態未改變: " + accA);
        System.out.println();

        System.out.println("=== 5. 測試無效金額 (金額 <= 0) ===");
        TransferService.transfer(accA, accB, -50);
        System.out.println("驗證狀態未改變: " + accA);
    }
}