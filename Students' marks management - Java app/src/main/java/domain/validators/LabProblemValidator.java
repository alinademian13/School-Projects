package domain.validators;

import domain.LabProblem;

public class LabProblemValidator implements Validator<LabProblem> {
    @Override
    public void validate(LabProblem labProblem) throws ValidatorException {
        if (labProblem.getDiscipline().isEmpty()) {
            throw new ValidatorException("Discipline cannot be empty");
        }

        if (labProblem.getDiscipline().length() < 3) {
            throw new ValidatorException("Discipline must contain at least 3 characters.");
        }

        if (labProblem.getLabNumber() <= 0 || labProblem.getLabNumber() > 10) {
            throw new ValidatorException("Lab number must be a positive integer between 0 and 10.");
        }
    }
}
