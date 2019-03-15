package domain.validators;

import domain.Student;

public class StudentValidator implements Validator<Student> {
    @Override
    public void validate(Student student) throws ValidatorException {
        if (student.getName().isEmpty()) {
            throw new ValidatorException("Student's name cannot be empty.");
        }

        if (student.getName().length() < 3) {
            throw new ValidatorException("Student's name cannot have less than 3 characters.");
        }

        if (student.getGroup() <= 0 || student.getGroup() >= 1000) {
            throw new ValidatorException("Student's group must be between 1 and 999");
        }
    }
}
