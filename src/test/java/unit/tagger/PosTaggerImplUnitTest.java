package unit.tagger;

import tagger.impl.PosTaggerImpl;
import exception.IncorrectFileFormatException;
import exception.NonExistentFileException;
import exception.NullOrEmptyFilePathException;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class PosTaggerImplUnitTest {
    private static final String VALID_FILE_PATH = "src/main/resources/en-pos-maxent.bin";
    private static final String INCORRECT_FORMAT_FILE_PATH = "src/main/resources/en-pos-maxent.txt";
    private static final String NONEXISTENT_FILE_PATH = "src/main/resources/fr-pos-maxent.bin";

    @Test
    void testEmptyFilePath() {
        assertThrows(NullOrEmptyFilePathException.class, () -> new PosTaggerImpl(" "));
    }

    @Test
    void testNullPath() {
        assertThrows(NullOrEmptyFilePathException.class, () -> new PosTaggerImpl(null));
    }

    @Test
    void testIncorrectFileFormat() {
        assertThrows(IncorrectFileFormatException.class, () -> new PosTaggerImpl(INCORRECT_FORMAT_FILE_PATH));
    }

    @Test
    void testNonExistentFile() {
        try (MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class)) {
            filesMock.when(() -> Files.exists(Paths.get(NONEXISTENT_FILE_PATH))).thenReturn(false);
            assertThrows(NonExistentFileException.class, () -> new PosTaggerImpl(NONEXISTENT_FILE_PATH));
        }
    }

    @Test
    void testGetTagsWithValidModel() throws Exception {
        assertTrue(Files.exists(Paths.get(VALID_FILE_PATH)), "POS model file must exist for this test.");

        PosTaggerImpl posTaggerImpl = new PosTaggerImpl(VALID_FILE_PATH);
        String[] tokens = {"This", "is", "a", "test"};
        String[] expectedTags = {"DET", "VERB", "DET", "NOUN"};

        String[] actualTags = posTaggerImpl.getTags(tokens);

        assertNotNull(actualTags, "Tags should not be null");
        assertEquals(tokens.length, actualTags.length, "Each token should have a corresponding tag.");
        assertArrayEquals(expectedTags, actualTags, "The actual tags should match the expected tags.");
    }
}
