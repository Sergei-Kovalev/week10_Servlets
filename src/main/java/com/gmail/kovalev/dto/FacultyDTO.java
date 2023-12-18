package com.gmail.kovalev.dto;

import lombok.experimental.FieldNameConstants;

/**
 * @author Sergey Kovalev
 * Объект для передачи данных в DAO слой из фронта/UI.
 * @param name - название факультатива
 * @param teacher - преподаватель
 * @param email - эл. почта преподавателя
 * @param actualVisitors - количество записавшихся студентов
 * @param maxVisitors - максимальная вместимость факультатива или аудитории
 * @param pricePerDay - стоимость 1го посещения.
 */
@FieldNameConstants
public record FacultyDTO(

        /**
         * name не может быть null или пустым, содержит 1-30 символов(на русском или английском, включая пробелы)
         */
        String name,

        /**
         * teacher не может быть null или пустым, содержит 1-50 символов(на русском или английском, включая пробелы)
         */
        String teacher,

        /**
         * email должно быть формата хххххх@хххх.ххх - после точки минимум 2 символа латинского алфавита(домен).
         * до символа "." может содержать "_" или "-"
         */
        String email,

        /**
         * actualVisitors не может быть null, меньше нуля или превышать maxVisitors
         */
        Integer actualVisitors,

        /**
         * maxVisitors не может быть null, меньше нули и превышать 50 человек (Физические ограничения аудитории)
         */
        Integer maxVisitors,

        /**
         * pricePerDay не может быть null и находится в промежутке от 2 до 100 рублей за посещение.
         */
        Double pricePerDay) {
}
