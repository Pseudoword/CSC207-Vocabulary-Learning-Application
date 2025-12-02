package entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Deck {

    private String title;
    private String description;
    private List<Vocabulary> vocabularies;
    private boolean quizTaken;
    private boolean lastAttemptAllCorrect;

    public Deck(String title, String description) {
        this.title = title;
        this.description = description;
        this.vocabularies = new ArrayList<>();
        this.quizTaken = false;
        this.lastAttemptAllCorrect = false;
    }

    public String getTitle() { return title;}

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public List<Vocabulary> getVocabularies() { return vocabularies; }

    public boolean isMastered() {
        return quizTaken && lastAttemptAllCorrect;
    }

    public void setLastAttemptAllCorrect(boolean allCorrect) {
        this.lastAttemptAllCorrect = allCorrect;
        this.quizTaken = true; // mark that a quiz was taken
    }

    public void markQuizTaken() {
        this.quizTaken = true;
    }

    public boolean hasBeenAttempted() {
        return quizTaken;
    }

    public void addWord(Vocabulary word) {
        vocabularies.add(word);
    }

    public boolean isEmpty() {
        return vocabularies.isEmpty();
    }

    public void removeWord(Vocabulary word) {
        vocabularies.remove(word);
    }

    public boolean containsWord(String word) {
        for (Vocabulary v: vocabularies) {
            if (Objects.equals(v.getWord(), word)) {
                return true;
            }
        }
        return false;
    }
}
