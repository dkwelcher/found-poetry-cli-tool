package library;

import java.util.ArrayList;
import java.util.List;

public class DocumentLibrary {
    private final List<String> listOfDocumentsAsStrings = new ArrayList<>();

    public void addText(String text) {
        this.listOfDocumentsAsStrings.add(text);
    }

    public List<String> getListOfDocumentsAsStrings() {
        return this.listOfDocumentsAsStrings;
    }
}
