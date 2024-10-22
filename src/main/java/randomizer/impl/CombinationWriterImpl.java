package randomizer.impl;

import randomizer.CombinationWriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CombinationWriterImpl implements CombinationWriter {
    private final List<String> combinations;

    public CombinationWriterImpl(final List<String> combinations) {
        this.combinations = combinations;
    }

    public boolean writeCombinationsToFile(String filePath) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (String combination : combinations) {
                bufferedWriter.write(combination);
                bufferedWriter.newLine();
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
