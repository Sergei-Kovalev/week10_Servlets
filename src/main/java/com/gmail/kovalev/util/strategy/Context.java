package com.gmail.kovalev.util.strategy;

import com.gmail.kovalev.util.decorator.ConsoleOutput;
import com.gmail.kovalev.util.decorator.VisualizeConsoleOutput;

import java.io.File;

public class Context {
    private Saver saver;

    public void setSaver(Saver saver) {
        this.saver = new VisualizeConsoleOutput(new ConsoleOutput(saver));
    }

    public void executeSaving(File filePath, String fileName, String[] strings) {
        saver.save(filePath, fileName, strings);
    }
}
