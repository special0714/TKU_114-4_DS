public class Equipment {
    private String id;
    private String name;
    private int availableCount;

    public Equipment(String id, String name, int availableCount) {
        this.id = (id == null || id.isBlank()) ? "Unknown" : id.trim();
        this.name = (name == null || name.isBlank()) ? "Unknown" : name.trim();
        this.availableCount = Math.max(0, availableCount); // 負數自動歸零
    }

    public boolean borrowOne() {
        if (this.availableCount > 0) {
            this.availableCount--;
            return true;
        }
        return false;
    }

    public void returnItems(int quantity) {
        if (quantity > 0) {
            this.availableCount += quantity;
        }
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public int getAvailableCount() { return availableCount; }

    @Override
    public String toString() {
        return String.format("設備編號: %s | 名稱: %s | 可借數量: %d", id, name, availableCount);
    }
}