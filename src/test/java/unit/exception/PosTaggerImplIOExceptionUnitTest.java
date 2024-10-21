package unit.exception;

import exception.PosTaggerIOException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;

public class PosTaggerIOExceptionUnitTest {
    @Test
    void testErrorMessage() {
        String filePath = "example/model/posmodel.bin";
        Throwable cause = new IOException("File not found");

        PosTaggerIOException exception = assertThrows(
                PosTaggerIOException.class,
                () -> { throw new PosTaggerIOException(filePath, cause); }
        );

        String expectedAnswer = "Failed to load POS model at example/model/posmodel.bin";
        assertEquals(expectedAnswer, exception.getMessage());
    }

    @Test
    void testExceptionCause() {
        String filePath = "example/model/posmodel.bin";
        Throwable cause = new IOException("File not found");

        PosTaggerIOException exception = assertThrows(
                PosTaggerIOException.class,
                () -> { throw new PosTaggerIOException(filePath, cause); }
        );

        assertSame(cause, exception.getCause());
    }
}
