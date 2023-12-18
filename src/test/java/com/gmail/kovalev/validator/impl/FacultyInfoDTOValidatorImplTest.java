package com.gmail.kovalev.validator.impl;

import com.gmail.kovalev.dto.FacultyInfoDTO;
import com.gmail.kovalev.exception.FacultyInfoDTOFormatException;
import com.gmail.kovalev.testData.FacultyInfoDTOTestData;
import com.gmail.kovalev.validator.FacultyInfoDTOValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FacultyInfoDTOValidatorImplTest {

    FacultyInfoDTOValidator facultyInfoDTOValidator;

    @BeforeEach
    void setUp() {
        facultyInfoDTOValidator = new FacultyInfoDTOValidatorImpl();
    }

    @Test
    void validateValidObject() {
        // given
        FacultyInfoDTO expected = FacultyInfoDTOTestData.builder()
                .build().buildFacultyInfoDTO();

        // when
        FacultyInfoDTO actual = facultyInfoDTOValidator.validate(expected);

        // then
        assertThat(actual)
                .hasFieldOrPropertyWithValue(FacultyInfoDTO.Fields.id, expected.id())
                .hasFieldOrPropertyWithValue(FacultyInfoDTO.Fields.name, expected.name())
                .hasFieldOrPropertyWithValue(FacultyInfoDTO.Fields.teacher, expected.teacher())
                .hasFieldOrPropertyWithValue(FacultyInfoDTO.Fields.email, expected.email())
                .hasFieldOrPropertyWithValue(FacultyInfoDTO.Fields.freePlaces, expected.freePlaces())
                .hasFieldOrPropertyWithValue(FacultyInfoDTO.Fields.pricePerDay, expected.pricePerDay());
    }
    @Test
    void validateObjectWithNullId() {
        // given
        FacultyInfoDTO facultyInfoDTO = FacultyInfoDTOTestData.builder()
                .withId(null)
                .build().buildFacultyInfoDTO();

        // when, then
        assertThatExceptionOfType(FacultyInfoDTOFormatException.class)
                .isThrownBy(() -> facultyInfoDTOValidator.validate(facultyInfoDTO))
                .withMessageContaining("UUID приходящий из базы данных не может быть null.");
    }

    @ParameterizedTest
    @MethodSource("getArgsWithIncorrectName")
    void validateObjectWithIncorrectName(FacultyInfoDTO facultyInfoDTO) {

        // given, when, then
        assertThatExceptionOfType(FacultyInfoDTOFormatException.class)
                .isThrownBy(() -> facultyInfoDTOValidator.validate(facultyInfoDTO))
                .withMessageContaining("Из базы пришло неверное значение facultyName " +
                        "(не может быть null или пустым, содержит 1-30 символов(на русском или английском, включая пробелы))");
    }

    @ParameterizedTest
    @MethodSource("getArgsWithIncorrectTeacher")
    void validateObjectWithIncorrectTeacher(FacultyInfoDTO facultyInfoDTO) {

        // given, when, then
        assertThatExceptionOfType(FacultyInfoDTOFormatException.class)
                .isThrownBy(() -> facultyInfoDTOValidator.validate(facultyInfoDTO))
                .withMessageContaining("Из базы пришло неверное значение ФИО преподавателя " +
                        "(не может быть null или пустым, содержит 1-50 символов(на русском или английском, включая пробелы))");
    }

    @ParameterizedTest
    @MethodSource("getArgsWithIncorrectEmail")
    void validateObjectWithIncorrectEmail(FacultyInfoDTO facultyInfoDTO) {

        // given, when, then
        assertThatExceptionOfType(FacultyInfoDTOFormatException.class)
                .isThrownBy(() -> facultyInfoDTOValidator.validate(facultyInfoDTO))
                .withMessageContaining("Из базы пришло неверное значение e-mail.. не прошло валидацию либо значение null!");
    }

    @Test
    void validateObjectWithNullFreePlaces() {
        // given
        FacultyInfoDTO facultyInfoDTO = FacultyInfoDTOTestData.builder()
                .withFreePlaces(null)
                .build().buildFacultyInfoDTO();

        // when, then
        assertThatExceptionOfType(FacultyInfoDTOFormatException.class)
                .isThrownBy(() -> facultyInfoDTOValidator.validate(facultyInfoDTO))
                .withMessageContaining("Из базы пришло неверное количество свободных мест.. не может быть null!");
    }

    @Test
    void validateObjectWithNegativeFreePlaces() {
        // given
        FacultyInfoDTO facultyInfoDTO = FacultyInfoDTOTestData.builder()
                .withFreePlaces(-5)
                .build().buildFacultyInfoDTO();

        // when, then
        assertThatExceptionOfType(FacultyInfoDTOFormatException.class)
                .isThrownBy(() -> facultyInfoDTOValidator.validate(facultyInfoDTO))
                .withMessageContaining("Из базы пришло неверное количество свободных мест.. не может быть отрицательным!");
    }

    @Test
    void validateObjectWithNullPricePerDay() {
        // given
        FacultyInfoDTO facultyInfoDTO = FacultyInfoDTOTestData.builder()
                .withPricePerDay(null)
                .build().buildFacultyInfoDTO();

        // when, then
        assertThatExceptionOfType(FacultyInfoDTOFormatException.class)
                .isThrownBy(() -> facultyInfoDTOValidator.validate(facultyInfoDTO))
                .withMessageContaining("Из базы пришло неверное значение цены посещения - не может быть null");
    }

    @Test
    void validateObjectWithLowMinPricePerDay() {
        // given
        FacultyInfoDTO facultyInfoDTO = FacultyInfoDTOTestData.builder()
                .withPricePerDay(1.99)
                .build().buildFacultyInfoDTO();

        // when, then
        assertThatExceptionOfType(FacultyInfoDTOFormatException.class)
                .isThrownBy(() -> facultyInfoDTOValidator.validate(facultyInfoDTO))
                .withMessageContaining("Из базы пришло неверное значение цены посещения " +
                        "- Минимальная цена для любого факультета 2,0 руб./посещение");
    }

    @Test
    void validateObjectWithLargeMinPricePerDay() {
        // given
        FacultyInfoDTO facultyInfoDTO = FacultyInfoDTOTestData.builder()
                .withPricePerDay(101.1)
                .build().buildFacultyInfoDTO();

        // when, then
        assertThatExceptionOfType(FacultyInfoDTOFormatException.class)
                .isThrownBy(() -> facultyInfoDTOValidator.validate(facultyInfoDTO))
                .withMessageContaining("Из базы пришло неверное значение цены посещения " +
                        "- Максимальная цена для любого факультета 100,0 руб./посещение. Выше - придётся делиться");
    }

    private static Stream<Arguments> getArgsWithIncorrectName() {
        return Stream.of(
                Arguments.of(
                        FacultyInfoDTOTestData.builder()
                                .withName(null)
                                .build().buildFacultyInfoDTO()
                ),
                Arguments.of(
                        FacultyInfoDTOTestData.builder()
                                .withName("")
                                .build().buildFacultyInfoDTO()
                ),
                Arguments.of(
                        FacultyInfoDTOTestData.builder()
                                .withName("this is too long name for the teacher I think so")
                                .build().buildFacultyInfoDTO()
                )
        );
    }

    private static Stream<Arguments> getArgsWithIncorrectTeacher() {
        return Stream.of(
                Arguments.of(
                        FacultyInfoDTOTestData.builder()
                                .withTeacher(null)
                                .build().buildFacultyInfoDTO()
                ),
                Arguments.of(
                        FacultyInfoDTOTestData.builder()
                                .withTeacher("")
                                .build().buildFacultyInfoDTO()
                ),
                Arguments.of(
                        FacultyInfoDTOTestData.builder()
                                .withTeacher("this is name of Teacher or may be not Дратуйте меня зовут Дядя Фёдор")
                                .build().buildFacultyInfoDTO()
                )
        );
    }

    private static Stream<Arguments> getArgsWithIncorrectEmail() {
        return Stream.of(
                Arguments.of(
                        FacultyInfoDTOTestData.builder()
                                .withEmail(null)
                                .build().buildFacultyInfoDTO()
                ),
                Arguments.of(
                        FacultyInfoDTOTestData.builder()
                                .withEmail("нарусском@мыло.бай")
                                .build().buildFacultyInfoDTO()
                ),
                Arguments.of(
                        FacultyInfoDTOTestData.builder()
                                .withEmail("format not valid")
                                .build().buildFacultyInfoDTO()
                ),
                Arguments.of(
                        FacultyInfoDTOTestData.builder()
                                .withEmail("sdafl@dsaf.t")
                                .build().buildFacultyInfoDTO()
                )
        );
    }



}