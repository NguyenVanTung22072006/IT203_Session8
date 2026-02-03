import java.util.Scanner;

public class kiemtrathuchanh_ss08 {
    static Scanner scanner = new Scanner(System.in);
    static StudentSS08[] students;
    static int n = 0;
    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== QUẢN LÝ ĐIỂM SINH VIÊN =====");
            System.out.println("1. Nhập danh sách sinh viên");
            System.out.println("2. Hiển thị danh sách sinh viên");
            System.out.println("3. Sắp xếp theo học lực giảm dần");
            System.out.println("4. Thoát");
            System.out.print("Chọn chức năng: ");
            choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    inputStudents();
                    break;
                case 2:
                    displayStudents();
                    break;
                case 3:
                    sortByScoreDesc();
                    break;
                case 4:
                    System.out.println("Thoát chương trình!");
                    break;
                default:
                    System.out.println("Chức năng không hợp lệ!");
            }
        } while (choice != 4);
    }

    public static void inputStudents() {
        System.out.print("Nhập số lượng sinh viên: ");
        n = scanner.nextInt();
        scanner.nextLine();
        students = new StudentSS08[n];
        for (int i = 0; i < n; i++) {
            System.out.println("\nNhập sinh viên thứ " + (i + 1));
            String id;
            while (true) {
                System.out.print("Mã SV (SVxxx): ");
                id = scanner.nextLine();
                if (id.matches("SV\\d{3}")) {
                    break;
                }
                System.out.println("Mã sinh viên không hợp lệ!");
            }
            System.out.print("Họ tên: ");
            String name = scanner.nextLine();

            double score;
            do {
                System.out.print("Điểm trung bình (0-10): ");
                score = scanner.nextDouble();
            } while (score < 0 || score > 10);
            scanner.nextLine();

            students[i] = new StudentSS08(id, name, score);
        }
    }

    public static void displayStudents() {
        if (students == null || n == 0) {
            System.out.println("Danh sách sinh viên trống!");
            return;
        }

        for (StudentSS08 student : students) {
            System.out.println(student);
        }
    }

    public static void sortByScoreDesc() {
        if (students == null || n == 0) {
            System.out.println("Chưa có danh sách sinh viên để sắp xếp!");
            return;
        }

        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (students[i].getScore() < students[j].getScore()) {
                    StudentSS08 temp = students[i];
                    students[i] = students[j];
                    students[j] = temp;
                }
            }
        }

        System.out.println("Đã sắp xếp xong theo học lực giảm dần!");
    }
}

class StudentSS08 {
    private String id;
    private String name;
    private double score;

    public StudentSS08() {
    }
    public StudentSS08(String id, String name, double score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getScore() {
        return score;
    }
    public String getRank() {
        if (score >= 8.0) {
            return "Giỏi";
        } else if (score >= 6.5) {
            return "Khá";
        } else {
            return "Trung bình";
        }
    }
    @Override
    public String toString() {
        return "Mã SV: " + id +
                " | Tên: " + name +
                " | Điểm: " + score +
                " | Học lực: " + getRank();
    }
}
