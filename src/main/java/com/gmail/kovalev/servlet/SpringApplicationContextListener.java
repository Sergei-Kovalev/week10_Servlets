package com.gmail.kovalev.servlet;

import com.gmail.kovalev.config.SpringConfig;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@WebListener
public class SpringApplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringConfig.class);

        sce.getServletContext().setAttribute("applicationContext", ac);
    }
}
