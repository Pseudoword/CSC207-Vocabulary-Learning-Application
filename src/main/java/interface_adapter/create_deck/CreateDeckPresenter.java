package interface_adapter.create_deck;

import interface_adapter.ViewManagerModel;
import use_case.create_deck.CreateDeckOutputBoundary;
import use_case.create_deck.CreateDeckOutputData;

// CreateDeckPresenter.java
public class CreateDeckPresenter implements CreateDeckOutputBoundary {
    private final CreateDeckViewModel viewModel;

    public CreateDeckPresenter(CreateDeckViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(CreateDeckOutputData outputData) {
        CreateDeckState state = new CreateDeckState();
        state.setDeckTitle(outputData.getDeckName());
        state.setSuccessMessage("Deck created successfully!");
        viewModel.setState(state);
    }

    @Override
    public void prepareFailView(String error) {
        CreateDeckState state = new CreateDeckState();
        state.setError(error);
        viewModel.setState(state);
    }
}