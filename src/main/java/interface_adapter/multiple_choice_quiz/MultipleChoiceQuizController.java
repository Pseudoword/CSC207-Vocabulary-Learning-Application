package interface_adapter.multiple_choice_quiz;

import entity.MultipleChoiceQuestion;
import use_case.multiple_choice_quiz.MultipleChoiceQuizInputBoundary;
import use_case.multiple_choice_quiz.MultipleChoiceQuizInteractor;

import java.util.List;

public class MultipleChoiceQuizController {

    private final MultipleChoiceQuizInputBoundary interactor;

    public MultipleChoiceQuizController(MultipleChoiceQuizInputBoundary interactor) {
        this.interactor = interactor;
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
