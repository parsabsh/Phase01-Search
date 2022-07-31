package controller;

import model.InvertedIndexDataBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

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

    public String search(String word) {
        ArrayList<String> appearances = dataBase.getAppearances(word.toLowerCase());
        if (appearances != null)
            return appearances.toString();
        else
            return null;
    }
}
