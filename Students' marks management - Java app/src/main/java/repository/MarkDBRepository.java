package repository;

import domain.Mark;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MarkDBRepository implements Repository<Long, Mark> {

    private Validator<Mark> markValidator;

    private static final String URL = "jdbc:postgresql://localhost:5432/students-labproblems";
    private static final String USERNAME = System.getProperty("user");
    private static final String PASSWORD = System.getProperty("password");

    public MarkDBRepository(Validator<Mark> validator) {
        this.markValidator = validator;
    }

    @Override
    public Optional<Mark> findOne(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

             PreparedStatement statement = connection.prepareStatement("SELECT * FROM mark WHERE id = ?")
        ) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long idLabProblem = resultSet.getLong("idlabproblem");
                Long idStudent = resultSet.getLong("idstudent");
                int nota = resultSet.getInt("mark");

                Mark mark = new Mark(id, idLabProblem, idStudent, nota);

                return Optional.of(mark);
            }

            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();

            return Optional.empty();
        }
    }

    @Override
    public Iterable<Mark> findAll() {

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

             PreparedStatement statement = connection.prepareStatement("SELECT * FROM mark");
             ResultSet resultSet = statement.executeQuery();
        ) {
            List<Mark> marks = new ArrayList<>();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long idLabProblem = resultSet.getLong("idlabproblem");
                Long idStudent = resultSet.getLong("idstudent");
                int nota = resultSet.getInt("mark");

                Mark mark = new Mark(id, idLabProblem, idStudent, nota);

                marks.add(mark);
            }
            return marks;

        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<Mark> save(Mark mark) throws ValidatorException {

        if (mark == null) {
            throw new IllegalArgumentException("Mark must not be null.");
        }

        this.markValidator.validate(mark);

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("INSERT INTO mark(idlabproblem, idstudent, mark) VALUES(?, ?, ?)")
        ) {
            statement.setLong(1, mark.getIdLabProblem());
            statement.setLong(2, mark.getIdStudent());
            statement.setInt(3, mark.getMark());

            statement.executeUpdate();

            return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();

            return Optional.of(mark);
        }
    }

    @Override
    public Optional<Mark> delete(Long id) {

        Optional<Mark> optional = this.findOne(id);

        if (!optional.isPresent()) {
            return Optional.empty();
        }

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("DELETE FROM mark WHERE id = ?")
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
    public Optional<Mark> update(Mark mark) throws ValidatorException {

        if (mark == null) {
            throw new IllegalArgumentException("Mark must not be null.");
        }

        this.markValidator.validate(mark);

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE mark SET idlabproblem = ?, idstudent = ?, mark = ? WHERE id = ?")
        ) {
            statement.setLong(1, mark.getIdLabProblem());
            statement.setLong(2, mark.getIdStudent());
            statement.setInt(3, mark.getMark());
            statement.setLong(4, mark.getId());

            statement.executeUpdate();

            return Optional.empty();

        } catch (SQLException e) {
            e.printStackTrace();

            return Optional.of(mark);
        }
    }
}
