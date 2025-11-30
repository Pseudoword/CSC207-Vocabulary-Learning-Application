package interface_adapter.create_deck;

import use_case.create_deck.CreateDeckInputBoundary;
import use_case.create_deck.CreateDeckInputData;

public class CreateDeckController {
    private final CreateDeckInputBoundary createDeckInteractor;

    public CreateDeckController(CreateDeckInputBoundary createDeckInteractor) {
        this.createDeckInteractor = createDeckInteractor;
    }

    /**
     * Executes the Create Deck Use Case.
     * @param deckTitle the title of the deck to create
     * @param description the description of the deck (can be empty)
     */
    public void execute(String deckTitle, String description) {
        final CreateDeckInputData inputData = new CreateDeckInputData(deckTitle);
        createDeckInteractor.execute(inputData);
    }
}
