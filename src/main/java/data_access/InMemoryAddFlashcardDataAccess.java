package data_access;

import entity.Deck;
import use_case.add_flashcard_to_deck.AddFlashcardToDeckDataAccessInterface;
import use_case.delete_flashcard_from_deck.DeleteFlashcardFromDeckDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * In-memory implementation for testing purposes.
 */
public class InMemoryAddFlashcardDataAccess implements AddFlashcardToDeckDataAccessInterface,
                                                    DeleteFlashcardFromDeckDataAccessInterface {
    private final Map<String, Deck> decks = new HashMap<>();
    private final Map<String, String> dictionary = new HashMap<>();

    @Override
    public Deck getDeck(String deckName) {
        return decks.get(deckName);
    }

    @Override
    public void save(Deck deck) {
        decks.put(deck.getTitle(), deck);
    }

    @Override
    public String fetchDefinition(String word) {
        return dictionary.get(word);
    }

    // Helper methods to set up test data
    public void addDeck(Deck deck) {
        decks.put(deck.getTitle(), deck);
    }

    public void addDefinition(String word, String definition) {
        dictionary.put(word, definition);
    }
}