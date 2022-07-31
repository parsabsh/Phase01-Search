package controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class InvertedIndexFileReader {

    public void readFilesInFolder(final File folder, HashMap<String, ArrayList<Integer>> invertedIndexMap) {
        for (final File file : folder.listFiles()) {
            if (file.isDirectory()) {
                readFilesInFolder(file, invertedIndexMap);
            } else {

            }
        }
    }
}
