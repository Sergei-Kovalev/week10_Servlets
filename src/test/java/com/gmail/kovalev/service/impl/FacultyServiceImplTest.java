package com.gmail.kovalev.service.impl;

import com.gmail.kovalev.dao.FacultyDAO;
import com.gmail.kovalev.dto.FacultyDTO;
import com.gmail.kovalev.dto.FacultyInfoDTO;
import com.gmail.kovalev.entity.Faculty;
import com.gmail.kovalev.exception.FacultyInfoDTOFormatException;
import com.gmail.kovalev.exception.FacultyNotFoundException;
import com.gmail.kovalev.mapper.FacultyMapper;
import com.gmail.kovalev.testData.FacultyDTOTestData;
import com.gmail.kovalev.testData.FacultyInfoDTOTestData;
import com.gmail.kovalev.testData.FacultyTestData;
import com.gmail.kovalev.util.FacultyCardGenerator;
import com.gmail.kovalev.validator.FacultyDTOValidator;
import com.gmail.kovalev.validator.FacultyInfoDTOValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FacultyServiceImplTest {

    @Mock
    FacultyMapper facultyMapper;
    @Mock
    FacultyDAO facultyDAO;
    @Mock
    FacultyDTOValidator facultyDTOValidator;
    @Mock
    FacultyInfoDTOValidator facultyInfoDTOValidator;
    @Mock
    FacultyCardGenerator facultyCardPDFGenerator;

    @Captor
    private ArgumentCaptor<Faculty> facultyCaptor;

    // через InjectMocks не работает.. берёт конструктор из реализации.. гад и выполняет реальный объект
    private FacultyServiceImpl facultyService;

    @BeforeEach
    void setUp() {
        facultyService = new FacultyServiceImpl(facultyMapper, facultyDAO, facultyDTOValidator, facultyInfoDTOValidator, facultyCardPDFGenerator);
    }

    @Nested
    class FindFacultyByIDTests {

        @Test
        void findFacultyByIdShouldReturnFacultyInfoDTOIfExistsInDB() {
            // given
            UUID uuid = FacultyInfoDTOTestData.builder().build().buildFacultyInfoDTO().id();
            Faculty facultyFromDB = FacultyTestData.builder().build().buildFaculty();
            FacultyInfoDTO afterMapper = FacultyInfoDTOTestData.builder().build().buildFacultyInfoDTO();
            FacultyInfoDTO afterValidator = FacultyInfoDTOTestData.builder().build().buildFacultyInfoDTO();
            FacultyInfoDTO expected = FacultyInfoDTOTestData.builder().build().buildFacultyInfoDTO();

            doReturn(facultyFromDB)
                    .when(facultyDAO).findFacultyById(uuid);
            doReturn(afterMapper)
                    .when(facultyMapper).fromEntityToInfoDTO(facultyFromDB);
            doReturn(afterValidator)
                    .when(facultyInfoDTOValidator).validate(afterMapper);
            doNothing()
                    .when(facultyCardPDFGenerator).facultyCardOutputInFile(expected);

            // when
            FacultyInfoDTO actual = facultyService.findFacultyById(uuid);

            // then
            assertThat(actual)
                    .isNotNull()
                    .isInstanceOf(FacultyInfoDTO.class)
                    .hasFieldOrPropertyWithValue(FacultyInfoDTO.Fields.id, expected.id())
                    .hasFieldOrPropertyWithValue(FacultyInfoDTO.Fields.name, expected.name())
                    .hasFieldOrPropertyWithValue(FacultyInfoDTO.Fields.teacher, expected.teacher())
                    .hasFieldOrPropertyWithValue(FacultyInfoDTO.Fields.email, expected.email())
                    .hasFieldOrPropertyWithValue(FacultyInfoDTO.Fields.freePlaces, expected.freePlaces())
                    .hasFieldOrPropertyWithValue(FacultyInfoDTO.Fields.pricePerDay, expected.pricePerDay());
        }

        @Test
        void findFacultyByIdShouldThrowExceptionIfDoesNotExistsInDB() {
            // given
            UUID uuid = UUID.randomUUID();


            when(facultyDAO.findFacultyById(uuid))
                    .thenThrow(new FacultyNotFoundException(uuid.toString()));

            // when, then
            assertThatThrownBy(() -> facultyService.findFacultyById(uuid))
                    .isInstanceOf(FacultyNotFoundException.class)
                    .hasMessage(String.format("Faculty with id: %s not found", uuid));
        }

        @Test
        void findFacultyByIdShouldThrowExceptionIfObjectNotValid() {
            // given
            UUID uuid = FacultyInfoDTOTestData.builder().build().buildFacultyInfoDTO().id();
            Faculty facultyFromDB = FacultyTestData.builder().build().buildFaculty();
            FacultyInfoDTO afterMapper = FacultyInfoDTOTestData.builder().build().buildFacultyInfoDTO();

            doReturn(facultyFromDB)
                    .when(facultyDAO).findFacultyById(uuid);
            doReturn(afterMapper)
                    .when(facultyMapper).fromEntityToInfoDTO(facultyFromDB);
            when(facultyInfoDTOValidator.validate(afterMapper))
                    .thenThrow(new FacultyInfoDTOFormatException("not valid"));

            // when, then
            assertThatThrownBy(() -> facultyService.findFacultyById(uuid))
                    .isInstanceOf(FacultyInfoDTOFormatException.class)
                    .hasMessage("not valid");
        }
    }

    @Nested
    class FindAllMethodTests {

        @Test
        void findAllFaculties() {
            // given
            List<Faculty> facultiesFromDB = new ArrayList<>();
            Faculty faculty1 = FacultyTestData.builder().build().buildFaculty();
            Faculty faculty2 = FacultyTestData.builder()
                    .withId(UUID.fromString("cd56ff80-1e5a-445d-b0ce-188c471f5804"))
                    .build().buildFaculty();
            facultiesFromDB.add(faculty1);
            facultiesFromDB.add(faculty2);

            List<FacultyInfoDTO> expected = new ArrayList<>();
            FacultyInfoDTO facultyInfoDTO1 = FacultyInfoDTOTestData.builder().build().buildFacultyInfoDTO();
            FacultyInfoDTO facultyInfoDTO2 = FacultyInfoDTOTestData.builder()
                    .withId(UUID.fromString("cd56ff80-1e5a-445d-b0ce-188c471f5804"))
                    .build().buildFacultyInfoDTO();
            expected.add(facultyInfoDTO1);
            expected.add(facultyInfoDTO2);

            doReturn(facultiesFromDB)
                    .when(facultyDAO).findAllFaculties(1, 2);
            doReturn(facultyInfoDTO1)
                    .when(facultyMapper).fromEntityToInfoDTO(faculty1);
            doReturn(facultyInfoDTO2)
                    .when(facultyMapper).fromEntityToInfoDTO(faculty2);
            doReturn(facultyInfoDTO1)
                    .when(facultyInfoDTOValidator).validate(facultyInfoDTO1);
            doReturn(facultyInfoDTO2)
                    .when(facultyInfoDTOValidator).validate(facultyInfoDTO2);

            // when
            List<FacultyInfoDTO> actual = facultyService.findAllFaculties(1, 2);

            // then
            assertThat(actual)
                    .isNotNull()
                    .hasSameClassAs(expected)
                    .hasSameSizeAs(expected)
                    .containsExactlyInAnyOrder(expected.get(0), expected.get(1));
        }
    }

    @Nested
    class SaveFacultyMethodTests {

        @Test
        void saveFacultyShouldReturnSameMessage() {
            // given
            FacultyDTO facultyDTOBeforeValidate = FacultyDTOTestData.builder().build().buildFacultyDTO();
            FacultyDTO facultyDTOAfterValidate = FacultyDTOTestData.builder().build().buildFacultyDTO();
            Faculty faculty = FacultyTestData.builder().build().buildFaculty();
            String expected = "All ok";

            doReturn(facultyDTOAfterValidate)
                    .when(facultyDTOValidator).validate(facultyDTOBeforeValidate);
            doReturn(faculty)
                    .when(facultyMapper).toFaculty(facultyDTOAfterValidate);
            doReturn(expected)
                    .when(facultyDAO).saveFaculty(faculty);

            // when
            String actual = facultyService.saveFaculty(facultyDTOBeforeValidate);

            // then
            assertThat(actual)
                    .isEqualTo(expected);
        }

        @Test
        void saveFacultyShouldContainsSameEntity() {
            // given
            FacultyDTO facultyDTOBeforeValidate = FacultyDTOTestData.builder().build().buildFacultyDTO();
            FacultyDTO facultyDTOAfterValidate = FacultyDTOTestData.builder().build().buildFacultyDTO();
            Faculty faculty = FacultyTestData.builder().build().buildFaculty();
            String expected = "All ok";

            doReturn(facultyDTOAfterValidate)
                    .when(facultyDTOValidator).validate(facultyDTOBeforeValidate);
            doReturn(faculty)
                    .when(facultyMapper).toFaculty(facultyDTOAfterValidate);
            doReturn(expected)
                    .when(facultyDAO).saveFaculty(faculty);

            // when
            facultyService.saveFaculty(facultyDTOBeforeValidate);

            // then
            verify(facultyDAO).saveFaculty(facultyCaptor.capture());
            assertThat(facultyCaptor.getValue())
                    .hasFieldOrPropertyWithValue(Faculty.Fields.id, faculty.getId());
        }
    }

    @Nested
    class UpdateMethodTests {

        @Test
        void updateFaculty() {
            // given
            UUID uuid = FacultyTestData.builder().build().buildFaculty().getId();
            FacultyDTO facultyDTO = FacultyDTOTestData.builder().build().buildFacultyDTO();
            FacultyDTO facultyDTOAfterValidate = FacultyDTOTestData.builder().build().buildFacultyDTO();
            Faculty facultyBeforeMerge = FacultyTestData.builder().build().buildFaculty();
            Faculty facultyAfterMerge = FacultyTestData.builder().build().buildFaculty();
            String expected = "All right";

            doReturn(facultyDTOAfterValidate)
                    .when(facultyDTOValidator).validate(facultyDTO);
            when(facultyDAO.findFacultyById(uuid))
                    .thenReturn(facultyBeforeMerge);
            doReturn(facultyAfterMerge)
                    .when(facultyMapper).merge(facultyBeforeMerge, facultyDTO);
            doReturn(expected)
                    .when(facultyDAO).updateFaculty(facultyAfterMerge);

            // when
            String actual = facultyService.updateFaculty(uuid, facultyDTO);

            // then
            assertThat(actual)
                    .isEqualTo(expected);
        }
    }

    @Nested
    class DeleteMethodTests {

        @Test
        void deleteFacultyByUUID() {
            // given
            UUID uuid = FacultyInfoDTOTestData.builder().build().getId();
            String expected = "Hi";

            doReturn(expected)
                    .when(facultyDAO).deleteFacultyByUUID(uuid);

            // when
            String actual = facultyService.deleteFacultyByUUID(uuid);

            // then
            assertThat(actual)
                    .isEqualTo(expected);
        }
    }

    @Nested
    class RollbackDeletedFacultyMethodTests {

        @Test
        void rollbackDeletedFaculty() {
            // given
            String expected = "Hi";

            doReturn(expected)
                    .when(facultyDAO).rollbackDeletedFaculty();

            // when
            String actual = facultyService.rollbackDeletedFaculty();

            // then
            assertThat(actual)
                    .isEqualTo(expected);
        }
    }
}