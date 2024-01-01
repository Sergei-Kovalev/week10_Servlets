package com.gmail.kovalev.validator.impl;

import com.gmail.kovalev.dto.FacultyDTO;
import com.gmail.kovalev.exception.FacultyDTOFormatException;
import com.gmail.kovalev.validator.FacultyDTOValidator;
import org.springframework.stereotype.Component;

/**
 * @author Sergey Kovalev
 * Класс для валидации класса:
 * @see FacultyDTO
 * и имплементирующий интерфейс:
 * @see FacultyDTOValidator
 */
@Component("facultyDTOValidatorImpl")
public class FacultyDTOValidatorImpl implements FacultyDTOValidator {

    /**
     * Общий метод для валидации объекта.
     * @param facultyDTO - принимает объект для валидации
     * @return проверенный объект
     * Условия валидности - смотри в описании объекта:
     * @see FacultyDTO
     */
    public FacultyDTO validate(FacultyDTO facultyDTO) {
        checkName(facultyDTO.name());
        checkTeacher(facultyDTO.teacher());
        checkEmail(facultyDTO.email());
        checkActualVisitors(facultyDTO.actualVisitors(), facultyDTO.maxVisitors());
        checkMaxVisitors(facultyDTO.maxVisitors());
        checkPricePerDay(facultyDTO.pricePerDay());
        return facultyDTO;
    }

    /**
     * Метод для проверки поля name
     * @param name - название факультатива.
     * @throws FacultyDTOFormatException - в случае невалидности поля.
     */
    private static void checkName(String name) throws FacultyDTOFormatException {

        if (name == null || !name.matches("[a-zA-Zа-яёА-ЯЁ\\s]{1,30}")) {
            throw new FacultyDTOFormatException(
                    "Название факультета (не может быть null или пустым, содержит 1-30 символов(на русском или английском, включая пробелы))"
            );
        }
    }

    /**
     * Метод для проверки поля teacher
     * @param teacher - ФИО преподавателя
     * @throws FacultyDTOFormatException - в случае невалидности поля.
     */
    private static void checkTeacher(String teacher) throws FacultyDTOFormatException {
        if (teacher == null || !teacher.matches("[a-zA-Zа-яёА-ЯЁ\\s]{1,50}")) {
            throw new FacultyDTOFormatException(
                    "ФИО преподавателя (не может быть null или пустым, содержит 1-50 символов(на русском или английском, включая пробелы))"
            );
        }
    }

    /**
     * Метод для проверки поля email
     * @param email - эл. почта преподавателя
     * @throws FacultyDTOFormatException - в случае невалидности поля.
     */
    private static void checkEmail(String email) {
        if (email == null || !email.matches("^[\\w_-]+@[\\w_-]+\\.[a-z]{2,}$")) {
            throw new FacultyDTOFormatException(
                    "e-mail не прошёл валидацию ... убедитесь что вы ввели его верно!"
            );
        }
    }

    /**
     * Метод для проверки поля actualVisitors
     * @param actualVisitors - количество записавшихся студентов
     * @throws FacultyDTOFormatException - в случае невалидности поля.
     */
    private static void checkActualVisitors(Integer actualVisitors, Integer maxVisitors) throws FacultyDTOFormatException {
        if (actualVisitors == null) {
            throw new FacultyDTOFormatException(
                    "Количество посетителей не может быть null"
            );
        }
        if (actualVisitors < 0) {
            throw new FacultyDTOFormatException(
                    "Количество посетителей не может быть меньше нуля"
            );
        }
        if (maxVisitors != null && maxVisitors > 0 && actualVisitors > maxVisitors) {
            throw new FacultyDTOFormatException(
                    "Количество посетителей не может быть больше, чем вмещает группа"
            );
        }
    }

    /**
     * Метод для проверки поля maxVisitors
     * @param maxVisitors - количество записавшихся студентов
     * @throws FacultyDTOFormatException - в случае невалидности поля.
     */
    private static void checkMaxVisitors(Integer maxVisitors) throws FacultyDTOFormatException {
        if (maxVisitors == null) {
            throw new FacultyDTOFormatException(
                    "Количество посетителей не может быть null"
            );
        }
        if (maxVisitors < 0) {
            throw new FacultyDTOFormatException(
                    "Количество посетителей не может быть меньше нуля"
            );
        }
        if (maxVisitors > 50) {
            throw new FacultyDTOFormatException(
                    "Максимальная аудитория вмещает 50 абитуриентов. Группы ограничены этим числом"
            );
        }
    }

    /**
     * Метод для проверки поля pricePerDay
     * @param pricePerDay - цена за 1 посещение.
     * @throws FacultyDTOFormatException - в случае невалидности поля.
     */
    private static void checkPricePerDay(Double pricePerDay) throws FacultyDTOFormatException {
        if (pricePerDay == null) {
            throw new FacultyDTOFormatException(
                    "Цена посещения не может быть null"
            );
        }
        if (pricePerDay < 2.0) {
            throw new FacultyDTOFormatException(
                    "Минимальная цена для любого факультета 2,0 руб./посещение"
            );
        }
        if (pricePerDay >= 100.0) {
            throw new FacultyDTOFormatException(
                    "Максимальная цена для любого факультета 100,0 руб./посещение. Выше - придётся делиться"
            );
        }
    }
}
