package entity;
import java.util.ArrayList;
import java.util.List;

public class Deck {

    private String title;
    private String description;
    private List<Vocabulary> vocabularies;
    private boolean mastered;

    public Deck(String title, String description) {
        this.title = title;
        this.description = description;
        this.vocabularies = new ArrayList<>();
        this.mastered = false;
    }

    public String getTitle() { return title;}

    public String getDescription() { return description; }

    public List<Vocabulary> getVocabularies() { return new ArrayList<>(vocabularies); }

    public boolean isMastered() { return mastered; }

    public void addWord(Vocabulary word) {
        vocabularies.add(word);
    }

    public void removeWord(Vocabulary word) {
        vocabularies.remove(word);
    }

    public boolean containsWord(String word) {
        for (Vocabulary v : vocabularies) {
            if (v.getWord().equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }

    public void markAsMastered() {
        this.mastered = true;
    }

    public void unmarkAsMastered() {
        this.mastered = false;
    }

}
