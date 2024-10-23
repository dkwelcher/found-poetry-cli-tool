package randomizer.impl;

import exception.CombinationWriterIOException;
import exception.NullOrEmptyFilePathException;
import randomizer.CombinationWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CombinationWriterImpl implements CombinationWriter {
    private static final String DEFAULT_OUTPUT_FILE_PATH = "associations.txt";
    private List<String> combinations;
    private String filePath;

    public CombinationWriterImpl() {
        this.combinations = new ArrayList<>();
        this.filePath = DEFAULT_OUTPUT_FILE_PATH;
    }

    public void setCombinations(List<String> combinations) {
        this.combinations = combinations;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void writeCombinationsToFile() throws CombinationWriterIOException {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.filePath))) {
            for (String combination : combinations) {
                bufferedWriter.write(combination);
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            throw new CombinationWriterIOException(e);
        }
    }
}
