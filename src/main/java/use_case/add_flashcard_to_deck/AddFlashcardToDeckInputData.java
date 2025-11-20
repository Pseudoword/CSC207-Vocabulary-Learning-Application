package use_case.add_flashcard_to_deck;

/**
 * The Input Data for the Add Flashcard to Deck Use Case.
 */
public class AddFlashcardToDeckInputData {

    private final String word;
    private final String deckTitle;

    public AddFlashcardToDeckInputData(String deckTitle, String word) {
        this.word = word;
        this.deckTitle = deckTitle;
    }

    public String getWord() {
        return word;
    }

    public String getDeckTitle() {
        return deckTitle;
    }
}