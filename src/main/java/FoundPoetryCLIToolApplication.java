import application.Application;
import library.DocumentLibrary;
import library.impl.DocumentLibraryImpl;
import randomizer.DocxCombinationWriter;
import randomizer.DocxDocumentCreator;
import randomizer.PatternRandomizer;
import randomizer.SentenceRandomizer;
import randomizer.impl.DocxCombinationWriterImpl;
import randomizer.impl.DocxDocumentCreatorImpl;
import randomizer.impl.PatternRandomizerImpl;
import randomizer.impl.SentenceRandomizerImpl;
import tagger.PosTaggerManager;
import tagger.PosTaggerResult;

public class FoundPoetryCLIToolApplication {
    private static final String MODEL_FILE_PATH = "..\\found-poetry-cli-tool\\src\\main\\resources\\en-pos-maxent.bin";

    public static void main(String[] args) {
        PosTaggerResult posTaggerResult = new PosTaggerManager(MODEL_FILE_PATH).getResult();

        if (!posTaggerResult.isSuccess()) {
            System.out.println(posTaggerResult.errorMessage());
            return;
        }

        DocumentLibrary documentLibrary = new DocumentLibraryImpl();
        SentenceRandomizer sentenceRandomizer = new SentenceRandomizerImpl();
        PatternRandomizer patternRandomizer = new PatternRandomizerImpl();
        DocxDocumentCreator documentCreator = new DocxDocumentCreatorImpl();
        DocxCombinationWriter combinationWriter = new DocxCombinationWriterImpl();

        new Application(posTaggerResult.posTagger(), documentLibrary, sentenceRandomizer, patternRandomizer, documentCreator, combinationWriter).run();
    }
}