package main.java.interface_adapter.create_deck;

import main.java.use_case.create_deck.CreateDeckInputBoundary;
import main.java.use_case.create_deck.CreateDeckInputData;

public class CreateDeckController {

    private final CreateDeckInputBoundary interactor;

    public CreateDeckController(CreateDeckInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void createDeck(String title, String description) {
        CreateDeckInputData inputData = new CreateDeckInputData(title, description);
        interactor.execute(inputData);
    }
}

