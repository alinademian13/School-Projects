import domain.LabProblem;
import domain.Mark;
import domain.Student;
import domain.validators.LabProblemValidator;
import domain.validators.MarkValidator;
import domain.validators.StudentValidator;
import domain.validators.Validator;
import repository.*;
import service.LabProblemService;
import service.MarkService;
import service.StudentService;
import ui.Console;

public class Main {

    public static void main(String[] args) {
        try {

            Validator<Student> studentValidator = new StudentValidator();
            Validator<LabProblem> labProblemValidator = new LabProblemValidator();
            Validator<Mark> markValidator = new MarkValidator();

            // in-memory repositories
//            Repository<Long, Student> studentRepository = new InMemoryRepository<>(studentValidator);
//            Repository<Long, LabProblem> labProblemRepository = new InMemoryRepository<>(labProblemValidator);
//            Repository<Long, Mark> markRepository = new InMemoryRepository<>(markValidator);

            // in-file repositories
//            Repository<Long, Student> studentRepository = new StudentFileRepository(studentValidator, "./data/students.txt");
//            Repository<Long, LabProblem> labProblemRepository = new LabProblemFileRepository(labProblemValidator, "./data/lab_problems.txt");
//            Repository<Long, Mark> markRepository = new MarkFileRepository(markValidator, "./data/marks.txt");

//            // in-xml-file repositories
//            Repository<Long, Student> studentRepository = new StudentXMLFileRepository(studentValidator, "./data/xml/students.xml");
//            Repository<Long, LabProblem> labProblemRepository = new LabProblemXMLFileRepository(labProblemValidator, "./data/xml/labproblems.xml");
//            Repository<Long, Mark> markRepository = new MarkXMLRepository(markValidator, "./data/xml/marks.xml");

//            // database repositories
            Repository<Long, Student> studentRepository = new StudentDBRepository(studentValidator);
            Repository<Long, LabProblem> labProblemRepository = new LabProblemDBRepository(labProblemValidator);
            Repository<Long, Mark> markRepository = new MarkDBRepository(markValidator);

            StudentService studentService = new StudentService(studentRepository);
            LabProblemService labProblemService = new LabProblemService(labProblemRepository);
            MarkService markService = new MarkService(markRepository, studentRepository, labProblemRepository);

            Console ui = new Console(studentService, labProblemService, markService);

            ui.runMenu();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
