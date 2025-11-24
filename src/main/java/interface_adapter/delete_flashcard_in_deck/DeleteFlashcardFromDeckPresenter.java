package interface_adapter.delete_flashcard_in_deck;

import interface_adapter.ViewManagerModel;
import use_case.delete_flashcard_from_deck.DeleteFlashcardFromDeckOutputBoundary;
import use_case.delete_flashcard_from_deck.DeleteFlashcardFromDeckOutputData;

/**
 * The Presenter for the Delete Flashcard From Deck Use Case.
 */
public class DeleteFlashcardFromDeckPresenter {

    private final DeleteFlashcardFromDeckViewModel deleteFlashcardFromDeckViewModel;
    private final ViewManagerModel viewManagerModel;

    public DeleteFlashcardFromDeckPresenter(ViewManagerModel viewManagerModel,
                                            DeleteFlashcardFromDeckViewModel deleteFlashcardFromDeckViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.deleteFlashcardFromDeckViewModel = deleteFlashcardFromDeckViewModel;
    }

    public void prepareSuccessView(DeleteFlashcardFromDeckOutputData response) {
        final DeleteFlashcardFromDeckState state = deleteFlashcardFromDeckViewModel.getState();
        state.setSuccessMessage("Successfully deleted '" + response.getFlashcardWord() +
                "' from deck '" + response.getDeckTitle() + "'.");
        state.setError(null);
        state.setWord(""); // Clear the word input for the next entry

        this.deleteFlashcardFromDeckViewModel.setState(state);
        this.deleteFlashcardFromDeckViewModel.firePropertyChange("state");
    }
}
