package randomizer;

import exception.CombinationWriterIOException;

import java.io.IOException;
import java.util.List;

public interface CombinationWriter {
    void setFilePath(String filePath);
    String getFilePath();
    void setCombinations(List<String> combinations);
    void writeCombinationsToFile() throws CombinationWriterIOException;
}
