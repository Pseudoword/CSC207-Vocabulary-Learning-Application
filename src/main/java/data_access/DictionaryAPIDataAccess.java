package data_access;

import entity.Deck;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.add_flashcard_to_deck.AddFlashcardToDeckDataAccessInterface;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DictionaryAPIDataAccess implements AddFlashcardToDeckDataAccessInterface {

    private static final String API_URL = "https://www.dictionaryapi.com/api/v3/references/learners/json";
    private static final String API_KEY_ENV = "API_KEY";
    private static final String DEFAULT_API_KEY = "b39169cd-e329-4bb0-9b46-31325235f40e";

    private final Map<String, Deck> savedDecks = new HashMap<>();

    public DictionaryAPIDataAccess() {
        // Initialize with a test deck for demonstration purposes
        savedDecks.put("Test Deck", new Deck("Test Deck", "A deck for testing"));
    }

    @Override
    public Deck getDeck(String deckName) {
        return savedDecks.get(deckName);
    }

    @Override
    public void save(Deck deck) {
        savedDecks.put(deck.getTitle(), deck);
    }

    @Override
    public String fetchDefinition(String word) {
        // 1. Retrieve API Key (Check env var first, fallback to default)
        String apiKey = System.getenv(API_KEY_ENV);
        if (apiKey == null || apiKey.isEmpty()) {
            apiKey = DEFAULT_API_KEY;
        }

        // 2. Build the URL: base_url/word?key=api_key
        String url = String.format("%s/%s?key=%s", API_URL, word, apiKey);

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }

            // 3. Parse the Response
            String responseBody = response.body().string();
            JSONArray jsonArray = new JSONArray(responseBody);

            if (jsonArray.isEmpty()) {
                return null; // Word not found
            }

            // The API can return an array of strings (suggestions) if the exact word isn't found
            if (jsonArray.get(0) instanceof String) {
                return null; // We only want exact matches with definitions
            }

            // 4. Extract definition from the first entry
            // Structure: [{ "shortdef": ["definition 1", "definition 2"], ... }]
            JSONObject firstEntry = jsonArray.getJSONObject(0);

            if (firstEntry.has("shortdef")) {
                JSONArray shortDefs = firstEntry.getJSONArray("shortdef");
                if (!shortDefs.isEmpty()) {
                    return shortDefs.getString(0);
                }
            }

            return "No simple definition found.";

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}