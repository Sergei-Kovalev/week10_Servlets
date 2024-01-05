package com.gmail.kovalev.dao.impl;

import com.gmail.kovalev.dao.FacultyDAO;
import com.gmail.kovalev.entity.Faculty;
import com.gmail.kovalev.exception.FacultyNotFoundException;
import com.gmail.kovalev.saver.Save;
import com.gmail.kovalev.saver.Storage;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component("facultyDAOImpl")
public class FacultyDAOImpl implements FacultyDAO {

    private final Storage storage;
    private Faculty faculty;

    private final HikariDataSource dataSource;

    @Autowired
    public FacultyDAOImpl(@Qualifier("storage") Storage storage, @Qualifier("dataSource") HikariDataSource dataSource) {
        this.storage = storage;
        this.dataSource = dataSource;
    }

    private final static String FIND_BY_ID = "SELECT * FROM faculties WHERE id = ?";
    private final static String FIND_ALL = """
            SELECT * FROM faculties
            LIMIT ?
            OFFSET ?
            """;
    private final static String SAVE_NEW_FACULTY = """
            INSERT INTO faculties(id, name, teacher, email, actual_visitors, max_visitors, price_per_day)
            VALUES (?, ?, ?, ?, ?, ?, ?);
            """;
    private final static String UPDATE_FACULTY = """
            UPDATE faculties
            SET name = ?, teacher = ?, email = ?, actual_visitors = ?, max_visitors = ?, price_per_day = ?
            WHERE id = ?;
            """;

    private final static String DELETE_BY_ID = "DELETE FROM faculties WHERE id = ?";

    @Override
    public Faculty findFacultyById(UUID uuid) throws FacultyNotFoundException {
        Faculty faculty = new Faculty();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_BY_ID);
            statement.setObject(1, uuid);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                fillFacultyFields(faculty, resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (faculty.getId() == null) {
            throw new FacultyNotFoundException(uuid.toString());
        }
        return faculty;
    }

    @Override
    public List<Faculty> findAllFaculties(int page, int pageSize) {
        List<Faculty> allFaculties = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(FIND_ALL);
            statement.setObject(1, pageSize);
            statement.setObject(2, (page - 1) * pageSize);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Faculty faculty = new Faculty();
                fillFacultyFields(faculty, resultSet);
                allFaculties.add(faculty);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allFaculties;
    }

    @Override
    public String saveFaculty(Faculty faculty) {
        UUID uuid = faculty.getId();
        if (uuid == null) {
            uuid = UUID.randomUUID();
        }
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(SAVE_NEW_FACULTY);
            statement.setObject(1, uuid);
            statement.setString(2, faculty.getName());
            statement.setString(3, faculty.getTeacher());
            statement.setString(4, faculty.getEmail());
            statement.setInt(5, faculty.getActualVisitors());
            statement.setInt(6, faculty.getMaxVisitors());
            statement.setDouble(7, faculty.getPricePerDay());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        faculty.setId(uuid);
        return "The faculty " + faculty.getName() + " has been saved in the database with UUID: " + uuid;
    }

    @Override
    public String updateFaculty(Faculty faculty) {
        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_FACULTY);
            statement.setString(1, faculty.getName());
            statement.setString(2, faculty.getTeacher());
            statement.setString(3, faculty.getEmail());
            statement.setInt(4, faculty.getActualVisitors());
            statement.setInt(5, faculty.getMaxVisitors());
            statement.setDouble(6, faculty.getPricePerDay());
            statement.setObject(7, faculty.getId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "The faculty with UUID: " + faculty.getId().toString() + " has been updated in the database.";
    }

    @Override
    public String deleteFacultyByUUID(UUID uuid) {
        try {
            faculty = findFacultyById(uuid);
        } catch (FacultyNotFoundException e) {
            return e.getMessage();
        }
        storage.setSave(saveCurrentVersion(faculty));

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(DELETE_BY_ID);
            statement.setObject(1, uuid);

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return "The faculty with UUID: " + uuid + " has been deleted.";
    }

    @Override
    public String rollbackDeletedFaculty() {
        loadPreviousVersion(storage.getSave());
        saveFaculty(faculty);
        return "The faculty with UUID: " + faculty.getId() + " has been restored.";
    }

    public void loadPreviousVersion(Save save) {
        faculty = save.getFaculty();
    }

    public Save saveCurrentVersion(Faculty faculty) {
        return new Save(faculty);
    }

    private static void fillFacultyFields(Faculty faculty, ResultSet resultSet) throws SQLException {
        faculty.setId(UUID.fromString(resultSet.getString("id")));
        faculty.setName(resultSet.getString("name"));
        faculty.setTeacher(resultSet.getString("teacher"));
        faculty.setEmail(resultSet.getString("email"));
        faculty.setActualVisitors(resultSet.getInt("actual_visitors"));
        faculty.setMaxVisitors(resultSet.getInt("max_visitors"));
        faculty.setPricePerDay(resultSet.getDouble("price_per_day"));
    }
}
