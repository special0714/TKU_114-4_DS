import java.util.Arrays;

final class InventorySnapshot {
    private final String warehouseId;
    private final int[] quantities;

    public InventorySnapshot(String warehouseId, int[] quantities) {
        this.warehouseId = warehouseId;
        if (quantities == null) {
            this.quantities = new int[0];
        } else {
            this.quantities = quantities.clone();
        }
    }

    public String getWarehouseId() {
        return warehouseId;
    }

    public int[] getQuantities() {
        return quantities.clone();
    }

    public int totalQuantity() {
        int total = 0;
        for (int q : quantities) {
            total += q;
        }
        return total;
    }

    public int outOfStockCount() {
        int count = 0;
        for (int q : quantities) {
            if (q == 0) {
                count++;
            }
        }
        return count;
    }
}

public class InventorySnapshotPractice {
    public static void main(String[] args) {
        int[] testData = {5, 0, 3, 0};
        InventorySnapshot snapshot = new InventorySnapshot("WH-001", testData);

        System.out.println("=== 1. 基本測試 ({5, 0, 3, 0}) ===");
        System.out.println("倉庫編號: " + snapshot.getWarehouseId());
        System.out.println("庫存明細: " + Arrays.toString(snapshot.getQuantities()));
        System.out.println("總數量 (應為 8): " + snapshot.totalQuantity());
        System.out.println("缺貨品項數 (應為 2): " + snapshot.outOfStockCount());
        System.out.println();

        System.out.println("=== 2. 不可變性與防禦性複製驗證 ===");
        testData[0] = 999;
        System.out.println("修改傳入的原始陣列後，快照總數 (應仍為 8): " + snapshot.totalQuantity());

        int[] retrievedQuantities = snapshot.getQuantities();
        retrievedQuantities[1] = 888;
        System.out.println("修改 Getter 回傳陣列後，快照缺貨數 (應仍為 2): " + snapshot.outOfStockCount());
        System.out.println();

        System.out.println("=== 3. 邊界條件測試 (Null 陣列) ===");
        InventorySnapshot nullSnapshot = new InventorySnapshot("WH-NULL", null);
        System.out.println("傳入 null 時的庫存陣列長度: " + nullSnapshot.getQuantities().length);
        System.out.println("總數量 (應為 0): " + nullSnapshot.totalQuantity());
        System.out.println("缺貨品項數 (應為 0): " + nullSnapshot.outOfStockCount());
    }
}