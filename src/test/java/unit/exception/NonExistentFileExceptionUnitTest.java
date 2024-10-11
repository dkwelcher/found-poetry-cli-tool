package unit.exception;

import exception.NonExistentFileException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NonExistentFileExceptionUnitTest {
    @Test
    void testConstructorErrorMessage() {
        String filePath = "example/filethatdoesntexist.docx";

        NonExistentFileException exception = assertThrows(
                NonExistentFileException.class,
                () -> { throw new NonExistentFileException(filePath); }
        );

        String expectedAnswer = "File does not exist: example/filethatdoesntexist.docx";
        assertEquals(expectedAnswer, exception.getMessage());
    }
}
