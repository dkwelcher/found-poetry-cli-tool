package exception;

public class NonExistentFileException extends IllegalArgumentException {
    private static final String ERROR_MESSAGE = "File does not exist: ";

    public NonExistentFileException(String filePath) {
        super(ERROR_MESSAGE + filePath);
    }
}
