package model.extractor;

import exception.IncorrectFileFormatException;
import exception.NonExistentFileException;
import exception.TextExtractorIOException;
import model.extractor.impl.DocTextExtractor;
import model.extractor.impl.DocxTextExtractor;

public class TextExtractorManager {
    private static final String[] EXPECTED_FORMATS = { ".docx", ".doc" };
    private final String filePath;

    public TextExtractorManager(final String filePath) {
        this.filePath = filePath;
    }

    public TextExtractorResult getResult() {
        return instantiateTextExtractorResult();
    }

    private TextExtractorResult instantiateTextExtractorResult() {
        final String successMessage = "No errors. TextExtractor is ready.";
        final String incorrectFileFormatExceptionMessage = "Incorrect file format: " + filePath + ". Expected format(s): " + getExpectedFormatsMessage();
        final String nonExistentFileExceptionMessage = "File does not exist: " + filePath;
        final String textExtractorIOExceptionMessage = "I/O error occurred while loading the file: " + filePath;

        try {
            if (filePath.endsWith(EXPECTED_FORMATS[0])) {
                return new TextExtractorResult(new DocxTextExtractor(filePath), successMessage);
            } else if (filePath.endsWith(EXPECTED_FORMATS[1])) {
                return new TextExtractorResult(new DocTextExtractor(filePath), successMessage);
            } else {
                return new TextExtractorResult(null, incorrectFileFormatExceptionMessage);
            }
        } catch (IncorrectFileFormatException e) {
            return new TextExtractorResult(null, incorrectFileFormatExceptionMessage);
        } catch (NonExistentFileException e) {
            return new TextExtractorResult(null, nonExistentFileExceptionMessage);
        } catch (TextExtractorIOException e) {
            return new TextExtractorResult(null, textExtractorIOExceptionMessage);
        }
    }

    private String getExpectedFormatsMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(EXPECTED_FORMATS[0]);
        for (int i = 1; i < EXPECTED_FORMATS.length; i++) {
            stringBuilder.append(", ");
            stringBuilder.append(EXPECTED_FORMATS[i]);
        }
        return stringBuilder.toString();
    }
}
