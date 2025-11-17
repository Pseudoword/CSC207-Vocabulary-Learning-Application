package use_case.update_deck_details;

import entity.Deck;

/**
 * The interactor for the Update Deck Details Use Case.
 */
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
        final Deck deck = new Deck(newTitle, inputData.getDescription());

        if (newTitle.isEmpty()) {
            userPresenter.prepareFailView("Deck title cannot be empty.");
            return;
        }

        if (userDataAccessObject.existsByTitle(newTitle)) {
            userPresenter.prepareFailView("A deck with this title already exists.");
            return;
        }

        userDataAccessObject.updateDeckDetails(inputData.getTitle(), deck);

        final UpdateDeckDetailsOutputData outputData =
                new UpdateDeckDetailsOutputData(deck.getTitle(), deck.getDescription());
        userPresenter.prepareSuccessView(outputData);
    }

}