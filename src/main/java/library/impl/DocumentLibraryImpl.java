package library.impl;

import library.DocumentLibrary;

import java.util.ArrayList;
import java.util.List;

public class DocumentLibraryImpl implements DocumentLibrary {
    private static final String REGEX = "(?<!\\w\\.\\w.)(?<![A-Z][a-z]\\.)(?<=[.!?])\\s";
    private int count;
    private final List<String[]> listOfDocumentsAsStrings;

    public DocumentLibraryImpl() {
        this.count = 0;
        this.listOfDocumentsAsStrings = new ArrayList<>();
    }

    public int getCount() {
        return this.count;
    }

    public List<String[]> getListOfDocumentsAsStrings() {
        return this.listOfDocumentsAsStrings;
    }

    public boolean addText(String text) {
        if (count >= 3) {
            return false;
        }
        count++;
        String[] splitText = text.split(REGEX);
        return this.listOfDocumentsAsStrings.add(splitText);
    }
}
