package main.java.use_case.multiple_choice_quiz;

public class MultipleChoiceQuizOutputData {
    private String correctAnswer;
    private boolean wasCorrect;

    public void MultipleChoiceQuizOutputData(String correctAnswer, boolean wasCorrect) {
        this.correctAnswer = correctAnswer;
        this.wasCorrect = wasCorrect;
    }

    public MultipleChoiceQuizOutputData(String correctAnswer, boolean wasCorrect) {
        this.correctAnswer = correctAnswer;
        this.wasCorrect = wasCorrect;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean wasCorrect() {
        return wasCorrect;
    }
}
