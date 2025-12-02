package data_access;

import entity.Deck;
import entity.Vocabulary;
import use_case.add_flashcard_to_deck.AddFlashcardToDeckDataAccessInterface;
import use_case.create_deck.CreateDeckDataAccessInterface;

import java.io.*;
import java.util.*;

/**
 * File-based DAO for deck persistence.
 * Stores decks in a CSV file with format:
 * deckTitle|description|word1:definition1:incorrect1;word2:definition2:incorrect2;...
 */
public class FileDeckDataAccessObject implements AddFlashcardToDeckDataAccessInterface,
        CreateDeckDataAccessInterface {

    private static final String DELIMITER = "|";
    private static final String WORD_DELIMITER = ";";
    private static final String FIELD_DELIMITER = ":";

    private File csvFile;
    private final Map<String, Deck> decks = new LinkedHashMap<>();
    private final DictionaryAPIDataAccess apiDataAccess;
    private String currentUsername;

    public FileDeckDataAccessObject() {
        this.apiDataAccess = new DictionaryAPIDataAccess();

    }

    /**
     * Set the current user and load their decks.
     * @param username the username of the current user
     */
    public void setCurrentUser(String username) {
        this.currentUsername = username;
        this.csvFile = new File("decks_" + username + ".csv");

        decks.clear(); // Clear previous user's decks

        if (!csvFile.exists()) {
            initializeWithSampleDecks();
            save();
        } else {
            load();
        }
    }

    /**
     * Initialize with sample decks if file doesn't exist.
     */
    private void initializeWithSampleDecks() {
        Deck testDeck = new Deck("Test Deck", "A deck for testing");
        decks.put(testDeck.getTitle(), testDeck);

        Deck deck1 = new Deck("Deck 1", "Sample deck 1");
        deck1.addWord(new Vocabulary("apple", "A fruit that is typically red or green", false));
        deck1.addWord(new Vocabulary("dog", "A common domesticated animal", false));
        deck1.addWord(new Vocabulary("red", "The color of fire and blood", false));
        deck1.addWord(new Vocabulary("cat", "A small domesticated feline", false));
        deck1.addWord(new Vocabulary("house", "A building for human habitation", false));
        decks.put(deck1.getTitle(), deck1);

        Deck deck2 = new Deck("Deck 2", "Sample deck 2");
        deck2.addWord(new Vocabulary("red", "The color of fire and blood", false));
        deck2.addWord(new Vocabulary("cat", "A small domesticated feline", false));
        decks.put(deck2.getTitle(), deck2);

        Deck deck3 = new Deck("Deck 3", "Sample deck 3");
        deck3.addWord(new Vocabulary("house", "A building for human habitation", false));
        decks.put(deck3.getTitle(), deck3);
    }

    /**
     * Load decks from file.
     */
    private void load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                Deck deck = parseDeckFromLine(line);
                if (deck != null) {
                    decks.put(deck.getTitle(), deck);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading decks: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Parse a deck from a CSV line.
     * Format: deckTitle|description|word1:definition1:incorrect1;word2:definition2:incorrect2
     */
    private Deck parseDeckFromLine(String line) {
        try {
            String[] parts = line.split("\\" + DELIMITER, 3);  // Split into max 3 parts

            if (parts.length < 2) {
                return null;
            }

            String title = parts[0];
            String description = parts[1];
            Deck deck = new Deck(title, description);

            // Parse vocabularies if they exist
            if (parts.length == 3 && !parts[2].trim().isEmpty()) {
                String[] vocabStrings = parts[2].split(WORD_DELIMITER);

                for (String vocabString : vocabStrings) {
                    if (vocabString.trim().isEmpty()) {
                        continue;
                    }

                    String[] vocabParts = vocabString.split(FIELD_DELIMITER, 3);
                    if (vocabParts.length == 3) {
                        String word = vocabParts[0];
                        String definition = vocabParts[1];
                        boolean incorrect = Boolean.parseBoolean(vocabParts[2]);

                        deck.addWord(new Vocabulary(word, definition, incorrect));
                    }
                }
            }

            return deck;
        } catch (Exception e) {
            System.err.println("Error parsing deck line: " + line);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Save all decks to file.
     */
    private void save() {
        if (csvFile == null) {
            System.err.println("Cannot save: no user set");
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            for (Deck deck : decks.values()) {
                String line = serializeDeck(deck);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving decks: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Serialize a deck to CSV format.
     * Format: deckTitle|description|word1:definition1:incorrect1;word2:definition2:incorrect2
     */
    private String serializeDeck(Deck deck) {
        StringBuilder sb = new StringBuilder();

        // Add title and description
        sb.append(deck.getTitle());
        sb.append(DELIMITER);
        sb.append(deck.getDescription());
        sb.append(DELIMITER);

        // Add vocabularies
        List<Vocabulary> vocabularies = deck.getVocabularies();
        for (int i = 0; i < vocabularies.size(); i++) {
            Vocabulary vocab = vocabularies.get(i);
            sb.append(vocab.getWord());
            sb.append(FIELD_DELIMITER);
            sb.append(vocab.getDefinition());
            sb.append(FIELD_DELIMITER);
            sb.append(vocab.isIncorrect());

            if (i < vocabularies.size() - 1) {
                sb.append(WORD_DELIMITER);
            }
        }

        return sb.toString();
    }

    // ========== Interface Implementation ==========

    @Override
    public Deck getDeck(String deckTitle) {
        return decks.get(deckTitle);
    }

    @Override
    public void save(Deck deck) {
        decks.put(deck.getTitle(), deck);
        save();
    }

    @Override
    public boolean existsByTitle(String deckTitle) {
        return decks.containsKey(deckTitle);
    }

    @Override
    public String fetchDefinition(String word) {
        return apiDataAccess.fetchDefinition(word);
    }

    /**
     * Get all decks.
     * @return list of all decks
     */
    public List<Deck> getAllDecks() {
        return new ArrayList<>(decks.values());
    }

    /**
     * Delete a deck by title.
     * @param deckTitle the title of the deck to delete
     * @return true if deleted, false if not found
     */
    public boolean deleteDeck(String deckTitle) {
        if (decks.remove(deckTitle) != null) {
            save();
            return true;
        }
        return false;
    }
    public String getCurrentUsername() {
        return currentUsername;
    }
}