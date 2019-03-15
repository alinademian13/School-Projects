package service;

import domain.LabProblem;
import domain.Mark;
import domain.Student;
import domain.validators.ValidatorException;
import model.StudentMark;
import repository.Repository;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MarkService {

    private static Predicate<Mark> verifyIdLabProblem(Long idLabProblem) {
        return m -> m.getIdLabProblem().equals(idLabProblem);
    }

    private static Predicate<Mark> verifyIdStudentAndIdLabProblem(Long idStudent, Long idLabProblem) {
        return m -> m.getIdStudent().equals(idStudent) && m.getIdLabProblem().equals(idLabProblem);
    }

    private Repository<Long, Mark> markRepo;
    private Repository<Long, Student> studentRepo;
    private Repository<Long, LabProblem> labProblemRepo;

    public MarkService(Repository<Long, Mark> markRepo, Repository<Long, Student> studentRepo, Repository<Long, LabProblem> labProblemRepo) {
        this.markRepo = markRepo;
        this.studentRepo = studentRepo;
        this.labProblemRepo = labProblemRepo;
    }

    public void addMark(Mark mark) throws ValidatorException {
        Optional<Student> studentOptional = this.studentRepo.findOne(mark.getIdStudent());

        studentOptional.orElseThrow(() -> new ValidatorException("Student cannot be found."));

        Optional<LabProblem> labProblemOptional = this.labProblemRepo.findOne(mark.getIdLabProblem());

        labProblemOptional.orElseThrow(() -> new ValidatorException("Lab problem cannot be found."));

        List<Mark> existingMarks = this.getAllMarks().stream()
                                                    .filter(verifyIdStudentAndIdLabProblem(mark.getIdStudent(), mark.getIdLabProblem()))
                                                    .collect(Collectors.toList());

        if (existingMarks.size() > 0) {
            throw new ValidatorException("A mark for this student and lab problem already exists.");
        }

        this.markRepo.save(mark);
    }

    public void deleteMark(Long id) {
        this.markRepo.delete(id);
    }

    public Set<Mark> getAllMarks() {
        Set<Mark> marks = new HashSet<>();
        this.markRepo.findAll().forEach(marks::add);

        return marks;
    }

    public void updateMark(Mark mark) throws ValidatorException {
        this.markRepo.update(mark);
    }

//    /**
//     * Se afiseaza primii numberOf studenti la o anumita disciplina, sortati dupa nota.
//     * @param idLabProblem id-ul problemei de laborator
//     * @param numberOf numarul primilor studenti
//     * @return lista sortata dupa nota
//     */
//    public List<StudentMark> filterAndSortStudents(Long idLabProblem, int numberOf) {   // {id, idS, idL, m}
//        return this.getAllMarks()
//                .stream()
//                .filter(verifyIdLabProblem(idLabProblem))
//                .map(m -> {
//                    Optional<Student> studentOptional = this.studentRepo.findOne(m.getIdStudent());
//
//                    studentOptional.orElseThrow(() -> new RuntimeException("Student cannot be found"));
//
//                    StudentMark studentMark = new StudentMark(studentOptional.get().getName(), m.getMark());
//
//                    return studentMark;
//                })
//                .sorted(Comparator.comparing(StudentMark::getMark).reversed())
//                .limit(numberOf)
//                .collect(Collectors.toList());
//    }

    /**
     * Se afiseaza primii numberOf studenti la o anumita disciplina, sortati dupa nota.
     * @param idLabProblem id-ul problemei de laborator
     * @param numberOf numarul primilor studenti
     * @return lista sortata dupa nota
     */
    public Set<Mark> filterAndSortStudents(Long idLabProblem, int numberOf) {   // {id, idS, idL, m}
        return this.getAllMarks()
                .stream()
                .filter(verifyIdLabProblem(idLabProblem))
                .sorted(Comparator.comparing(Mark::getMark).reversed())
                .limit(numberOf)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    /** !!! SELECT idlabproblem, COUNT(idlabproblem) AS num FROM mark GROUP BY idlabproblem ORDER BY num DESC

    /**
     * Sa se afiseze o lista de nr elemente de forma <lab-problem> <count>
     *     sortata descrescator dupa count
     *     count = de cate ori a fost asignata respectiva problema de lab
     * @param nr primele nr probleme (care au fost asignate de cele mai multe ori)
     * @return o lista sortata descrescator dupa count
     */
    public Map<Long, Long> countAssignedLabProblems (int nr) {
        Map<Long, Long> countByIdLabProblem = this.getAllMarks()
                .stream()
                .collect(Collectors.groupingBy(Mark::getIdLabProblem, Collectors.counting()));

        return countByIdLabProblem
                .entrySet()
                .stream()
                .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
                .limit(nr)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public Map<Boolean, List<Mark>> marksBeforeAfter(int a) {
        Map<Boolean, List<Mark>> marks = this.getAllMarks()
                .stream()
                .collect(Collectors.partitioningBy(m -> m.getMark() > a));

        return marks;
    }

    public List<Mark> marksSortedBeforeAfter(int a) {

        Map<Boolean, List<Mark>> marks = this.getAllMarks()
                .stream()
                .collect(Collectors.partitioningBy(m -> m.getMark() > a));

        List<Mark> marksAfter = marks.get(false).stream()
                .sorted(Comparator.comparing(Mark::getMark))
                .collect(Collectors.toList());

        List<Mark> marksBefore = marks.get(true).stream()
                .sorted(Comparator.comparing(Mark::getIdStudent).reversed())
                .collect(Collectors.toList());

        //colectez in 2 liste si apoi cele 2 liste le adaug cu addAll in a 3 a lista

        List<Mark> allMarksSorted = new ArrayList<>();
        allMarksSorted.addAll(marksAfter);
        allMarksSorted.addAll(marksBefore);
        return allMarksSorted;
    }


}
