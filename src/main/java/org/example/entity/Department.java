package org.example.entity;

public class Department {
//     "dean": "Rubio",
//    "name": "Computer Science",
//    "deptID": "CS",
//    "building": "Ajax",
//    "room": "100"
    private String dean;
    private String name;
    private String deptID;
    private String building;
    private String room;

    public Department(String dean, String name, String deptID, String building, String room) {
        this.dean = dean;
        this.name = name;
        this.deptID = deptID;
        this.building = building;
        this.room = room;
    }

    public String getDean() {
        return dean;
    }

    public void setDean(String dean) {
        this.dean = dean;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeptID() {
        return deptID;
    }

    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Department{" +
                "dean='" + dean + '\'' +
                ", name='" + name + '\'' +
                ", deptID='" + deptID + '\'' +
                ", building='" + building + '\'' +
                ", room='" + room + '\'' +
                '}';
    }
}
