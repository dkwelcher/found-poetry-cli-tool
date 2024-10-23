import application.Application;
import exception.IncorrectFileFormatException;
import exception.NonExistentFileException;
import exception.PosTaggerIOException;
import library.DocumentLibrary;
import library.impl.DocumentLibraryImpl;
import randomizer.CombinationWriter;
import randomizer.SentenceRandomizer;
import randomizer.impl.CombinationWriterImpl;
import randomizer.impl.SentenceRandomizerImpl;
import tagger.PosTagger;
import tagger.PosTaggerResult;
import tagger.impl.PosTaggerImpl;

public class FoundPoetryCLIToolApplication {
    private static final String MODEL_FILE_PATH = "..\\found-poetry-cli-tool\\src\\main\\resources\\en-pos-maxent.bin";

    public static void main(String[] args) {
        PosTaggerResult posTaggerResult = getPosTaggerManager();

        if (!posTaggerResult.isSuccess()) {
            System.out.println(posTaggerResult.errorMessage());
            return;
        }

        DocumentLibrary documentLibrary = new DocumentLibraryImpl();
        SentenceRandomizer sentenceRandomizer = new SentenceRandomizerImpl();
        CombinationWriter combinationWriter = new CombinationWriterImpl();

        new Application(posTaggerResult.posTagger(), documentLibrary, sentenceRandomizer, combinationWriter).run();
    }

    private static PosTaggerResult getPosTaggerManager() {
        final String systemShutdownMessage = "System forced to close.";
        final String noErrorMessage = "No errors. PosTagger is ready.";
        final String incorrectFileFormatExceptionMessage = "Incorrect file format: " + MODEL_FILE_PATH + ". Expected format: .bin; " + systemShutdownMessage;
        final String nonExistentFileExceptionMessage = "File does not exist: " + MODEL_FILE_PATH + "; " + systemShutdownMessage;
        final String posTaggerIOExceptionMessage = "I/O error occurred while loading the file: " + MODEL_FILE_PATH + "; " + systemShutdownMessage;

        PosTagger posTagger;
        try {
            posTagger = new PosTaggerImpl(MODEL_FILE_PATH);
            return new PosTaggerResult(posTagger, noErrorMessage);
        } catch (IncorrectFileFormatException e) {
            return new PosTaggerResult(null, incorrectFileFormatExceptionMessage);
        } catch (NonExistentFileException e) {
            return new PosTaggerResult(null, nonExistentFileExceptionMessage);
        } catch (PosTaggerIOException e) {
            return new PosTaggerResult(null, posTaggerIOExceptionMessage);
        }
    }
}
