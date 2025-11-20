package use_case.add_flashcard_to_deck;

import entity.Deck;

/**
 * The DAO interface for the Add Flashcard to Deck Use Case.
 */
public interface AddFlashcardToDeckDataAccessInterface {

    /**
     * Returns the deck with the given name.
     * @param deckName the name of the deck
     * @return the Deck object, or null if not found
     */
    Deck getDeck(String deckName);

    /**
     * Saves the updated deck.
     * @param deck the deck to save
     */
    void save(Deck deck);

    /**
     * Fetches the definition of a word from an external source.
     * @param word the word to look up
     * @return the definition of the word, or null if not found
     */
    String fetchDefinition(String word);
}