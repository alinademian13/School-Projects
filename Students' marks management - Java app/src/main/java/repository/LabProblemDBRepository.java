package repository;

import domain.LabProblem;
import domain.validators.Validator;
import domain.validators.ValidatorException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LabProblemDBRepository implements Repository<Long, LabProblem> {

    private Validator<LabProblem> labProblemValidator;

    private static final String URL = "jdbc:postgresql://localhost:5432/students-labproblems";
    private static final String USERNAME = System.getProperty("user");
    private static final String PASSWORD = System.getProperty("password");

    public LabProblemDBRepository(Validator<LabProblem> validator) {
        this.labProblemValidator = validator;
    }

    @Override
    public Optional<LabProblem> findOne(Long id) {

        if (id == null) {
            throw new IllegalArgumentException("ID must not be null.");
        }

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM labproblem WHERE id = ?")
        ) {
            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String discipline = resultSet.getString("discipline");
                int labnumber = resultSet.getInt("labnumber");

                LabProblem labProblem = new LabProblem(id, discipline, labnumber);

                return Optional.of(labProblem);
            }
            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();

            return Optional.empty();
        }
    }

    @Override
    public Iterable<LabProblem> findAll() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

             PreparedStatement statement = connection.prepareStatement("SELECT * FROM labproblem");
             ResultSet resultSet = statement.executeQuery()
        ) {
            List<LabProblem> labProblems = new ArrayList<>();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String discipline = resultSet.getString("discipline");
                int labNumber = resultSet.getInt("labnumber");

                LabProblem labProblem = new LabProblem(id, discipline, labNumber);

                labProblems.add(labProblem);

            }
            return labProblems;
        } catch (SQLException e) {
            e.printStackTrace();

            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public Optional<LabProblem> save(LabProblem entity) throws ValidatorException {

        if (entity == null) {
            throw new IllegalArgumentException("Lab problem must not be null.");
        }

        this.labProblemValidator.validate(entity);

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

             PreparedStatement statement = connection.prepareStatement("INSERT INTO labproblem(discipline, labnumber) VALUES (?,?)")
        ) {
            statement.setString(1, entity.getDiscipline());
            statement.setInt(2, entity.getLabNumber());

            statement.executeUpdate();

            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();

            return Optional.of(entity);
        }
    }

    @Override
    public Optional<LabProblem> delete(Long id) {

        Optional<LabProblem> optional = this.findOne(id);

        if (!optional.isPresent()) {
            return Optional.empty();
        }

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM labproblem WHERE id = ?")
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
    public Optional<LabProblem> update(LabProblem entity) throws ValidatorException {

        if (entity == null) {
            throw new IllegalArgumentException("Lab problem must not be null.");
        }

        this.labProblemValidator.validate(entity);

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

             PreparedStatement statement = connection.prepareStatement("UPDATE labproblem SET discipline=?, labnumber=? WHERE id=?")
        ) {
            statement.setString(1, entity.getDiscipline());
            statement.setInt(2, entity.getLabNumber());
            statement.setLong(3, entity.getId());

            statement.executeUpdate();

            return Optional.empty();
        } catch (SQLException e) {
            e.printStackTrace();

            return Optional.of(entity);
        }
    }
}
