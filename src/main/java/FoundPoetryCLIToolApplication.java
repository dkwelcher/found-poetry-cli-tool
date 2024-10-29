import application.Application;
import config.ModelFilePathConfig;
import exception.IncorrectFileFormatException;
import exception.NonExistentFileException;
import exception.NullOrEmptyFilePathException;
import exception.PosTaggerIOException;
import model.library.DocumentLibrary;
import model.library.impl.DocumentLibraryImpl;
import model.randomizer.DocxCombinationWriter;
import model.randomizer.DocxDocumentCreator;
import model.randomizer.PatternRandomizer;
import model.randomizer.SentenceRandomizer;
import model.randomizer.impl.DocxCombinationWriterImpl;
import model.randomizer.impl.DocxDocumentCreatorImpl;
import model.randomizer.impl.PatternRandomizerImpl;
import model.randomizer.impl.SentenceRandomizerImpl;
import model.tagger.PosTagger;
import model.tagger.impl.PosTaggerImpl;
import utility.Message;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FoundPoetryCLIToolApplication {
    private static final String MODEL_FILE_PATH = ModelFilePathConfig.getProperty("MODEL_FILE_PATH");
    private static final String EXPECTED_FORMAT = ".bin";

    public static void main(String[] args) {
        PosTagger posTagger;
        try {
            posTagger = initPosTagger();
        } catch (NullOrEmptyFilePathException e) {
            displayErrorMessage(Message.taggerNullOrEmptyFilePathExceptionMessage());
            return;
        } catch (IncorrectFileFormatException e) {
            displayErrorMessage(Message.taggerIncorrectFileFormatExceptionMessage());
            return;
        } catch (NonExistentFileException e) {
            displayErrorMessage(Message.taggerNonExistentFileException());
            return;
        } catch (PosTaggerIOException e) {
            displayErrorMessage(Message.PosTaggerIOExceptionMessage(MODEL_FILE_PATH, e));
            return;
        }

        DocumentLibrary documentLibrary = new DocumentLibraryImpl();
        SentenceRandomizer sentenceRandomizer = new SentenceRandomizerImpl();
        PatternRandomizer patternRandomizer = new PatternRandomizerImpl();
        DocxDocumentCreator documentCreator = new DocxDocumentCreatorImpl();
        DocxCombinationWriter combinationWriter = new DocxCombinationWriterImpl();

        new Application(posTagger, documentLibrary, sentenceRandomizer, patternRandomizer, documentCreator, combinationWriter).run();
    }

    private static void displayErrorMessage(String errorMessage) {
        System.out.println(Message.systemShutdownMessage());
        System.err.println(errorMessage);
    }

    private static PosTagger initPosTagger() throws NullOrEmptyFilePathException,
                                                    IncorrectFileFormatException,
                                                    NonExistentFileException,
                                                    PosTaggerIOException {

        if (MODEL_FILE_PATH == null || MODEL_FILE_PATH.trim().isEmpty()) {
            throw new NullOrEmptyFilePathException((MODEL_FILE_PATH));
        }

        if (!MODEL_FILE_PATH.endsWith(EXPECTED_FORMAT)) {
            throw new IncorrectFileFormatException(MODEL_FILE_PATH, EXPECTED_FORMAT);
        }

        if (!Files.exists(Paths.get(MODEL_FILE_PATH))) {
            throw new NonExistentFileException(MODEL_FILE_PATH);
        }

        PosTagger posTagger = new PosTaggerImpl();
        posTagger.initializeTagger(MODEL_FILE_PATH);
        return posTagger;
    }
}