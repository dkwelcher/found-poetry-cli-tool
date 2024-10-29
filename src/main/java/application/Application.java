package application;

import exception.CombinationWriterIOException;
import model.extractor.TextExtractor;
import model.extractor.TextExtractorManager;
import model.extractor.TextExtractorResult;
import model.library.DocumentLibrary;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import model.randomizer.DocxCombinationWriter;
import model.randomizer.DocxDocumentCreator;
import model.randomizer.PatternRandomizer;
import model.randomizer.SentenceRandomizer;
import model.tagger.PosTagger;
import utility.Command;
import utility.Message;

import java.util.*;

public class Application {
    private final PosTagger posTagger;
    private final DocumentLibrary documentLibrary;
    private final SentenceRandomizer sentenceRandomizer;
    private final PatternRandomizer patternRandomizer;
    private final DocxDocumentCreator documentCreator;
    private final DocxCombinationWriter combinationWriter;

    public Application(PosTagger posTagger,
                       DocumentLibrary documentLibrary,
                       SentenceRandomizer sentenceRandomizer,
                       PatternRandomizer patternRandomizer,
                       DocxDocumentCreator documentCreator,
                       DocxCombinationWriter combinationWriter) {

        this.posTagger = posTagger;
        this.documentLibrary = documentLibrary;
        this.sentenceRandomizer = sentenceRandomizer;
        this.patternRandomizer = patternRandomizer;
        this.documentCreator = documentCreator;
        this.combinationWriter = combinationWriter;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        display(Message.welcomeMessage());

        String userInput = "";

        while (!userInput.equalsIgnoreCase(Command.USER_EXIT_COMMAND)) {
            display(Message.menu());
            userInput = scanner.nextLine().trim();

            if (userInput.equals(Command.USER_SUBMIT_DOCUMENT_COMMAND)) {
                display(Message.getUserSubmitDocumentCommandPrompt());
                userInput = scanner.nextLine().trim();
                executeUserSubmitDocumentCommand(userInput);
            }

            if (userInput.equals(Command.USER_SPECIFY_OUTPUT_FILE_PATH_COMMAND)) {
                display(Message.getUserSpecifyOutputFilePathCommandPrompt());
                userInput = scanner.nextLine().trim();
                executeUserSpecifyOutputFilePathCommand(userInput);
            }

            if (userInput.equals(Command.USER_RANDOMIZE_BY_SENTENCE_COMMAND)) {
                executeUserRandomizeBySentenceCommand();
            }

            if (userInput.equals(Command.USER_RANDOMIZE_BY_PATTERN_COMMAND)) {
                display(Message.getUserRandomizeByPatternCommandMenu());
                userInput = scanner.nextLine().trim();
                executeUserRandomizeByPatternCommand(userInput);
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
        this.documentLibrary.add(textExtractor.getDocumentAsString());
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
        return filePath.endsWith(".docx");
    }

    private void executeUserRandomizeBySentenceCommand() {
        List<String> combinations = getSentenceCombinations();
        XWPFDocument document = getDocument(combinations);

        try {
            writeCombinationsToFile(document);
            display(Message.getUserRandomizeBySentenceCommandSuccess());
        } catch (CombinationWriterIOException e) {
            display(Message.getUserRandomizeBySentenceCommandError());
        }
    }

    private List<String> getSentenceCombinations() {
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

    private void executeUserRandomizeByPatternCommand(String userPOSPatternInput) {
        List<String> combinations = getPatternCombinations(userPOSPatternInput);
        XWPFDocument document = getDocument(combinations);

        try {
            writeCombinationsToFile(document);
            display(Message.getUserRandomizeByPatternCommandSuccess());
        } catch (CombinationWriterIOException e) {
            display(Message.getUserRandomizeByPatternCommandError());
        }
    }

    private List<String> getPatternCombinations(String pattern) {
        String[] tagClasses = getTagClasses(pattern);
        patternRandomizer.setTagClasses(tagClasses);
        Map<String, List<String>> tagsToTokens = getMapOfTokensToTags();
        patternRandomizer.setTagsToTokens(tagsToTokens);
        return patternRandomizer.generateCombinations();
    }

    private String[] getTagClasses(String pattern) {
        return pattern.split("\\s+");
    }

    private Map<String, List<String>> getMapOfTokensToTags() {
        Map<String, List<String>> tagsToTokens = new HashMap<>();
        List<String[]> listOfDocumentsAsTokens = documentLibrary.getListOfDocumentsAsTokens();

        for (String[] tokens : listOfDocumentsAsTokens) {
            String[] tags = posTagger.getTags(tokens);

            for (int i = 0; i < tags.length; i++) {
                List<String> tokensList = tagsToTokens.getOrDefault(tags[i], new ArrayList<>());
                tokensList.add(tokens[i]);
                tagsToTokens.put(tags[i], tokensList);
            }
        }
        return tagsToTokens;
    }
}
