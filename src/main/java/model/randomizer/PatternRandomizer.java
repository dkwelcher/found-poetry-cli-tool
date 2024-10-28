package model.randomizer;

import java.util.List;
import java.util.Map;

public interface PatternRandomizer {
    void setTagsToTokens(Map<String, List<String>> tagsToTokens);
    void setTagClasses(String[] tagClasses);
    List<String> generateCombinations();
}
