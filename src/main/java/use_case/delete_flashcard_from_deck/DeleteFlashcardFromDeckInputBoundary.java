package use_case.delete_flashcard_from_deck;

/**
 * Input Boundary for the Delete Flashcard From Deck Use Case.
 */
public interface DeleteFlashcardFromDeckInputBoundary {

    /**
     * Executes the Delete Flashcard from Deck Use Case.
     * @param inputData the input data
     */
    void execute(DeleteFlashcardFromDeckInputData inputData);
}
