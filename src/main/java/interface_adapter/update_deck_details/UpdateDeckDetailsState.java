package interface_adapter.update_deck_details;

/**
 * The state for the Update Deck Details View Model.
 */
public class UpdateDeckDetailsState {
    private String originalDeckTitle = "";
    private String deckTitle = "";
    private String deckDescription = "";
    private String error = null;
    private String successMessage = null;

    public UpdateDeckDetailsState(UpdateDeckDetailsState copy){
        this.originalDeckTitle = copy.originalDeckTitle;
        this.deckTitle = copy.deckTitle;
        this.deckDescription = copy.deckDescription;
        this.error = copy.error;
        this.successMessage = copy.successMessage;
    }

    public UpdateDeckDetailsState() {}

    public String getOriginalDeckTitle() { return originalDeckTitle; }

    public void setOriginalDeckTitle(String originalDeckTitle) { this.originalDeckTitle = originalDeckTitle; }

    public String getDeckTitle() { return deckTitle; }

    public void setDeckTitle(String deckTitle) { this.deckTitle = deckTitle; }

    public String getDeckDescription() { return deckDescription; }

    public void setDeckDescription(String deckDescription) { this.deckDescription = deckDescription; }

    public String getError() { return error; }

    public void setError(String error) { this.error = error; }

    public String getSuccessMessage() { return successMessage; }

    public void setSuccessMessage(String successMessage) { this.successMessage = successMessage; }
}
