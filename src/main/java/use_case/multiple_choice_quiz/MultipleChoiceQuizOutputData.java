package use_case.multiple_choice_quiz;

import java.util.List;

public class MultipleChoiceQuizOutputData {

    public enum ViewType {
        QUESTION_VIEW,
        QUIZ_FINISHED_VIEW,
        ERROR_VIEW
    }

    private final ViewType viewType;

    // For QUESTION_VIEW
    private final String word;
    private final List<String> choices;
    private final String correctAnswer;
    private final Integer currentQuestionNumber;
    private final Integer totalQuestions;

    // For QUIZ_FINISHED_VIEW
    private final Boolean hasMistakes;
    private final Integer correctCount;

    // For ERROR_VIEW
    private final String errorMessage;

    // Constructor for QUESTION_VIEW
    public MultipleChoiceQuizOutputData(String word, List<String> choices, String correctAnswer,
                                        int currentQuestionNumber, int totalQuestions) {
        this.viewType = ViewType.QUESTION_VIEW;
        this.word = word;
        this.choices = choices;
        this.correctAnswer = correctAnswer;
        this.currentQuestionNumber = currentQuestionNumber;
        this.totalQuestions = totalQuestions;
        this.hasMistakes = null;
        this.correctCount = null;
        this.errorMessage = null;
    }

    // Constructor for QUIZ_FINISHED_VIEW
    public MultipleChoiceQuizOutputData(boolean hasMistakes, int correctCount, int totalQuestions) {
        this.viewType = ViewType.QUIZ_FINISHED_VIEW;
        this.hasMistakes = hasMistakes;
        this.correctCount = correctCount;
        this.totalQuestions = totalQuestions;
        this.word = null;
        this.choices = null;
        this.correctAnswer = null;
        this.currentQuestionNumber = null;
        this.errorMessage = null;
    }

    // Constructor for ERROR_VIEW
    public MultipleChoiceQuizOutputData(String errorMessage) {
        this.viewType = ViewType.ERROR_VIEW;
        this.errorMessage = errorMessage;
        this.word = null;
        this.choices = null;
        this.correctAnswer = null;
        this.currentQuestionNumber = null;
        this.totalQuestions = null;
        this.hasMistakes = null;
        this.correctCount = null;
    }

    public ViewType getViewType() {
        return viewType;
    }

    // Getters for QUESTION_VIEW
    public String getWord() {
        return word;
    }

    public List<String> getChoices() {
        return choices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public int getCurrentQuestionNumber() {
        return currentQuestionNumber;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    // Getters for QUIZ_FINISHED_VIEW
    public boolean hasMistakes() {
        return hasMistakes;
    }

    public int getCorrectCount() {
        return correctCount;
    }

    // Getter for ERROR_VIEW
    public String getErrorMessage() {
        return errorMessage;
    }
}