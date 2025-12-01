package interface_adapter.create_deck;

import interface_adapter.ViewManagerModel;
import use_case.create_deck.CreateDeckOutputBoundary;
import use_case.create_deck.CreateDeckOutputData;


public class CreateDeckPresenter implements CreateDeckOutputBoundary {
    private final CreateDeckViewModel createDeckViewModel;
    private final ViewManagerModel viewManagerModel;

    public CreateDeckPresenter(ViewManagerModel viewManagerModel,
                               CreateDeckViewModel createDeckViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.createDeckViewModel = createDeckViewModel;
    }

    @Override
    public void prepareSuccessView(CreateDeckOutputData response) {
        final CreateDeckState state = createDeckViewModel.getState();

        String successMsg = "Successfully created deck: '" + response.getDeckTitle() + "'";
        if (response.getDescription() != null && !response.getDescription().isEmpty()) {
            successMsg += "\nDescription: " + response.getDescription();
        }

        state.setSuccessMessage(successMsg);
        state.setError(null);
        state.setDeckTitle("");
        state.setDescription("");

        this.createDeckViewModel.setState(state);
        this.createDeckViewModel.firePropertyChange();
    }

    @Override
    public void prepareFailView(String error) {
        final CreateDeckState state = createDeckViewModel.getState();
        state.setError(error);
        state.setSuccessMessage(null);
        this.createDeckViewModel.setState(state);
        this.createDeckViewModel.firePropertyChange();

    }
}