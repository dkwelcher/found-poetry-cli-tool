package tagger;

import tagger.impl.PosTaggerImpl;

public interface PosTagger {
    String[] getTags(String[] tokens);
}
