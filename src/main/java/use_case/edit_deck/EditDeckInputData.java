package use_case.edit_deck;

public class EditDeckInputData {

    private final String newTitle;

    public EditDeckInputData(String newTitle) {
        this.newTitle = newTitle;
    }

    String getNewTitle() { return newTitle; }

}
