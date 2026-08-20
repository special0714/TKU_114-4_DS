public class PayrollPolymorphismSystem {
    public static void main(String[] args) {
        Employee[] employees = new Employee[] {
            new SalariedEmployee("E001", "張小明", 55000),
            new HourlyEmployee("E002", "李美麗", 180, 160),
            new CommissionEmployee("E003", "王大衛", 30000, 500000, 0.05),
            new SalariedEmployee("E004", "陳志強", 62000),
            new HourlyEmployee("E005", "林雅婷", 200, 120),
            new CommissionEmployee("E006", "黃健豪", 28000, 800000, 0.06)
        };

        double totalPayroll = 0;
        Employee highestPaidEmployee = null;
        double maxPay = -1;

        System.out.println("=== 員工薪資明細 ===");
        for (Employee emp : employees) {
            double pay = emp.calculatePay();
            totalPayroll += pay;
            System.out.printf("員工編號: %s | 姓名: %-4s | 應發薪資: $%,10.2f元%n", 
                              emp.getId(), emp.getName(), pay);

            if (pay > maxPay) {
                maxPay = pay;
                highestPaidEmployee = emp;
            }
        }

        System.out.println("\n=== 薪資統計結果 ===");
        System.out.printf("全公司總薪資支出: $%,12.2f元%n", totalPayroll);
        if (highestPaidEmployee != null) {
            System.out.printf("最高薪資員工: %s (%s)，薪資金額: $%,10.2f元%n", 
                              highestPaidEmployee.getName(), 
                              highestPaidEmployee.getId(), 
                              maxPay);
        }
    }
}

abstract class Employee {
    private String id;
    private String name;

    public Employee(String id, String name) {
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

class SalariedEmployee extends Employee {
    private double monthlySalary;

    public SalariedEmployee(String id, String name, double monthlySalary) {
        super(id, name);
        this.monthlySalary = Math.max(0, monthlySalary);
    }

    @Override
    public double calculatePay() {
        return monthlySalary;
    }
}

class HourlyEmployee extends Employee {
    private double hourlyRate;
    private double hoursWorked;

    public HourlyEmployee(String id, String name, double hourlyRate, double hoursWorked) {
        super(id, name);
        this.hourlyRate = Math.max(0, hourlyRate);
        this.hoursWorked = Math.max(0, hoursWorked);
    }

    @Override
    public double calculatePay() {
        return hourlyRate * hoursWorked;
    }
}

class CommissionEmployee extends Employee {
    private double baseSalary;
    private double salesVolume;
    private double commissionRate;

    public CommissionEmployee(String id, String name, double baseSalary, double salesVolume, double commissionRate) {
        super(id, name);
        this.baseSalary = Math.max(0, baseSalary);
        this.salesVolume = Math.max(0, salesVolume);
        this.commissionRate = Math.max(0, commissionRate);
    }

    @Override
    public double calculatePay() {
        return baseSalary + (salesVolume * commissionRate);
    }
}