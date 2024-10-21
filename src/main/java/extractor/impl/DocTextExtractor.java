package extractor.impl;

import exception.IncorrectFileFormatException;
import exception.NonExistentFileException;
import exception.TextExtractorIOException;
import extractor.TextExtractor;
import org.apache.poi.extractor.ExtractorFactory;
import org.apache.poi.extractor.POITextExtractor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DocTextExtractor implements TextExtractor {
    private static final String EXPECTED_FORMAT = ".doc";
    private final POITextExtractor extractor;

    public DocTextExtractor(String filePath) throws IncorrectFileFormatException, NonExistentFileException, TextExtractorIOException {
        if (!filePath.toLowerCase().endsWith(EXPECTED_FORMAT)) {
            throw new IncorrectFileFormatException(filePath, EXPECTED_FORMAT);
        }

        if (!Files.exists(Paths.get(filePath))) {
            throw new NonExistentFileException(filePath);
        }

        try (InputStream fileIn = new FileInputStream(filePath); POIFSFileSystem fileSystem = new POIFSFileSystem(fileIn)) {
            this.extractor = ExtractorFactory.createExtractor(fileSystem);
        } catch (IOException e) {
            throw new TextExtractorIOException(filePath, e);
        }
    }

    public String getDocumentAsString() {
        return this.extractor.getText();
    }
}
