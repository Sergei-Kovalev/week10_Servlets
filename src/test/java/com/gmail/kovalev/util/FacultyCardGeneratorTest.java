package com.gmail.kovalev.util;

import com.gmail.kovalev.dto.FacultyInfoDTO;
import com.gmail.kovalev.testData.FacultyInfoDTOTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class FacultyCardGeneratorTest {

    @Mock
    FacultyCardGenerator facultyCardGenerator;

    @Test
    void facultyCardOutputInFile() {
        // given
        FacultyInfoDTO facultyInfoDTO = FacultyInfoDTOTestData.builder()
                .build().buildFacultyInfoDTO();

        // when
        facultyCardGenerator.facultyCardOutputInFile(facultyInfoDTO);

        // then
        verify(facultyCardGenerator)
                .facultyCardOutputInFile(facultyInfoDTO);
    }
}