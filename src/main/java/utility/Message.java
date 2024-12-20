package utility;

import exception.PosTaggerIOException;

import java.util.Map;

public final class Message {
    private Message() {}

    /**
     * Messages related to PosTagger instantiation failure
     */
    public static String systemShutdownMessage() {
        return "The system will shut down";
    }

    public static String taggerNullOrEmptyFilePathExceptionMessage() {
        return "PosTagger model file path is null or empty";
    }

    public static String taggerIncorrectFileFormatExceptionMessage() {
        return "PosTagger model file path is an incorrect file format. Expected .bin";
    }

    public static String taggerNonExistentFileException() {
        return "PosTagger model file does not exist";
    }

    public static String PosTaggerIOExceptionMessage(String filePath, PosTaggerIOException e) {
        return "Failed to load PosTagger at: " + filePath + e.getMessage();
    }

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

    /*
     * USER_RANDOMIZE_BY_PATTERN_COMMAND MESSAGES
     */

    public static String getUserRandomizeByPatternCommandMenu() {
        Map<String, String> classToTagsMap = POSTags.getPOSTagsMap();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Part of Speech Tags:\n");

        final int spaceCount = 3;
        int count = 0;
        for (Map.Entry<String, String> entry : classToTagsMap.entrySet()) {
            count++;
            stringBuilder.append(entry.getKey());
            stringBuilder.append(": ");
            stringBuilder.append(entry.getValue());
            stringBuilder.append(" ".repeat(spaceCount));

            if (count == 5) {
                stringBuilder.append("\n");
                count = 0;
            }
        }
        return stringBuilder.toString();
    }

    public static String getUserRandomizeByPatternCommandSuccess() {
        return "Pattern randomization successful.";
    }

    public static String getUserRandomizeByPatternCommandError() {
        return "Pattern randomization unsuccessful. Please check specified output file path.";
    }
}
