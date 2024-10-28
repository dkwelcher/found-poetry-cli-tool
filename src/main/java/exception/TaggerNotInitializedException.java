package exception;

public class TaggerNotInitializedException extends IllegalStateException {
    public TaggerNotInitializedException() {
        super("Tagger not initialized. Must call initializeTagger() before getTags().");
    }
}
