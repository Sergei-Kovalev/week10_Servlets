package com.gmail.kovalev.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SaveToPDFTest {

    @Spy
    SaveToPDF saveToPDF;

    @Test
    void save() {
        // given
        File filePath = new File("faculty_cards");
        String fileName = "testFile";
        String[] strings = new String[] {"Hi bro"};

        // when
        saveToPDF.save(filePath, fileName, strings);

        // then
        verify(saveToPDF)
                .save(filePath, fileName, strings);
    }

    @Test
    void checkFileSizeAfterSave() {
        // given
        File filePath = new File("faculty_cards");
        String fileName = "testFile";
        String[] strings = new String[] {"Hi bro"};

        // when
        saveToPDF.save(filePath, fileName, strings);
        File savedFile = new File(filePath + "\\" + fileName + ".pdf");
        long actual = savedFile.length();

        // then
        assertThat(actual)
                .isGreaterThan(0);
    }
}