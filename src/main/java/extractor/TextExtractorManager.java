package extractor;

import org.w3c.dom.Text;

public class TextExtractorManager {
    private final TextExtractor textExtractor;
    private final String errorMessage;

    public TextExtractorManager(TextExtractor textExtractor, String errorMessage) {
        this.textExtractor = textExtractor;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() { return this.textExtractor != null; }

    public TextExtractor getTextExtractor() { return this.textExtractor; }

    public String getErrorMessage() { return this.errorMessage; }
}
