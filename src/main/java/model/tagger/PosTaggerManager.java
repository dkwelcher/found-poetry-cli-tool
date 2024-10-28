package model.tagger;

import exception.IncorrectFileFormatException;
import exception.NonExistentFileException;
import exception.PosTaggerIOException;
import model.tagger.impl.PosTaggerImpl;

public class PosTaggerManager {
    private final String filePath;

    public PosTaggerManager(final String filePath) {
        this.filePath = filePath;
    }

    public PosTaggerResult getResult() {
        return getPosTaggerResult();
    }

    private PosTaggerResult getPosTaggerResult() {
        final String systemShutdownMessage = "System forced to close.";
        final String noErrorMessage = "No errors. PosTagger is ready.";
        final String incorrectFileFormatExceptionMessage = "Incorrect file format: " + this.filePath + ". Expected format: .bin; " + systemShutdownMessage;
        final String nonExistentFileExceptionMessage = "File does not exist: " + this.filePath + "; " + systemShutdownMessage;
        final String posTaggerIOExceptionMessage = "I/O error occurred while loading the file: " + this.filePath + "; " + systemShutdownMessage;

        PosTagger posTagger;
        try {
            posTagger = new PosTaggerImpl(this.filePath);
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
