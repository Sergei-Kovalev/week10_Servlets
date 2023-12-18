package com.gmail.kovalev.mapper;

import com.gmail.kovalev.dto.FacultyDTO;
import com.gmail.kovalev.dto.FacultyInfoDTO;
import com.gmail.kovalev.entity.Faculty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper
public interface FacultyMapper {

    Faculty toFaculty(FacultyDTO facultyDTO);

    @Mapping(target = "freePlaces", source = "faculty")
    FacultyInfoDTO fromEntityToInfoDTO(Faculty faculty);

    Faculty merge(@MappingTarget Faculty faculty, FacultyDTO facultyDTO);

    default Integer facultyToFreePlaces(Faculty faculty) {
        return faculty.getMaxVisitors() - faculty.getActualVisitors();
    }
}
