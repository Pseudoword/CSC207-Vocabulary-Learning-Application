package data_access;

import entity.Deck;
import use_case.add_flashcard_to_deck.AddFlashcardToDeckDataAccessInterface;
import use_case.add_flashcard_to_deck.DictionaryAPIInterface;
import use_case.create_deck.CreateDeckDataAccessInterface;
import use_case.StudyFlashCards.StudyFlashCardsDataAccessInterface;
import use_case.update_deck_details.UpdateDeckDetailsDataAccessInterface;
import use_case.delete_flashcard_from_deck.DeleteFlashcardFromDeckDataAccessInterface;

import java.util.*;

/**
 * File-based DAO for deck persistence.
 * Stores decks in a CSV file with format:
 * deckTitle|description|word1:definition1:incorrect1;word2:definition2:incorrect2;...
 */
public class FileDeckDataAccessObject implements
        AddFlashcardToDeckDataAccessInterface,
        CreateDeckDataAccessInterface,
        StudyFlashCardsDataAccessInterface,
        UpdateDeckDetailsDataAccessInterface,
        DeleteFlashcardFromDeckDataAccessInterface   {

    private final Map<String, Deck> decks = new LinkedHashMap<>();
    private final DictionaryAPIInterface dictionaryAPI;
    private final DeckRepository repository;
    private final SampleDeckInitializer sampleDeckInitializer;
    private String currentUsername;

    /**
     * Constructor with full dependency injection (for testing and flexibility).
     */
    public FileDeckDataAccessObject(
            DictionaryAPIInterface dictionaryAPI,
            DeckRepository repository,
            SampleDeckInitializer sampleDeckInitializer) {
        this.dictionaryAPI = dictionaryAPI;
        this.repository = repository;
        this.sampleDeckInitializer = sampleDeckInitializer;
    }

    /**
     * Convenience constructor with default implementations.
     * Uses file-based storage and standard sample data.
     */
    public FileDeckDataAccessObject(DictionaryAPIInterface dictionaryAPI) {
        this(dictionaryAPI,
                new FileDeckRepository(new DeckSerializer()),
                new SampleDeckInitializer());
    }

    /**
     * Set the current user and load their decks.
     * @param username the username of the current user
     */
    public void setCurrentUser(String username) {
        this.currentUsername = username;
        decks.clear();

        if (!repository.storageExists(username)) {
            initializeWithSampleDecks();
            saveToStorage();
        } else {
            loadFromStorage();
        }

    }

    /**
     * Initialize with sample decks if file doesn't exist.
     */
    private void initializeWithSampleDecks() {
        List<Deck> samples = sampleDeckInitializer.createSampleDecks();
        for (Deck deck : samples) {
            decks.put(deck.getTitle(), deck);
        }
    }

    private void loadFromStorage() {
        List<Deck> loadedDecks = repository.loadDecks(currentUsername);
        for (Deck deck : loadedDecks) {
            decks.put(deck.getTitle(), deck);
        }
    }

    private void saveToStorage() {
        if (currentUsername == null) {
            System.err.println("Cannot save: no user set");
            return;
        }
        repository.saveDecks(currentUsername, new ArrayList<>(decks.values()));
    }

    @Override
    public Deck getDeck(String deckTitle) {
        return decks.get(deckTitle);
    }

    @Override
    public void save(Deck deck) {
        decks.put(deck.getTitle(), deck);
        saveToStorage();
    }

    @Override
    public boolean existsByTitle(String deckTitle) {
        return decks.containsKey(deckTitle);
    }

    @Override
    public void updateDeckDetails(String oldTitle, Deck deck) {
        if (!oldTitle.equals(deck.getTitle())) {
            decks.remove(oldTitle);
        }
        decks.put(deck.getTitle(), deck);
        saveToStorage();
    }

    /**
     * Get all decks.
     * @return list of all decks
     */
    public List<Deck> getAllDecks() {
        return new ArrayList<>(decks.values());
    }

}