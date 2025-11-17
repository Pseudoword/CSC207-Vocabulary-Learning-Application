package use_case.update_deck_details;

/**
 * The output data for the Update Deck Details Use Case.
 */
public class UpdateDeckDetailsOutputData {

    private final String title;
    private final String description;

    public UpdateDeckDetailsOutputData(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() { return title; }

    public String getDescription() { return description; }

}
