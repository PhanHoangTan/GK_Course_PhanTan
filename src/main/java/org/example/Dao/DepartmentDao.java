package org.example.Dao;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.AppUtil.AppUtils;
import org.example.entity.Department;
import org.neo4j.driver.*;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.Node;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class DepartmentDao {
    private static final Gson GSON = new GsonBuilder().create();
    private static Driver driver;
    private SessionConfig sessionConfig;


    public DepartmentDao(Driver driver, String dbName) {
        this.driver = AppUtils.initDriver();
        sessionConfig = SessionConfig.builder().withDatabase(dbName).build();
    }


    public void close() {
        driver.close();
    }
    //4.Cập nhật name = “Mathematics” cho department_id = “Math”
//5. Cập nhật name = “Rock n Roll” cho department_id = “Music”
       public void updateDepartment(String deptID, String name) {
            String query = "MATCH (dept:Department) WHERE toUpper(dept.deptID) = $deptID SET dept.name = $name";
            try (Session session = driver.session(sessionConfig)) {
                session.writeTransaction(tx -> {
                    tx.run(query, Values.parameters("deptID", deptID, "name", name));
                    return name;
                });
            }
        }


//    8. Liệt kê tất cả các khoa
    public List<Department> listDepartments() {
        String query = "MATCH (dept:Department) RETURN dept";
        List<Department> departments = new ArrayList<>();

        try (Session session = driver.session(sessionConfig)) {
            session.readTransaction(tx -> {
                tx.run(query).stream().forEach(record -> {
                    Node node = record.get("dept").asNode();
                    departments.add(AppUtils.convert(node, Department.class));
                });
                return departments;
            });
        }
        return departments;
    }
//9. Liệt kê tên của tất cả các trưởng khoa

    public List<String> listHeads() {
        String query = "MATCH (dept:Department) RETURN dept.dean";
        List<String> heads = new ArrayList<>();

        try (Session session = driver.session(sessionConfig)) {
            session.readTransaction(tx -> {
                tx.run(query).stream().forEach(record -> {
                    heads.add(record.get("dept.dean").asString());
                });
                return heads;
            });
        }
        return heads;
    }
//10. Tìm tên của trưởng khoa CS

    public String findHead(String deptID) {
        String query = "MATCH (dept:Department) WHERE toUpper(dept.deptID) = $deptID RETURN dept.dean";
        String head = null;
        try (Session session = driver.session(sessionConfig)) {
            head = session.readTransaction(tx -> {
                Result result = tx.run(query, Values.parameters("deptID", deptID));
                if (result.hasNext()) {
                    Record record = result.next();
                    return record.get("dept.dean").asString();
                }
                return null;
            });
        }
        return head;
    }
//11. Liệt kê tất cả các khóa học của CS và IE

    public List<String> listAllCoursesOfCSAndIE() {
        String query = "MATCH (c:Course)\n" +
                "WHERE c.deptID IN ['CS', 'IE']\n" +
                "RETURN c.courseID";
        try (Session session = driver.session(sessionConfig)) {
            return session.readTransaction(tx -> {
                Result result = tx.run(query);
                return result.stream()
                        .map(record -> record.get("c.courseID").asString())
                        .toList();
            });
        }
    }
//13. Tổng số sinh viên đăng ký học của mỗi khoa

    public Map<String, Integer> countStudentsByDepartment() {
            String query = "MATCH (d:Department)<-[:BELONGS_TO]-(:Course)<-[:ENROLLED]-(:Student) \n" +
                    "RETURN d.deptID AS deptID, COUNT(*) AS total";
            try (Session session = driver.session(sessionConfig)) {
                return session.readTransaction(tx -> {
                    Result result = tx.run(query);
                    return result.stream()
                            .collect(Collectors.toMap(
                                    record -> record.get("deptID").asString(),
                                    record -> record.get("total").asInt()
                            ));
                });
            }
        }
//14. Tổng số sinh viên đăng ký học của mỗi khoa, kết quả sắp xếp theo mã khoa

    public Map<String, Integer> countStudentsByDepartmentSorted() {
        String query = "MATCH (d:Department)<-[:BELONGS_TO]-(:Course)<-[:ENROLLED]-(:Student) \n" +
                "RETURN d.deptID AS deptID, COUNT(*) AS total\n" +
                "ORDER BY deptID";
        try (Session session = driver.session(sessionConfig)) {
            return session.readTransaction(tx -> {
                Result result = tx.run(query);
                return result.stream()
                        .collect(Collectors.toMap(
                                record -> record.get("deptID").asString(),
                                record -> record.get("total").asInt()
                        ));
            });
        }
    }
//    15. Tổng số sinh viên đăng ký học của mỗi khoa, kết quả sắp xếp theo số sinh viên

        public Map<String, Integer> countStudentsByDepartmentOrderByDeptID() {
            String query = "MATCH (d:Department)<-[:BELONGS_TO]-(:Course)<-[:ENROLLED]-(:Student) \n" +
                    "RETURN d.deptID AS deptID, COUNT(*) AS total\n" +
                    "ORDER BY total";
            try (Session session = driver.session(sessionConfig)) {
                return session.readTransaction(tx -> {
                    Result result = tx.run(query);
                    return result.stream()
                            .collect(Collectors.toMap(
                                    record -> record.get("deptID").asString(),
                                    record -> record.get("total").asInt()
                            ));
                });
            }
        }
//16. Liệt kê danh sách tên của các trưởng khoa mà các khoa này không có sinh viên đăng ký học

    public List<String> listHeadsWithNoStudents() {
        String query = "MATCH (dept:Department) WHERE NOT (dept)<-[:BELONGS_TO]-(:Course)<-[:ENROLLED]-(:Student) RETURN dept.dean";
        try (Session session = driver.session(sessionConfig)) {
            return session.readTransaction(tx -> {
                Result result = tx.run(query);
                return result.stream()
                        .map(record -> record.get("dept.dean").asString())
                        .toList();
            });
        }
    }
//17. Danh sách khoa có số sinh viên đăng ký học nhiều nhất

        public List<String> listDepartmentsWithMostStudents() {
            String query = "MATCH (d:Department)<-[:BELONGS_TO]-(:Course)<-[:ENROLLED]-(:Student) \n" +
                    "WITH d, COUNT(*) AS total\n" +
                    "RETURN d.deptID AS deptID, total\n" +
                    "ORDER BY total DESC\n" +
                    "LIMIT 1";
            try (Session session = driver.session(sessionConfig)) {
                return session.readTransaction(tx -> {
                    Result result = tx.run(query);
                    return result.stream()
                            .map(record -> record.get("deptID").asString())
                            .toList();
                });
            }
        }


}

