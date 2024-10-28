package model.tagger;

public record PosTaggerResult(PosTagger posTagger, String errorMessage) {

    public boolean isSuccess() {
        return this.posTagger != null;
    }
}
