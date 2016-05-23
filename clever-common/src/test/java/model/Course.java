package model;

/**
 * 作者：LiZW <br/>
 * 创建时间：2016-4-30 11:33 <br/>
 */
public class Course {
    private String courseName;
    private double score;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseName='" + courseName + '\'' +
                ", score=" + score +
                '}';
    }
}
