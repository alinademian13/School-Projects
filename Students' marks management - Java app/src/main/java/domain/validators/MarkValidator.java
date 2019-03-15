package domain.validators;

import domain.Mark;

public class MarkValidator implements Validator<Mark> {

    @Override
    public void validate(Mark mark) throws ValidatorException {
        if (mark.getMark() < 1 || mark.getMark() > 10) {
            throw new ValidatorException("Mark must be between 1 and 10.");
        }
    }
}
