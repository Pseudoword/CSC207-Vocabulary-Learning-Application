package use_case.update_deck_details;

import entity.Deck;

import java.util.Objects;

public class UpdateDeckDetailsInteractor implements UpdateDeckDetailsInputBoundary {

    private final UpdateDeckDetailsDataAccessInterface userDataAccessObject;
    private final UpdateDeckDetailsOutputBoundary userPresenter;

    public UpdateDeckDetailsInteractor(UpdateDeckDetailsDataAccessInterface dataAccess,
                                       UpdateDeckDetailsOutputBoundary outputBoundary) {
        this.userDataAccessObject = dataAccess;
        this.userPresenter = outputBoundary;
    }

    @Override
    public void execute(UpdateDeckDetailsInputData inputData) {
        final String newTitle = inputData.getNewTitle();
        final String oldTitle = inputData.getTitle();
        final String newDescription = inputData.getDescription();

        if(newTitle.isEmpty()) {
            userPresenter.prepareFailView("Deck title cannot be empty.");
            return;
        }

        final Deck deck = userDataAccessObject.getDeck(oldTitle);

        if (deck == null) {
            userPresenter.prepareFailView("Target deck '" + oldTitle + "' does not exist.");
            return;
        }

        if (!(Objects.equals(deck.getTitle(), newTitle)) && userDataAccessObject.existsByTitle(newTitle)) {
            userPresenter.prepareFailView("A deck with this title already exists.");
            return;
        }

        deck.setTitle(newTitle);
        deck.setDescription(newDescription);

        userDataAccessObject.updateDeckDetails(oldTitle, deck);

        final UpdateDeckDetailsOutputData outputData =
                new UpdateDeckDetailsOutputData(deck.getTitle(), deck.getDescription());
        userPresenter.prepareSuccessView(outputData);
    }
}