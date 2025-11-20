package data_access;

import entity.Deck;
import use_case.add_flashcard_to_deck.AddFlashcardToDeckDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

public class InMemoryAddFlashcardDataAccess implements AddFlashcardToDeckDataAccessInterface {
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

    // Helper methods for test setup
    public void addDeck(Deck deck) {
        decks.put(deck.getTitle(), deck);
    }

    public void addDefinition(String word, String definition) {
        dictionary.put(word, definition);
    }
}

