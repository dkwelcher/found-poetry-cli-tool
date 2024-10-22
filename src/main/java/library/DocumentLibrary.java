package library;

import java.util.List;

public interface DocumentLibrary {
    int getCount();
    List<String[]> getListOfDocumentsAsStrings();
    boolean addText(String text);
}
