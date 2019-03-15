package service;

import domain.Student;
import domain.validators.ValidatorException;
import repository.Repository;
import repository.paging.PagingRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class StudentService {

    private Repository<Long, Student> studentRepo;

    private int page = 0;
    private int size = 1;

//    private Repository<Long, Student> studentRepo;

    public StudentService(Repository<Long, Student> studentRepo) {
        this.studentRepo = studentRepo;
    }

    public void addStudent(Student student) throws ValidatorException {
        this.studentRepo.save(student);
    }

    public void removeStudent(Long id) {
        this.studentRepo.delete(id);
    }

    public Set<Student> getAllStudents() {
        Set<Student> students = new HashSet<>();
        this.studentRepo.findAll().forEach(students::add);

        return students;
    }

    public void updateStudent(Student student) throws ValidatorException {
        this.studentRepo.update(student);
    }

    public Set<Student> studentsWithGivenString(String s) {
        return this.getAllStudents()
                .stream()
                .filter(student -> student.getName().toLowerCase().contains(s))
                .collect(Collectors.toCollection(HashSet::new));
    }




    public void setPageSize(int size) {
        this.size = size;
    }

    public Set<Student> getNextStudents() {
        //TODO: use repository.findAll(Pageable)
        throw new RuntimeException("not yet implemented");
    }
}
