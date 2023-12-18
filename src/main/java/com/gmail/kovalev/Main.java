package com.gmail.kovalev;

import com.gmail.kovalev.facade.AppFacade;

public class Main {
    public static void main(String[] args) {
        AppFacade facade = new AppFacade();

        facade.findByIdSample();

//        facade.findAllSample();

//        facade.saveNewFromJsonSample();

//        facade.saveFromXmlSample();

//        facade.updateFromJsonSample();

//        facade.deleteSample();

//        facade.rollbackDeletedFaculty();
    }
}
