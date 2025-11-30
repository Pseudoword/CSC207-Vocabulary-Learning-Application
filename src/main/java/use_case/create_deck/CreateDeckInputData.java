package use_case.create_deck;

public class CreateDeckInputData {
    private final String deckTitle;
    private final String deckDescription;

    public CreateDeckInputData(String deckTitle, String description) {
        this.deckTitle = deckTitle;
        this.deckDescription = description;
    }

    public String getDeckTitle() {
        return deckTitle;
    }

    public String getDescription() {
        return deckDescription;
    }
}
