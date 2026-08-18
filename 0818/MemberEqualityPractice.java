import java.util.Objects;

class LibraryMember {
    private String memberId;
    private String name;
    private String email;

    public LibraryMember(String memberId, String name, String email) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("會員 ID: %s | 姓名: %s | Email: %s", memberId, name, email);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        LibraryMember other = (LibraryMember) obj;
        return Objects.equals(this.memberId, other.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId);
    }
}

public class MemberEqualityPractice {
    public static void main(String[] args) {
        LibraryMember member1 = new LibraryMember("M001", "張小明", "old_email@gmail.com");
        LibraryMember member2 = new LibraryMember("M001", "張小明", "new_email@gmail.com");

        System.out.println("=== 1. 物件內容輸出 ===");
        System.out.println("member1: " + member1);
        System.out.println("member2: " + member2);
        System.out.println();

        System.out.println("=== 2. 比較測試 ===");
        System.out.println("member1 == member2: " + (member1 == member2));
        System.out.println("member1.equals(member2): " + member1.equals(member2));
        System.out.println();

        System.out.println("=== 3. 邊界條件測試 ===");
        boolean nullComparisonResult = member1.equals(null);
        System.out.println("member1.equals(null): " + nullComparisonResult);
    }
}