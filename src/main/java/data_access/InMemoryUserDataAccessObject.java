package data_access;

import entity.Deck;
import use_case.update_deck_details.UpdateDeckDetailsDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * An extremely simple DAO for user data. This is only for testing the Update Deck Details use case.
 */
public class InMemoryUserDataAccessObject implements UpdateDeckDetailsDataAccessInterface {

    private final Map<String, Deck> decks;

    public InMemoryUserDataAccessObject(){
        this.decks = new HashMap<>();
    }

    @Override
    public boolean existsByTitle(String title) {
        return decks.containsKey(title);
    }

    public void addDeck(Deck deck) {
        decks.put(deck.getTitle(), deck);
    }

    @Override
    public void updateDeckDetails(String oldTitle, Deck deck) {
        // Remove the old entry and add the new deck under its new title
        if (decks.containsKey(oldTitle)) {
            decks.remove(oldTitle);
            decks.put(deck.getTitle(), deck);
        }
        // If oldTitle does not exist, do nothing (no-op)
    }
}
