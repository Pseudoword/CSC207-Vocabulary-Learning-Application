package data_access;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * DictionaryAPIDataAccess class for Merriam-Webster Learner's Dictionary API.
 */
public class DictionaryAPIDataAccess {
    private static final String API_URL = "https://www.dictionaryapi.com/api/v3/references/learners/json";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String API_KEY_ENV = "API_KEY";
    private static final String DEFAULT_API_KEY = "b39169cd-e329-4bb0-9b46-31325235f40e";

    /**
     * Load API key from environment variable, or use default if not set.
     * @return the API key
     */
    public static String getAPIKey() {
        final String envKey = System.getenv(API_KEY_ENV);
        return (envKey != null && !envKey.isEmpty()) ? envKey : DEFAULT_API_KEY;
    }

    /**
     * Fetch word definition from Merriam-Webster Learner's Dictionary API.
     * @param word the word to look up
     * @return WordDefinition object containing the word and its definitions
     * @throws RuntimeException if the word cannot be found or API call fails
     */
    public WordDefinition getWordDefinition(String word) {
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final String apiKey = getAPIKey();

        final Request request = new Request.Builder()
                .url(String.format("%s/%s?key=%s", API_URL, word, apiKey))
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();

        try {
            final Response response = client.newCall(request).execute();

            if (response.body() == null) {
                throw new RuntimeException("Response body is null");
            }

            final String responseBodyString = response.body().string();

            if (!response.isSuccessful()) {
                throw new RuntimeException("API request failed with status code: " + response.code());
            }

            final JSONArray responseArray = new JSONArray(responseBodyString);

            if (responseArray.isEmpty()) {
                throw new RuntimeException("No definition found for word: " + word);
            }

            // Check if the response contains suggestions (when word is not found)
            // If the first element is a String, it means the API returned suggestions, not definitions
            final Object firstElement = responseArray.get(0);

            if (firstElement instanceof String) {
                throw new RuntimeException("Word not found: " + word);
            }

            final List<String> definitions = new ArrayList<>();
            final JSONObject firstEntry = responseArray.getJSONObject(0);

            // Extract definitions from shortdef field
            if (firstEntry.has("shortdef")) {
                final JSONArray shortDefs = firstEntry.getJSONArray("shortdef");
                for (int i = 0; i < shortDefs.length(); i++) {
                    definitions.add(shortDefs.getString(i));
                }
            }

            if (definitions.isEmpty()) {
                throw new RuntimeException("No definitions found for word: " + word);
            }

            return new WordDefinition(word, definitions);

        } catch (IOException | JSONException event) {
            throw new RuntimeException("Error fetching word definition: " + event.getMessage(), event);
        }
    }

    /**
     * Inner class to represent a word definition.
     */
    public static class WordDefinition {
        private final String word;
        private final List<String> definitions;

        public WordDefinition(String word, List<String> definitions) {
            this.word = word;
            this.definitions = definitions;
        }

        public String getWord() {
            return word;
        }

        public List<String> getDefinitions() {
            return definitions;
        }

        @Override
        public String toString() {
            return "WordDefinition{" +
                    "word='" + word + '\'' +
                    ", definitions=" + definitions +
                    '}';
        }
    }
}
