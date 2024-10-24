package randomizer.impl;

import exception.CombinationWriterIOException;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import randomizer.DocxCombinationWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public class DocxCombinationWriterImpl implements DocxCombinationWriter {
    private static final String DEFAULT_OUTPUT_FILE_PATH = "Associations.docx";
    private XWPFDocument document;
    private String filePath;

    public DocxCombinationWriterImpl() {
        this.filePath = DEFAULT_OUTPUT_FILE_PATH;
    }

    @Override
    public void setDocument(XWPFDocument document) {
        this.document = document;
    }

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getFilePath() {
        return this.filePath;
    }

    @Override
    public void writeCombinationsToFile() throws CombinationWriterIOException {
        try (OutputStream outputStream = new FileOutputStream(this.filePath)) {
            document.write(outputStream);
        } catch (IOException e) {
            throw new CombinationWriterIOException(e);
        }
    }
}
