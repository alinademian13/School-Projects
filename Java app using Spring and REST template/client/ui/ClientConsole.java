package lab5.catalog.client.ui;

import lab5.catalog.web.dto.LabProblemDto;
import lab5.catalog.web.dto.LabProblemsDto;
import lab5.catalog.web.dto.StudentDto;
import lab5.catalog.web.dto.StudentsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

public class ClientConsole {

    private Scanner scanner = new Scanner(System.in);

    @Autowired
    private RestTemplate restTemplate;


    public void runConsole() {

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
                default:
                    System.out.println("Not Yet Implemented");
            }
            printMenu();
        }
    }

    private void addStudent() {

        System.out.println("Dati numele studentului: ");
        String name = scanner.next();
        System.out.println("Dati grupa: ");
        int grupa = scanner.nextInt();

        StudentDto studentDto = new StudentDto(name, grupa);
        StudentDto saveStudent = restTemplate.postForObject("http://localhost:8080/api/students", studentDto, StudentDto.class);

        System.out.println("Student " + saveStudent + " was added.");

    }

    private void removeStudent() {

        System.out.println("Dati ID-ul studentului: ");
        Long id = scanner.nextLong();

        restTemplate.delete("http://localhost:8080/api/students/{id}", id, StudentDto.class);

    }

    private void updateStudent() {

        System.out.println("Dati ID-ul studentului: ");
        Long id = scanner.nextLong();

        System.out.println("Dati noul nume al studentului: ");
        String name = scanner.next();
        System.out.println("Dati noua grupa: ");
        int grupa = scanner.nextInt();

        StudentDto updateStudent = new StudentDto(name, grupa);

        restTemplate.put("http://localhost:8080/api/students/{id}", updateStudent, id);

    }

    private void printStudents() {

        StudentsDto result = restTemplate.getForObject(
                "http://localhost:8080/api/students",
                StudentsDto.class);

        System.out.println("All students: " + result);

    }

    private void addLabProblem() {

        System.out.println("Dati disciplina: ");
        String discipline = scanner.next();
        System.out.println("Dati numarul laboratorului: ");
        int labNumber = scanner.nextInt();

        LabProblemDto labProblemDto = new LabProblemDto(discipline, labNumber);
        LabProblemDto saveLabProblem = restTemplate.postForObject("http://localhost:8080/api/labproblems", labProblemDto, LabProblemDto.class);

        System.out.println("Lab problem " + saveLabProblem + " was added.");

    }

    private void removeLabProblem() {

        System.out.println("Dati ID-ul problemei de lab: ");
        Long labId = scanner.nextLong();

        restTemplate.delete("http://localhost:8080/api/labproblems/{labProblemId}", labId, LabProblemDto.class);
    }

    private void updateLabProblem() {

        System.out.println("Dati ID-ul problemei de lab: ");
        Long id = scanner.nextLong();

        System.out.println("Dati disciplina: ");
        String discipline = scanner.next();
        System.out.println("Dati numarul laboratorului: ");
        int labNumber = scanner.nextInt();

        LabProblemDto updateLabProblem = new LabProblemDto(discipline, labNumber);

        restTemplate.put("http://localhost:8080/api/labproblems/{labProblemId}", updateLabProblem, id);
    }

    private void printLabProblems() {

        LabProblemsDto result = restTemplate.getForObject("http://localhost:8080/api/labproblems", LabProblemsDto.class);

        System.out.println("All lab problems: " + result);

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
                        "x - Exit\n" +
                        "☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠☠\n");
    }

}
