// 顧客類別
class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = (customerId == null || customerId.isBlank()) ? "Unknown" : customerId.trim();
        this.name = (name == null || name.isBlank()) ? "Unknown" : name.trim();
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
}

class OrderItem {
    private String productName;
    private int unitPrice;
    private int quantity;

    public OrderItem(String productName, int unitPrice, int quantity) {
        this.productName = (productName == null || productName.isBlank()) ? "Unknown" : productName.trim();
        this.unitPrice = Math.max(0, unitPrice);
        this.quantity = Math.max(0, quantity);
    }

    public String getProductName() {
        return productName;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getSubtotal() {
        return unitPrice * quantity;
    }
}

class CustomerOrder {
    private String orderId;
    private Customer customer;     
    private OrderItem[] items;  

    public CustomerOrder(String orderId, Customer customer, OrderItem[] items) {
        this.orderId = (orderId == null || orderId.isBlank()) ? "Unknown" : orderId.trim();
        this.customer = customer;
        this.items = (items != null) ? items.clone() : new OrderItem[0];
    }

    public int totalAmount() {
        int total = 0;
        for (OrderItem item : items) {
            if (item != null) {
                total += item.getSubtotal();
            }
        }
        return total;
    }

    public int totalItemQuantity() {
        int totalQty = 0;
        for (OrderItem item : items) {
            if (item != null) {
                totalQty += item.getQuantity();
            }
        }
        return totalQty;
    }

    public void printSummary() {
        System.out.println("==========================================");
        System.out.println("              訂單摘要 (Order Summary)    ");
        System.out.println("==========================================");
        System.out.println("訂單編號 : " + orderId);
        System.out.println("顧客資訊 : [" + (customer != null ? customer.getCustomerId() : "N/A") + "] " 
                + (customer != null ? customer.getName() : "無顧客資訊"));
        System.out.println("------------------------------------------");
        System.out.println("品項明細 :");
        
        for (int i = 0; i < items.length; i++) {
            OrderItem item = items[i];
            if (item != null) {
                System.out.printf(" %d. %-12s | 單價: %5d 元 | 數量: %2d | 小計: %6d 元%n",
                        (i + 1), item.getProductName(), item.getUnitPrice(), item.getQuantity(), item.getSubtotal());
            }
        }
        
        System.out.println("------------------------------------------");
        System.out.println("商品總件數 : " + totalItemQuantity() + " 件");
        System.out.printf("訂單總金額 : %,d 元%n", totalAmount());
        System.out.println("==========================================");
    }
}

public class CustomerOrderSystem {
    public static void main(String[] args) {
        Customer customer = new Customer("CST-8821", "林小華");

        OrderItem[] items = {
            new OrderItem("無線滑鼠", 650, 2),
            new OrderItem("機械鍵盤", 2480, 1),
            new OrderItem("27吋顯示器", 5900, 1),
            new OrderItem("Type-C 傳輸線", 290, 3)
        };

        CustomerOrder order = new CustomerOrder("ORD-20260818-01", customer, items);

        order.printSummary();
    }
}