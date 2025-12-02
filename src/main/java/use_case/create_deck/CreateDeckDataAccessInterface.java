package use_case.create_deck;

import entity.Deck;

public interface CreateDeckDataAccessInterface {
    boolean existsByTitle(String deckTitle);
    void save(Deck deck);
}
