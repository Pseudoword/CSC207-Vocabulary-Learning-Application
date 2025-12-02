package use_case.add_flashcard_to_deck;

/**
 * Interface for fetching word definitions from an external dictionary API.
 * SINGLE RESPONSIBILITY: Define contract for dictionary lookups only.
 */
public interface DictionaryAPIInterface {
    /**
     * Fetches the definition of a word from an external dictionary source.
     *
     * @param word the word to define
     * @return the definition as a String, or {@code null} if the word is not found,
     *         the API is unavailable, a network error occurs, or any other error is encountered.
     */
    String fetchDefinition(String word);
}