package com.gmail.kovalev.exception;

public class PDFTemplateNotFoundException extends RuntimeException {
    public PDFTemplateNotFoundException() {
        super("Template for background image not found. Please check src/main/resources/Clevertec_Template.pdf");
    }
}
