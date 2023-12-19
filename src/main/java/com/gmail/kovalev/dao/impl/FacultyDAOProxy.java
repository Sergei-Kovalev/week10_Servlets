package com.gmail.kovalev.dao.impl;

import com.gmail.kovalev.cache.Cache;
import com.gmail.kovalev.cacheFactory.CacheFactory;
import com.gmail.kovalev.dao.FacultyDAO;
import com.gmail.kovalev.entity.Faculty;
import com.gmail.kovalev.exception.FacultyNotFoundException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class FacultyDAOProxy implements InvocationHandler {
    private final FacultyDAO facultyDAO;
    private final Cache<UUID, Faculty> cache;

    public FacultyDAOProxy(FacultyDAO facultyDAO) {
        this.facultyDAO = facultyDAO;
        this.cache = CacheFactory.createCacheByName();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        switch (methodName) {
            case "findFacultyById" -> {
                Faculty faculty = cache.get((UUID) args[0]);
                if (faculty == null) {
                    try {
                        faculty = (Faculty) method.invoke(facultyDAO, args);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new FacultyNotFoundException(args[0].toString());
                    }
                    cache.set(faculty.getId(), faculty);
                }
                return faculty;
            }
            case "findAllFaculties" -> {
                System.out.println("""
                        Тянем всё из базы. Кэш не используется. Не рационально.
                        """);
                return method.invoke(facultyDAO, args);
            }
            case "saveFaculty" -> {
                String message = (String) method.invoke(facultyDAO, args);
                String uuidString = message.substring(message.lastIndexOf(" ") + 1);
                Faculty facultyForSaveToCache = facultyDAO.findFacultyById(UUID.fromString(uuidString));
                cache.set(facultyForSaveToCache.getId(), facultyForSaveToCache);
                return message;
            }
            case "updateFaculty" -> {
                String message = (String) method.invoke(facultyDAO, args);
                Faculty faculty = (Faculty) args[0];
                cache.set(faculty.getId(), faculty);
                return message;
            }
            case "deleteFacultyByUUID" -> {
                String message = (String) method.invoke(facultyDAO, args);
                cache.remove((UUID) args[0]);
                return message;
            }
            case "rollbackDeletedFaculty" -> {
                return method.invoke(facultyDAO, args);
            }
        }
        return methodName;
    }
}
