import application.Application;
import library.DocumentLibrary;
import library.impl.DocumentLibraryImpl;
import randomizer.CombinationWriter;
import randomizer.SentenceRandomizer;
import randomizer.impl.CombinationWriterImpl;
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
        CombinationWriter combinationWriter = new CombinationWriterImpl();

        new Application(posTaggerResult.posTagger(), documentLibrary, sentenceRandomizer, combinationWriter).run();
    }
}