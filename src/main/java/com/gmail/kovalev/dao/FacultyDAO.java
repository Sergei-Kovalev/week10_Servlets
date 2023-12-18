package com.gmail.kovalev.dao;

import com.gmail.kovalev.entity.Faculty;

import java.util.List;
import java.util.UUID;

public interface FacultyDAO {
    Faculty findFacultyById(UUID uuid);

    List<Faculty> findAllFaculties();

    String saveFaculty(Faculty faculty);

    String updateFaculty(Faculty faculty);

    String deleteFacultyByUUID(UUID uuid);

    String rollbackDeletedFaculty();
}
