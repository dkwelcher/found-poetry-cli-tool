package randomizer;

import exception.CombinationWriterIOException;

import java.io.IOException;
import java.util.List;

public interface DocxCombinationWriter {
    void setFilePath(String filePath);
    void writeCombinationsToFile() throws CombinationWriterIOException;
}
