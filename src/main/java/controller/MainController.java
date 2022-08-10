package controller;

import model.InvertedIndexDataBase;
import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.porterStemmer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

public class MainController {
    private final InvertedIndexDataBase dataBase = new InvertedIndexDataBase();
    private final InvertedIndexFileReader fileReader = new InvertedIndexFileReader();

    public void readAndStoreWordsInDirectory(String directoryFile) throws FileNotFoundException {
        fileReader.readFilesInFolder(new File(directoryFile), getDataBase().getInvertedIndexMap());
    }

    public String search(String input) {
        String[] words = splitAndStemWordsFromInputString(input);

        ArrayList<String> wordsWithoutPrep = new ArrayList<>();
        ArrayList<String> wordsWithPlus = new ArrayList<>();
        ArrayList<String> wordsWithMinus = new ArrayList<>();

        groupWords(words, wordsWithoutPrep, wordsWithPlus, wordsWithMinus);
        ArrayList<String> appearances = mainQuery(wordsWithoutPrep, wordsWithPlus, wordsWithMinus);
        if (appearances.isEmpty()) return "Nothing found!";
        return appearances.toString();
    }

    private ArrayList<String> mainQuery(ArrayList<String> wordsWithoutPrep, ArrayList<String> wordsWithPlus, ArrayList<String> wordsWithMinus) {
        ArrayList<String> appearances;
        if (wordsWithoutPrep.isEmpty()) {
            appearances = getDataBase().getAllFiles();
        } else {
            appearances = new ArrayList<>(getDataBase().getCommonAppearances(wordsWithoutPrep));
        }
        for (Iterator<String> iterator = appearances.iterator(); iterator.hasNext(); ) {
            if (wordsWithMinus.isEmpty() && wordsWithPlus.isEmpty()) {
                break;
            }
            String fileName = iterator.next();
            for (String word : wordsWithMinus) {
                if (getDataBase().hasAppearance(word, fileName)) {
                    iterator.remove();
                    break;
                }
            }
            if (!appearances.contains(fileName) || wordsWithPlus.isEmpty()) continue;

            boolean shouldBeDeleted = true;
            for (String word : wordsWithPlus) {
                if (getDataBase().hasAppearance(word, fileName)) {
                    shouldBeDeleted = false;
                }
            }
            if (shouldBeDeleted) iterator.remove();
        }
        return appearances;
    }

    private void groupWords(String[] words, ArrayList<String> wordsWithoutPrep, ArrayList<String> wordsWithPlus, ArrayList<String> wordsWithMinus) {
        for (String word : words) {
            if (word.startsWith("+")) {
                wordsWithPlus.add(word.substring(1));
            } else if (word.startsWith("-")) {
                wordsWithMinus.add(word.substring(1));
            } else {
                wordsWithoutPrep.add(word);
            }
        }
    }

    private String[] splitAndStemWordsFromInputString(String input) {
        input = input.toLowerCase();
        String[] words = input.split(" ");
        for (int i = 0; i < words.length; i++) {
            SnowballStemmer stemmer = new porterStemmer();
            stemmer.setCurrent(words[i]);
            stemmer.stem();
            words[i] = stemmer.getCurrent();
        }
        return words;
    }

    public InvertedIndexDataBase getDataBase() {
        return dataBase;
    }
}
