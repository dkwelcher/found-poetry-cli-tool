package service;

import domain.PosTagger;

public class PosTaggerManager {
    private final PosTagger posTagger;
    private final String errorMessage;

    public PosTaggerManager(PosTagger posTagger, String errorMessage) {
        this.posTagger = posTagger;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return this.posTagger != null;
    }

    public PosTagger getPosTagger() {
        return this.posTagger;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
