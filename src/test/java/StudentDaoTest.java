import org.example.AppUtil.AppUtils;
import org.example.Dao.StudentDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentDaoTest {
    public static String DB_NAME = "student";
    private StudentDao studentDao;
    @BeforeAll
    public void setUp() {
        studentDao = new StudentDao(AppUtils.initDriver(), DB_NAME);
    }

    // 1. Liệt kê tất cả sinh viên
    @Test
    public void testListStudents() {
        studentDao.listStudents().forEach(System.out::println);
    }
    //  2.   Tìm kiếm sinh viên khi biết mã số
    @Test
    public void testFindStudentById() {
        studentDao.findStudentById("11");
    }
//    12. Liệt kê danh sách các tên của các sinh viên đăng ký học khóa học CS101

    @Test
    public void testListStudentsByCourse() {
        studentDao.listStudentsByCourse("CS101").forEach(System.out::println);
    }
//18. Danh sách sinh viên có gpa >= 3.2, kết quả sắp xếp giảm dần theo gpa

    @Test
    public void testListStudentsGpaGreaterEqual3_2() {
        studentDao.listStudentsGpaGreaterEqual3_2().forEach(System.out::println);
    }

    @AfterAll

    public void tearDown() {
        studentDao.close();
    }
}
