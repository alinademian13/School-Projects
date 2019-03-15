package repository;

import domain.LabProblem;
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

public class LabProblemFileRepository extends InMemoryRepository<Long, LabProblem> {

    protected String fileName;

    public LabProblemFileRepository(Validator<LabProblem> validator, String fileName) throws Exception {
        super(validator);

        this.fileName = fileName;

        loadData();
    }

    @Override
    public Optional<LabProblem> save(LabProblem labProblem) throws ValidatorException {
        Optional<LabProblem> optional = super.save(labProblem);

        if (optional.isPresent()) {
            return optional;
        }

        saveToFile();

        return Optional.empty();
    }

    @Override
    public Optional<LabProblem> delete(Long id) {
        Optional<LabProblem> optional = super.findOne(id);

        if (!optional.isPresent()) {
            return Optional.empty();
        }

        super.delete(id);

        saveToFile();

        return optional;
    }

    @Override
    public Optional<LabProblem> update(LabProblem labProblem) throws ValidatorException {
        Optional<LabProblem> optional = super.update(labProblem);

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
            String discipline = items.get(1);
            int labNumber = Integer.parseInt(items.get(2));

            LabProblem labProblem = new LabProblem(id, discipline, labNumber);

            try {
                super.save(labProblem);
            } catch (Exception e) {
                e.printStackTrace();

                throw new RuntimeException(e.getMessage());
            }
        });
    }

    protected void saveToFile() {
        Path filePath = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(filePath)) {
            for (LabProblem labProblem : this.findAll()) {
                bufferedWriter.write(labProblem.getId() + "," + labProblem.getDiscipline() + "," + labProblem.getLabNumber());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        }
    }
}
