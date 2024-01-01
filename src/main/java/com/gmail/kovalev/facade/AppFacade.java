package com.gmail.kovalev.facade;

import com.gmail.kovalev.controller.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("appFacade")
public class AppFacade {

    private final Controller controller;

    @Autowired
    public AppFacade(@Qualifier("controller") Controller controller) {
        this.controller = controller;
    }

//
//    public AppFacade() {
//        this.controller = new Controller();
//    }

    public void findByIdSample() {
        //*****************БЛОК ДЛЯ ПОЛУЧЕНИЯ ПО ID************************
        String facultyById = controller.findFacultyById("773dcbc0-d2fa-45b4-acf8-485b682adedd");
        System.out.println(facultyById);
        System.out.println("------------------------------------------------------");
    }

    public void findAllSample() {
        //*****************БЛОК ДЛЯ ПОЛУЧЕНИЯ ВСЕХ ИЗ БД************************
        String allFaculties = controller.findAllFaculties(1, 20);
        System.out.println(allFaculties);
        System.out.println("------------------------------------------------------");
    }

    public void saveNewFromJsonSample() {
        //*****************БЛОК ДЛЯ СОХРАНЕНИЯ НОВОГО ИЗ JSON************************
        String jsonForSave = """
                {
                  "name": "Economic",
                  "teacher": "Petrov Ivan Afeevich",
                  "email": "econo-my647@yahoo.com",
                  "actualVisitors": 10,
                  "maxVisitors": 15,
                  "pricePerDay": 9.99
                }
                """;

        String report = controller.saveFaculty(jsonForSave);
        System.out.println(report);
        System.out.println("------------------------------------------------------");
    }

    public void updateFromJsonSample() {
        //*****************БЛОК ДЛЯ ОБНОВЛЕНИЯ ИЗ JSON************************
        String uuid = "773dcbc0-d2fa-45b4-acf8-485b682adedd";
        String jsonForUpdate = """
                 {
                  "name": "Culture",
                  "teacher": "Karamzina Anna Ivanovna",
                  "email": "culture@yahoo.com",
                  "actualVisitors": 20,
                  "maxVisitors": 21,
                  "pricePerDay": 9.99
                 }
                """;
        String report = controller.updateFaculty(uuid, jsonForUpdate);
        System.out.println(report);
        System.out.println("------------------------------------------------------");
    }

    public void deleteSample() {
        //*****************БЛОК ДЛЯ УДАЛЕНИЯ************************
        String s = controller.deleteFacultyByUUID("da1a2959-363b-477e-ab23-66ef983a7568");
        System.out.println(s);
        System.out.println("------------------------------------------------------");
    }

    public void saveFromXmlSample() {
        //*****************БЛОК ДЛЯ СОХРАНЕНИЯ НОВОГО ИЗ XML************************
        String xmlForSave = """
                <?xml version="1.0" encoding="UTF-8"?>
                 <root>
                    <name>Pagonini Day</name>
                    <teacher>Santiago Dell Torro</teacher>
                    <email>santiDT@yahoo.com</email>
                    <actualVisitors>20</actualVisitors>
                    <maxVisitors>20</maxVisitors>
                    <pricePerDay>10.99</pricePerDay>
                 </root>
                """;

        String report = controller.saveFacultyFromXML(xmlForSave);
        System.out.println(report);
        System.out.println("------------------------------------------------------");
    }

    public void rollbackDeletedFaculty() {
        String report = controller.rollbackDeletedFaculty();
        System.out.println(report);
        System.out.println("------------------------------------------------------");
    }
}
