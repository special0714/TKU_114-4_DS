class Instructor {
    private String id;
    private String name;

    public Instructor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Course {
    private String courseCode;
    private String title;
    private Instructor instructor; 

    public Course(String courseCode, String title, Instructor instructor) {
        this.courseCode = courseCode;
        this.title = title;
        this.instructor = instructor;
    }

    public String summary() {
        String instructorName = (this.instructor != null) ? this.instructor.getName() : "未指定";
        return String.format("[%s] %s (授課講師: %s)", courseCode, title, instructorName);
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public Instructor getInstructor() {
        return instructor;
    }
}

public class CourseComposition {
    public static void main(String[] args) {
        Instructor instructor1 = new Instructor("INS01", "張教授");
        Instructor instructor2 = new Instructor("INS02", "陳老師");

        Course course1 = new Course("CS101", "Java 程式設計", instructor1);
        Course course2 = new Course("CS102", "資料結構", instructor1);
        Course course3 = new Course("WEB201", "前端網頁開發", instructor2);

        System.out.println("=== 課程資訊列表 ===");
        System.out.println(course1.summary());
        System.out.println(course2.summary());
        System.out.println(course3.summary());

        System.out.println("\n=== 記憶體引用驗證 ===");
        System.out.println("course1 與 course2 是否共用同一位講師物件: " 
            + (course1.getInstructor() == course2.getInstructor()));
    }
}