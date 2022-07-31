package model;

import java.util.ArrayList;
import java.util.HashMap;

public class InvertedIndexDataBase {
    private final HashMap<String, ArrayList<String>> invertedIndexMap = new HashMap<>();

    public HashMap<String, ArrayList<String>> getInvertedIndexMap() {
        return invertedIndexMap;
    }

    public ArrayList<String> getAppearances(String word) {
        return invertedIndexMap.get(word);
    }
}
