package interface_adapter.update_deck_details;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Update Deck Details View.
 */
public class UpdateDeckDetailsViewModel extends ViewModel<UpdateDeckDetailsState> {

    public static final String TITLE_LABEL = "Update Deck Details";
    public static final String DECK_TITLE_LABEL = "Deck Title";
    public static final String DECK_DESCRIPTION_LABEL = "Deck Description";
    public static final String APPLY_BUTTON_LABEL = "Apply";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public UpdateDeckDetailsViewModel() {
        super("update deck details");
        setState(new UpdateDeckDetailsState());
    }
}
