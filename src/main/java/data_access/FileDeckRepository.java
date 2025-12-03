package data_access;

import entity.Deck;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * File-based implementation of DeckRepository.
 */
public class FileDeckRepository implements DeckRepository {
    private final DeckSerializer serializer;

    public FileDeckRepository(DeckSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public List<Deck> loadDecks(String username) {
        List<Deck> decks = new ArrayList<>();
        String filename = getFilename(username);

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                Deck deck = serializer.deserialize(line);
                if (deck != null) {
                    decks.add(deck);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading decks: " + e.getMessage());
            e.printStackTrace();
        }
        return decks;
    }

    @Override
    public void saveDecks(String username, List<Deck> decks) {
        String filename = getFilename(username);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Deck deck : decks) {
                String line = serializer.serialize(deck);
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving decks: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean storageExists(String username) {
        return new File(getFilename(username)).exists();
    }

    private String getFilename(String username) {
        return "decks_" + username + ".csv";
    }
}