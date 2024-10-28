import application.Application;
import config.ModelFilePathConfig;
import model.library.DocumentLibrary;
import model.library.impl.DocumentLibraryImpl;
import model.randomizer.DocxCombinationWriter;
import model.randomizer.DocxDocumentCreator;
import model.randomizer.PatternRandomizer;
import model.randomizer.SentenceRandomizer;
import model.randomizer.impl.DocxCombinationWriterImpl;
import model.randomizer.impl.DocxDocumentCreatorImpl;
import model.randomizer.impl.PatternRandomizerImpl;
import model.randomizer.impl.SentenceRandomizerImpl;
import model.tagger.PosTaggerManager;
import model.tagger.PosTaggerResult;

public class FoundPoetryCLIToolApplication {
    private static final String MODEL_FILE_PATH = ModelFilePathConfig.getProperty("MODEL_FILE_PATH");

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