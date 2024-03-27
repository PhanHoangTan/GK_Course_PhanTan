import org.example.AppUtil.AppUtils;
import org.example.Dao.DepartmentDao;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DepartmentDaoTest {
    public static String DB_NAME = "student";
    private DepartmentDao departmentDao;
    @BeforeAll
    public void setUp() {
        departmentDao = new DepartmentDao(AppUtils.initDriver(), DB_NAME);
    }

    //4. Cập nhật name = “Mathematics” cho department_id = “Math”
    @Test
    public void testUpdateDepartment1() {
        departmentDao.updateDepartment("Math", "Mathematics");
        System.out.println("Cập nhật thành công");
    }

    // 5. Cập nhật name = “Rock n Roll” cho department_id = “Music”
    @Test
    public void testUpdateDepartment2() {
        departmentDao.updateDepartment("Music", "Rock n Roll");
        System.out.println("Cập nhật thành công");
    }
    //    8. Liệt kê tất cả các khoa
    @Test
    public void testListDepartments() {
        departmentDao.listDepartments().forEach(System.out::println);
    }
//    9. Liệt kê tên của tất cả các trưởng khoa
    @Test
    public void testListHeads() {
        departmentDao.listHeads().forEach(System.out::println);
    }

//    10. Tìm tên của trưởng khoa CS

    @Test
    public void testFindHead() {
        System.out.println(departmentDao.findHead("CS"));
    }
    //11. Liệt kê tất cả các khóa học của CS và IE
    @Test
    public void testListAllCourses() {
        System.out.println(departmentDao.listAllCoursesOfCSAndIE());
    }
//13. Tổng số sinh viên đăng ký học của mỗi khoa
    @Test
    public void testCountStudents() {
        System.out.println(departmentDao.countStudentsByDepartment());
    }
//    14. Tổng số sinh viên đăng ký học của mỗi khoa, kết quả sắp xếp theo mã khoa

    @Test
    public void testCountStudentsSortByDeptID() {
        System.out.println(departmentDao.countStudentsByDepartmentSorted());
    }
//    15. Tổng số sinh viên đăng ký học của mỗi khoa, kết quả sắp xếp theo số sinh viên

    @Test
    public void testCountStudentsSortByCount() {
        System.out.println(departmentDao.countStudentsByDepartmentOrderByDeptID());
    }
//16. Liệt kê danh sách tên của các trưởng khoa mà các khoa này không có sinh viên đăng ký học

    @Test
    public void testListHeadsNoStudents() {
        System.out.println(departmentDao.listHeadsWithNoStudents());
    }
//    17. Danh sách khoa có số sinh viên đăng ký học nhiều nhất

    @Test
    public void testListDeptMostStudents() {
        System.out.println(departmentDao.listDepartmentsWithMostStudents());
    }


    @AfterAll


    public void tearDown() {
        departmentDao.close();
    }


}
