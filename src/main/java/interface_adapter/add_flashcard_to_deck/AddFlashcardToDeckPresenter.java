package interface_adapter.add_flashcard_to_deck;

import interface_adapter.ViewManagerModel;
import use_case.add_flashcard_to_deck.AddFlashcardToDeckOutputBoundary;
import use_case.add_flashcard_to_deck.AddFlashcardToDeckOutputData;

/**
 * The Presenter for the Add Flashcard To Deck Use Case.
 */
public class AddFlashcardToDeckPresenter implements AddFlashcardToDeckOutputBoundary {

    private final AddFlashcardToDeckViewModel addFlashcardToDeckViewModel;
    private final ViewManagerModel viewManagerModel;

    public AddFlashcardToDeckPresenter(ViewManagerModel viewManagerModel,
                                       AddFlashcardToDeckViewModel addFlashcardToDeckViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.addFlashcardToDeckViewModel = addFlashcardToDeckViewModel;
    }

    @Override
    public void prepareSuccessView(AddFlashcardToDeckOutputData response) {
        final AddFlashcardToDeckState state = addFlashcardToDeckViewModel.getState();
        state.setSuccessMessage("Successfully added '" + response.getWord() + "' to deck.\n" +
                "Definition: '" + response.getDefinition() + "'");
        state.setError(null);
        state.setWord(""); // Clear the word input for the next entry

        this.addFlashcardToDeckViewModel.setState(state);
        this.addFlashcardToDeckViewModel.firePropertyChange("state");
    }

    @Override
    public void prepareFailView(String error) {
        final AddFlashcardToDeckState state = addFlashcardToDeckViewModel.getState();
        state.setError(error);

        this.addFlashcardToDeckViewModel.setState(state);
        this.addFlashcardToDeckViewModel.firePropertyChange("state");
    }
}