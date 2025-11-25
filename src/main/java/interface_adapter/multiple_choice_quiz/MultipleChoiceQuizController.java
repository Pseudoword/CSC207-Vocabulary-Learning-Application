package interface_adapter.multiple_choice_quiz;

import use_case.multiple_choice_quiz.MultipleChoiceQuizOutputBoundary;

public class MultipleChoiceQuizController {
    private final MultipleChoiceQuizState state;
    private final MultipleChoiceQuizOutputBoundary presenter;

    public MultipleChoiceQuizController(MultipleChoiceQuizState state, MultipleChoiceQuizOutputBoundary presenter) {
        this.state = state;
        this.presenter = presenter;
        showCurrentQuestion();
    }

    private void showCurrentQuestion() {
        var question = state.getCurrentQuestion();
        if (question != null) {
            presenter.presentQuestion(question.getWord(), question.getChoices());
        }
    }

    public void answerSelected(int index) {
        state.selectAnswer(index);
        var question = state.getCurrentQuestion();
        if (!question.checkAnswer(index)) {
            presenter.presentCorrectAnswer(question.getChoices().get(question.getAnswerIndex()));
        }
    }

    public void nextPressed() {
        state.nextQuestion();
        if (state.isQuizFinished()) {
            presenter.presentQuizFinished();
        } else {
            showCurrentQuestion();
        }
    }
}
