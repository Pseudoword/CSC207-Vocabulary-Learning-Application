package use_case.add_flashcard_to_deck;

/**
 * Input Boundary for the Add Flashcard to Deck Use Case.
 */
public interface AddFlashcardToDeckInputBoundary {

    /**
     * Executes the Add Flashcard to Deck Use Case.
     * @param inputData the input data
     */
    void execute(AddFlashcardToDeckInputData inputData);
}