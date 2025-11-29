package use_case.StudyFlashCards;

public interface StudyFlashCardsInputBoundary {

    void execute(StudyFlashCardsInputData inputData);

    void next(StudyFlashCardsInputData inputData);

    void prev(StudyFlashCardsInputData inputData);

    void flag(StudyFlashCardsInputData inputData);

    void reveal(StudyFlashCardsInputData inputData);

    void switchToReviewView();
}
