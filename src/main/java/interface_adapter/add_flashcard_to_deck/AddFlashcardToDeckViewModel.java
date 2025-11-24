package interface_adapter.add_flashcard_to_deck;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Add Flashcard To Deck View.
 */
public class AddFlashcardToDeckViewModel extends ViewModel<AddFlashcardToDeckState> {

    public static final String TITLE_LABEL = "Add Flashcard to Deck";
    public static final String DECK_NAME_LABEL = "Deck Name";
    public static final String WORD_LABEL = "Word";
    public static final String ADD_BUTTON_LABEL = "Add Word";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public AddFlashcardToDeckViewModel() {
        super("add flashcard");
        setState(new AddFlashcardToDeckState());
    }
}