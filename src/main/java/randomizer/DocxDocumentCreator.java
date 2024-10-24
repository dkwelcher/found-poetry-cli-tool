package randomizer;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.util.List;

public interface DocxDocumentCreator {
    void setCombinations(List<String> combinations);
    XWPFDocument create();
}
