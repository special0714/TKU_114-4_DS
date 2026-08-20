public class TransportFareSystem {
    public static void main(String[] args) {
        Transport[] transports = new Transport[] {
            new Bus("101路公車"),
            new Bus("快捷2號"),
            new Taxi("台灣大車隊-A"),
            new Taxi("優步計程車-B")
        };

        int distance = 15;

        for (Transport t : transports) {
            System.out.println(t.getRouteName() + " (里程 " + distance + " km) 票價: " + t.calculateFare(distance) + " 元");
        }
    }
}

abstract class Transport {
    private String routeName;

    public Transport(String routeName) {
        this.routeName = routeName;
    }

    public String getRouteName() {
        return routeName;
    }

    public abstract int calculateFare(int distance);
}

class Bus extends Transport {
    public Bus(String routeName) {
        super(routeName);
    }

    @Override
    public int calculateFare(int distance) {
        return 15 + Math.max(0, distance - 8) * 2;
    }
}

class Taxi extends Transport {
    public Taxi(String routeName) {
        super(routeName);
    }

    @Override
    public int calculateFare(int distance) {
        return 85 + Math.max(0, distance - 1) * 25;
    }
}