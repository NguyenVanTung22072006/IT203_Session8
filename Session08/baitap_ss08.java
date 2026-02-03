import java.util.Scanner;

public class baitap_ss08 {
    static Scanner sc = new Scanner(System.in);
    static StudentManagerSS08 manager = new StudentManagerSS08(100);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== QUẢN LÝ SINH VIÊN =====");
            System.out.println("1. Thêm sinh viên mới");
            System.out.println("2. Hiển thị tất cả sinh viên");
            System.out.println("3. Tìm kiếm sinh viên theo mã");
            System.out.println("4. Cập nhật thông tin sinh viên");
            System.out.println("5. Xóa sinh viên");
            System.out.println("6. Sắp xếp theo điểm TB giảm dần");
            System.out.println("7. Thống kê");
            System.out.println("8. Thoát");
            System.out.print("Chọn chức năng: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1 -> addStudent();
                case 2 -> manager.displayAll();
                case 3 -> findStudent();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 6 -> {
                    manager.sortByDTBDesc();
                    System.out.println("Đã sắp xếp xong!");
                }
                case 7 -> manager.statistic();
                case 8 -> System.out.println("Thoát chương trình!");
                default -> System.out.println("Chức năng không hợp lệ!");
            }
        } while (choice != 8);
    }

    static void addStudent() {
        System.out.print("Mã SV: ");
        String ma = sc.nextLine();

        System.out.print("Họ tên: ");
        String ten = sc.nextLine();

        int tuoi;
        do {
            System.out.print("Tuổi (18-30): ");
            tuoi = sc.nextInt();
        } while (tuoi < 18 || tuoi > 30);

        sc.nextLine();
        System.out.print("Giới tính: ");
        String gt = sc.nextLine();

        double toan, ly, hoa;
        do {
            System.out.print("Điểm Toán: ");
            toan = sc.nextDouble();
        } while (toan < 0 || toan > 10);

        do {
            System.out.print("Điểm Lý: ");
            ly = sc.nextDouble();
        } while (ly < 0 || ly > 10);

        do {
            System.out.print("Điểm Hóa: ");
            hoa = sc.nextDouble();
        } while (hoa < 0 || hoa > 10);

        StudentSS08 s = new StudentSS08(ma, ten, tuoi, gt, toan, ly, hoa);
        if (manager.addStudent(s))
            System.out.println("Thêm sinh viên thành công!");
        else
            System.out.println("Mã sinh viên đã tồn tại!");
    }

    static void findStudent() {
        System.out.print("Nhập mã SV cần tìm: ");
        StudentSS08 s = manager.findByMaSV(sc.nextLine());
        System.out.println(s == null ? "Không tìm thấy sinh viên!" : s);
    }

    static void updateStudent() {
        System.out.print("Nhập mã SV cần cập nhật: ");
        String ma = sc.nextLine();
        StudentSS08 s = manager.findByMaSV(ma);
        if (s == null) {
            System.out.println("Không tìm thấy sinh viên!");
            return;
        }

        System.out.print("Tên mới: ");
        s.setHoTen(sc.nextLine());
        System.out.print("Tuổi mới: ");
        s.setTuoi(sc.nextInt());
        System.out.print("Toán mới: ");
        double t = sc.nextDouble();
        System.out.print("Lý mới: ");
        double l = sc.nextDouble();
        System.out.print("Hóa mới: ");
        double h = sc.nextDouble();
        s.setDiem(t, l, h);
        System.out.println("Cập nhật thành công!");
    }
    static void deleteStudent() {
        System.out.print("Nhập mã SV cần xóa: ");
        System.out.println(manager.delete(sc.nextLine()) ? "Đã xóa!" : "Không tìm thấy!");
    }
}
class StudentSS08 {
    private String maSV;
    private String hoTen;
    private int tuoi;
    private String gioiTinh;
    private double diemToan, diemLy, diemHoa;
    private double diemTB;
    private String xepLoai;
    public StudentSS08(String maSV, String hoTen, int tuoi, String gioiTinh,
                       double diemToan, double diemLy, double diemHoa) {
        this.maSV = maSV;
        this.hoTen = hoTen;
        this.tuoi = tuoi;
        this.gioiTinh = gioiTinh;
        this.diemToan = diemToan;
        this.diemLy = diemLy;
        this.diemHoa = diemHoa;
        tinhDiemTB();
        xepLoai();
    }
    private void tinhDiemTB() {
        diemTB = (diemToan + diemLy + diemHoa) / 3;
    }
    private void xepLoai() {
        if (diemTB >= 8 && diemToan >= 6.5 && diemLy >= 6.5 && diemHoa >= 6.5)
            xepLoai = "Giỏi";
        else if (diemTB >= 6.5 && diemToan >= 5 && diemLy >= 5 && diemHoa >= 5)
            xepLoai = "Khá";
        else if (diemTB >= 5 && diemToan >= 3.5 && diemLy >= 3.5 && diemHoa >= 3.5)
            xepLoai = "Trung bình";
        else
            xepLoai = "Yếu";
    }
    public String getMaSV() { return maSV; }
    public double getDiemTB() { return diemTB; }
    public String getXepLoai() { return xepLoai; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public void setTuoi(int tuoi) { this.tuoi = tuoi; }
    public void setDiem(double toan, double ly, double hoa) {
        diemToan = toan;
        diemLy = ly;
        diemHoa = hoa;
        tinhDiemTB();
        xepLoai();
    }
    @Override
    public String toString() {
        return String.format("%-8s %-20s %-4d %-6s %-6.2f %-10s",
                maSV, hoTen, tuoi, gioiTinh, diemTB, xepLoai);
    }
}
class StudentManagerSS08 {
    private StudentSS08[] students;
    private int count;
    public StudentManagerSS08(int size) {
        students = new StudentSS08[size];
        count = 0;
    }
    public boolean addStudent(StudentSS08 s) {
        for (int i = 0; i < count; i++)
            if (students[i].getMaSV().equalsIgnoreCase(s.getMaSV()))
                return false;
        students[count++] = s;
        return true;
    }
    public void displayAll() {
        if (count == 0) {
            System.out.println("Danh sách trống!");
            return;
        }
        System.out.printf("%-8s %-20s %-4s %-6s %-6s %-10s\n",
                "MaSV", "HoTen", "Tuoi", "GT", "DTB", "XepLoai");
        for (int i = 0; i < count; i++)
            System.out.println(students[i]);
    }
    public StudentSS08 findByMaSV(String ma) {
        for (int i = 0; i < count; i++)
            if (students[i].getMaSV().equalsIgnoreCase(ma))
                return students[i];
        return null;
    }
    public boolean delete(String ma) {
        for (int i = 0; i < count; i++) {
            if (students[i].getMaSV().equalsIgnoreCase(ma)) {
                for (int j = i; j < count - 1; j++)
                    students[j] = students[j + 1];
                students[--count] = null;
                return true;
            }
        }
        return false;
    }
    public void sortByDTBDesc() {
        for (int i = 0; i < count - 1; i++)
            for (int j = i + 1; j < count; j++)
                if (students[i].getDiemTB() < students[j].getDiemTB()) {
                    StudentSS08 tmp = students[i];
                    students[i] = students[j];
                    students[j] = tmp;
                }
    }
    public void statistic() {
        int gioi = 0, kha = 0, tb = 0, yeu = 0;
        double sum = 0;
        for (int i = 0; i < count; i++) {
            sum += students[i].getDiemTB();
            switch (students[i].getXepLoai()) {
                case "Giỏi" -> gioi++;
                case "Khá" -> kha++;
                case "Trung bình" -> tb++;
                default -> yeu++;
            }
        }
        System.out.println("Giỏi: " + gioi);
        System.out.println("Khá: " + kha);
        System.out.println("Trung bình: " + tb);
        System.out.println("Yếu: " + yeu);
        System.out.printf("Điểm TB chung: %.2f\n", sum / count);
    }
}
