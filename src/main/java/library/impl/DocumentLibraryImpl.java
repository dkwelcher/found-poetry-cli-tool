package library.impl;

import library.DocumentLibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DocumentLibraryImpl implements DocumentLibrary {
    private static final String SENTENCE_REGEX = "(?<!\\w\\.\\w.)(?<![A-Z][a-z]\\.)(?<=[.!?])\\s";
    private static final String TOKEN_REGEX = "\\w[\\w'-]*|[.,!?;]";
    private final List<String> listOfDocuments;

    public DocumentLibraryImpl() {
        this.listOfDocuments = new ArrayList<>();
    }

    @Override
    public void add(String text) {
        this.listOfDocuments.add(text);
    }

    @Override
    public List<String[]> getListOfDocumentsAsSentences() {
        List<String[]> listOfDocumentsAsSentences = new ArrayList<>();
        for (String document : this.listOfDocuments) {
            listOfDocumentsAsSentences.add(document.split(SENTENCE_REGEX));
        }
        return listOfDocumentsAsSentences;
    }

    @Override
    public List<String[]> getListOfDocumentsAsTokens() {
        List<String[]> listOfDocumentsAsTokens = new ArrayList<>();
        Pattern tokenPattern = Pattern.compile(TOKEN_REGEX);

        for (String document : this.listOfDocuments) {
            List<String> tokensList = new ArrayList<>();
            Matcher matcher = tokenPattern.matcher(document);
            while (matcher.find()) {
                tokensList.add(matcher.group());
            }
            listOfDocumentsAsTokens.add(tokensList.toArray(new String[0]));
        }

        return listOfDocumentsAsTokens;
    }
}
