package use_case.delete_flashcard_from_deck;

/**
 * Output Data for the Delete Flashcard From Deck Use Case.
 */
public class DeleteFlashcardFromDeckOutputData {
    private final String deckTitle;
    private final String flashcardWord;

    public DeleteFlashcardFromDeckOutputData(String deckTitle, String flashcardWord) {
        this.deckTitle = deckTitle;
        this.flashcardWord = flashcardWord;
    }

    public String getDeckTitle() {
        return deckTitle;
    }

    public String getFlashcardWord() {
        return flashcardWord;
    }
}
