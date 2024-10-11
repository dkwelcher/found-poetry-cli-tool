package unit.exception;

import exception.NullOrEmptyFilePathException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NullOrEmptyFilePathExceptionUnitTest {
    @Test
    void testEmptyPathErrorMessage() {
        String filePath = "";

        NullOrEmptyFilePathException exception = assertThrows(
                NullOrEmptyFilePathException.class,
                () -> { throw new NullOrEmptyFilePathException(filePath); }
        );

        String expectedAnswer = "Null or empty file path provided: ";
        assertEquals(expectedAnswer, exception.getMessage());
    }

    @Test
    void testNullErrorMessage() {
        String filePath = null;

        NullOrEmptyFilePathException exception = assertThrows(
                NullOrEmptyFilePathException.class,
                () -> { throw new NullOrEmptyFilePathException(filePath); }
        );

        String expectedAnswer = "Null or empty file path provided: null";
        assertEquals(expectedAnswer, exception.getMessage());
    }
}
