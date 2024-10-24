package library;

import java.util.List;

public interface DocumentLibrary {
    void add(String text);

    List<String[]> getListOfDocumentsAsSentences();
    List<String[]> getListOfDocumentsAsTokens();
}
