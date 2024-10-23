package utility;

public final class Message {
    private Message() {};

    public static String welcomeMessage() {
        return "The Found Poetry CLI Tool extracts text from .docx and .doc files and provides options to randomize text by sentences or parts-of-speech patterns";
    }

    public static String menu() {
        final String divider = "*".repeat(40);
        final String menu = "\nMENU\n1 - Submit documents (.docx or .doc)\n2 - Specify file path for output\n3 - Randomize by sentence\n4 - Randomize by pattern\n5 - Exit\n";
        return divider + menu + divider;
    }

    public static String getUserSubmitDocumentCommandPrompt() {
        return "Specify the file path: ";
    }

    public static String getUserSpecifyOutputFilePathCommandPrompt() {
        return "Specify the file path: ";
    }

    public static String getUserSpecifyOutputFilePathCommandError() {
        return "File path invalid. File format must be .txt";
    }

    /*
     * USER_SUBMIT_DOCUMENT_COMMAND MESSAGES
     */

    public static String getUserSubmitDocumentCommandSuccess() {
        return "Document successfully extracted.";
    }

    /*
     * USER_SPECIFY_FILE_OUTPUT_FILE_PATH_COMMAND MESSAGES
     */

    public static String getUserSpecifyOutputFilePathCommandResult(String filePath) {
        final String resultsLocationMessage = "Randomization results will be written to this location: ";
        return resultsLocationMessage + filePath;
    }

    /*
     * USER_RANDOMIZE_BY_SENTENCE_COMMAND MESSAGES
     */

    public static String getUserRandomizeBySentenceCommandSuccess() {
        return "Sentence randomization successful.";
    }

    public static String getUserRandomizeBySentenceCommandError() {
        return "Sentence randomization unsuccessful. Please check specified output file path.";
    }
}
