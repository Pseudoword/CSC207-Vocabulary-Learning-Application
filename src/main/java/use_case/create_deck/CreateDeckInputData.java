package use_case.create_deck;

public class CreateDeckInputData {
    private final String deckName;

    public CreateDeckInputData(String deckName) {
        this.deckName = deckName;
    }

    public String getDeckName() {
        return deckName;
    }
}
