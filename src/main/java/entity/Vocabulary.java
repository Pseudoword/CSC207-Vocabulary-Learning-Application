package entity;

public class Vocabulary {

    private final String word;
    private final String definition;
    private Boolean flagged;


    public Vocabulary(String word, String definition, Boolean flagged) {
        this.word = word;
        this.definition = definition;
        this.flagged = flagged;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setFlagged(Boolean flagged) {this.flagged = flagged;}

    public Boolean getFlagged() {return flagged;}

}