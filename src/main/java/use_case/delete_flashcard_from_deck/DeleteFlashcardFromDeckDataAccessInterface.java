package use_case.delete_flashcard_from_deck;

import entity.Deck;

/**
 * The Data Access Interface for the Add Flashcard to Deck Use Case.
 */
public interface DeleteFlashcardFromDeckDataAccessInterface {

    /**
     * Returns the deck with the given title.
     * @param deckTitle the title of the deck to retrieve
     * @return the Deck object, or null if it does not exist
     */
    Deck getDeck(String deckTitle);

    /**
     * Saves the updated deck to the persistence layer.
     * @param deck the deck to save
     */
    void save(Deck deck);

    /**
     * Returns the flashcard with the given word.
     * @param word the word of the flashcard
     * @return the flashcard as a Vocabulary, or null if the flashcard is not found
     */
    String getVocabulary(String word);
}
