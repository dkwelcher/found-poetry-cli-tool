package randomizer.impl;

import randomizer.PatternRandomizer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PatternRandomizerImpl implements PatternRandomizer {
    private Map<String, List<String>> tagsToTokens;
    private String[] tagClasses;

    @Override
    public void setTagsToTokens(Map<String, List<String>> tagsToTokens) {
        this.tagsToTokens = tagsToTokens;
    }

    @Override
    public void setTagClasses(String[] tagClasses) {
        this.tagClasses = tagClasses;
    }

    @Override
    public List<String> generateCombinations() {
        List<List<String>> patternListOfTokensByTagClass = convertMapToListAccordingToPattern();
        shuffleListOfLists(patternListOfTokensByTagClass);
        int largestListSize = getLargestListSize(patternListOfTokensByTagClass);
        return getCombinations(patternListOfTokensByTagClass, largestListSize);
    }

    private List<List<String>> convertMapToListAccordingToPattern() {
        List<List<String>> patternListOfTokensByTagClass = new ArrayList<>();

        for (String tagClass : this.tagClasses) {
            patternListOfTokensByTagClass.add(new ArrayList<>(this.tagsToTokens.get(tagClass)));
        }

        return patternListOfTokensByTagClass;
    }

    private void shuffleListOfLists(List<List<String>> listOfLists) {
        for (List<String> list : listOfLists) {
            Collections.shuffle(list);
        }
    }

    private int getLargestListSize(List<List<String>> listOfLists) {
        int largestListSize = 0;
        for (List<String> list : listOfLists) {
            int currentSize = list.size();
            if (currentSize > largestListSize) {
                largestListSize = currentSize;
            }
        }
        return largestListSize;
    }

    private List<String> getCombinations(List<List<String>> patternListOfTokensByTagClass, int largestListSize) {
        List<String> combinations = new ArrayList<>();

        for (int i = 0; i < largestListSize; i++) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int j = 0; j < tagClasses.length; j++) {
                List<String> tokens = patternListOfTokensByTagClass.get(j);

                if (tokens != null && !tokens.isEmpty()) {
                    stringBuilder.append(tokens.get(i % tokens.size()));

                    if (j < tagClasses.length - 1) {
                        stringBuilder.append(" ");
                    }
                }
            }
            combinations.add(stringBuilder.toString());
        }
        return combinations;
    }
}
