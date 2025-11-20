package interface_adapter.add_flashcard_to_deck;

/**
 * The state for the Add Flashcard To Deck View Model.
 */
public class AddFlashcardToDeckState {
    private String deckName = "";
    private String word = "";
    private String error = null;
    private String successMessage = null;

    public AddFlashcardToDeckState(AddFlashcardToDeckState copy) {
        this.deckName = copy.deckName;
        this.word = copy.word;
        this.error = copy.error;
        this.successMessage = copy.successMessage;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public AddFlashcardToDeckState() {}

    public String getDeckName() { return deckName; }

    public void setDeckName(String deckName) { this.deckName = deckName; }

    public String getWord() { return word; }

    public void setWord(String word) { this.word = word; }

    public String getError() { return error; }

    public void setError(String error) { this.error = error; }

    public String getSuccessMessage() { return successMessage; }

    public void setSuccessMessage(String successMessage) { this.successMessage = successMessage; }
}