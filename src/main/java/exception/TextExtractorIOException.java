package exception;

import java.io.IOException;

public class TextExtractorIOException extends IOException {
    private static final String EXCEPTION_MESSAGE = "Failed to load Text Extractor model at ";
    public TextExtractorIOException(String filePath, Throwable cause) {
        super(EXCEPTION_MESSAGE + filePath);
        initCause(cause);
    }
}
