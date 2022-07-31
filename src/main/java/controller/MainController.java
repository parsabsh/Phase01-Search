package controller;

import model.InvertedIndexDataBase;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.porterStemmer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

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

    public String search(String input) {
        input = input.toLowerCase();
        String[] words = input.split(" ");
        for (int i = 0; i < words.length; i++) {
            SnowballStemmer stemmer = new porterStemmer();
            stemmer.setCurrent(words[i]);
            stemmer.stem();
            words[i] = stemmer.getCurrent();
        }

        ArrayList<String> wordsWithoutPrep = new ArrayList<>();
        ArrayList<String> wordsWithPlus = new ArrayList<>();
        ArrayList<String> wordsWithMinus = new ArrayList<>();

        for (String word : words) {
            if (word.startsWith("+")) {
                wordsWithPlus.add(word.substring(1));
            } else if (word.startsWith("-")) {
                wordsWithMinus.add(word.substring(1));
            } else {
                wordsWithoutPrep.add(word);
            }
        }
        ArrayList<String> appearances = new ArrayList<>(dataBase.getCommonAppearances(wordsWithoutPrep));

        for (Iterator<String> iterator = appearances.iterator(); iterator.hasNext(); ) {
            String fileName = iterator.next();
            for (String word : wordsWithMinus) {
                if (dataBase.hasAppearance(word, fileName)) {
                    iterator.remove();
                    break;
                }
            }
            if (!appearances.contains(fileName) || wordsWithPlus.isEmpty()) continue;

            boolean shouldBeDeleted = true;
            for (String word : wordsWithPlus) {
                if (dataBase.hasAppearance(word, fileName)) {
                    shouldBeDeleted = false;
                }
            }
            if (shouldBeDeleted) iterator.remove();
        }
        if (appearances.isEmpty()) return "Nothing found!";
        return appearances.toString();
    }
}
