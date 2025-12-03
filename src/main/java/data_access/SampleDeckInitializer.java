package data_access;

import entity.Deck;
import entity.Vocabulary;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates sample/default decks for new users.
 */
public class SampleDeckInitializer {

    public List<Deck> createSampleDecks() {
        List<Deck> decks = new ArrayList<>();

        Deck testDeck = new Deck("Test Deck", "A deck for testing");
        decks.add(testDeck);

        Deck deck1 = new Deck("Deck 1", "Sample deck 1");
        deck1.addWord(new Vocabulary("apple", "A fruit that is typically red or green", false));
        deck1.addWord(new Vocabulary("dog", "A common domesticated animal", false));
        decks.add(deck1);

        Deck deck2 = new Deck("Deck 2", "Sample deck 2");
        deck2.addWord(new Vocabulary("red", "The color of fire and blood", false));
        deck2.addWord(new Vocabulary("cat", "A small domesticated feline", false));
        decks.add(deck2);

        Deck deck3 = new Deck("Deck 3", "Sample deck 3");
        deck3.addWord(new Vocabulary("house", "A building for human habitation", false));
        decks.add(deck3);

        return decks;
    }
}
