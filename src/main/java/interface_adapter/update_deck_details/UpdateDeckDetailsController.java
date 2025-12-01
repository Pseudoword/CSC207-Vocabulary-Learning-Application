package interface_adapter.update_deck_details;

import use_case.update_deck_details.UpdateDeckDetailsInputBoundary;
import use_case.update_deck_details.UpdateDeckDetailsInputData;

public class UpdateDeckDetailsController {

    private final UpdateDeckDetailsInputBoundary updateDeckDetailsInteractor;

    public UpdateDeckDetailsController(UpdateDeckDetailsInputBoundary updateDeckDetailsInteractor) {
        this.updateDeckDetailsInteractor = updateDeckDetailsInteractor;
    }

    /**
     * Executes the Update Deck Details Use Case.
     *
     * @param title         the title of the deck being modified
     * @param newTitle      the title to be changed to
     * @param description   the description to be changed to
     */
    public void execute(String title, String newTitle, String description) {
        final UpdateDeckDetailsInputData inputData = new UpdateDeckDetailsInputData(title, newTitle, description);
        updateDeckDetailsInteractor.execute(inputData);
    }
}
