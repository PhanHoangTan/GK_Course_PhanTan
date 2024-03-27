package org.example.Dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.AppUtil.AppUtils;
import org.example.entity.Course;
import org.example.entity.Department;
import org.example.entity.Student;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StudentDao {
    private static final Gson GSON = new GsonBuilder().create();
    private static Driver driver;
    private SessionConfig sessionConfig;


    public StudentDao(Driver driver, String dbName) {
        this.driver = AppUtils.initDriver();
        sessionConfig = SessionConfig.builder().withDatabase(dbName).build();
    }


    public void close() {
        driver.close();
    }

    //1.Liệt kê danh sách n sinh viên

    public List<Student> listStudents() {
        String query = "MATCH (a:Student) RETURN a";
        List<Student> students = new ArrayList<>();

        try (Session session = driver.session(sessionConfig)) {
            session.readTransaction(tx -> {
                tx.run(query).stream().forEach(record -> {
                    students.add(new Student(record.get("a").get("studentID").asString(),
                            record.get("a").get("name").asString(),
                            record.get("a").get("gpa").asDouble()));
                });
                return students;
            });
        }
        return students;
    }



//  2.   Tìm kiếm sinh viên khi biết mã số
    public void findStudentById(String studentID) {
        String query = "MATCH (a:Student) WHERE toUpper(a.studentID) = $studentID RETURN a";
        try (Session session = driver.session(sessionConfig)) {
            session.readTransaction(tx -> {
                tx.run(query, Values.parameters("studentID", studentID)).stream().forEach(record -> {
                    Node node = record.get("a").asNode();
                    System.out.println(AppUtils.convert(node, Student.class));
                });
                return studentID;
            });
        }
    }
//12. Liệt kê danh sách các tên của các sinh viên đăng ký học khóa học CS101

    public List<String> listStudentsByCourse(String courseID) {
        String query = "MATCH (a:Student) -[:ENROLLED]-> (course:Course) WHERE toUpper(course.courseID) = $courseID RETURN a.name";
        List<String> students = new ArrayList<>();

        try (Session session = driver.session(sessionConfig)) {
            session.readTransaction(tx -> {
                tx.run(query, Values.parameters("courseID", courseID)).stream().forEach(record -> {
                    students.add(record.get("a.name").asString());
                });
                return students;
            });
        }
        return students;
    }
//18. Danh sách sinh viên có gpa >= 3.2, kết quả sắp xếp giảm dần theo gpa

        public List<Student> listStudentsGpaGreaterEqual3_2() {
            String query = "MATCH (a:Student) WHERE a.gpa >= 3.2 RETURN a ORDER BY a.gpa DESC";
            List<Student> students = new ArrayList<>();

            try (Session session = driver.session(sessionConfig)) {
                session.readTransaction(tx -> {
                    tx.run(query).stream().forEach(record -> {
                        students.add(new Student(record.get("a").get("studentID").asString(),
                                record.get("a").get("name").asString(),
                                record.get("a").get("gpa").asDouble()));
                    });
                    return students;
                });
            }
            return students;
        }













}

