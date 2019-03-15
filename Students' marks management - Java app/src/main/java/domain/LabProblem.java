package domain;

public class LabProblem extends Entity<Long> {

    private String discipline;
    private int labNumber;

    public LabProblem() {
    }

    public LabProblem(Long id, String discipline, int labNumber) {
        super(id);
        this.discipline = discipline;
        this.labNumber = labNumber;
    }

    public LabProblem(String discipline, int labNumber) {
        this.discipline = discipline;
        this.labNumber = labNumber;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public int getLabNumber() {
        return labNumber;
    }

    public void setLabNumber(int labNumber) {
        this.labNumber = labNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LabProblem that = (LabProblem) o;

        if (labNumber != that.labNumber) return false;
        return discipline != null ? discipline.equals(that.discipline) : that.discipline == null;
    }

    @Override
    public int hashCode() {
        int result = discipline != null ? discipline.hashCode() : 0;
        result = 31 * result + labNumber;
        return result;
    }

    @Override
    public String toString() {
        return "Lab Problem#" + this.getId() + ": " + this.getDiscipline() + ", " + this.getLabNumber();
    }
}
