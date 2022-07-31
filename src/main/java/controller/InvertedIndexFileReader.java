package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class InvertedIndexFileReader {

    public void readFilesInFolder(final File folder, HashMap<String, ArrayList<String>> invertedIndexMap) throws FileNotFoundException {
        if (folder.exists() && folder.isDirectory()) {
            for (final File file : folder.listFiles()) {
                if (file.isDirectory()) {
                    readFilesInFolder(file, invertedIndexMap);
                } else {
                    Scanner scanner = new Scanner(file);
                    while (scanner.hasNext()) {
                        String word = scanner.next().toLowerCase();
                        if (invertedIndexMap.containsKey(word)) {
                            invertedIndexMap.get(word).add(file.getName());
                        } else {
                            invertedIndexMap.put(word, new ArrayList<>(Arrays.asList(file.getName())));
                        }
                    }
                }
            }
        } else {
            throw new FileNotFoundException();
        }
    }
}
