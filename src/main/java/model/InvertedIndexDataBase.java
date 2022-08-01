package model;

import java.util.*;

public class InvertedIndexDataBase {
    private final HashMap<String, ArrayList<String>> invertedIndexMap = new HashMap<>();

    public HashMap<String, ArrayList<String>> getInvertedIndexMap() {
        return invertedIndexMap;
    }

    public ArrayList<String> getAllFiles() {
        HashSet<String> answer = new HashSet<>();
        for (String word : invertedIndexMap.keySet()) {
            answer.addAll(invertedIndexMap.get(word));
        }
        return new ArrayList<>(answer);
    }

    public ArrayList<String> getAppearances(String word) {
        if (invertedIndexMap.get(word) == null) {
            return new ArrayList<>();
        } else {
            return invertedIndexMap.get(word);
        }
    }

    public ArrayList<String> getCommonAppearances(Collection<String> words) {
        ArrayList<ArrayList<String>> listOfAppearances = new ArrayList<>();
        for (String word : words) {
            listOfAppearances.add(getAppearances(word));
        }

        ArrayList<String> commons = new ArrayList<>(listOfAppearances.get(0));

        for (ListIterator<ArrayList<String>> iter = listOfAppearances.listIterator(1); iter.hasNext(); ) {
            commons.retainAll(iter.next());
        }

        return commons;
    }

    public Boolean hasAppearance(String word, String fileName){
        return getAppearances(word).contains(fileName);
    }
}
