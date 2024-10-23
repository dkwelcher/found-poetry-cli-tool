package extractor;

public record TextExtractorResult(TextExtractor textExtractor, String errorMessage) {

    public boolean isSuccess() {
        return this.textExtractor != null;
    }
}
