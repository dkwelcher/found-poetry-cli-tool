package tagger;

public interface PosTagger {
    String[] getTags(String[] tokens);
}
