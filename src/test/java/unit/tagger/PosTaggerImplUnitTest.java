package unit.tagger;

import exception.PosTaggerIOException;
import exception.TaggerNotInitializedException;
import model.tagger.PosTagger;
import model.tagger.impl.PosTaggerImpl;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class PosTaggerImplUnitTest {
    private static final String VALID_FILE_PATH = "..\\found-poetry-cli-tool\\src\\main\\resources\\en-pos-maxent.bin";
    private static final String INVALID_FILE_PATH = "..\\found-poetry-cli-tool\\src\\main\\resources\\fr-pos-maxent.bin";

    @Test
    void testModelFilePathExists() {
        assertTrue(Files.exists(Paths.get(VALID_FILE_PATH)), "POS model file must exist for this test.");
    }

    @Test
    void testInitializeTaggerWithPosTaggerIOException() {
        PosTagger posTagger = new PosTaggerImpl();
        assertThrows(PosTaggerIOException.class, () -> posTagger.initializeTagger(INVALID_FILE_PATH));
    }

    @Test
    void testGetTagsWithNotInitializedTagger() {
        PosTagger posTagger = new PosTaggerImpl();
        String[] tokens = {"This", "is", "a", "test"};
        assertThrows(TaggerNotInitializedException.class, () -> posTagger.getTags(tokens));
    }

    @Test
    void testGetTagsWithValidModel() throws PosTaggerIOException, TaggerNotInitializedException {
        PosTagger posTagger = new PosTaggerImpl();
        posTagger.initializeTagger(VALID_FILE_PATH);
        String[] tokens = {"This", "is", "a", "test"};
        String[] expectedTags = {"DET", "VERB", "DET", "NOUN"};

        String[] actualTags = posTagger.getTags(tokens);

        assertNotNull(actualTags, "Tags should not be null");
        assertEquals(tokens.length, actualTags.length, "Each token should have a corresponding tag.");
        assertArrayEquals(expectedTags, actualTags, "The actual tags should match the expected tags.");
    }
}
