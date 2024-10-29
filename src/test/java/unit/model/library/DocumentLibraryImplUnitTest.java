package unit.model.library;

import model.library.DocumentLibrary;
import model.library.impl.DocumentLibraryImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DocumentLibraryImplUnitTest {
    private static final String TEXT1 = "This is the first sentence. This is the second sentence. This is the third sentence.";
    private static final String TEXT2 = "THIS IS THE FIRST SENTENCE. THIS IS THE SECOND SENTENCE. THIS IS THE THIRD SENTENCE";

    @Test
    void testGetListOfDocumentsAsSentencesWhereListIsEmpty_returnsListOfStringArrayWithListSizeOfZero() {
        DocumentLibrary documentLibrary = new DocumentLibraryImpl();

        List<String[]> listOfTextsSplitIntoSentences = documentLibrary.getListOfDocumentsAsSentences();
        assertEquals(0, listOfTextsSplitIntoSentences.size());
    }

    @Test
    void testGetListOfDocumentsAsSentences_returnsListOfStringArrayWithExpectedArrayLength() {
        DocumentLibrary documentLibrary = new DocumentLibraryImpl();
        documentLibrary.add(TEXT1);

        List<String[]> listOfTextsSplitIntoSentences = documentLibrary.getListOfDocumentsAsSentences();
        assertEquals(3, listOfTextsSplitIntoSentences.get(0).length);
    }

    @Test
    void testGetListOfDocumentsAsSentences_returnsListOfStringArrayWithExpectedListSize() {
        DocumentLibrary documentLibrary = new DocumentLibraryImpl();
        documentLibrary.add(TEXT1);
        documentLibrary.add(TEXT2);

        List<String[]> listOfTextsSplitIntoSentences = documentLibrary.getListOfDocumentsAsSentences();
        assertEquals(2, listOfTextsSplitIntoSentences.size());
    }

    @Test
    void testGetListOfDocumentsAsTokens_returnsListOfStringArrayWithExpectedArrayLength() {
        DocumentLibrary documentLibrary = new DocumentLibraryImpl();
        documentLibrary.add(TEXT1);

        List<String[]> listOfTextsSplitIntoTokens = documentLibrary.getListOfDocumentsAsTokens();
        assertEquals(18, listOfTextsSplitIntoTokens.get(0).length);
    }

    @Test
    void testGetListOfDocumentsAsTokens_returnsListOfStringArrayWithExpectedListSize() {
        DocumentLibrary documentLibrary = new DocumentLibraryImpl();
        documentLibrary.add(TEXT1);
        documentLibrary.add(TEXT2);

        List<String[]> listOfTextsSplitIntoTokens = documentLibrary.getListOfDocumentsAsTokens();
        assertEquals(2, listOfTextsSplitIntoTokens.size());
    }
}
