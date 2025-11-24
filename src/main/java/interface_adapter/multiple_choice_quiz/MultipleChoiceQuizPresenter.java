package main.java.interface_adapter.multiple_choice_quiz;

import main.java.use_case.multiple_choice_quiz.MultipleChoiceQuizOutputBoundary;
import java.util.List;

public class MultipleChoiceQuizPresenter implements MultipleChoiceQuizOutputBoundary {

    private final MultipleChoiceQuizViewModel viewModel;

    public MultipleChoiceQuizPresenter(MultipleChoiceQuizViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void presentQuestion(String word, List<String> choices) {
        viewModel.setCurrentWord(word);
        viewModel.setChoices(choices);
    }

    @Override
    public void presentCorrectAnswer(String correctAnswer) {
        viewModel.setCorrectAnswer(correctAnswer);
    }

    @Override
    public void presentQuizFinished() {
        viewModel.setQuizFinished(true);
    }
}
