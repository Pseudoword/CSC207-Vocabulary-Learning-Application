package use_case.update_deck_details;

/**
 * The Update Deck Details Use Case.
 */
public interface UpdateDeckDetailsInputBoundary {

    /**
     * Execute the Update Deck Details Input Boundary.
     * @param updateDeckDetailsInputData the input data containing the new title and description
     */
    void execute(UpdateDeckDetailsInputData updateDeckDetailsInputData);

}
