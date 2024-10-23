package extractor;

public record TextExtractorManager(TextExtractor textExtractor, String errorMessage) {

    public boolean isSuccess() {
        return this.textExtractor != null;
    }
}
