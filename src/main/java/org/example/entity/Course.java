package org.example.entity;

public class Course {
//      "hours": 4,
//              "name": "Programming",
//              "courseID": "CS101"
    private int hours;
    private String name;
    private String courseID;
    private  Department department;


    public Course(int hours, String name, String courseID, Department department) {
        this.hours = hours;
        this.name = name;
        this.courseID = courseID;
        this.department = department;
    }

    public Course(int hours, String name, String courseID) {
        this.hours = hours;
        this.name = name;
        this.courseID = courseID;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    @Override
    public String toString() {
        return "Course{" +
                "hours=" + hours +
                ", name='" + name + '\'' +
                ", courseID='" + courseID + '\'' +
                ", department=" + department +
                '}';
    }

}
