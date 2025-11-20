package main.java.use_case.create_deck;

import main.java.data_access.DeckDataAccessInterface;
import main.java.entity.Deck;

public class CreateDeckInteractor implements CreateDeckInputBoundary {

    private final DeckDataAccessInterface deckRepo;
    private final CreateDeckOutputBoundary presenter;

    public CreateDeckInteractor(DeckDataAccessInterface deckRepo,
                                CreateDeckOutputBoundary presenter) {
        this.deckRepo = deckRepo;
        this.presenter = presenter;
    }

    @Override
    public void execute(CreateDeckInputData inputData) {

        String title = inputData.getTitle();

        if (title == null || title.isEmpty()) {
            presenter.prepareFailView("Deck title cannot be empty.");
            return;
        }

        if (deckRepo.existsByTitle(title)) {
            presenter.prepareFailView("A deck with this title already exists.");
            return;
        }

        Deck deck = new Deck(inputData.getTitle(), inputData.getDescription());
        deckRepo.saveDeck(deck);

        CreateDeckOutputData outputData =
                new CreateDeckOutputData(deck.getTitle(), deck.getDescription());

        presenter.prepareSuccessView(outputData);
    }
}