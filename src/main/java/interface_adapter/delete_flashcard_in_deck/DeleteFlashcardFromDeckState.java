package interface_adapter.delete_flashcard_in_deck;

/**
 * The state for the Delete Flashcard From Deck View Model.
 */
public class DeleteFlashcardFromDeckState {
    private String deckTitle = "";
    private String word = "";
    private String error = null;
    private String successMessage = null;

    public DeleteFlashcardFromDeckState(DeleteFlashcardFromDeckState copy) {
        this.deckTitle = copy.deckTitle;
        this.word = copy.word;
        this.error = copy.error;
        this.successMessage = copy.successMessage;
    }

    public DeleteFlashcardFromDeckState() {}

    public String getDeckTitle() { return deckTitle; }

    public void setDeckTitle(String deckTitle) { this.deckTitle = deckTitle; }

    public String getWord() { return word; }

    public void setWord(String word) { this.word = word; }

    public String getError() { return error; }

    public void setError(String error) { this.error = error; }

    public String getSuccessMessage() { return successMessage; }

    public void setSuccessMessage(String successMessage) { this.successMessage = successMessage; }
}