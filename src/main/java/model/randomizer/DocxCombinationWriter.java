package model.randomizer;

import exception.CombinationWriterIOException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

public interface DocxCombinationWriter {
    void setDocument(XWPFDocument document);
    void setFilePath(String filePath);
    String getFilePath();
    void writeCombinationsToFile() throws CombinationWriterIOException;
}
