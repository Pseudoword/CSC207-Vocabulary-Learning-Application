package interface_adapter.add_flashcard_to_deck;

import use_case.add_flashcard_to_deck.AddFlashcardToDeckInputBoundary;
import use_case.add_flashcard_to_deck.AddFlashcardToDeckInputData;

/**
 * The controller for the Add Flashcard To Deck Use Case.
 */
public class AddFlashcardToDeckController {

    private final AddFlashcardToDeckInputBoundary addFlashcardToDeckInteractor;

    public AddFlashcardToDeckController(AddFlashcardToDeckInputBoundary addFlashcardToDeckInteractor) {
        this.addFlashcardToDeckInteractor = addFlashcardToDeckInteractor;
    }

    /**
     * Executes the Add Flashcard To Deck Use Case.
     * @param deckTitle the title of the deck to add to
     * @param word the word to be added
     */
    public void execute(String deckTitle, String word) {
        final AddFlashcardToDeckInputData inputData = new AddFlashcardToDeckInputData(deckTitle, word);
        addFlashcardToDeckInteractor.execute(inputData);
    }
}