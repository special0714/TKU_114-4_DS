public class DeviceInspectionSystem {
    public static void main(String[] args) {
        Device[] devices = new Device[] {
            new Laptop("MacBook Pro"),
            new Printer("Epson L3210"),
            new Router("ASUS RT-AX88U"),
            new Printer("HP LaserJet Pro")
        };

        System.out.println("=== 1. 執行所有設備的診斷程序 (Polymorphism) ===");
        for (Device d : devices) {
            d.runDiagnostic();
        }

        System.out.println("\n=== 2. 針對印表機執行專屬清潔程序 (Pattern Matching instanceof) ===");
        for (Device d : devices) {
            if (d instanceof Printer printer) {
                printer.cleanPrintHead();
            }
        }
    }
}

class Device {
    private String modelName;

    public Device(String modelName) {
        this.modelName = modelName;
    }

    public String getModelName() {
        return modelName;
    }

    public void runDiagnostic() {
        System.out.println("[" + modelName + "] 正在執行標準設備自我診斷...");
    }
}

class Laptop extends Device {
    public Laptop(String modelName) {
        super(modelName);
    }

    @Override
    public void runDiagnostic() {
        System.out.println("[" + getModelName() + "] 檢測項目：CPU 溫度、記憶體使用率、電池健康度。狀況正常。");
    }
}

class Printer extends Device {
    public Printer(String modelName) {
        super(modelName);
    }

    @Override
    public void runDiagnostic() {
        System.out.println("[" + getModelName() + "] 檢測項目：墨水/碳粉殘量、進紙感應器。狀況正常。");
    }

    public void cleanPrintHead() {
        System.out.println("[" + getModelName() + "] 開始執行噴頭自動清潔作業... 清潔完成！");
    }
}

class Router extends Device {
    public Router(String modelName) {
        super(modelName);
    }

    @Override
    public void runDiagnostic() {
        System.out.println("[" + getModelName() + "] 檢測項目：網絡連線狀態、Wi-Fi 訊號強度、防火牆設定。狀況正常。");
    }
}