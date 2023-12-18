package com.gmail.kovalev.util.decorator;

import com.gmail.kovalev.util.strategy.Saver;

import java.io.File;

public class SaverDecorator implements Saver {
    Saver saver;

    public SaverDecorator(Saver saver) {
        this.saver = saver;
    }

    @Override
    public void save(File filePath, String fileName, String[] strings) {
        saver.save(filePath, fileName, strings);
    }
}
