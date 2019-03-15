package domain;

public class Student extends Entity<Long>{

    private String name;
    private int group;

    public Student() {
    }

    public Student(Long id, String name, int group) {
        super(id);
        this.name = name;
        this.group = group;
    }

    public Student(String name, int group) {
        this.name = name;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    @Override
    public String toString() {
        return "Student#" + this.getId() + ": " + this.getName() + ", " + this.getGroup();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (group != student.group) return false;
        return name != null ? name.equals(student.name) : student.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + group;
        return result;
    }
}
