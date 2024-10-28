package model.tagger;

public interface PosTagger {
    String[] getTags(String[] tokens);
}
