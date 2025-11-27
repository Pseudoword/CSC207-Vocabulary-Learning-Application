package interface_adapter.multiple_choice_quiz;

import entity.MultipleChoiceQuestion;
import use_case.multiple_choice_quiz.MultipleChoiceQuizInputBoundary;
import use_case.multiple_choice_quiz.MultipleChoiceQuizInteractor;
import use_case.multiple_choice_quiz.MultipleChoiceQuizOutputBoundary;

import java.util.List;

public class MultipleChoiceQuizController {

    private final MultipleChoiceQuizInputBoundary interactor;
    private final MultipleChoiceQuizOutputBoundary presenter;

    public MultipleChoiceQuizController(MultipleChoiceQuizInputBoundary interactor, MultipleChoiceQuizOutputBoundary presenter) {
        this.interactor = interactor;
        this.presenter = presenter;
    }

    public void answerSelected(int index) {
        interactor.selectAnswer(index);
    }

    public void nextPressed() {
        interactor.nextQuestion();
    }

    public List<MultipleChoiceQuestion> getIncorrectQuestions() {
        if (interactor instanceof MultipleChoiceQuizInteractor) {
            return ((MultipleChoiceQuizInteractor) interactor).getIncorrectQuestions();
        }
        return List.of();
    }
}
