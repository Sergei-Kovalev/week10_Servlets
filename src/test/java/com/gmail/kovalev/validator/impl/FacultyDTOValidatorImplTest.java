package com.gmail.kovalev.validator.impl;

import com.gmail.kovalev.dto.FacultyDTO;
import com.gmail.kovalev.exception.FacultyDTOFormatException;
import com.gmail.kovalev.testData.FacultyDTOTestData;
import com.gmail.kovalev.validator.FacultyDTOValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FacultyDTOValidatorImplTest {

    FacultyDTOValidator facultyDTOValidator;

    @BeforeEach
    void setUp() {
        facultyDTOValidator = new FacultyDTOValidatorImpl();
    }

    @Test
    void validateValidObject() {
        // given
        FacultyDTO expected = FacultyDTOTestData.builder()
                .build().buildFacultyDTO();

        // when
        FacultyDTO actual = facultyDTOValidator.validate(expected);

        // then
        assertThat(actual)
                .hasFieldOrPropertyWithValue(FacultyDTO.Fields.name, expected.name())
                .hasFieldOrPropertyWithValue(FacultyDTO.Fields.email, expected.email())
                .hasFieldOrPropertyWithValue(FacultyDTO.Fields.teacher, expected.teacher())
                .hasFieldOrPropertyWithValue(FacultyDTO.Fields.actualVisitors, expected.actualVisitors())
                .hasFieldOrPropertyWithValue(FacultyDTO.Fields.maxVisitors, expected.maxVisitors())
                .hasFieldOrPropertyWithValue(FacultyDTO.Fields.pricePerDay, expected.pricePerDay());
    }

    @ParameterizedTest
    @MethodSource("getArgsWithIncorrectName")
    void validateObjectWithIncorrectName(FacultyDTO facultyDTO) {

        // given, when, then
        assertThatExceptionOfType(FacultyDTOFormatException.class)
                .isThrownBy(() -> facultyDTOValidator.validate(facultyDTO))
                .withMessageContaining("Название факультета (не может быть null или пустым, " +
                        "содержит 1-30 символов(на русском или английском, включая пробелы))");
    }

    @ParameterizedTest
    @MethodSource("getArgsWithIncorrectTeacher")
    void validateObjectWithIncorrectTeacher(FacultyDTO facultyDTO) {

        // given, when, then
        assertThatExceptionOfType(FacultyDTOFormatException.class)
                .isThrownBy(() -> facultyDTOValidator.validate(facultyDTO))
                .withMessageContaining("ФИО преподавателя (не может быть null или пустым, содержит 1-50 символов(на русском или английском, включая пробелы))");
    }

    @ParameterizedTest
    @MethodSource("getArgsWithIncorrectEmail")
    void validateObjectWithIncorrectEmail(FacultyDTO facultyDTO) {

        // given, when, then
        assertThatExceptionOfType(FacultyDTOFormatException.class)
                .isThrownBy(() -> facultyDTOValidator.validate(facultyDTO))
                .withMessageContaining("e-mail не прошёл валидацию ... убедитесь что вы ввели его верно!");
    }

    @Test
    void validateObjectWithNullActualVisitors() {
        // given
        FacultyDTO facultyDTO = FacultyDTOTestData.builder()
                        .withActualVisitors(null)
                                .build().buildFacultyDTO();

        // when, then
        assertThatExceptionOfType(FacultyDTOFormatException.class)
                .isThrownBy(() -> facultyDTOValidator.validate(facultyDTO))
                .withMessageContaining("Количество посетителей не может быть null");
    }

    @Test
    void validateObjectWithNegativeActualVisitors() {
        // given
        FacultyDTO facultyDTO = FacultyDTOTestData.builder()
                .withActualVisitors(-5)
                .build().buildFacultyDTO();

        // when, then
        assertThatExceptionOfType(FacultyDTOFormatException.class)
                .isThrownBy(() -> facultyDTOValidator.validate(facultyDTO))
                .withMessageContaining("Количество посетителей не может быть меньше нуля");
    }

    @Test
    void validateObjectWithActualVisitorsMoreThanMaxVisitors() {
        // given
        FacultyDTO facultyDTO = FacultyDTOTestData.builder()
                .withActualVisitors(30)
                .withMaxVisitors(25)
                .build().buildFacultyDTO();

        // when, then
        assertThatExceptionOfType(FacultyDTOFormatException.class)
                .isThrownBy(() -> facultyDTOValidator.validate(facultyDTO))
                .withMessageContaining("Количество посетителей не может быть больше, чем вмещает группа");
    }

    @Test
    void validateObjectWithNullMaxVisitors() {
        // given
        FacultyDTO facultyDTO = FacultyDTOTestData.builder()
                .withMaxVisitors(null)
                .build().buildFacultyDTO();

        // when, then
        assertThatExceptionOfType(FacultyDTOFormatException.class)
                .isThrownBy(() -> facultyDTOValidator.validate(facultyDTO))
                .withMessageContaining("Количество посетителей не может быть null");
    }

    @Test
    void validateObjectWithNegativeMaxVisitors() {
        // given
        FacultyDTO facultyDTO = FacultyDTOTestData.builder()
                .withMaxVisitors(-5)
                .build().buildFacultyDTO();

        // when, then
        assertThatExceptionOfType(FacultyDTOFormatException.class)
                .isThrownBy(() -> facultyDTOValidator.validate(facultyDTO))
                .withMessageContaining("Количество посетителей не может быть меньше нуля");
    }

    @Test
    void validateObjectWithOverLimitMaxVisitors() {
        // given
        FacultyDTO facultyDTO = FacultyDTOTestData.builder()
                .withMaxVisitors(51)
                .build().buildFacultyDTO();

        // when, then
        assertThatExceptionOfType(FacultyDTOFormatException.class)
                .isThrownBy(() -> facultyDTOValidator.validate(facultyDTO))
                .withMessageContaining("Максимальная аудитория вмещает 50 абитуриентов. Группы ограничены этим числом");
    }

    @Test
    void validateObjectWithNullPricePerDay() {
        // given
        FacultyDTO facultyDTO = FacultyDTOTestData.builder()
                .withPricePerDay(null)
                .build().buildFacultyDTO();

        // when, then
        assertThatExceptionOfType(FacultyDTOFormatException.class)
                .isThrownBy(() -> facultyDTOValidator.validate(facultyDTO))
                .withMessageContaining("Цена посещения не может быть null");
    }

    @Test
    void validateObjectWithLowMinPricePerDay() {
        // given
        FacultyDTO facultyDTO = FacultyDTOTestData.builder()
                .withPricePerDay(1.)
                .build().buildFacultyDTO();

        // when, then
        assertThatExceptionOfType(FacultyDTOFormatException.class)
                .isThrownBy(() -> facultyDTOValidator.validate(facultyDTO))
                .withMessageContaining("Минимальная цена для любого факультета 2,0 руб./посещение");
    }

    @Test
    void validateObjectWithLargeMinPricePerDay() {
        // given
        FacultyDTO facultyDTO = FacultyDTOTestData.builder()
                .withPricePerDay(100.)
                .build().buildFacultyDTO();

        // when, then
        assertThatExceptionOfType(FacultyDTOFormatException.class)
                .isThrownBy(() -> facultyDTOValidator.validate(facultyDTO))
                .withMessageContaining("Максимальная цена для любого факультета 100,0 руб./посещение. Выше - придётся делиться");
    }

    private static Stream<Arguments> getArgsWithIncorrectName() {
        return Stream.of(
                Arguments.of(
                        FacultyDTOTestData.builder()
                                .withName(null)
                                .build().buildFacultyDTO()
                ),
                Arguments.of(
                        FacultyDTOTestData.builder()
                        .withName("")
                        .build().buildFacultyDTO()
                ),
                Arguments.of(
                        FacultyDTOTestData.builder()
                                .withName("this is too long name for the teacher I think so")
                                .build().buildFacultyDTO()
                )
        );
    }

    private static Stream<Arguments> getArgsWithIncorrectTeacher() {
        return Stream.of(
                Arguments.of(
                        FacultyDTOTestData.builder()
                                .withTeacher(null)
                                .build().buildFacultyDTO()
                ),
                Arguments.of(
                        FacultyDTOTestData.builder()
                                .withTeacher("")
                                .build().buildFacultyDTO()
                ),
                Arguments.of(
                        FacultyDTOTestData.builder()
                                .withTeacher("this is name of Teacher or may be not Дратуйте меня зовут Дядя Фёдор")
                                .build().buildFacultyDTO()
                )
        );
    }

    private static Stream<Arguments> getArgsWithIncorrectEmail() {
        return Stream.of(
                Arguments.of(
                        FacultyDTOTestData.builder()
                                .withEmail(null)
                                .build().buildFacultyDTO()
                ),
                Arguments.of(
                        FacultyDTOTestData.builder()
                                .withEmail("нарусском@мыло.бай")
                                .build().buildFacultyDTO()
                ),
                Arguments.of(
                        FacultyDTOTestData.builder()
                                .withEmail("format not valid")
                                .build().buildFacultyDTO()
                ),
                Arguments.of(
                        FacultyDTOTestData.builder()
                                .withEmail("sdafl@dsaf.t")
                                .build().buildFacultyDTO()
                )
        );
    }
}