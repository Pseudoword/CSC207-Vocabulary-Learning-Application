package interface_adapter.create_deck;

import use_case.create_deck.CreateDeckInputBoundary;
import use_case.create_deck.CreateDeckInputData;

public class CreateDeckController {
    private final CreateDeckInputBoundary createDeckInteractor;

    public CreateDeckController(CreateDeckInputBoundary createDeckInteractor) {
        this.createDeckInteractor = createDeckInteractor;
    }

    public void execute(String deckTitle, String description) {
        final CreateDeckInputData inputData = new CreateDeckInputData(deckTitle, description);
        createDeckInteractor.execute(inputData);
    }
}
