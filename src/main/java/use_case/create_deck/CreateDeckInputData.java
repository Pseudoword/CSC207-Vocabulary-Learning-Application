package main.java.use_case.create_deck;

public class CreateDeckInputData {
    private final String title;
    private final String description;

    public CreateDeckInputData(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
}

