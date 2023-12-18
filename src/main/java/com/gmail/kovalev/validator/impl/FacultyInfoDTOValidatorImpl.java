package com.gmail.kovalev.validator.impl;

import com.gmail.kovalev.dto.FacultyInfoDTO;
import com.gmail.kovalev.exception.FacultyInfoDTOFormatException;
import com.gmail.kovalev.validator.FacultyInfoDTOValidator;

import java.util.UUID;

/**
 * @author Sergey Kovalev
 * Класс для валидации класса:
 * @see FacultyInfoDTO
 * и имплементирующий интерфейс:
 * @see FacultyInfoDTOValidator
 */
public class FacultyInfoDTOValidatorImpl implements FacultyInfoDTOValidator {
    /**
     * Общий метод для валидации объекта.
     * @param facultyInfoDTO - принимает объект для валидации
     * @return проверенный объект
     * Условия валидности - смотри в описании объекта:
     * @see FacultyInfoDTO
     */
    @Override
    public FacultyInfoDTO validate(FacultyInfoDTO facultyInfoDTO) {
        checkUuid(facultyInfoDTO.id());
        checkName(facultyInfoDTO.name());
        checkTeacher(facultyInfoDTO.teacher());
        checkEmail(facultyInfoDTO.email());
        checkFreePlaces(facultyInfoDTO.freePlaces());
        checkPricePerDay(facultyInfoDTO.pricePerDay());
        return facultyInfoDTO;
    }

    /**
     * Метод для проверки поля id
     * @param id - UUID объекта.
     * @throws FacultyInfoDTOFormatException - в случае невалидности поля.
     */
    private static void checkUuid(UUID id) throws FacultyInfoDTOFormatException {
        if (id == null) {
            throw new FacultyInfoDTOFormatException(
                    "UUID приходящий из базы данных не может быть null."
            );
        }
    }
    /**
     * Метод для проверки поля name
     * @param name - название факультатива.
     * @throws FacultyInfoDTOFormatException - в случае невалидности поля.
     */
    private static void checkName(String name) throws FacultyInfoDTOFormatException {
        if (name == null || !name.matches("[a-zA-Zа-яёА-ЯЁ\\s]{1,30}")) {
            throw new FacultyInfoDTOFormatException(
                    "Из базы пришло неверное значение facultyName (не может быть null или пустым, содержит 1-30 символов(на русском или английском, включая пробелы))"
            );
        }
    }

    /**
     * Метод для проверки поля teacher
     * @param teacher - ФИО преподавателя
     * @throws FacultyInfoDTOFormatException - в случае невалидности поля.
     */
    private static void checkTeacher(String teacher) throws FacultyInfoDTOFormatException {
        if (teacher == null || !teacher.matches("[a-zA-Zа-яёА-ЯЁ\\s]{1,50}")) {
            throw new FacultyInfoDTOFormatException(
                    "Из базы пришло неверное значение ФИО преподавателя (не может быть null или пустым, содержит 1-50 символов(на русском или английском, включая пробелы))"
            );
        }
    }

    /**
     * Метод для проверки поля email
     * @param email - эл. почта преподавателя
     * @throws FacultyInfoDTOFormatException - в случае невалидности поля.
     */
    private static void checkEmail(String email) throws FacultyInfoDTOFormatException {
        if (email == null || !email.matches("^[\\w_-]+@[\\w_-]+\\.[a-z]{2,}$")) {
            throw new FacultyInfoDTOFormatException(
                    "Из базы пришло неверное значение e-mail.. не прошло валидацию либо значение null!"
            );
        }
    }

    /**
     * Метод для проверки поля freePlaces
     * @param freePlaces - количество свободных мест на факультативе
     * @throws FacultyInfoDTOFormatException - в случае невалидности поля.
     */
    private static void checkFreePlaces(Integer freePlaces) throws FacultyInfoDTOFormatException {
        if (freePlaces == null) {
            throw new FacultyInfoDTOFormatException(
                    "Из базы пришло неверное количество свободных мест.. не может быть null!"
            );
        }
        if (freePlaces < 0) {
            throw new FacultyInfoDTOFormatException(
                    "Из базы пришло неверное количество свободных мест.. не может быть отрицательным!"
            );
        }
    }

    /**
     * Метод для проверки поля pricePerDay
     * @param pricePerDay - цена за 1 посещение.
     * @throws FacultyInfoDTOFormatException - в случае невалидности поля.
     */
    private void checkPricePerDay(Double pricePerDay) throws FacultyInfoDTOFormatException {
        if (pricePerDay == null) {
            throw new FacultyInfoDTOFormatException(
                    "Из базы пришло неверное значение цены посещения - не может быть null"
            );
        }
        if (pricePerDay < 2.0) {
            throw new FacultyInfoDTOFormatException(
                    "Из базы пришло неверное значение цены посещения - Минимальная цена для любого факультета 2,0 руб./посещение"
            );
        }
        if (pricePerDay >= 100.0) {
            throw new FacultyInfoDTOFormatException(
                    "Из базы пришло неверное значение цены посещения - Максимальная цена для любого факультета 100,0 руб./посещение. Выше - придётся делиться"
            );
        }
    }
}
