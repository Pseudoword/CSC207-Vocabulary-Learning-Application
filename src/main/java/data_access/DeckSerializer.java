package data_access;

import entity.Deck;
import entity.Vocabulary;
import java.util.List;

/**
 * Handles serialization/deserialization of Deck objects to/from CSV format.
 */
public class DeckSerializer {
    private static final String DELIMITER = "|";
    private static final String WORD_DELIMITER = ";";
    private static final String FIELD_DELIMITER = ":";

    /**
     * Serializes a Deck to CSV format.
     * Format: deckTitle|description|word1:definition1:incorrect1;word2:definition2:incorrect2
     */
    public String serialize(Deck deck) {
        StringBuilder sb = new StringBuilder();
        sb.append(deck.getTitle())
                .append(DELIMITER)
                .append(deck.getDescription())
                .append(DELIMITER);

        List<Vocabulary> vocabularies = deck.getVocabularies();
        for (int i = 0; i < vocabularies.size(); i++) {
            Vocabulary vocab = vocabularies.get(i);
            sb.append(vocab.getWord())
                    .append(FIELD_DELIMITER)
                    .append(vocab.getDefinition())
                    .append(FIELD_DELIMITER)
                    .append(vocab.isIncorrect());

            if (i < vocabularies.size() - 1) {
                sb.append(WORD_DELIMITER);
            }
        }
        return sb.toString();
    }

    /**
     * Deserializes a CSV line to a Deck object.
     */
    public Deck deserialize(String line) {
        try {
            String[] parts = line.split("\\" + DELIMITER, 3);
            if (parts.length < 2) {
                return null;
            }

            String title = parts[0];
            String description = parts[1];
            Deck deck = new Deck(title, description);

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
}
