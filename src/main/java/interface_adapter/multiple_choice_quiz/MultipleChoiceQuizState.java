package interface_adapter.multiple_choice_quiz;

import entity.MultipleChoiceQuestion;
import java.util.List;

public class MultipleChoiceQuizState {

    private final List<MultipleChoiceQuestion> questions;
    private int currentIndex = 0;
    private Integer selectedAnswerIndex = null; // currently selected choice
    private boolean quizFinished = false;

    public MultipleChoiceQuizState(List<MultipleChoiceQuestion> questions) {
        this.questions = questions;
    }

    public MultipleChoiceQuestion getCurrentQuestion() {
        if (currentIndex < questions.size()) {
            return questions.get(currentIndex);
        }
        return null;
    }

    public void selectAnswer(int index) {
        this.selectedAnswerIndex = index;
    }

    public Integer getSelectedAnswerIndex() {
        return selectedAnswerIndex;
    }

    public void nextQuestion() {
        currentIndex++;
        selectedAnswerIndex = null;
        if (currentIndex >= questions.size()) {
            quizFinished = true;
        }
    }

    public boolean isQuizFinished() {
        return quizFinished;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public int getTotalQuestions() {
        return questions.size();
    }
}
