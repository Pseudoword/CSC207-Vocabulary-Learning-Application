package entity;

public class Vocabulary {

    private final String word;
    private final String definition;
    private Boolean flagged;
    private Boolean incorrect;


    public Vocabulary(String word, String definition, Boolean incorrect) {
        this.word = word;
        this.definition = definition;
        this.incorrect = incorrect;
        this.flagged = false;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }

    public void markAsIncorrect() {
        this.incorrect = true;
    }

    public void markAsCorrect() {
        this.incorrect = false;
    }

    public boolean isIncorrect() {
        return incorrect;
    }

    public void setFlagged(Boolean flagged) {this.flagged = flagged;}

    public boolean getFlagged() {return flagged;}

}
