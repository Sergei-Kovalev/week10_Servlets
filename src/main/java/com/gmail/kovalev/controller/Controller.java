package com.gmail.kovalev.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.gmail.kovalev.dto.FacultyDTO;
import com.gmail.kovalev.dto.FacultyInfoDTO;
import com.gmail.kovalev.service.FacultyService;
import com.gmail.kovalev.service.impl.FacultyServiceImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component("controller")
public class Controller {
    private final FacultyService service;
    private final Gson gson;
    private final XmlMapper xmlMapper;

    public Controller() {
        this.service = new FacultyServiceImpl();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        this.xmlMapper = new XmlMapper();
    }

    public String findFacultyById(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        FacultyInfoDTO facultyById = service.findFacultyById(uuid);
        return gson.toJson(facultyById);
    }

    public String findAllFaculties(int page, int pageSize) {
        List<FacultyInfoDTO> allFaculties = service.findAllFaculties(page, pageSize);
        return gson.toJson(allFaculties);
    }

    public String saveFaculty(String facultyDTOJSON) {
        FacultyDTO facultyDTO = gson.fromJson(facultyDTOJSON, FacultyDTO.class);
        String message = service.saveFaculty(facultyDTO);
        return gson.toJson(message);
    }

    public String updateFaculty(String uuidString, String facultyDTOJSON) {
        UUID uuid = UUID.fromString(uuidString);
        FacultyDTO facultyDTO = gson.fromJson(facultyDTOJSON, FacultyDTO.class);
        String message = service.updateFaculty(uuid, facultyDTO);
        return gson.toJson(message);
    }

    public String deleteFacultyByUUID(String uuidString) {
        UUID uuid = UUID.fromString(uuidString);
        String message = service.deleteFacultyByUUID(uuid);
        return gson.toJson(message);
    }

    public String rollbackDeletedFaculty() {
        String message = service.rollbackDeletedFaculty();
        return gson.toJson(message);
    }

    public String saveFacultyFromXML(String facultyDTOXml) {
        FacultyDTO facultyDTO;
        try {
            facultyDTO = xmlMapper.readValue(facultyDTOXml, FacultyDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String message = service.saveFaculty(facultyDTO);
        try {
            return xmlMapper.writeValueAsString(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
