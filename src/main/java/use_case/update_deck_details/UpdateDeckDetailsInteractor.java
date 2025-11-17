package use_case.update_deck_details;

import entity.Deck;

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
        final Deck deck = new Deck(inputData.getNewTitle(), inputData.getDescription());

        userDataAccessObject.updateDeckDetails(deck);

        final UpdateDeckDetailsOutputData updateDeckDetailsoutputData =
                new UpdateDeckDetailsOutputData(deck.getTitle(), deck.getDescription());
        userPresenter.prepareSuccessView(updateDeckDetailsoutputData);
    }

}
