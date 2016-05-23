package model;

import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-4-30 11:32 <br/>
 */
public class Class {
    private String className;
    private int grade;
    private List<Student> studentList;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    @Override
    public String toString() {
        return "Class{" +
                "className='" + className + '\'' +
                ", grade=" + grade +
                ", studentList=" + (studentList == null ? "null" : studentList.size()) +
                '}';
    }
}
