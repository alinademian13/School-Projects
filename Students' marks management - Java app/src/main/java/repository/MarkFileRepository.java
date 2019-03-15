package repository;

import domain.Mark;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MarkFileRepository extends InMemoryRepository<Long, Mark> {

    protected String fileName;

    public MarkFileRepository(Validator<Mark> validator, String fileName) throws Exception {
        super(validator);

        this.fileName = fileName;

        loadData();
    }

    @Override
    public Optional<Mark> save(Mark mark) throws ValidatorException {
        Optional<Mark> optional = super.save(mark);

        if (optional.isPresent()) {
            return optional;
        }

        saveToFile();

        return Optional.empty();
    }

    @Override
    public Optional<Mark> delete(Long id) {
        Optional<Mark> optional = super.findOne(id);

        if (!optional.isPresent()) {
            return Optional.empty();
        }

        super.delete(id);

        saveToFile();

        return optional;
    }

    @Override
    public Optional<Mark> update(Mark mark) throws ValidatorException {
        Optional<Mark> optional = super.update(mark);

        if (optional.isPresent()) {
            return optional;
        }

        saveToFile();

        return Optional.empty();
    }

    protected void loadData() throws Exception {
        Path filePath = Paths.get(this.fileName);

        Files.lines(filePath).forEach(line -> {
            List<String> items = Arrays.asList(line.split(","));

            Long id = Long.parseLong(items.get(0));
            Long idLabProblem = Long.parseLong(items.get(1));
            Long idStudent = Long.parseLong(items.get(2));
            int mark = Integer.parseInt(items.get(3));

            Mark m = new Mark(id, idLabProblem, idStudent, mark);

            try {
                super.save(m);
            } catch (ValidatorException e) {
                e.printStackTrace();

                throw new RuntimeException(e.getMessage());
            }
        });
    }

    protected void saveToFile() {
        Path filePath = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(filePath)) {
            for (Mark mark : this.findAll()) {
                bufferedWriter.write(mark.getId() + "," + mark.getIdLabProblem() + "," + mark.getIdStudent() + "," + mark.getMark());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        }
    }
}
