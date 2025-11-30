package use_case.create_deck;

public class CreateDeckOutputData {
    private final String deckName;
    private final boolean success;

    public CreateDeckOutputData(String deckName, boolean success) {
        this.deckName = deckName;
        this.success = success;
    }

    public String getDeckName() {
        return deckName;
    }

    public boolean isSuccess() {
        return success;
    }
}