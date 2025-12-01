package interface_adapter.update_deck_details;

import interface_adapter.ViewManagerModel;
import use_case.update_deck_details.UpdateDeckDetailsOutputBoundary;
import use_case.update_deck_details.UpdateDeckDetailsOutputData;

/**
 * The Presenter for the Update Deck Details Use Case.
 */
public class UpdateDeckDetailsPresenter implements UpdateDeckDetailsOutputBoundary {

    private final UpdateDeckDetailsViewModel updateDeckDetailsViewModel;
    private final ViewManagerModel viewManagerModel;

    public UpdateDeckDetailsPresenter(ViewManagerModel viewManagerModel,
                                      UpdateDeckDetailsViewModel updateDeckDetailsViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.updateDeckDetailsViewModel = updateDeckDetailsViewModel;
    }

    @Override
    public void prepareSuccessView(UpdateDeckDetailsOutputData response) {
        final UpdateDeckDetailsState state = updateDeckDetailsViewModel.getState();
        state.setSuccessMessage("Successfully changed deck title to " + response.getTitle() +
                ". Successfully changed deck description to " + response.getDescription());
        state.setError(null);
        state.setDeckDescription(response.getDescription());

        this.updateDeckDetailsViewModel.setState(state);
        this.updateDeckDetailsViewModel.firePropertyChange("state");
    }

    @Override
    public void prepareFailView(String error) {
        final UpdateDeckDetailsState state = updateDeckDetailsViewModel.getState();
        state.setError(error);
        state.setSuccessMessage(null);

        this.updateDeckDetailsViewModel.setState(state);
        this.updateDeckDetailsViewModel.firePropertyChange("state");
    }


}
