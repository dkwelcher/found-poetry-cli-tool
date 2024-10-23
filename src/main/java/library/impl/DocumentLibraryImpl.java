package library.impl;

import library.DocumentLibrary;

import java.util.ArrayList;
import java.util.List;

public class DocumentLibraryImpl implements DocumentLibrary {
    private static final String REGEX = "(?<!\\w\\.\\w.)(?<![A-Z][a-z]\\.)(?<=[.!?])\\s";
    private final List<String[]> listOfDocumentsAsStrings;

    public DocumentLibraryImpl() {
        this.listOfDocumentsAsStrings = new ArrayList<>();
    }


    public List<String[]> getListOfDocumentsAsStrings() {
        return this.listOfDocumentsAsStrings;
    }

    public void addText(String text) {
        String[] splitText = text.split(REGEX);
        this.listOfDocumentsAsStrings.add(splitText);
    }
}
