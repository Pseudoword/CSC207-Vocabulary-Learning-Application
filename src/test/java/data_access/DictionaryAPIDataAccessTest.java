package data_access;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for DictionaryAPIDataAccess.
 */
class DictionaryAPIDataAccessTest {
    private DictionaryAPIDataAccess dataAccess;

    @BeforeEach
    void setUp() {
        dataAccess = new DictionaryAPIDataAccess();
    }

    @Test
    void testGetAPIKey() {
        // Test that API key can be retrieved (either from env or default)
        final String apiKey = DictionaryAPIDataAccess.getAPIKey();
        assertNotNull(apiKey, "API key should not be null");
        assertFalse(apiKey.isEmpty(), "API key should not be empty");
        System.out.println("API Key retrieved successfully");
    }

    @Test
    void testGetWordDefinition_ValidWord() {
        final DictionaryAPIDataAccess.WordDefinition result = dataAccess.getWordDefinition("test");

        assertNotNull(result, "WordDefinition should not be null");
        assertEquals("test", result.getWord(), "Word should match the queried word");
        assertNotNull(result.getDefinitions(), "Definitions list should not be null");
        assertFalse(result.getDefinitions().isEmpty(), "Definitions list should not be empty");

        System.out.println("Word: " + result.getWord());
        System.out.println("Definitions:");
        for (String def : result.getDefinitions()) {
            System.out.println("  - " + def);
        }
    }

    @Test
    void testGetWordDefinition_CommonWord() {
        final DictionaryAPIDataAccess.WordDefinition result = dataAccess.getWordDefinition("hello");

        assertNotNull(result, "WordDefinition should not be null");
        assertNotNull(result.getDefinitions(), "Definitions list should not be null");
        assertTrue(result.getDefinitions().size() > 0, "Should have at least one definition");

        System.out.println("Fetched " + result.getDefinitions().size() + " definitions for 'hello'");
    }

    @Test
    void testGetWordDefinition_InvalidWord() {
        // Testing with a nonsense word that likely doesn't exist
        final RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dataAccess.getWordDefinition("xyzabc123notaword");
        });

        assertTrue(exception.getMessage().contains("Word not found"),
                "Exception message should indicate word not found");
        System.out.println("Expected exception caught: " + exception.getMessage());
    }

    @Test
    void testGetWordDefinition_AnotherValidWord() {
        final DictionaryAPIDataAccess.WordDefinition result = dataAccess.getWordDefinition("book");

        assertNotNull(result.getWord(), "Word should not be null");
        assertEquals("book", result.getWord(), "Word should be 'book'");
        assertNotNull(result.getDefinitions(), "Definitions should not be null");
        assertTrue(result.getDefinitions() instanceof java.util.List, "Definitions should be a List");
        assertFalse(result.getDefinitions().isEmpty(), "Should have definitions");

        System.out.println("Successfully fetched definition for 'book'");
    }

    @Test
    void testGetWordDefinition_MultipleDefinitions() {
        final DictionaryAPIDataAccess.WordDefinition result = dataAccess.getWordDefinition("run");

        assertNotNull(result, "WordDefinition should not be null");
        assertTrue(result.getDefinitions().size() > 1,
                "The word 'run' should have multiple definitions");

        System.out.println("Word 'run' has " + result.getDefinitions().size() + " definitions");
    }

    @Test
    void testWordDefinition_ToString() {
        final DictionaryAPIDataAccess.WordDefinition result = dataAccess.getWordDefinition("cat");

        final String stringResult = result.toString();
        assertNotNull(stringResult, "toString should not return null");
        assertTrue(stringResult.contains("cat"), "toString should contain the word");
        assertTrue(stringResult.contains("WordDefinition"), "toString should contain class name");

        System.out.println("WordDefinition toString: " + stringResult);
    }
}
