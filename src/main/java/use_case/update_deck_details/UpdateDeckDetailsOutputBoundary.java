package use_case.update_deck_details;

/**
 * The output boundary for the Update Deck Details Use Case.
 */
public interface UpdateDeckDetailsOutputBoundary {

    /**
     * Prepares the success view for the Update Deck Details Use Case.
     * @param updateDeckDetailsOutputData the output data
     */
    void prepareSuccessView(UpdateDeckDetailsOutputData updateDeckDetailsOutputData);

    /**
     * Prepares the failure view for the Update Deck Details Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

}
