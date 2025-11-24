package entity;

public class Vocabulary {

    private final String word;
    private final String definition;
    private boolean flagged;


    public Vocabulary(String word, String definition, Boolean flagged) {
        this.word = word;
        this.definition = definition;
        this.flagged = flagged;
    }

    public Vocabulary(String word, String definition) {
        this.word = word;
        this.definition = definition;
        this.flagged = false;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setFlagged(boolean flagged) {this.flagged = flagged;}

    public boolean getFlagged() {return flagged;}

}