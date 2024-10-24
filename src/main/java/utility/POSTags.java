package utility;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public final class POSTags {
    private POSTags() {}
    static Map<String, String> getPOSTagsMap() {
        Map<String, String> classToTagMap = new LinkedHashMap<>();

        classToTagMap.put("adjective", "ADJ");
        classToTagMap.put("adposition", "ADP");
        classToTagMap.put("adverb", "ADV");
        classToTagMap.put("auxiliary", "AUX");
        classToTagMap.put("coordinating conjunction", "CCONJ");
        classToTagMap.put("determiner", "DET");
        classToTagMap.put("interjection", "INTJ");
        classToTagMap.put("noun", "NOUN");
        classToTagMap.put("numeral", "NUM");
        classToTagMap.put("particle", "PART");
        classToTagMap.put("pronoun", "PRON");
        classToTagMap.put("proper noun", "PROPN");
        classToTagMap.put("punctuation", "PUNCT");
        classToTagMap.put("subordinating conjunction", "SCONJ");
        classToTagMap.put("symbol", "SYM");
        classToTagMap.put("verb", "VERB");
        classToTagMap.put("other", "X");

        return classToTagMap;
    }
}
