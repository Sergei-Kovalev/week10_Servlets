package com.gmail.kovalev.service;

import com.gmail.kovalev.dto.FacultyDTO;
import com.gmail.kovalev.dto.FacultyInfoDTO;

import java.util.List;
import java.util.UUID;

public interface FacultyService {
    FacultyInfoDTO findFacultyById(UUID uuid);

    List<FacultyInfoDTO> findAllFaculties();

    String saveFaculty(FacultyDTO facultyDTO);

    String updateFaculty(UUID uuid, FacultyDTO facultyDTO);

    String deleteFacultyByUUID(UUID uuid);

    String rollbackDeletedFaculty();
}
