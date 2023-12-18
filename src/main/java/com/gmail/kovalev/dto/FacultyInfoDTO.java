package com.gmail.kovalev.dto;

import lombok.experimental.FieldNameConstants;

import java.util.UUID;

/**
 * @author Sergey Kovalev
 * Объект для передачи на фронт(UI или консоль)
 * @param id - уникальный идентификатор
 * @param name - название факультатива
 * @param teacher - преподаватель
 * @param email - эл. почта преподавателя
 * @param freePlaces - свободные места
 * @param pricePerDay - стоимость 1го посещения.
 */
@FieldNameConstants
public record FacultyInfoDTO(

        /**
         * id не может быть null
         */
        UUID id,

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
         * freePlaces не может быть отрицательным
         */
        Integer freePlaces,

        /**
         * pricePerDay не может быть null и находится в промежутке от 2 до 100 рублей за посещение.
         */
        Double pricePerDay) {
}
