package exception;

import java.io.IOException;

public class CombinationWriterIOException extends IOException {
    private static final String EXCEPTION_MESSAGE = "CombinationWriter could not write to file.";

    public CombinationWriterIOException(Throwable cause) {
        super(EXCEPTION_MESSAGE);
        initCause(cause);
    }
}
