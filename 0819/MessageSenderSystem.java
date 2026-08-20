public class MessageSenderSystem {
    public static void main(String[] args) {
        MessageSender emailSender = new EmailSender();
        MessageSender smsSender = new SmsSender();
        MessageSender consoleSender = new ConsoleSender();

        notify(emailSender, "user@example.com", "您的驗證碼為 123456");
        notify(smsSender, "0912345678", "您的餐點已外送到達！");
        notify(consoleSender, "System", "系統維護公告：今晚 12 點進行例行維護。");

        notify(emailSender, "  ", "測試無效接收者");
        notify(smsSender, "0987654321", "");
    }

    public static void notify(MessageSender sender, String receiver, String message) {
        if (sender == null) {
            System.out.println("[錯誤] 未指定 MessageSender。");
            return;
        }
        if (receiver == null || receiver.trim().isEmpty()) {
            System.out.println("[錯誤] 接收者無效或為空白。");
            return;
        }
        if (message == null || message.trim().isEmpty()) {
            System.out.println("[錯誤] 訊息內容無效或為空白。");
            return;
        }
        sender.send(receiver, message);
    }
}

interface MessageSender {
    void send(String receiver, String message);
}

class EmailSender implements MessageSender {
    @Override
    public void send(String receiver, String message) {
        System.out.println("【Email 傳送】寄送至: " + receiver + " | 內容: " + message);
    }
}

class SmsSender implements MessageSender {
    @Override
    public void send(String receiver, String message) {
        System.out.println("【SMS 簡訊傳送】寄送至: " + receiver + " | 內容: " + message);
    }
}

class ConsoleSender implements MessageSender {
    @Override
    public void send(String receiver, String message) {
        System.out.println("【Console 輸出】[對象: " + receiver + "] " + message);
    }
}