package use_case.update_deck_details;

import entity.Deck;

/**
 * The DAO interface for the Update Deck Details Use Case.
 */
public interface UpdateDeckDetailsDataAccessInterface {

    /**
     * Checks whether a deck with the given title exists.
     * @param title the title to check
     * @return true if a deck with this title exists, false otherwise
     */
    boolean existsByTitle(String title);

    /**
     * Updates the deck's title and description.
     * @param oldTitle the current title of the deck to update
     * @param deck the deck object containing the new title and description
     */
    void updateDeckDetails(String oldTitle, Deck deck);
