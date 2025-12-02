package use_case.delete_flashcard_from_deck;

/**
 * The output boundary for the Add Flashcard to Deck Use Case.
 */
public interface DeleteFlashcardFromDeckOutputBoundary {
    /**
     * Prepares the success view for the Add Flashcard to Deck Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(DeleteFlashcardFromDeckOutputData outputData);

    /**
     * Prepares the failure view for the Add Flashcard to Deck Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
