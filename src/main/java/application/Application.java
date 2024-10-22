package application;

import exception.TextExtractorIOException;
import extractor.TextExtractorManager;
import extractor.impl.DocTextExtractor;
import extractor.impl.DocxTextExtractor;
import library.DocumentLibrary;
import library.impl.DocumentLibraryImpl;
import randomizer.CombinationWriter;
import randomizer.SentenceRandomizer;
import randomizer.impl.CombinationWriterImpl;
import randomizer.impl.SentenceRandomizerImpl;
import tagger.PosTagger;
import tagger.impl.PosTaggerImpl;
import exception.IncorrectFileFormatException;
import exception.NonExistentFileException;
import exception.PosTaggerIOException;
import tagger.PosTaggerManager;

import java.util.List;
import java.util.Scanner;

public class Application {
    private static final String MODEL_FILE_PATH = "..\\found-poetry-cli-tool\\src\\main\\resources\\en-pos-maxent.bin";
    private static final String DEFAULT_OUTPUT_FILE_PATH = "associations.txt";
    private static final String USER_SUBMIT_DOCUMENT_COMMAND = "1";
    private static final String USER_SPECIFY_OUTPUT_FILE_PATH_COMMAND = "2";
    private static final String USER_RANDOMIZE_BY_SENTENCE_COMMAND = "3";
    private static final String USER_RANDOMIZE_BY_PATTERN_COMMAND = "4";
    private static final String USER_EXIT_COMMAND = "5";

    public static void run() {
        PosTaggerManager posTaggerManager = getPosTaggerManager();

        if (!posTaggerManager.isSuccess()) {
            System.out.println(posTaggerManager.errorMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        DocumentLibrary documentLibrary = new DocumentLibraryImpl();

        displayWelcomeMessage();

        String outputFilePath = DEFAULT_OUTPUT_FILE_PATH;

        while (!userInput.equalsIgnoreCase(USER_EXIT_COMMAND)) {
            displayMenu();
            userInput = scanner.nextLine().trim();

            if (userInput.equals(USER_SUBMIT_DOCUMENT_COMMAND)) {
                System.out.println(getUserSubmitDocumentCommandPrompt());
                userInput = scanner.nextLine().trim();
                System.out.println(handleUserSubmitDocumentCommand(userInput, documentLibrary));
            }

            if (userInput.equals(USER_SPECIFY_OUTPUT_FILE_PATH_COMMAND)) {
                System.out.println(getUserSpecifyOutputFilePathCommandPrompt());
                userInput = scanner.nextLine().trim();

                if (validateUserSpecifyOutputFilePathCommand(userInput)) {
                    outputFilePath = userInput;
                    System.out.println(getUserSpecifyOutputFilePathCommandResult(outputFilePath));
                } else {
                    System.out.println(getUserSpecifyOutputFilePathCommandErrorMessage());
                }
            }

            if (userInput.equals(USER_RANDOMIZE_BY_SENTENCE_COMMAND)) {
                System.out.println(
                        getUserRandomizeBySentenceCommandResult(
                                handleUserRandomizeBySentenceCommand(
                                        documentLibrary,
                                        outputFilePath
                                )
                        )
                );
            }

            if (userInput.equals(USER_RANDOMIZE_BY_PATTERN_COMMAND)) {
                System.out.println("Not implemented");
            }
        }
    }

    private static PosTaggerManager getPosTaggerManager() {
        final String systemShutdownMessage = "System forced to close.";
        final String noErrorMessage = "No errors. PosTagger is ready.";
        final String incorrectFileFormatExceptionMessage = "Incorrect file format: " + MODEL_FILE_PATH + ". Expected format: .bin; " + systemShutdownMessage;
        final String nonExistentFileExceptionMessage = "File does not exist: " + MODEL_FILE_PATH + "; " + systemShutdownMessage;
        final String posTaggerIOExceptionMessage = "I/O error occurred while loading the file: " + MODEL_FILE_PATH + "; " + systemShutdownMessage;

        PosTagger posTagger;
        try {
            posTagger = new PosTaggerImpl(MODEL_FILE_PATH);
            return new PosTaggerManager(posTagger, noErrorMessage);
        } catch (IncorrectFileFormatException e) {
            return new PosTaggerManager(null, incorrectFileFormatExceptionMessage);
        } catch (NonExistentFileException e) {
            return new PosTaggerManager(null, nonExistentFileExceptionMessage);
        } catch (PosTaggerIOException e) {
            return new PosTaggerManager(null, posTaggerIOExceptionMessage);
        }
    }

    private static void displayWelcomeMessage() {
        final String welcomeMessage = "The Found Poetry CLI Tool extracts text from .docx and .doc files and provides options to randomize text by sentences or parts-of-speech patterns";
        System.out.println(welcomeMessage);
    }

    private static void displayMenu() {
        final String divider = "*".repeat(40);
        final String menu = "\nMENU\n1 - Submit documents (.docx or .doc)\n2 - Specify file path for output\n3 - Randomize by sentence\n4 - Randomize by pattern\n5 - Exit\n";
        System.out.println(divider + menu + divider);
    }

    private static String getUserSubmitDocumentCommandPrompt() {
        return "Specify the file path: ";
    }

    private static String handleUserSubmitDocumentCommand(String userInput, DocumentLibrary documentLibrary) {
        final int maximumDocumentsAllowed = 3;
        final String successMessage = "Document successfully extracted.";
        final String documentLibraryFullMessage = "Maximum documents added. Max allowed: " + maximumDocumentsAllowed + ".";
        final String unsuccessfulMessage = "Unable to add document.";

        if (documentLibrary.getCount() >= maximumDocumentsAllowed) {
            return documentLibraryFullMessage;
        }

        TextExtractorManager textExtractorManager = getTextExtractorManager(userInput);

        if (!textExtractorManager.isSuccess()) {
            return textExtractorManager.errorMessage();
        }

        boolean isSuccess = documentLibrary.addText(textExtractorManager.textExtractor().getDocumentAsString());
        if (!isSuccess) {
            return unsuccessfulMessage;
        }

        return successMessage;
    }

    private static TextExtractorManager getTextExtractorManager(String userInputFilePath) {
        final String[] expectedFormats = { ".docx", ".doc" };

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(expectedFormats[0]);
        for (int i = 1; i < expectedFormats.length; i++) {
            stringBuilder.append(", ");
            stringBuilder.append(expectedFormats[i]);
        }
        final String expectedFormatsMessage = stringBuilder.toString();

        final String noErrorMessage = "No errors. TextExtractor is ready.";
        final String incorrectFileFormatExceptionMessage = "Incorrect file format: " + userInputFilePath + ". Expected format(s): " + expectedFormatsMessage;
        final String nonExistentFileExceptionMessage = "File does not exist: " + userInputFilePath;
        final String textExtractorIOExceptionMessage = "I/O error occurred while loading the file: " + userInputFilePath;
        
        try {
            if (userInputFilePath.endsWith(expectedFormats[0])) {
                return new TextExtractorManager(new DocxTextExtractor(userInputFilePath), noErrorMessage);
            } else if (userInputFilePath.endsWith(expectedFormats[1])) {
                return new TextExtractorManager(new DocTextExtractor(userInputFilePath), noErrorMessage);
            } else {
                return new TextExtractorManager(null, incorrectFileFormatExceptionMessage);
            }
        } catch (IncorrectFileFormatException e) {
            return new TextExtractorManager(null, incorrectFileFormatExceptionMessage);
        } catch (NonExistentFileException e) {
            return new TextExtractorManager(null, nonExistentFileExceptionMessage);
        } catch (TextExtractorIOException e) {
            return new TextExtractorManager(null, textExtractorIOExceptionMessage);
        }
    }

    private static String getUserSpecifyOutputFilePathCommandPrompt() {
        return "Specify the file path: ";
    }

    private static String getUserSpecifyOutputFilePathCommandErrorMessage() {
        return "File path invalid. File format must be .txt";
    }

    private static boolean validateUserSpecifyOutputFilePathCommand(String userInput) {
        return userInput.endsWith(".txt");
    }

    private static String getUserSpecifyOutputFilePathCommandResult(String userFilePathInput) {
        final String resultsLocationMessage = "Randomization results will be written to this location: ";
        return resultsLocationMessage + userFilePathInput;
    }

    private static boolean handleUserRandomizeBySentenceCommand(DocumentLibrary documentLibrary, String outputFilePath) {
        SentenceRandomizer sentenceRandomizer = new SentenceRandomizerImpl(documentLibrary);
        List<String> combinations = sentenceRandomizer.generateCombinations();
        CombinationWriter combinationWriter = new CombinationWriterImpl(combinations);
        return combinationWriter.writeCombinationsToFile(outputFilePath);
    }

    private static String getUserRandomizeBySentenceCommandResult(boolean isSuccess) {
        final String successMessage = "Sentence randomization successful.";
        final String errorMessage = "Sentence randomization unsuccessful. Please check specified file path for output.";
        return isSuccess ? successMessage : errorMessage;
    }
}
