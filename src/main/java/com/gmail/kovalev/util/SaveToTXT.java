package com.gmail.kovalev.util;

import com.gmail.kovalev.util.strategy.Saver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveToTXT implements Saver {
    @Override
    public void save(File filePath, String fileName, String[] strings) {
        File file = new File(filePath + "\\" + fileName + ".txt");
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(String.join("\n", strings));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
