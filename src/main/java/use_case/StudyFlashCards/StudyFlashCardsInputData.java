package use_case.StudyFlashCards;

public class StudyFlashCardsInputData {
    private final String deckName;

    public StudyFlashCardsInputData(String deckName) {
        this.deckName = deckName;
    }
    public String getDeckName() {
        return deckName;
    }

}
