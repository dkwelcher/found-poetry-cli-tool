package model.tagger;

import exception.PosTaggerIOException;
import exception.TaggerNotInitializedException;

public interface PosTagger {
    void initializeTagger(String filePath) throws PosTaggerIOException;
    String[] getTags(String[] tokens) throws TaggerNotInitializedException;
}
