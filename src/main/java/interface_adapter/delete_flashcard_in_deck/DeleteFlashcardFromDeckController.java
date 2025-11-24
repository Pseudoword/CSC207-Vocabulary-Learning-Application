package interface_adapter.delete_flashcard_in_deck;

import use_case.delete_flashcard_from_deck.DeleteFlashcardFromDeckInputData;
import use_case.delete_flashcard_from_deck.DeleteFlashcardFromDeckInteractor;

import java.util.ArrayList;

/**
 * The controller for the Delete Flashcard From Deck Use Case.
 */
public class DeleteFlashcardFromDeckController {

    private final DeleteFlashcardFromDeckInteractor deleteFlashcardFromDeckInteractor;

    public DeleteFlashcardFromDeckController(DeleteFlashcardFromDeckInteractor DeleteFlashcardFromDeckInteractor) {
        this.deleteFlashcardFromDeckInteractor = DeleteFlashcardFromDeckInteractor;
    }

    /**
     * Executes the Delete Flashcard From Deck Use Case.
     * @param deckTitle the title of the deck to delete from
     * @param word the word to be deleted
     */
    public void execute(String deckTitle, String word, ArrayList<String> vocabularies) {
        final DeleteFlashcardFromDeckInputData inputData = new DeleteFlashcardFromDeckInputData(deckTitle, word);
        deleteFlashcardFromDeckInteractor.execute(inputData);
    }
}
