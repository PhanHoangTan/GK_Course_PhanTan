import org.example.AppUtil.AppUtils;
import org.example.Dao.CourseDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourseDaoTest {
    public static String DB_NAME = "student";
    private CourseDao courseDao;
    @BeforeAll
    public void setUp() {
        courseDao = new CourseDao(AppUtils.initDriver(), DB_NAME);
    }

    // 3.Tìm danh sách khóa học thuộc của một khoa nào đó khi biết mã khoa
    @Test
    public void testListCourses() {
        courseDao.listCourses("MATH").forEach(System.out::println);
    }
//    6. Thêm khóa học vào khoa IE: IE202, Simulation, 3 hours.
    @Test
    public void testAddCourse() {
        courseDao.addCourse("CS", "Simulation", 3);
        System.out.println("Thêm thành công");
    }
//    7. Xóa toàn bộ các khóa học
    @Test
    public void testDeleteAllCourses() {
        courseDao.deleteAllCourses();
        System.out.println("Xóa thành công");
    }







    @AfterAll

    public void tearDown() {
        courseDao.close();
    }






}
