package use_case.multiple_choice_quiz;

import entity.MultipleChoiceQuestion;

import java.util.List;

public class MultipleChoiceQuizInteractor implements MultipleChoiceQuizInputBoundary {

    private List<MultipleChoiceQuestion> questions;
    private MultipleChoiceQuizOutputBoundary presenter;
    private int currentIndex = 0;

    public MultipleChoiceQuizInteractor(List<MultipleChoiceQuestion> questions, MultipleChoiceQuizOutputBoundary presenter) {
        this.questions = questions;
        this.presenter = presenter;
    }

    private void showCurrentQuestion() {
        MultipleChoiceQuestion question = questions.get(currentIndex);
        presenter.presentQuestion(question.getWord(), question.getChoices());
    }

    @Override
    public void selectAnswer(int selectedIndex) {
        MultipleChoiceQuestion question = questions.get(currentIndex);
        boolean correct = question.checkAnswer(selectedIndex);
        if (!correct) {
            presenter.presentCorrectAnswer(question.getChoices().get(question.getAnswerIndex()));
        }
    }

    @Override
    public void nextQuestion() {
        currentIndex++;
        if (currentIndex >= questions.size()) {
            presenter.presentQuizFinished();
        } else {
            showCurrentQuestion();
        }
    }
}
