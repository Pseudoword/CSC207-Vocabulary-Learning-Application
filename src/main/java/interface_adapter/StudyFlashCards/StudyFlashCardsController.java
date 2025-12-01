package interface_adapter.StudyFlashCards;

import use_case.StudyFlashCards.StudyFlashCardsInputBoundary;
import use_case.StudyFlashCards.StudyFlashCardsInputData;

public class StudyFlashCardsController {
    private final StudyFlashCardsInputBoundary studyFlashCardsInputBoundary;

    public StudyFlashCardsController(StudyFlashCardsInputBoundary studyFlashCardsInputBoundary) {
        this.studyFlashCardsInputBoundary = studyFlashCardsInputBoundary;
    }
    public void execute(String deckName) {
        final StudyFlashCardsInputData inputData = new StudyFlashCardsInputData(deckName);
        studyFlashCardsInputBoundary.execute(inputData);
    }

    public void next(String deckName) {
        final StudyFlashCardsInputData inputData = new StudyFlashCardsInputData(deckName);
        studyFlashCardsInputBoundary.next(inputData);
    }

    public void prev(String deckName) {
        final StudyFlashCardsInputData inputData = new StudyFlashCardsInputData(deckName);
        studyFlashCardsInputBoundary.prev(inputData);
    }
    public void flag(String deckName) {
        final StudyFlashCardsInputData inputData = new StudyFlashCardsInputData(deckName);
        studyFlashCardsInputBoundary.flag(inputData);
    }
    public void reveal(String deckName) {
        final StudyFlashCardsInputData inputData = new StudyFlashCardsInputData(deckName);
        studyFlashCardsInputBoundary.reveal(inputData);
    }
    public void switchToReviewView(){
        //not used now
    }
}
