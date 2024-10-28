package model.randomizer.impl;

import model.library.DocumentLibrary;
import model.randomizer.SentenceRandomizer;

import java.util.*;

public class SentenceRandomizerImpl implements SentenceRandomizer {
    private List<String[]> listOfSentenceArrays;

    public SentenceRandomizerImpl() {
        this.listOfSentenceArrays = new ArrayList<>();
    }

    public void setListOfSentenceArrays(DocumentLibrary documentLibrary) {
        this.listOfSentenceArrays = documentLibrary.getListOfDocumentsAsSentences();
    }

    public List<String> generateCombinations() {
        List<String> combinedSentences = new ArrayList<>();
        for (String[] array : listOfSentenceArrays) {
            combinedSentences.addAll(Arrays.asList(array));
        }
        Collections.shuffle(combinedSentences);
        return combinedSentences;
    }
}
