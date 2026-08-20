public class EmployeeConstructorChain {
    public static void main(String[] args) {
        System.out.println("--- 建立 FullTimeEmployee 物件 ---");
        EmployeeBase ft = new FullTimeEmployee("FT01", "Alice", -50000);
        System.out.println("正職薪資: " + ft.calculatePay());

        System.out.println("\n--- 建立 PartTimeEmployee 物件 ---");
        EmployeeBase pt = new PartTimeEmployee("PT01", "Bob", -150, -20);
        System.out.println("兼職薪資: " + pt.calculatePay());

        System.out.println("\n--- Constructor 實際執行順序 ---");
        System.out.println("1. 建立 FullTimeEmployee 物件時：");
        System.out.println("   - 第一步：進入 EmployeeBase 的 constructor，印出 \"[Constructor] EmployeeBase\"");
        System.out.println("   - 第二步：回到 FullTimeEmployee 的 constructor，印出 \"[Constructor] FullTimeEmployee\"");
        System.out.println("2. 建立 PartTimeEmployee 物件時：");
        System.out.println("   - 第一步：進入 EmployeeBase 的 constructor，印出 \"[Constructor] EmployeeBase\"");
        System.out.println("   - 第二步：回到 PartTimeEmployee 的 constructor，印出 \"[Constructor] PartTimeEmployee\"");
    }
}

abstract class EmployeeBase {
    private String id;
    private String name;

    public EmployeeBase(String id, String name) {
        System.out.println("[Constructor] EmployeeBase");
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public abstract double calculatePay();
}

class FullTimeEmployee extends EmployeeBase {
    private double monthlySalary;

    public FullTimeEmployee(String id, String name, double monthlySalary) {
        super(id, name);
        System.out.println("[Constructor] FullTimeEmployee");
        this.monthlySalary = Math.max(0, monthlySalary);
    }

    @Override
    public double calculatePay() {
        return monthlySalary;
    }
}

class PartTimeEmployee extends EmployeeBase {
    private double hourlyRate;
    private double hoursWorked;

    public PartTimeEmployee(String id, String name, double hourlyRate, double hoursWorked) {
        super(id, name);
        System.out.println("[Constructor] PartTimeEmployee");
        this.hourlyRate = Math.max(0, hourlyRate);
        this.hoursWorked = Math.max(0, hoursWorked);
    }

    @Override
    public double calculatePay() {
        return hourlyRate * hoursWorked;
    }
}