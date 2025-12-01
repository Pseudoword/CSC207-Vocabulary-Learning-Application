package data_access;

import entity.Deck;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.add_flashcard_to_deck.AddFlashcardToDeckDataAccessInterface;
import use_case.update_deck_details.UpdateDeckDetailsDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object that interacts with the Merriam-Webster Dictionary API.
 */
public class DictionaryAPIDataAccess implements AddFlashcardToDeckDataAccessInterface, UpdateDeckDetailsDataAccessInterface {

    private static final String API_URL = "https://www.dictionaryapi.com/api/v3/references/learners/json";
    private static final String API_KEY = "b39169cd-e329-4bb0-9b46-31325235f40e";
    private final Map<String, Deck> savedDecks = new HashMap<>();

    public DictionaryAPIDataAccess() {
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
    public boolean existsByTitle(String deckTitle) { return savedDecks.containsKey(deckTitle); }

    /**
     * Gets all decks.
     * @return a list of all decks
     */
    public List<Deck> getAllDecks() {
        return new ArrayList<>(savedDecks.values());
    }

    @Override
    public String fetchDefinition(String word) {
        final OkHttpClient client = new OkHttpClient();
        final String url = String.format("%s/%s?key=%s", API_URL, word, API_KEY);

        final Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected API response code: " + response.code());
            }

            final String responseBody = response.body().string();
            final JSONArray jsonArray = new JSONArray(responseBody);

            if (jsonArray.isEmpty()) {
                return null;
            }

            if (jsonArray.get(0) instanceof String) {
                return null;
            }

            final JSONObject firstEntry = jsonArray.getJSONObject(0);

            if (firstEntry.has("shortdef")) {
                final JSONArray shortDefs = firstEntry.getJSONArray("shortdef");
                if (!shortDefs.isEmpty()) {
                    return shortDefs.getString(0);
                }
            }

            return null;

        } catch (IOException | JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean existsByTitle(String title) {
        return savedDecks.containsKey(title);
    }

    @Override
    public void updateDeckDetails(String oldTitle, Deck deck) {
        // Replace the old entry with the new deck details
        savedDecks.put(deck.getTitle(), savedDecks.remove(oldTitle));
    }
}
