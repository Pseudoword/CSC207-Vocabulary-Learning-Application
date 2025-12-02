package data_access;

import entity.Deck;
import entity.User;
import entity.UserFactory;
import entity.Vocabulary;
import use_case.StudyFlashCards.StudyFlashCardsDataAccessInterface;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * DAO for user data implemented using a File to persist the data.
 */
public class FileUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface,
        StudyFlashCardsDataAccessInterface {

    private static final String HEADER = "username,password";

    private final File csvFile;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<String, User> accounts = new HashMap<>();

    private String currentUsername;

    /**
     * Construct this DAO for saving to and reading from a local file.
     * @param csvPath the path of the file to save to
     * @param userFactory factory for creating user objects
     * @throws RuntimeException if there is an IOException when accessing the file
     */
    public FileUserDataAccessObject(String csvPath, UserFactory userFactory) {

        csvFile = new File(csvPath);
        headers.put("username", 0);
        headers.put("password", 1);

        if (csvFile.length() == 0) {
            save();
        }
        else {

            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                final String header = reader.readLine();

                if (!header.equals(HEADER)) {
                    throw new RuntimeException(String.format("header should be%n: %s%n but was:%n%s", HEADER, header));
                }

                String row;
                while ((row = reader.readLine()) != null) {
                    final String[] col = row.split(",");
                    final String username = String.valueOf(col[headers.get("username")]);
                    final String password = String.valueOf(col[headers.get("password")]);
                    final User user = userFactory.create(username, password);
                    accounts.put(username, user);
                }
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void save() {
        final BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(csvFile));
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();

            for (User user : accounts.values()) {
                final String line = String.format("%s,%s",
                        user.getName(), user.getPassword());
                writer.write(line);
                writer.newLine();
            }

            writer.close();

        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void save(User user) {

        accounts.put(user.getName(), user);
        this.save();
    }

    @Override
    public User get(String username) {
        return accounts.get(username);
    }

    @Override
    public void setCurrentUsername(String name) {
        currentUsername = name;
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public boolean existsByName(String identifier) {
        return accounts.containsKey(identifier);
    }

    @Override
    public void changePassword(User user) {
        // Replace the User object in the map
        accounts.put(user.getName(), user);
        save();
    }

    @Override
    public Deck getDeck(String deckName) {
        //temp
        Deck testDeck = new Deck("Temporary Test Deck", "description");
        Vocabulary vocab1 = new Vocabulary("word1", "defn1", false);
        Vocabulary vocab2 = new Vocabulary("word2", "defn2", false);
        Vocabulary vocab3 = new Vocabulary("word3", "defn3", false);
        Vocabulary vocab4 = new Vocabulary("word4", "defn4", false);
        Vocabulary vocab5 = new Vocabulary("word5", "defn5", false);
        Vocabulary vocab6 = new Vocabulary("word6", "defn6", false);
        testDeck.addWord(vocab1);
        testDeck.addWord(vocab2);
        testDeck.addWord(vocab3);
        testDeck.addWord(vocab4);
        testDeck.addWord(vocab5);
        testDeck.addWord(vocab6);

        Deck deck1 = new Deck("Deck 1", "Sample deck 1");
        deck1.addWord(new Vocabulary("apple", "A fruit that is typically red or green", false));
        deck1.addWord(new Vocabulary("dog", "A common domesticated animal", false));
        deck1.addWord(new Vocabulary("red", "The color of fire and blood", false));
        deck1.addWord(new Vocabulary("cat", "A small domesticated feline", false));
        deck1.addWord(new Vocabulary("house", "A building for human habitation", false));

        Deck deck2 = new Deck("Deck 2", "Sample deck 2");
        deck2.addWord(new Vocabulary("red", "The color of fire and blood", false));
        deck2.addWord(new Vocabulary("cat", "A small domesticated feline", false));

        Deck deck3 = new Deck("Deck 3", "Sample deck 3");
        deck3.addWord(new Vocabulary("house", "A building for human habitation", false));
        if (deckName == "testDeck") {return testDeck;}
        else if (deckName == "Deck 1") {
            return deck1;
        } else if (deckName == "Deck 2") {
            return deck2;
        } else if (deckName == "Deck 3") {
            return deck3;
        } else {return null;}
    }
}
