package data_access;

import entity.User;
import entity.UserFactory;
import use_case.change_password.ChangePasswordUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        ChangePasswordUserDataAccessInterface,
        LogoutUserDataAccessInterface {

    private static final String HEADER = "username,password";

    private final File csvFile;
    private final Map<String, Integer> headers = new LinkedHashMap<>();
    private final Map<String, User> accounts = new HashMap<>();

    private String currentUsername;

    public FileUserDataAccessObject(String csvPath, UserFactory userFactory) {
        csvFile = new File(csvPath);
        headers.put("username", 0);
        headers.put("password", 1);

        if (!csvFile.exists() || csvFile.length() == 0) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
                writer.write(HEADER);
                writer.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
            final String header = reader.readLine();
            if (!header.equals(HEADER)) {
                throw new RuntimeException(String.format("header should be%n: %s%n but was:%n%s", HEADER, header));
            }

            String row;
            while ((row = reader.readLine()) != null) {
                if (row.isBlank()) continue;
                final String[] col = row.split(",");
                if (col.length < 2) continue;
                final String username = col[headers.get("username")].trim();
                final String password = col[headers.get("password")].trim();
                if (username.isEmpty()) continue;
                final User user = userFactory.create(username, password);
                accounts.put(username, user);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            writer.write(HEADER);
            writer.newLine();
            for (User user : accounts.values()) {
                writer.write(user.getName() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void save(User user) {
        accounts.put(user.getName(), user);
        save();
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
        accounts.put(user.getName(), user);
        save();
    }
}
