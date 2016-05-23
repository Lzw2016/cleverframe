package model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-4-30 11:30 <br/>
 */
public class Student implements Serializable {
    private String name;
    private int age;
    private double height;
    private boolean sex;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", locale = "zh", timezone = "GMT+8")
    private Date birthday;
    private Class clzz;
    private List<Course> courseList;
    private List<Teacher> teacherList;

    public Student() {

    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public List<Teacher> getTeacherList() {
        return teacherList;
    }

    public void setTeacherList(List<Teacher> teacherList) {
        this.teacherList = teacherList;
    }

    @SuppressWarnings("SpellCheckingInspection")
    public Class getClzz() {
        return clzz;
    }

    public void setClzz(Class clzz) {
        this.clzz = clzz;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                ", sex=" + sex +
                ", birthday=" + birthday +
                ", clzz=" + (clzz == null ? "null" : "clzz") +
                ", courseList=" + (courseList == null ? "null" : courseList.size()) +
                ", teacherList=" + (teacherList == null ? "null" : teacherList.size()) +
                '}';
    }
}
