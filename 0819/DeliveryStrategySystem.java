public class DeliveryStrategySystem {
    public static void main(String[] args) {
        OrderService homeOrder = new OrderService("ORD-2026-001", 1200.0, new HomeDelivery());
        OrderService storeOrder = new OrderService("ORD-2026-002", 800.0, new ConvenienceStorePickup());
        OrderService selfOrder = new OrderService("ORD-2026-003", 2500.0, new SelfPickup());

        homeOrder.printOrderSummary();
        storeOrder.printOrderSummary();
        selfOrder.printOrderSummary();

        System.out.println("--- 動態切換配送方式測試 ---");
        System.out.println("將訂單 ORD-2026-001 變更為 [自取]:");
        homeOrder.setDeliveryMethod(new SelfPickup());
        homeOrder.printOrderSummary();
    }
}

interface DeliveryMethod {
    double calculateFee(double orderAmount);
    String getEstimatedDeliveryInfo();
    String getMethodName();
}

class HomeDelivery implements DeliveryMethod {
    @Override
    public double calculateFee(double orderAmount) {
        if (orderAmount >= 1000.0) {
            return 0.0;
        }
        return 100.0;
    }

    @Override
    public String getEstimatedDeliveryInfo() {
        return "宅配到府：預計 1~2 個工作天內送達指定地址。";
    }

    @Override
    public String getMethodName() {
        return "宅配到府";
    }
}

class ConvenienceStorePickup implements DeliveryMethod {
    @Override
    public double calculateFee(double orderAmount) {
        if (orderAmount >= 600.0) {
            return 0.0;
        }
        return 60.0;
    }

    @Override
    public String getEstimatedDeliveryInfo() {
        return "超商取貨：預計 2~3 天送達指定超商門市，請憑簡訊通知取貨。";
    }

    @Override
    public String getMethodName() {
        return "超商取貨";
    }
}

class SelfPickup implements DeliveryMethod {
    @Override
    public double calculateFee(double orderAmount) {
        return 0.0;
    }

    @Override
    public String getEstimatedDeliveryInfo() {
        return "門市自取：訂單確認後即可於營業時間內至指定實體門市領取。";
    }

    @Override
    public String getMethodName() {
        return "門市自取";
    }
}

class OrderService {
    private String orderId;
    private double orderAmount;
    private DeliveryMethod deliveryMethod;

    public OrderService(String orderId, double orderAmount, DeliveryMethod deliveryMethod) {
        this.orderId = orderId;
        this.orderAmount = Math.max(0, orderAmount);
        this.deliveryMethod = deliveryMethod;
    }

    public void setDeliveryMethod(DeliveryMethod deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public double calculateShippingFee() {
        if (deliveryMethod == null) {
            return 0.0;
        }
        return deliveryMethod.calculateFee(orderAmount);
    }

    public double calculateTotalAmount() {
        return orderAmount + calculateShippingFee();
    }

    public void printOrderSummary() {
        System.out.println("==========================================");
        System.out.println("訂單編號: " + orderId);
        System.out.printf("商品金額: $%,.2f 元%n", orderAmount);
        if (deliveryMethod != null) {
            System.out.println("配送方式: " + deliveryMethod.getMethodName());
            System.out.printf("運費金額: $%,.2f 元%n", calculateShippingFee());
            System.out.printf("應付總額: $%,.2f 元%n", calculateTotalAmount());
            System.out.println("運送說明: " + deliveryMethod.getEstimatedDeliveryInfo());
        } else {
            System.out.println("配送方式: 未指定");
        }
        System.out.println("==========================================");
    }
}