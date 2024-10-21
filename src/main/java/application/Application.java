package application;

import domain.PosTagger;
import exception.IncorrectFileFormatException;
import exception.NonExistentFileException;
import exception.PosTaggerIOException;
import service.PosTaggerManager;

public class Application {
    private static final String MODEL_FILE_PATH = "..\\found-poetry-cli-tool\\src\\main\\resources\\en-pos-maxent.bin";
    private static final String USER_EXIT_COMMAND = "exit";

    public static void run() {
        PosTaggerManager posTaggerManager = initPosTagger();

        if (!posTaggerManager.isSuccess()) {
            System.out.println(posTaggerManager.getErrorMessage());
            return;
        }

        PosTagger posTagger = posTaggerManager.getPosTagger();
        System.out.println("Success");
    }

    private static PosTaggerManager initPosTagger() {
        final String systemShutdownMessage = "System forced to close.";
        final String noErrorMessage = "No errors. PosTagger is ready.";
        final String incorrectFileFormatExceptionMessage = "Incorrect file format: " + MODEL_FILE_PATH + ". Expected format: .bin; " + systemShutdownMessage;
        final String nonExistentFileExceptionMessage = "File does not exist: " + MODEL_FILE_PATH + "; " + systemShutdownMessage;
        final String posTaggerIOExceptionMessage = "I/O error occurred while loading the file: " + MODEL_FILE_PATH + "; " + systemShutdownMessage;

        PosTagger posTagger;
        try {
            posTagger = PosTagger.getInstance(Application.MODEL_FILE_PATH);
            return new PosTaggerManager(posTagger, noErrorMessage);
        } catch (IncorrectFileFormatException e) {
            return new PosTaggerManager(null, incorrectFileFormatExceptionMessage);
        } catch (NonExistentFileException e) {
            return new PosTaggerManager(null, nonExistentFileExceptionMessage);
        } catch (PosTaggerIOException e) {
            return new PosTaggerManager(null, posTaggerIOExceptionMessage);
        }
    }
}
