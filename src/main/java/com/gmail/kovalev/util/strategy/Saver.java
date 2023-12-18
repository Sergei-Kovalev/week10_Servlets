package com.gmail.kovalev.util.strategy;

import java.io.File;

public interface Saver {
    void save(File filePath, String fileName, String[] strings);
}
