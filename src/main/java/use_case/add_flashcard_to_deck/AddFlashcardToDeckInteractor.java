package use_case.add_flashcard_to_deck;

import entity.Deck;
import entity.Vocabulary;

/**
 * The Interactor for adding a flashcard to a deck.
 */
public class AddFlashcardToDeckInteractor implements AddFlashcardToDeckInputBoundary {
    private final AddFlashcardToDeckDataAccessInterface dataAccessObject;
    private final AddFlashcardToDeckOutputBoundary outputBoundary;

    public AddFlashcardToDeckInteractor(AddFlashcardToDeckDataAccessInterface dataAccessObject,
                                        AddFlashcardToDeckOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(AddFlashcardToDeckInputData inputData) {
        final String deckTitle = inputData.getDeckTitle();
        final String word = inputData.getWord();
        final Deck deck = dataAccessObject.getDeck(deckTitle);

        if (deckTitle == null || deckTitle.isEmpty()) {
            outputBoundary.prepareFailView("Deck title cannot be empty.");
            return;
        }
        if (word == null || word.isEmpty()) {
            outputBoundary.prepareFailView("Word cannot be empty.");
            return;
        }
        if (deck == null) {
            outputBoundary.prepareFailView("Deck '" + deckTitle + "' does not exist.");
            return;
        }
        for (Vocabulary v : deck.getVocabularies()) {
            if (v.getWord().equalsIgnoreCase(word)) {
                outputBoundary.prepareFailView("Word '" + word + "' is already in the deck.");
                return;
            }
        }

        final String definition = dataAccessObject.fetchDefinition(word);

        if (definition == null) {
            outputBoundary.prepareFailView("Could not find a definition for '" + word + "'.");
        } else {
            final Vocabulary newFlashcard = new Vocabulary(word, definition, false);

            deck.addWord(newFlashcard);
            dataAccessObject.save(deck);

            final AddFlashcardToDeckOutputData outputData = new AddFlashcardToDeckOutputData(word, definition, deckTitle);
            outputBoundary.prepareSuccessView(outputData);
        }
    }
}