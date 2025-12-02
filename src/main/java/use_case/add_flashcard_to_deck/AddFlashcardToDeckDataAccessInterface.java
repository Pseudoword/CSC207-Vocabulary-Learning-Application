package use_case.add_flashcard_to_deck;

import entity.Deck;

/**
 * The Data Access Interface for the Add Flashcard to Deck Use Case.
 */
public interface AddFlashcardToDeckDataAccessInterface {

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

}