package application;

import exception.CombinationWriterIOException;
import extractor.TextExtractor;
import extractor.TextExtractorManager;
import extractor.TextExtractorResult;
import library.DocumentLibrary;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import randomizer.DocxCombinationWriter;
import randomizer.DocxDocumentCreator;
import randomizer.SentenceRandomizer;
import tagger.PosTagger;
import utility.Message;

import java.util.List;
import java.util.Scanner;

public class Application {
    private static final String USER_SUBMIT_DOCUMENT_COMMAND = "1";
    private static final String USER_SPECIFY_OUTPUT_FILE_PATH_COMMAND = "2";
    private static final String USER_RANDOMIZE_BY_SENTENCE_COMMAND = "3";
    private static final String USER_RANDOMIZE_BY_PATTERN_COMMAND = "4";
    private static final String USER_EXIT_COMMAND = "5";
    private final PosTagger posTagger;
    private final DocumentLibrary documentLibrary;
    private final SentenceRandomizer sentenceRandomizer;
    private final DocxDocumentCreator documentCreator;
    private final DocxCombinationWriter combinationWriter;

    public Application(PosTagger posTagger,
                       DocumentLibrary documentLibrary,
                       SentenceRandomizer sentenceRandomizer,
                       DocxDocumentCreator documentCreator,
                       DocxCombinationWriter combinationWriter) {

        this.posTagger = posTagger;
        this.documentLibrary = documentLibrary;
        this.sentenceRandomizer = sentenceRandomizer;
        this.documentCreator = documentCreator;
        this.combinationWriter = combinationWriter;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        display(Message.welcomeMessage());

        String userInput = "";

        while (!userInput.equalsIgnoreCase(USER_EXIT_COMMAND)) {
            display(Message.menu());
            userInput = scanner.nextLine().trim();

            if (userInput.equals(USER_SUBMIT_DOCUMENT_COMMAND)) {
                display(Message.getUserSubmitDocumentCommandPrompt());
                userInput = scanner.nextLine().trim();
                executeUserSubmitDocumentCommand(userInput);
            }

            if (userInput.equals(USER_SPECIFY_OUTPUT_FILE_PATH_COMMAND)) {
                display(Message.getUserSpecifyOutputFilePathCommandPrompt());
                userInput = scanner.nextLine().trim();
                executeUserSpecifyOutputFilePathCommand(userInput);
            }

            if (userInput.equals(USER_RANDOMIZE_BY_SENTENCE_COMMAND)) {
                executeUserRandomizeBySentenceCommand();
            }

            if (userInput.equals(USER_RANDOMIZE_BY_PATTERN_COMMAND)) {
                executeUserRandomizeByPatternCommand();
            }
        }
        scanner.close();
    }

    private void display(String message) {
        System.out.println(message);
    }

    private void executeUserSubmitDocumentCommand(String userFilePathInput) {
        TextExtractorResult textExtractorResult = getTextExtractorResult(userFilePathInput);

        if (!textExtractorResult.isSuccess()) {
            display(textExtractorResult.errorMessage());
            return;
        }

        addTextToDocumentLibrary(textExtractorResult.textExtractor());
        display(Message.getUserSubmitDocumentCommandSuccess());
    }

    private TextExtractorResult getTextExtractorResult(String filePath) {
        return new TextExtractorManager(filePath).getResult();
    }

    private void addTextToDocumentLibrary(TextExtractor textExtractor) {
        this.documentLibrary.addText(textExtractor.getDocumentAsString());
    }

    private void executeUserSpecifyOutputFilePathCommand(String userFilePathInput) {
        if (!isFilePathFileFormatValid(userFilePathInput)) {
            display(Message.getUserSpecifyOutputFilePathCommandError());
            return;
        }

        this.combinationWriter.setFilePath(userFilePathInput);
        display(Message.getUserSpecifyOutputFilePathCommandResult(userFilePathInput));
    }

    private boolean isFilePathFileFormatValid(String filePath) {
        return filePath.endsWith(".txt");
    }

    private void executeUserRandomizeBySentenceCommand() {
        List<String> combinations = getCombinations();
        XWPFDocument document = getDocument(combinations);

        try {
            writeCombinationsToFile(document);
            display(Message.getUserRandomizeBySentenceCommandSuccess());
        } catch (CombinationWriterIOException e) {
            display(Message.getUserRandomizeBySentenceCommandError());
        }
    }

    private List<String> getCombinations() {
        this.sentenceRandomizer.setListOfSentenceArrays(this.documentLibrary);
        return sentenceRandomizer.generateCombinations();
    }

    private XWPFDocument getDocument(List<String> combinations) {
        documentCreator.setCombinations(combinations);
        return documentCreator.create();
    }

    private void writeCombinationsToFile(XWPFDocument document) throws CombinationWriterIOException {
        combinationWriter.setDocument(document);
        combinationWriter.writeCombinationsToFile();
    }

    private void executeUserRandomizeByPatternCommand() {
        display(Message.getUserRandomizeByPatternCommandMenu());
    }
}
