package main.java.data_access;

import main.java.entity.Deck;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDeckDataAccessObject implements DeckDataAccessInterface {

    private final Map<String, Deck> storage = new HashMap<>();

    @Override
    public void saveDeck(Deck deck) {
        storage.put(deck.getTitle(), deck);
    }

    @Override
    public boolean existsByTitle(String title) {
        return storage.containsKey(title);
    }
}
