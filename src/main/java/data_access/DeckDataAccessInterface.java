package main.java.data_access;

import main.java.entity.Deck;

public interface DeckDataAccessInterface {
    void saveDeck(Deck deck);
    boolean existsByTitle(String title);
}

