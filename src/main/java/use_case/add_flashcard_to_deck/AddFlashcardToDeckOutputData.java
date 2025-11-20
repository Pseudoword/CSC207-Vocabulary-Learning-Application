package use_case.add_flashcard_to_deck;

/**
 * Output Data for the Add Flashcard to Deck Use Case.
 */
public class AddFlashcardToDeckOutputData {

    private final String word;
    private final String definition;
    private final String deckTitle;

    public AddFlashcardToDeckOutputData(String word, String definition, String deckTitle) {
        this.word = word;
        this.definition = definition;
        this.deckTitle = deckTitle;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }

    public String getDeckTitle() {
        return deckTitle;
    }
}