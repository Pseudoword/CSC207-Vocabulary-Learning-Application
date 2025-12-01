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
        final String deckTitle = inputData.getDeckTitle();
        final String description = inputData.getDescription();

        if (deckTitle == null || deckTitle.trim().isEmpty()) {
            outputBoundary.prepareFailView("Deck title cannot be empty.");
            return;
        }

        if (dataAccessObject.existsByTitle(deckTitle)) {
            outputBoundary.prepareFailView("Deck '" + deckTitle + "' already exists.");
            return;
        }

        final String finalDescription = (description == null) ? "" : description;
        final Deck newDeck = new Deck(deckTitle, finalDescription);
        dataAccessObject.save(newDeck);

        final CreateDeckOutputData outputData = new CreateDeckOutputData(deckTitle, finalDescription);
        outputBoundary.prepareSuccessView(outputData);
    }
}