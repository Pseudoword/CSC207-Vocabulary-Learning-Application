package use_case.update_deck_details;

import entity.Deck;

/**
 * The DAO interface for the Update Deck Details Use Case.
 */
public interface UpdateDeckDetailsDataAccessInterface {

    /**
     * Updates the system to record this deck's title and description.
     * @param deck the deck whose title and description is to be updated
     */
    boolean existsByTitle(String title);

    void updateDeckDetails(String oldTitle, Deck deck);
}
