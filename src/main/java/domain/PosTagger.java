package domain;

import exception.IncorrectFileFormatException;
import exception.NonExistentFileException;
import exception.NullOrEmptyFilePathException;
import exception.PosTaggerIOException;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PosTagger {
    private static PosTagger instance;
    private POSTaggerME tagger;

    private PosTagger(String filePath) throws NullOrEmptyFilePathException, IncorrectFileFormatException, NonExistentFileException, PosTaggerIOException {
        validateFilePath(filePath);
        initializeTagger(filePath);
    }

    private static void validateFilePath(String filePath) throws NullOrEmptyFilePathException, IncorrectFileFormatException, NonExistentFileException {
        if (filePath == null || filePath.trim().isEmpty()) {
            throw new NullOrEmptyFilePathException((filePath));
        }

        final String expectedFormat = ".bin";
        if (!filePath.endsWith(expectedFormat)) {
            throw new IncorrectFileFormatException(filePath, expectedFormat);
        }

        if (!Files.exists(Paths.get(filePath))) {
            throw new NonExistentFileException(filePath);
        }
    }

    private void initializeTagger(String filePath) throws PosTaggerIOException {
        POSModel model;
        try (InputStream modelIn = new FileInputStream(filePath)) {
            model = new POSModel(modelIn);
        } catch (IOException e) {
            throw new PosTaggerIOException(filePath, e);
        }
        this.tagger = new POSTaggerME(model);
    }

    public static synchronized PosTagger getInstance(String filePath) throws NullOrEmptyFilePathException, IncorrectFileFormatException, NonExistentFileException, PosTaggerIOException {
        if (instance == null) {
            instance = new PosTagger(filePath);
        }
        return instance;
    }

    public String[] getTags(String[] tokens) {
        return this.tagger.tag(tokens);
    }
}