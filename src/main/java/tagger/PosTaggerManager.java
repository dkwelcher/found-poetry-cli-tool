package tagger;

public record PosTaggerManager(PosTagger posTagger, String errorMessage) {

    public boolean isSuccess() {
        return this.posTagger != null;
    }
}
