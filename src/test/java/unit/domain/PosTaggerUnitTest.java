package unit.domain;

import domain.PosTagger;
import exception.IncorrectFileFormatException;
import exception.NonExistentFileException;
import exception.NullOrEmptyFilePathException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class PosTaggerUnitTest {
    private static final String VALID_FILE_PATH = "src/main/resources/en-pos-maxent.bin";
    private static final String INCORRECT_FORMAT_FILE_PATH = "src/main/resources/en-pos-maxent.txt";
    private static final String NONEXISTENT_FILE_PATH = "src/main/resources/fr-pos-maxent.bin";

    @BeforeEach
    void resetInstance() throws NoSuchFieldException, IllegalAccessException {
        var field = PosTagger.class.getDeclaredField("instance");
        field.setAccessible(true);
        field.set(null, null);
    }

    @Test
    void testEmptyFilePath() {
        assertThrows(NullOrEmptyFilePathException.class, () -> PosTagger.getInstance(" "));
    }

    @Test
    void testNullPath() {
        assertThrows(NullOrEmptyFilePathException.class, () -> PosTagger.getInstance(null));
    }

    @Test
    void testIncorrectFileFormat() {
        assertThrows(IncorrectFileFormatException.class, () -> PosTagger.getInstance(INCORRECT_FORMAT_FILE_PATH));
    }

    @Test
    void testNonExistentFile() {
        try (MockedStatic<Files> filesMock = Mockito.mockStatic(Files.class)) {
            filesMock.when(() -> Files.exists(Paths.get(NONEXISTENT_FILE_PATH))).thenReturn(false);
            assertThrows(NonExistentFileException.class, () -> PosTagger.getInstance(NONEXISTENT_FILE_PATH));
        }
    }

    @Test
    void testGetTagsWithValidModel() throws Exception {
        assertTrue(Files.exists(Paths.get(VALID_FILE_PATH)), "POS model file must exist for this test.");

        PosTagger posTagger = PosTagger.getInstance(VALID_FILE_PATH);
        String[] tokens = {"This", "is", "a", "test"};
        String[] expectedTags = {"DET", "VERB", "DET", "NOUN"};

        String[] actualTags = posTagger.getTags(tokens);

        assertNotNull(actualTags, "Tags should not be null");
        assertEquals(tokens.length, actualTags.length, "Each token should have a corresponding tag.");
        assertArrayEquals(expectedTags, actualTags, "The actual tags should match the expected tags.");
    }
}
