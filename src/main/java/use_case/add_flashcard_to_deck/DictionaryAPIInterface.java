package use_case.add_flashcard_to_deck;

/**
 * Interface for fetching word definitions from an external dictionary API.
 * SINGLE RESPONSIBILITY: Define contract for dictionary lookups only.
 */
public interface DictionaryAPIInterface {
    String fetchDefinition(String word);
}