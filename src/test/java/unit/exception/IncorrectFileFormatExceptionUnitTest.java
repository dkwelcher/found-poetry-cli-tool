package unit.exception;

import exception.IncorrectFileFormatException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IncorrectFileFormatExceptionUnitTest {
    @Test
    void testSingleFormatConstructorErrorMessage() {
        String filePath = "example/singleformat/test.csv";
        String expectedFormat = ".docx";

        IncorrectFileFormatException exception = assertThrows(
                IncorrectFileFormatException.class,
                () -> { throw new IncorrectFileFormatException(filePath, expectedFormat); }
        );

        String expectedAnswer = "Incorrect file format. Expected format: .docx; Provided file: example/singleformat/test.csv";
        assertEquals(expectedAnswer, exception.getMessage());
    }

    @Test
    void testMultipleFormatConstructorErrorMessage() {
        String filePath = "example/multipleformat/test.txt";
        String[] expectedFormats = { ".docx", ".doc" };

        IncorrectFileFormatException exception = assertThrows(
                IncorrectFileFormatException.class,
                () -> { throw new IncorrectFileFormatException(filePath, expectedFormats); }
        );

        String expectedAnswer = "Incorrect file format. Expected one of the following format(s): .docx, .doc; Provided file: example/multipleformat/test.txt";
        assertEquals(expectedAnswer, exception.getMessage());
    }

    @Test
    void testMultipleFormatConstructorWithSingleFormatInArrayErrorMessage() {
        String filePath = "example/multipleformat/test.docx";
        String[] expectedFormats = { ".bin" };

        IncorrectFileFormatException exception = assertThrows(
                IncorrectFileFormatException.class,
                () -> { throw new IncorrectFileFormatException(filePath, expectedFormats); }
        );

        String expectedAnswer = "Incorrect file format. Expected one of the following format(s): .bin; Provided file: example/multipleformat/test.docx";
        assertEquals(expectedAnswer, exception.getMessage());
    }
}
