package entity;
import java.util.ArrayList;
import java.util.List;

public class Deck {

    private String title;
    private String description;
    private List<Vocabulary> vocabularies;
    private boolean quizTaken;

    public Deck(String title, String description) {
        this.title = title;
        this.description = description;
        this.vocabularies = new ArrayList<>();
        this.quizTaken = false;
    }

    public String getTitle() { return title;}

    public String getDescription() { return description; }

    public List<Vocabulary> getVocabularies() { return vocabularies; }

    public boolean isMastered() {
        if (!quizTaken || vocabularies.isEmpty()) {
            return false;
        }
        for (Vocabulary vocab : vocabularies) {
            if (vocab.isIncorrect()) {
                return false;
            }
        }
        return true;
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
}
