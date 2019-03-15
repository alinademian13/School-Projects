package model;

public class StudentMark {
    private String studentName;

    private Integer mark;

    public StudentMark(String studentName, Integer mark) {
        this.studentName = studentName;
        this.mark = mark;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return this.getStudentName() + ", " + this.getMark();
    }
}
