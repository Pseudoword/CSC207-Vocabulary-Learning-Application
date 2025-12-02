package use_case.multiple_choice_quiz;

public class MultipleChoiceQuizInputData {

    public enum ActionType {
        START_QUIZ,
        SELECT_ANSWER,
        NEXT_QUESTION,
    }

    private final ActionType actionType;
    private final String deckId;
    private final Integer selectedIndex;

    // Constructor for START_QUIZ
    public MultipleChoiceQuizInputData(String deckId) {
        this.actionType = ActionType.START_QUIZ;
        this.deckId = deckId;
        this.selectedIndex = null;
    }

    // Constructor for SELECT_ANSWER
    public MultipleChoiceQuizInputData(int selectedIndex) {
        this.actionType = ActionType.SELECT_ANSWER;
        this.deckId = null;
        this.selectedIndex = selectedIndex;
    }

    // Constructor for NEXT_QUESTION
    public MultipleChoiceQuizInputData() {
        this.actionType = ActionType.NEXT_QUESTION;
        this.deckId = null;
        this.selectedIndex = null;
    }

    public ActionType getActionType() {
        return actionType;
    }

    public String getDeckId() {
        return deckId;
    }

    public Integer getSelectedIndex() {
        return selectedIndex;
    }
}