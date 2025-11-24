package interface_adapter.delete_flashcard_in_deck;

import interface_adapter.ViewModel;

import java.util.ArrayList;

public class DeleteFlashcardFromDeckViewModel extends ViewModel<DeleteFlashcardFromDeckState> {

    public static final String TITLE_LABEL = "Delete Flashcard from Deck";
    public static final String DECK_NAME_LABEL = "Deck Name";
    public static final String WORD_LABEL = "Word";
    public static final String ADD_BUTTON_LABEL = "Delete Word";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public DeleteFlashcardFromDeckViewModel() {
        super("delete flashcard");
        setState(new DeleteFlashcardFromDeckState());
    }
}