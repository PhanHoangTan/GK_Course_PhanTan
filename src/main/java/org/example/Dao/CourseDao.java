package org.example.Dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.AppUtil.AppUtils;
import org.example.entity.Course;
import org.neo4j.driver.*;
import org.neo4j.driver.types.Node;

import java.util.ArrayList;
import java.util.List;


public class CourseDao {
    private static final Gson GSON = new GsonBuilder().create();
    private static Driver driver;
    private SessionConfig sessionConfig;


    public CourseDao(Driver driver, String dbName) {
        this.driver = AppUtils.initDriver();
        sessionConfig = SessionConfig.builder().withDatabase(dbName).build();
    }


    public void close() {
        driver.close();
    }
    // 3.Tìm danh sách khóa học thuộc của một khoa nào đó khi biết mã khoa
    //MATCH (course:Course) -[:BELONGS_TO]-> (dept:Department)
    //WHERE toUpper(dept.deptID) = "MATH"
    //RETURN course;

    public List<Course> listCourses(String deptID) {
        String query = "MATCH (course:Course) -[:BELONGS_TO]-> (dept:Department) WHERE toUpper(dept.deptID) = $deptID RETURN course";
        List<Course> courses = new ArrayList<>();

        try (Session session = driver.session(sessionConfig)) {
            session.readTransaction(tx -> {
                tx.run(query, Values.parameters("deptID", deptID)).stream().forEach(record -> {
                    Node node = record.get("course").asNode();
                    courses.add(AppUtils.convert(node, Course.class));
                });
                return courses;
            });
        }
        return courses;
    }
//    6. Thêm khóa học vào khoa IE: IE202, Simulation, 3 hours.

    public void addCourse(String courseID, String name, int credit) {
        String query = "CREATE (course:Course {courseID: $courseID, name: $name, credit: $credit})";
        try (Session session = driver.session(sessionConfig)) {
            session.writeTransaction(tx -> {
                tx.run(query, Values.parameters("courseID", courseID, "name", name, "credit", credit));
                return name;
            });
        }
    }

//7. Xóa toàn bộ các khóa học
    public void deleteAllCourses() {
        String query = "MATCH (course:Course) DETACH DELETE course";
        try (Session session = driver.session(sessionConfig)) {
            session.writeTransaction(tx -> {
                tx.run(query);
                return null;
            });
        }
    }











}

