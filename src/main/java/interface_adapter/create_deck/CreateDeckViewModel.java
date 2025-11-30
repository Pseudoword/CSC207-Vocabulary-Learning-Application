package interface_adapter.create_deck;

import interface_adapter.ViewModel;

public class CreateDeckViewModel extends ViewModel<CreateDeckState> {
    public static final String TITLE_LABEL = "Create Deck View";
    public static final String DECK_TITLE_LABEL = "Enter deck title:";
    public static final String DESCRIPTION_LABEL = "Enter description (optional):";
    public static final String CREATE_BUTTON_LABEL = "Create";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public CreateDeckViewModel() {
        super("create deck");
        setState(new CreateDeckState());
    }
}
