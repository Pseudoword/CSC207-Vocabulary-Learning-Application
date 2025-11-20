package use_case.add_flashcard_to_deck;

/**
 * The Input Data for the Add Flashcard to Deck Use Case.
 */
public class AddFlashcardToDeckInputData {

    private final String deckName;
    private final String word;

    public AddFlashcardToDeckInputData(String deckName, String word) {
        this.deckName = deckName;
        this.word = word;
    }

    String getDeckName() {
        return deckName;
    }

    String getWord() {
        return word;
    }
}