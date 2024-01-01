package com.gmail.kovalev;

import com.gmail.kovalev.config.SpringConfig;
import com.gmail.kovalev.facade.AppFacade;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
        AppFacade facade = context.getBean("appFacade", AppFacade.class);

        facade.findByIdSample();

//        facade.findAllSample();

//        facade.saveNewFromJsonSample();

//        facade.saveFromXmlSample();

//        facade.updateFromJsonSample();

//        facade.deleteSample();
//
//        facade.rollbackDeletedFaculty();
    }
}
