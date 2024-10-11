package exception;

public class NullOrEmptyFilePathException extends IllegalArgumentException {
    private static final String ERROR_MESSAGE = "Null or empty file path provided: ";

    public NullOrEmptyFilePathException(String filePath) {
        super(ERROR_MESSAGE + filePath);
    }
}
