package com.gmail.kovalev.util;

import com.gmail.kovalev.exception.PDFTemplateNotFoundException;
import com.gmail.kovalev.util.strategy.Saver;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;

public class SaveToPDF implements Saver {
    @Override
    public void save(File filePath, String fileName, String[] strings) {
        File file = new File(filePath + "\\" + fileName + ".pdf");

        PDDocument document;
        try {
            document = Loader.loadPDF(new File("src/main/resources/Clevertec_Template.pdf"));
        } catch (IOException e) {
            throw new PDFTemplateNotFoundException();
        }
        try {
            PDPage page = document.getPage(0);

            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);

            contentStream.beginText();

            PDFont font = PDType0Font.load(document, new File("C:\\Windows\\Fonts\\consola.ttf"));

            contentStream.setFont(font, 13);
            contentStream.newLineAtOffset(65, 580);
            contentStream.setLeading(14.5f);

            for (String string : strings) {
                String replace = string.replace("\r", "");
                contentStream.showText(replace);
                contentStream.newLine();
            }

            contentStream.endText();
            contentStream.close();

            document.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
