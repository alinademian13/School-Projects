package ui;

import domain.LabProblem;
import domain.Mark;
import domain.Student;
import domain.validators.ValidatorException;
import model.StudentMark;
import service.LabProblemService;
import service.MarkService;
import service.StudentService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Console {

    private Scanner scanner;

    private StudentService studentService;
    private LabProblemService labProblemService;
    private MarkService markService;

    public Console(StudentService studentService, LabProblemService labProblemService, MarkService markService) {
        scanner = new Scanner(System.in);

        this.studentService = studentService;
        this.labProblemService = labProblemService;
        this.markService = markService;
    }

    public void runMenu() {
        printMenu();

        while (true) {

            String option = scanner.next();
            if (option.equals("x")) {
                break;
            }
            switch (option) {
                case "1":
                    addStudent();
                    break;
                case "2":
                    removeStudent();
                    break;
                case "3":
                    updateStudent();
                    break;
                case "4":
                    printStudents();
//                    printStudentsWithPaging();
                    break;
                case "5":
                    addLabProblem();
                    break;
                case "6":
                    removeLabProblem();
                    break;
                case "7":
                    updateLabProblem();
                    break;
                case "8":
                    printLabProblems();
                    break;
                case "9":
                    addMark();
                    break;
                case "10":
                    removeMark();
                    break;
                case "11":
                    updateMark();
                    break;
                case "12":
                    printMarks();
                    break;
                case "13":
                    filterAndSort();
                    break;
                case "14":
                    countAssignedLabProblems();
                    break;
                case "15":
                    studentsWithGivenString();
                    break;
                case "16":
                    marksBeforeAfter();
                    break;
                case "17":
                    marksSortedBeforeAfter();
                    break;
                default:
                    System.out.println("not yet implemented");
            }
            printMenu();
        }
    }

    private void printStudentsWithPaging() {

        System.out.println("Enter page size: ");
        int size = scanner.nextInt();
        studentService.setPageSize(size);

        System.out.println("Enter 'n' - for next; 'x' - for exit: ");

        while (true) {
            String option = scanner.next();
            if (option.equals("x")) {
                System.out.println("Exit");
                break;
            }
            if (!option.equals("n")) {
                System.out.println("This option doesn't exist");
                continue;
            }

            Set<Student> students = studentService.getNextStudents();
            if (students.size() == 0) {
                System.out.println("There are no more students.");
                break;
            }
            students.forEach(student -> System.out.println(student));
        }
    }

    private void addStudent() {

        System.out.println("Id: ");
        Long id = scanner.nextLong();

        System.out.println("Name: ");
        String name = scanner.next();

        System.out.println("Group: ");
        int group = scanner.nextInt();

        Student student = new Student(id, name, group);

        try {
            studentService.addStudent(student);
        } catch (ValidatorException exception) {
            System.out.println("ERROR: " + exception.getMessage());
        }
    }

    private void removeStudent() {

        System.out.println("Id: ");
        Long id = scanner.nextLong();

        try {
            studentService.removeStudent(id);
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void updateStudent() {

        System.out.println("Id: ");
        Long id = scanner.nextLong();

        System.out.println("Noul nume: ");
        String name = scanner.next();

        System.out.println("Noua grupa: ");
        Integer group = scanner.nextInt();

        Student newStudent = new Student(id, name, group);

        try {
            studentService.updateStudent(newStudent);
        } catch (ValidatorException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void printStudents() {

        System.out.println("All students: \n");
        Set<Student> students = studentService.getAllStudents();
        students.forEach(System.out::println);
    }

    private void addLabProblem() {

        System.out.println("Id: ");
        Long id = scanner.nextLong();

        System.out.println("Discipline: ");
        String discipline = scanner.next();

        System.out.println("Lab number: ");
        int labnr = scanner.nextInt();

        LabProblem labProblem = new LabProblem(id, discipline, labnr);

        try {
            labProblemService.addLabProblem(labProblem);
        } catch (ValidatorException exception) {
            System.out.println("ERROR: " + exception.getMessage());
        }
    }

    private void removeLabProblem() {

        System.out.println("Id: ");
        Long id = scanner.nextLong();

        try {
            labProblemService.removeLabProblem(id);
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void updateLabProblem() {

        System.out.println("Id: ");
        Long id = scanner.nextLong();

        System.out.println("Noua disciplina: ");
        String discipline = scanner.next();

        System.out.println("Noul lab number");
        int labNumber = scanner.nextInt();

        LabProblem newLabProblem = new LabProblem(id, discipline, labNumber);

        try {
            this.labProblemService.updateLabProblem(newLabProblem);
        } catch (ValidatorException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void printLabProblems() {

        System.out.println("All lab problems: \n");
        Set<LabProblem> labs = labProblemService.getAllLabProblems();
        labs.forEach(System.out::println);
    }

    private void addMark() {

        printStudents();
        System.out.println("______________________________________");
        printLabProblems();
        System.out.println("______________________________________");

        System.out.println("Id-ul notei: ");
        Long id = scanner.nextLong();

        System.out.println("Id-ul problemei: ");
        Long idLabProblem = scanner.nextLong();

        System.out.println("Id-ul studentului: ");
        Long idStudent = scanner.nextLong();

        System.out.println("Nota: ");
        int mark = scanner.nextInt();

        Mark m = new Mark(id, idLabProblem, idStudent, mark);

        try {
            markService.addMark(m);
        } catch (ValidatorException exception) {
            System.out.println("ERROR: " + exception.getMessage());
        }
    }

    private void removeMark() {

        System.out.println("Id-ul notei: ");
        Long id = scanner.nextLong();

        try {
            markService.deleteMark(id);
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void updateMark() {

        printStudents();
        System.out.println("______________________________________");
        printLabProblems();
        System.out.println("______________________________________");

        System.out.println("Id-ul notei:");
        Long id = scanner.nextLong();

        System.out.println("Noul id al problemei:");
        Long idLabProblem = scanner.nextLong();

        System.out.println("Noul id al studentului:");
        Long idStudent = scanner.nextLong();

        System.out.println("Noua nota:");
        int mark = scanner.nextInt();

        Mark newMark = new Mark(id, idLabProblem, idStudent, mark);

        try {
            this.markService.updateMark(newMark);
        } catch (ValidatorException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void printMarks() {
        System.out.println("All marks: \n");
        Set<Mark> marks = markService.getAllMarks();
        marks.forEach(System.out::println);
    }

    private void filterAndSort() {
        printLabProblems();
        System.out.println("______________________________________");

        System.out.println("Id-ul problemei:");
        Long idLabProblem = scanner.nextLong();

        System.out.println("Numarul de studenti:");
        int numberOf = scanner.nextInt();

//        List<StudentMark> studentMarks = this.markService.filterAndSortStudents(idLabProblem, numberOf);

        Set<Mark> marks = this.markService.filterAndSortStudents(idLabProblem, numberOf);

        marks.forEach(System.out::println);
    }

    private void countAssignedLabProblems() {
        System.out.println("Dati nr-ul:");
        int nr = scanner.nextInt();

        Map<Long, Long> assignedLabProblems = this.markService.countAssignedLabProblems(nr);
        assignedLabProblems.forEach((idLabProblem, count) -> System.out.println(idLabProblem + " - " + count));
    }

    private void studentsWithGivenString() {
        System.out.println("Dati string-ul:");
        String s = scanner.next();

        Set<Student> students = this.studentService.studentsWithGivenString(s);
        students.forEach(System.out::println);
    }

    private void marksBeforeAfter() {
        System.out.println("Dati nota:");
        int a = scanner.nextInt();

        Map<Boolean, List<Mark>> markSet = this.markService.marksBeforeAfter(a);
        markSet.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    private void marksSortedBeforeAfter() {
        System.out.println("Dati nota:");
        int a = scanner.nextInt();

        List<Mark> markSet = this.markService.marksSortedBeforeAfter(a);
        markSet.forEach(System.out::println);
    }

    private void printMenu() {
        System.out.println(
                "1 - Add Student\n" +
                "2 - Remove Student\n" +
                "3 - Update Student\n" +
                "4 - Print all Students\n" +
                "♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡\n" +
                "5 - Add Lab Problem\n" +
                "6 - Remove Lab Problem\n" +
                "7 - Update Lab Problem\n" +
                "8 - Print all Lab Problems\n" +
                "♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡\n" +
                "9 - Add Mark\n" +
                "10 - Remove Mark\n" +
                "11 - Update Mark\n" +
                "12 - Print all Marks\n" +
                "♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡♡\n" +
                "13. Filter and Sort\n" +
                "14. Count\n" +
                "_____________________________________________\n" +
                "15. Print students with given string\n" +
                "16. Print marks before and after a given mark\n" +
                "17. Print sorted marks before and after a given mark\n" +
                "x - Exit\n" +
                "☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠\n");
    }
}
