package exception;

import java.io.IOException;

public class PosTaggerIOException extends IOException {
    private static final String ERROR_MESSAGE = "Failed to load POS model at ";

    public PosTaggerIOException(String filePath, Throwable cause) {
        super(ERROR_MESSAGE + filePath);
        initCause(cause);
    }
}
