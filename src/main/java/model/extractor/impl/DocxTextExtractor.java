package model.extractor.impl;

import exception.IncorrectFileFormatException;
import exception.NonExistentFileException;
import exception.TextExtractorIOException;
import model.extractor.TextExtractor;
import org.apache.poi.extractor.POITextExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DocxTextExtractor implements TextExtractor {
    private static final String EXPECTED_FORMAT = ".docx";
    private final POITextExtractor extractor;

    public DocxTextExtractor(String filePath) throws IncorrectFileFormatException, NonExistentFileException, TextExtractorIOException {
        if (!filePath.toLowerCase().endsWith(EXPECTED_FORMAT)) {
            throw new IncorrectFileFormatException(filePath, EXPECTED_FORMAT);
        }

        if (!Files.exists(Paths.get(filePath))) {
            throw new NonExistentFileException(filePath);
        }

        try (InputStream fileIn = new FileInputStream(filePath); XWPFDocument document = new XWPFDocument(fileIn)) {
            this.extractor = new XWPFWordExtractor(document);
        } catch (IOException e) {
            throw new TextExtractorIOException(filePath, e);
        }
    }

    public String getDocumentAsString() {
        return this.extractor.getText();
    }
}
