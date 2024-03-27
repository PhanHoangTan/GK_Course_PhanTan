package org.example.entity;

public class Enrollment {
    private  Course course;
    private  Student student;

    public Enrollment(Course course, Student student) {
        this.course = course;
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "course=" + course +
                ", student=" + student +
                '}';
    }
}
