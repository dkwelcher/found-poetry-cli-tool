package exception;

public class IncorrectFileFormatException extends IllegalArgumentException {
    private static final String SINGLE_FORMAT_ERROR_MESSAGE = "Incorrect file format. Expected format: ";
    private static final String MULTIPLE_FORMATS_ERROR_MESSAGE = "Incorrect file format. Expected one of the following format(s): ";
    private static final String PROVIDED_FILE_MESSAGE = "; Provided file: ";

    public IncorrectFileFormatException(String filePath, String expectedFormat) {
        super(SINGLE_FORMAT_ERROR_MESSAGE + expectedFormat + PROVIDED_FILE_MESSAGE + filePath);
    }

    public IncorrectFileFormatException(String filePath, String[] expectedFormats) {
        super(MULTIPLE_FORMATS_ERROR_MESSAGE + createExpectedFormatsMessage(expectedFormats) + PROVIDED_FILE_MESSAGE + filePath);
    }

    private static String createExpectedFormatsMessage(String[] expectedFormats) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(expectedFormats[0]);
        for (int i = 1; i < expectedFormats.length; i++) {
            stringBuilder.append(", ");
            stringBuilder.append(expectedFormats[i]);
        }
        return stringBuilder.toString();
    }
}
