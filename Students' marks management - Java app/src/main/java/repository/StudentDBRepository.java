package repository;

import domain.Student;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDBRepository implements Repository<Long, Student> {

    private Validator<Student> studentValidator;

    private static final String URL = "jdbc:postgresql://localhost:5432/students-labproblems";
    private static final String USERNAME = System.getProperty("user");
    private static final String PASSWORD = System.getProperty("password");

    public StudentDBRepository(Validator<Student> validator) {
        this.studentValidator = validator;
    }

    @Override
    public Optional<Student> findOne(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

             PreparedStatement statement = connection.prepareStatement("SELECT * FROM student WHERE id = ?")
        ) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String name = resultSet.getString("name");
                int group = resultSet.getInt("groupnumber");

                Student student = new Student(id, name, group);

                return Optional.of(student);
            }

            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();

            return Optional.empty();
        }
    }

    @Override
    public Iterable<Student> findAll() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

             PreparedStatement statement = connection.prepareStatement("SELECT * FROM student");
             ResultSet resultSet = statement.executeQuery()
        ) {
            List<Student> students = new ArrayList<>();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                int group = resultSet.getInt("groupnumber");

                Student student = new Student(id, name, group);

                students.add(student);
            }

            return students;
        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<Student> save(Student entity) throws ValidatorException {

        if (entity == null) {
            throw new IllegalArgumentException("Student must not be null.");
        }

        this.studentValidator.validate(entity);

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

             PreparedStatement statement = connection.prepareStatement("INSERT INTO student(name, groupnumber) VALUES (?, ?)")
        ) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getGroup());

            statement.executeUpdate();

            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();

            return Optional.of(entity);
        }
    }

    @Override
    public Optional<Student> delete(Long id) {
        Optional<Student> optional = this.findOne(id);

        if (!optional.isPresent()) {
            return Optional.empty();
        }

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

             PreparedStatement statement = connection.prepareStatement("DELETE FROM student WHERE id = ?")
        ) {
            statement.setLong(1, id);

            statement.executeUpdate();

            return optional;
        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<Student> update(Student entity) throws ValidatorException {

        if (entity == null) {
            throw new IllegalArgumentException("Student must not be null.");
        }

        this.studentValidator.validate(entity);

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

             PreparedStatement statement = connection.prepareStatement("UPDATE student SET name=?, groupnumber=? WHERE id=?")
        ) {
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getGroup());
            statement.setLong(3, entity.getId());

            statement.executeUpdate();

            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();

            return Optional.of(entity);
        }
    }
}
