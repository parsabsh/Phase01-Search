package controller;

import model.InvertedIndexDataBase;

import java.io.File;
import java.io.FileNotFoundException;

public class MainController {
    private InvertedIndexDataBase dataBase = new InvertedIndexDataBase();
    private final InvertedIndexFileReader fileReader = new InvertedIndexFileReader();

    public void readAndStoreWordsInDirectory(String directoryFile) throws FileNotFoundException {
        fileReader.readFilesInFolder(new File(directoryFile), dataBase.getInvertedIndexMap());
    }

    public InvertedIndexDataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(InvertedIndexDataBase dataBase) {
        this.dataBase = dataBase;
    }
}
