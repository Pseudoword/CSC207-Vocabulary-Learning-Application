package use_case.update_deck_details;

import entity.Deck;

/**
 * The input data for the Update Deck Details Use Case.
 */
public class UpdateDeckDetailsInputData {

    private final String title;
    private final String newTitle;
    private final String description;

    public UpdateDeckDetailsInputData(String title, String newTitle, String description) {
        this.title = title;
        this.newTitle = newTitle;
        this.description = description;
    }

    String getTitle() { return title; }

    String getNewTitle() { return newTitle; }

    String getDescription() { return description; }

}
