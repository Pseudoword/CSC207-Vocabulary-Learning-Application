package data_access;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.add_flashcard_to_deck.DictionaryAPIInterface;

import java.io.IOException;

/**
 * Data Access Object that interacts with the Merriam-Webster Dictionary API.
 * SINGLE RESPONSIBILITY: Fetch word definitions from external API only.
 */
public class DictionaryAPIDataAccess implements DictionaryAPIInterface {

    private static final String API_URL = "https://www.dictionaryapi.com/api/v3/references/learners/json";
    private static final String API_KEY = "b39169cd-e329-4bb0-9b46-31325235f40e";

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
}
