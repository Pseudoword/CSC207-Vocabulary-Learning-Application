package use_case.StudyFlashCards;

public interface StudyFlashCardsOutputBoundary {

    void prepareSuccessView(StudyFlashCardsOutputData data);

    void prepareFailureView(String message);
}
