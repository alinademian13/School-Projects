package domain;

public class Mark extends Entity<Long> {

    private Long idLabProblem;
    private Long idStudent;
    private int mark;

    public Mark() {
    }

    public Mark(Long id, Long idLabProblem, Long idStudent, int mark) {
        super(id);
        this.idLabProblem = idLabProblem;
        this.idStudent =  idStudent;
        this.mark = mark;
    }

    public Long getIdLabProblem() {
        return idLabProblem;
    }

    public void setIdLabProblem(Long idLabProblem) {
        this.idLabProblem = idLabProblem;
    }

    public Long getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(Long idStudent) {
        this.idStudent = idStudent;
    }

    public int getMark() {
        return this.mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Mark mark1 = (Mark) o;

        if (mark != mark1.mark) return false;
        if (!idLabProblem.equals(mark1.idLabProblem)) return false;
        return idStudent.equals(mark1.idStudent);
    }

    @Override
    public int hashCode() {
        int result = idLabProblem.hashCode();
        result = 31 * result + idStudent.hashCode();
        result = 31 * result + mark;
        return result;
    }

    @Override
    public String toString() {
        return "Mark#" + this.getId() + ": Student#" + this.getIdStudent() + ", LabProblem#" + this.getIdLabProblem() + ", " + this.getMark();
    }
}
