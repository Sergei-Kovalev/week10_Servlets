package com.gmail.kovalev.util.decorator;

import com.gmail.kovalev.util.strategy.Saver;

import java.io.File;

public class ConsoleOutput extends SaverDecorator {
    public ConsoleOutput(Saver saver) {
        super(saver);
    }

    public void outputInConsole() {
        System.out.println("Your file is ready. Check folder /faculty_cards.");
    }

    @Override
    public void save(File filePath, String fileName, String[] strings) {
        super.save(filePath, fileName, strings);
        outputInConsole();
    }
}
