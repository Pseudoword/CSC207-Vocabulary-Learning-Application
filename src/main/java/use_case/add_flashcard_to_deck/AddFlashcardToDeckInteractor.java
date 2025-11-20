package use_case.add_flashcard_to_deck;

import entity.Deck;
import entity.Vocabulary;

/**
 * The Add Flashcard to Deck Interactor.
 */
public class AddFlashcardToDeckInteractor implements AddFlashcardToDeckInputBoundary {
    private final AddFlashcardToDeckDataAccessInterface dataAccessObject;
    private final AddFlashcardToDeckOutputBoundary outputBoundary;

    public AddFlashcardToDeckInteractor(AddFlashcardToDeckDataAccessInterface dataAccessInterface,
                                        AddFlashcardToDeckOutputBoundary outputBoundary) {
        this.dataAccessObject = dataAccessInterface;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(AddFlashcardToDeckInputData inputData) {
        final String deckName = inputData.getDeckName();
        final String wordText = inputData.getWord();

        // 1. Validate Input
        if (deckName == null || deckName.isEmpty()) {
            outputBoundary.prepareFailView("Deck name cannot be empty.");
            return;
        }
        if (wordText == null || wordText.isEmpty()) {
            outputBoundary.prepareFailView("Word cannot be empty.");
            return;
        }

        // 2. Retrieve Deck
        final Deck deck = dataAccessObject.getDeck(deckName);
        if (deck == null) {
            outputBoundary.prepareFailView("Deck '" + deckName + "' does not exist.");
            return;
        }

        // 3. Fetch Definition
        final String definition = dataAccessObject.fetchDefinition(wordText);
        if (definition == null) {
            outputBoundary.prepareFailView("Could not find a definition for '" + wordText + "'.");
            return;
        }

        // 4. Create Business Entity (Vocabulary)
        // Note: Flagged is initialized to false by default for new words
        final Vocabulary newVocabulary = new Vocabulary(wordText, definition, false);

        // 5. Update Entity (Deck)
        // Check if word already exists in deck to prevent duplicates
        for (Vocabulary v : deck.getVocabularies()) {
            if (v.getWord().equalsIgnoreCase(wordText)) {
                outputBoundary.prepareFailView("Word '" + wordText + "' is already in the deck.");
                return;
            }
        }
        deck.addWord(newVocabulary);

        // 6. Persist Data
        dataAccessObject.save(deck);

        // 7. Prepare Success View
        final AddFlashcardToDeckOutputData outputData = new AddFlashcardToDeckOutputData(
                newVocabulary.getWord(),
                newVocabulary.getDefinition(),
                deck.getTitle()
        );
        outputBoundary.prepareSuccessView(outputData);
    }
}