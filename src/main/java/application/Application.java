package application;

import exception.TextExtractorIOException;
import extractor.TextExtractor;
import extractor.TextExtractorManager;
import extractor.impl.DocTextExtractor;
import extractor.impl.DocxTextExtractor;
import library.DocumentLibrary;
import org.apache.poi.ss.formula.functions.T;
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
    private static final String USER_SUBMIT_DOCUMENT_COMMAND = "1";
    private static final String USER_RANDOMIZE_BY_SENTENCE_COMMAND = "2";
    private static final String USER_RANDOMIZE_BY_PATTERN_COMMAND = "3";
    private static final String USER_EXIT_COMMAND = "4";

    public static void run() {
        PosTaggerManager posTaggerManager = getPosTaggerManager();

        if (!posTaggerManager.isSuccess()) {
            System.out.println(posTaggerManager.getErrorMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        String userInput = "";
        DocumentLibrary documentLibrary = new DocumentLibrary();

        displayWelcomeMessage();

        while (!userInput.equalsIgnoreCase(USER_EXIT_COMMAND)) {
            displayMenu();
            userInput = scanner.nextLine().trim();

            if (userInput.equals(USER_SUBMIT_DOCUMENT_COMMAND)) {
                displayDocumentPrompt();
                userInput = scanner.nextLine().trim();
                System.out.println(handleUserDocumentSubmission(userInput, documentLibrary));
            }

            if (userInput.equals(USER_RANDOMIZE_BY_SENTENCE_COMMAND)) {
                System.out.println("Not implemented");
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
        final String menu = "\nMENU\n1 - Submit documents (.docx or .doc)\n2 - Randomize by sentence\n3 - Randomize by pattern\n4 - Exit\n";
        System.out.println(divider + menu + divider);
    }

    private static void displayDocumentPrompt() {
        final String documentPrompt = "Specify the file path: ";
        System.out.println(documentPrompt);
    }

    private static String handleUserDocumentSubmission(String userInput, DocumentLibrary documentLibrary) {
        final String successMessage = "Document successfully extracted.";
        TextExtractorManager textExtractorManager = getTextExtractorManager(userInput);

        if (!textExtractorManager.isSuccess()) {
            return textExtractorManager.getErrorMessage();
        }

        documentLibrary.addText(textExtractorManager.getTextExtractor().getDocumentAsString());
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
}
