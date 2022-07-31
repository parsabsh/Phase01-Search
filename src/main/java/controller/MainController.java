package controller;

import model.InvertedIndexDataBase;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.porterStemmer;

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
        word = word.toLowerCase();
        SnowballStemmer stemmer = new porterStemmer();
        stemmer.setCurrent(word);
        stemmer.stem();
        word = stemmer.getCurrent();
        ArrayList<String> appearances = dataBase.getAppearances(word);
        if (appearances != null)
            return appearances.toString();
        else
            return "Nothing found!";
    }
}
