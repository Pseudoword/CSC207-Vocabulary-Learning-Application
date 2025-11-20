package use_case.add_flashcard_to_deck;

/**
 * The output boundary for the Add Flashcard to Deck Use Case.
 */
public interface AddFlashcardToDeckOutputBoundary {
    /**
     * Prepares the success view for the Add Flashcard to Deck Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(AddFlashcardToDeckOutputData outputData);

    /**
     * Prepares the failure view for the Add Flashcard to Deck Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}