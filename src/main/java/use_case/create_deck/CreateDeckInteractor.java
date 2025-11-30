package use_case.create_deck;

import entity.Deck;

public class CreateDeckInteractor implements CreateDeckInputBoundary {
    private final CreateDeckDataAccessInterface dataAccessObject;
    private final CreateDeckOutputBoundary outputBoundary;

    public CreateDeckInteractor(CreateDeckDataAccessInterface dataAccessObject,
                                CreateDeckOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(CreateDeckInputData inputData) {
        final String deckTitle = inputData.getDeckName();

        // Validation
        if (deckTitle == null || deckTitle.trim().isEmpty()) {
            outputBoundary.prepareFailView("Deck title cannot be empty.");
            return;
        }

        // Check if deck already exists
        if (dataAccessObject.existsByTitle(deckTitle)) {
            outputBoundary.prepareFailView("Deck '" + deckTitle + "' already exists.");
            return;
        }

        // Create and save the new deck
        final Deck newDeck = new Deck(deckTitle);
        dataAccessObject.save(newDeck);

        final CreateDeckOutputData outputData = new CreateDeckOutputData(deckTitle);
        outputBoundary.prepareSuccessView(outputData);
    }
}