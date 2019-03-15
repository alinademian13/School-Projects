package repository;

import domain.Student;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class StudentFileRepository extends InMemoryRepository<Long, Student> {

    protected String fileName;

    public StudentFileRepository(Validator<Student> validator, String fileName) throws Exception {
        super(validator);

        this.fileName = fileName;

        loadData();
    }

    @Override
    public Optional<Student> save(Student student) throws ValidatorException {
        Optional<Student> optional = super.save(student);

        if (optional.isPresent()) {
            return optional;
        }

        saveToFile();

        return Optional.empty();
    }

    @Override
    public Optional<Student> delete(Long id) {
        Optional<Student> optional = super.findOne(id);

        if (!optional.isPresent()) {
            return Optional.empty();
        }

        super.delete(id);

        saveToFile();

        return optional;
    }

    @Override
    public Optional<Student> update(Student student) throws ValidatorException {
        Optional<Student> optional = super.update(student);

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
            String name = items.get(1);
            int group = Integer.parseInt(items.get(2));

            Student student = new Student(id, name, group);

            try {
                super.save(student);
            } catch (ValidatorException e) {
                e.printStackTrace();

                throw new RuntimeException(e.getMessage());
            }
        });
    }

    protected void saveToFile() {
        Path filePath = Paths.get(fileName);

        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(filePath)) {
            for (Student s : this.findAll()) {
                bufferedWriter.write(s.getId() + "," + s.getName() + "," + s.getGroup());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        }
    }
}
