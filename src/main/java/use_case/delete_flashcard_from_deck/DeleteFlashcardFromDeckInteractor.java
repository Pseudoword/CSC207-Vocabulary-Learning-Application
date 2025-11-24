package use_case.delete_flashcard_from_deck;

import entity.Deck;
import entity.Vocabulary;

/**
 * The Interactor for deleting a flashcard from a deck.
 */
public class DeleteFlashcardFromDeckInteractor implements DeleteFlashcardFromDeckInputBoundary{
    private final DeleteFlashcardFromDeckDataAccessInterface dataAccessObject;
    private final DeleteFlashcardFromDeckOutputBoundary outputBoundary;

    public DeleteFlashcardFromDeckInteractor(DeleteFlashcardFromDeckDataAccessInterface dataAccessObject,
                                        DeleteFlashcardFromDeckOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(DeleteFlashcardFromDeckInputData inputData) {
        final String deckTitle = inputData.getDeckTitle();
        final String word = inputData.getFlashcardWord();

        if (deckTitle == null || deckTitle.isEmpty()) {
            outputBoundary.prepareFailView("Deck title cannot be empty.");
            return;
        }
        if (word == null || word.isEmpty()) {
            outputBoundary.prepareFailView("Flashcard word cannot be empty.");
            return;
        }

        final Deck deck = dataAccessObject.getDeck(deckTitle);

        if (deck == null) {
            outputBoundary.prepareFailView("Deck '" + deckTitle + "' does not exist.");
            return;
        }

        for (Vocabulary v : deck.getVocabularies()) {
            if (v.getWord().equalsIgnoreCase(word)) {
                deck.removeWord(v);
                dataAccessObject.save(deck);
                break;
            }
        }

        final DeleteFlashcardFromDeckOutputData outputData =
                new DeleteFlashcardFromDeckOutputData(deckTitle, word);
        outputBoundary.prepareSuccessView(outputData);
    }
}
