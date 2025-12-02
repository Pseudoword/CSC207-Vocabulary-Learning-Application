package use_case.create_deck;

public class CreateDeckOutputData {
    private final String deckTitle;
    private final String description;

    public CreateDeckOutputData(String deckTitle, String description) {
        this.deckTitle = deckTitle;
        this.description = description;
    }

    public String getDeckTitle() {
        return deckTitle;
    }

    public String getDescription() {
        return description;
    }
}