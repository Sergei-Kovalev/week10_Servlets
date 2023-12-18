package com.gmail.kovalev.util.decorator;

import com.gmail.kovalev.util.strategy.Saver;

import java.io.File;

public class VisualizeConsoleOutput extends SaverDecorator {
    public VisualizeConsoleOutput(Saver saver) {
        super(saver);
    }

    private void beautifyOutput() {
        System.out.println("------------------------------------------------");
    }

    @Override
    public void save(File filePath, String fileName, String[] strings) {
        beautifyOutput();
        super.save(filePath, fileName, strings);
        beautifyOutput();
    }
}
