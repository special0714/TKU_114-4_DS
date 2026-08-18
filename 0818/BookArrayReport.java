class Book {
    private String id;
    private String title;
    private int price;
    private int stock;

    public Book(String id, String title, int price, int stock) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.stock = stock;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getTotalValue() {
        return price * stock;
    }

    @Override
    public String toString() {
        return String.format("書號: %-6s | 書名: %-14s | 單價: %5d 元 | 庫存: %2d 本", 
                id, title, price, stock);
    }
}

public class BookArrayReport {
    public static void main(String[] args) {
        Book[] books = {
            new Book("B001", "Java 程式設計", 650, 10),
            new Book("B002", "資料結構實作", 580, 2),
            new Book("B003", "演算法圖解", 720, 5),
            new Book("B004", "網頁前端入門", 450, 1),
            new Book("B005", "資料庫系統概論", 800, 3)
        };

        System.out.println("=== 1. 所有書籍列表 ===");
        for (Book book : books) {
            System.out.println(book);
        }
        System.out.println();

        int totalInventoryValue = 0;
        for (Book book : books) {
            totalInventoryValue += book.getTotalValue();
        }
        System.out.println("=== 2. 庫存總價值 ===");
        System.out.printf("所有書籍庫存總價值: %,d 元%n%n", totalInventoryValue);

        Book mostExpensiveBook = books[0];
        for (int i = 1; i < books.length; i++) {
            if (books[i].getPrice() > mostExpensiveBook.getPrice()) {
                mostExpensiveBook = books[i];
            }
        }
        System.out.println("=== 3. 價格最高的書籍 ===");
        System.out.println(mostExpensiveBook);
        System.out.println();

        System.out.println("=== 4. 庫存不足警戒列表 (庫存 <= 3) ===");
        for (Book book : books) {
            if (book.getStock() <= 3) {
                System.out.println(book);
            }
        }
    }
}