package library;

import java.util.List;

public interface DocumentLibrary {
    List<String[]> getListOfDocumentsAsStrings();
    void addText(String text);
}
