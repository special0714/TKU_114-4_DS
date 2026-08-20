public class FlexibleCheckoutSystem {
    public static void main(String[] args) {
        PricingPolicy originalPolicy = new OriginalPricingPolicy();
        PricingPolicy vipPolicy = new VipDiscountPricingPolicy();
        PricingPolicy thresholdPolicy = new ThresholdDiscountPricingPolicy();

        NotificationChannel emailChannel = new EmailNotificationChannel();
        NotificationChannel smsChannel = new SmsNotificationChannel();
        NotificationChannel consoleChannel = new ConsoleNotificationChannel();

        CheckoutService checkoutService = new CheckoutService();

        System.out.println("=== 開始測試 6 種 Pricing / Channel 組合 ===\n");

        CheckoutResult r1 = checkoutService.checkout("ORD-001", 1500.0, "alice@example.com", originalPolicy, emailChannel);
        r1.printResult();

        CheckoutResult r2 = checkoutService.checkout("ORD-002", 1500.0, "0912345678", vipPolicy, smsChannel);
        r2.printResult();

        CheckoutResult r3 = checkoutService.checkout("ORD-003", 2500.0, "AdminTerminal", thresholdPolicy, consoleChannel);
        r3.printResult();

        CheckoutResult r4 = checkoutService.checkout("ORD-004", 3000.0, "bob@example.com", vipPolicy, emailChannel);
        r4.printResult();

        CheckoutResult r5 = checkoutService.checkout("ORD-005", 1800.0, "0987654321", thresholdPolicy, smsChannel);
        r5.printResult();

        CheckoutResult r6 = checkoutService.checkout("ORD-006", 800.0, "ConsoleUser", originalPolicy, consoleChannel);
        r6.printResult();
    }
}

interface PricingPolicy {
    double calculateFinalPrice(double originalPrice);
    String getPolicyName();
}

class OriginalPricingPolicy implements PricingPolicy {
    @Override
    public double calculateFinalPrice(double originalPrice) {
        return Math.max(0, originalPrice);
    }

    @Override
    public String getPolicyName() {
        return "原價計費";
    }
}

class VipDiscountPricingPolicy implements PricingPolicy {
    @Override
    public double calculateFinalPrice(double originalPrice) {
        return Math.max(0, originalPrice * 0.85);
    }

    @Override
    public String getPolicyName() {
        return "VIP 85 折優惠";
    }
}

class ThresholdDiscountPricingPolicy implements PricingPolicy {
    @Override
    public double calculateFinalPrice(double originalPrice) {
        double validPrice = Math.max(0, originalPrice);
        if (validPrice >= 2000.0) {
            return validPrice - 300.0;
        }
        return validPrice;
    }

    @Override
    public String getPolicyName() {
        return "滿 2000 折 300 優惠";
    }
}

interface NotificationChannel {
    boolean notifyUser(String recipient, String message);
    String getChannelName();
}

class EmailNotificationChannel implements NotificationChannel {
    @Override
    public boolean notifyUser(String recipient, String message) {
        if (recipient == null || recipient.trim().isEmpty()) {
            return false;
        }
        System.out.println("[Email 發送至 " + recipient + "]: " + message);
        return true;
    }

    @Override
    public String getChannelName() {
        return "Email 通知";
    }
}

class SmsNotificationChannel implements NotificationChannel {
    @Override
    public boolean notifyUser(String recipient, String message) {
        if (recipient == null || recipient.trim().isEmpty()) {
            return false;
        }
        System.out.println("[SMS 發送至 " + recipient + "]: " + message);
        return true;
    }

    @Override
    public String getChannelName() {
        return "SMS 簡訊通知";
    }
}

class ConsoleNotificationChannel implements NotificationChannel {
    @Override
    public boolean notifyUser(String recipient, String message) {
        if (recipient == null || recipient.trim().isEmpty()) {
            return false;
        }
        System.out.println("[Console 輸出 (" + recipient + ")]: " + message);
        return true;
    }

    @Override
    public String getChannelName() {
        return "Console 控制台通知";
    }
}

class CheckoutResult {
    private String orderId;
    private double originalPrice;
    private double finalPrice;
    private boolean notificationSuccess;
    private String policyName;
    private String channelName;

    public CheckoutResult(String orderId, double originalPrice, double finalPrice, boolean notificationSuccess, String policyName, String channelName) {
        this.orderId = orderId;
        this.originalPrice = originalPrice;
        this.finalPrice = finalPrice;
        this.notificationSuccess = notificationSuccess;
        this.policyName = policyName;
        this.channelName = channelName;
    }

    public String getOrderId() {
        return orderId;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public boolean isNotificationSuccess() {
        return notificationSuccess;
    }

    public String getPolicyName() {
        return policyName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void printResult() {
        System.out.println("--------------------------------------------------");
        System.out.println("訂單編號: " + orderId);
        System.out.println("計價策略: " + policyName);
        System.out.printf("原價: $%,.2f 元 | 實付金額: $%,.2f 元%n", originalPrice, finalPrice);
        System.out.println("通知管道: " + channelName);
        System.out.println("通知狀態: " + (notificationSuccess ? "成功" : "失敗"));
        System.out.println("--------------------------------------------------\n");
    }
}

class CheckoutService {
    public CheckoutResult checkout(String orderId, double originalPrice, String recipient, PricingPolicy pricingPolicy, NotificationChannel notificationChannel) {
        double validOriginalPrice = Math.max(0, originalPrice);
        double finalPrice = (pricingPolicy != null) ? pricingPolicy.calculateFinalPrice(validOriginalPrice) : validOriginalPrice;
        String policyName = (pricingPolicy != null) ? pricingPolicy.getPolicyName() : "預設原價";
        String channelName = (notificationChannel != null) ? notificationChannel.getChannelName() : "無通知管道";

        String msg = "訂單 [" + orderId + "] 結帳成功！實付金額為: $" + String.format("%.2f", finalPrice) + " 元。";
        boolean notifyStatus = false;

        if (notificationChannel != null) {
            notifyStatus = notificationChannel.notifyUser(recipient, msg);
        }

        return new CheckoutResult(orderId, validOriginalPrice, finalPrice, notifyStatus, policyName, channelName);
    }
}