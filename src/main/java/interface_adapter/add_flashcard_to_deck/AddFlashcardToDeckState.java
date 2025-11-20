package interface_adapter.add_flashcard_to_deck;

/**
 * The state for the Add Flashcard To Deck View Model.
 */
public class AddFlashcardToDeckState {
    private String deckTitle = "";
    private String word = "";
    private String error = null;
    private String successMessage = null;

    public AddFlashcardToDeckState(AddFlashcardToDeckState copy) {
        this.deckTitle = copy.deckTitle;
        this.word = copy.word;
        this.error = copy.error;
        this.successMessage = copy.successMessage;
    }

    public AddFlashcardToDeckState() {}

    public String getDeckTitle() { return deckTitle; }

    public void setDeckTitle(String deckTitle) { this.deckTitle = deckTitle; }

    public String getWord() { return word; }

    public void setWord(String word) { this.word = word; }

    public String getError() { return error; }

    public void setError(String error) { this.error = error; }

    public String getSuccessMessage() { return successMessage; }

    public void setSuccessMessage(String successMessage) { this.successMessage = successMessage; }
}