package use_case.update_deck_details;

import entity.Deck;

/**
 * The DAO interface for the Update Deck Details Use Case.
 */
public interface UpdateDeckDetailsDataAccessInterface {

    /**
     * Checks if a deck with the given title exists in the system.
     * @param title the title of the deck to check for existence
     */
    boolean existsByTitle(String title);

    void updateDeckDetails(String oldTitle, Deck deck);
}
