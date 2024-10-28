package model.tagger.impl;

import exception.*;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import model.tagger.PosTagger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class PosTaggerImpl implements PosTagger {
    private POSTaggerME tagger;

    @Override
    public void initializeTagger(String filePath) throws PosTaggerIOException {
        POSModel model;
        try (InputStream modelIn = new FileInputStream(filePath)) {
            model = new POSModel(modelIn);
        } catch (IOException e) {
            throw new PosTaggerIOException(filePath, e);
        }
        this.tagger = new POSTaggerME(model);
    }

    @Override
    public String[] getTags(String[] tokens) {
        if (this.tagger == null) {
            throw new TaggerNotInitializedException();
        }
        return this.tagger.tag(tokens);
    }
}