package unit.model.extractor;

import exception.IncorrectFileFormatException;
import exception.NonExistentFileException;
import model.extractor.TextExtractor;
import model.extractor.impl.DocxTextExtractor;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DocxTextExtractorUnitTest {
    private static final String VALID_DOCUMENT = "..\\found-poetry-cli-tool\\src\\test\\resources\\testDocx1.docx";
    private static final String INCORRECT_FILE_FORMAT_DOCUMENT = "test.txt";
    private static final String NONEXISTENT_FILE_DOCUMENT = "..\\found-poetry-cli-tool\\src\\test\\resources\\doesntexist.docx";

    @Test
    void testDocumentWithIncorrectFileFormat_throwsIncorrectFileFormatException() {
        assertThrows(IncorrectFileFormatException.class, () -> new DocxTextExtractor(INCORRECT_FILE_FORMAT_DOCUMENT));
    }

    @Test
    void testDocumentThatDoesntExist_throwsNonExistentFileException() {
        assertThrows(NonExistentFileException.class, () -> new DocxTextExtractor(NONEXISTENT_FILE_DOCUMENT));
    }

    @Test
    void testGetDocumentAsString_returnsString() throws Exception {
        assertTrue(Files.exists(Paths.get(VALID_DOCUMENT)), "Valid document file path must exist for this test.");

        TextExtractor textExtractor =  new DocxTextExtractor(VALID_DOCUMENT);
        assertEquals("This is a test.\n", textExtractor.getDocumentAsString());
    }
}
