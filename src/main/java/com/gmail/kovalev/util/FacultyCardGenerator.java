package com.gmail.kovalev.util;

import com.gmail.kovalev.dto.FacultyInfoDTO;
import com.gmail.kovalev.exception.SaverNotFoundException;
import com.gmail.kovalev.util.strategy.Context;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component("facultyCardGenerator")
public class FacultyCardGenerator {
    private final static String FACULTY_CARD_TEMPLATE = """
            ---------------------------------------------------------------------
                               Карточка факультатива
                               
             id:                        | %s
             Название факультатива:     | %s
             Преподаватель:             | %s
             Эл. почта для связи:       | %s
             Количество свободных мест: | %s
             Стоимость 1-го занятия:    | %s
             ---------------------------------------------------------------------
            """;
    private static final String CURRENCY = " BYN";
    private static final String DIRECTORY_NAME = "faculty_cards";

    @Value("${application.saving_strategy}")
    private String strategy;

    private String generateFacultyCard(FacultyInfoDTO facultyInfoDTO) {
        if (facultyInfoDTO == null) {
            return "There is no such faculty in the database.";
        }
        return String.format(FACULTY_CARD_TEMPLATE,
                facultyInfoDTO.id(), facultyInfoDTO.name(), facultyInfoDTO.teacher(), facultyInfoDTO.email(),
                facultyInfoDTO.freePlaces(), facultyInfoDTO.pricePerDay() + CURRENCY);
    }

    public void facultyCardOutputInFile(FacultyInfoDTO facultyInfoDTO) {
        LocalDateTime timeCreated = LocalDateTime.now();
        String fileName = (
                facultyInfoDTO.name() + " card actualize " + timeCreated.getYear() + " "
                        + timeCreated.getMonth() + " " + timeCreated.getDayOfMonth() + " " +
                        timeCreated.getHour() + "h " + timeCreated.getMinute() + "m").replace(" ", "_");
        File filePath = new File(DIRECTORY_NAME);
        filePath.mkdir();

        deleteOldFileVersion(facultyInfoDTO, filePath);
        String[] strings = generateFacultyCard(facultyInfoDTO).split("\n");

        Context context = new Context();

        if (strategy.equalsIgnoreCase("pdf")) {
            context.setSaver(new SaveToPDF());
            context.executeSaving(filePath, fileName, strings);
        } else if (strategy.equalsIgnoreCase("txt")) {
            context.setSaver(new SaveToTXT());
            context.executeSaving(filePath, fileName, strings);
        } else {
            throw new SaverNotFoundException(strategy);
        }
    }

    private void deleteOldFileVersion(FacultyInfoDTO facultyInfoDTO, File filePath) {
        File[] files = filePath.listFiles();
        if (files != null) {
            Arrays.stream(files).forEach(file -> {
                if (file.getName().contains(facultyInfoDTO.name())) {
                    file.delete();
                }
            });
        }
    }
}
