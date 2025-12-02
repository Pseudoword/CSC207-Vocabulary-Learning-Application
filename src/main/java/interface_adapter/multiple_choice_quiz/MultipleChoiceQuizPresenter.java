package interface_adapter.multiple_choice_quiz;

import use_case.multiple_choice_quiz.MultipleChoiceQuizOutputBoundary;
import use_case.multiple_choice_quiz.MultipleChoiceQuizOutputData;

public class MultipleChoiceQuizPresenter implements MultipleChoiceQuizOutputBoundary {

    private final MultipleChoiceQuizViewModel viewModel;

    public MultipleChoiceQuizPresenter(MultipleChoiceQuizViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareView(MultipleChoiceQuizOutputData outputData) {
        switch (outputData.getViewType()) {
            case QUESTION_VIEW:
                viewModel.setCurrentWord(outputData.getWord());
                viewModel.setChoices(outputData.getChoices());
                viewModel.setCorrectAnswer(outputData.getCorrectAnswer());
                viewModel.setQuizFinished(false);
                break;

            case QUIZ_FINISHED_VIEW:
                viewModel.setQuizFinished(true);
                viewModel.setHasMistakes(outputData.hasMistakes());
                break;

            case ERROR_VIEW:
                System.err.println(" ");
                break;
        }
    }
}