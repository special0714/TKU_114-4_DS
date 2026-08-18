class CourseGrade {
    private String studentId;
    private String name;
    private double homeworkScore; 
    private double midtermScore;  
    private double finalExamScore; 
    private double attendanceScore;

    public CourseGrade(String studentId, String name, double homeworkScore, 
                       double midtermScore, double finalExamScore, double attendanceScore) {
        this.studentId = (studentId == null || studentId.isBlank()) ? "Unknown" : studentId.trim();
        this.name = (name == null || name.isBlank()) ? "Unknown" : name.trim();

        this.homeworkScore = clampScore(homeworkScore);
        this.midtermScore = clampScore(midtermScore);
        this.finalExamScore = clampScore(finalExamScore);
        this.attendanceScore = clampScore(attendanceScore);
    }

    private double clampScore(double score) {
        return Math.max(0.0, Math.min(100.0, score));
    }

    public double calculateFinalScore() {
        return (homeworkScore * 0.50) 
             + (midtermScore * 0.20) 
             + (finalExamScore * 0.20) 
             + (attendanceScore * 0.10);
    }

    public String getLevel() {
        double score = calculateFinalScore();
        if (score >= 90.0) return "A";
        if (score >= 80.0) return "B";
        if (score >= 70.0) return "C";
        if (score >= 60.0) return "D";
        return "F";
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("學號: %-6s | 姓名: %-4s | 平時: %5.1f | 期中: %5.1f | 期末: %5.1f | 出席: %5.1f | 總分: %5.1f | 等級: %s",
                studentId, name, homeworkScore, midtermScore, finalExamScore, attendanceScore, calculateFinalScore(), getLevel());
    }
}

public class CourseGradeManager {
    public static void main(String[] args) {
        CourseGrade[] grades = {
            new CourseGrade("S001", "張小明", 85.0, 90.0, 88.0, 100.0),
            new CourseGrade("S002", "李大華", 95.0, 92.0, 96.0, 90.0),
            new CourseGrade("S003", "王小美", 40.0, 50.0, 45.0, 60.0), // 不及格
            new CourseGrade("S004", "陳志明", 60.0, 55.0, 50.0, 70.0), // 不及格
            new CourseGrade("S005", "林雅婷", 75.0, 80.0, 78.0, 85.0)
        };

        System.out.println("=========================================================================================");
        System.out.println("                                 全班學生成績明細                                        ");
        System.out.println("=========================================================================================");
        double totalClassScore = 0.0;
        CourseGrade topStudent = grades[0];

        for (CourseGrade g : grades) {
            System.out.println(g);
            double finalScore = g.calculateFinalScore();
            totalClassScore += finalScore;

            if (finalScore > topStudent.calculateFinalScore()) {
                topStudent = g;
            }
        }
        System.out.println("=========================================================================================\n");

        double averageScore = totalClassScore / grades.length;
        System.out.println("=== 1. 全班平均成績 ===");
        System.out.printf("全班平均總分: %.2f 分%n%n", averageScore);

        System.out.println("=== 2. 最高分學生 ===");
        System.out.printf("最高分學生: %s (%s) | 總分: %.1f 分 (等級 %s)%n%n",
                topStudent.getName(), topStudent.getStudentId(), topStudent.calculateFinalScore(), topStudent.getLevel());

        System.out.println("=== 3. 不及格名單 (總分 < 60) ===");
        boolean hasFailingStudent = false;
        for (CourseGrade g : grades) {
            if (g.calculateFinalScore() < 60.0) {
                System.out.printf("- [%s] %-4s | 總分: %.1f 分 (等級 %s)%n",
                        g.getStudentId(), g.getName(), g.calculateFinalScore(), g.getLevel());
                hasFailingStudent = true;
            }
        }

        if (!hasFailingStudent) {
            System.out.println("全班皆高於 60 分，無不及格學生。");
        }
    }
}