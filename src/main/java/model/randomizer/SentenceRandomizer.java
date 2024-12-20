package model.randomizer;

import model.library.DocumentLibrary;

import java.util.List;

public interface SentenceRandomizer {
    void setListOfSentenceArrays(DocumentLibrary documentLibrary);
    List<String> generateCombinations();
}
