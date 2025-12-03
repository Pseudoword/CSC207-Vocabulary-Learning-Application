package data_access;

import entity.Deck;
import java.util.List;

/**
 * Interface for deck persistence operations.
 * Allows different storage implementations (file, database, in-memory).
 */
public interface DeckRepository {
    /**
     * Loads all decks for the current user from storage.
     */
    List<Deck> loadDecks(String username);

    /**
     * Saves all decks for the current user to storage.
     */
    void saveDecks(String username, List<Deck> decks);

    /**
     * Checks if storage exists for the given user.
     */
    boolean storageExists(String username);
}
