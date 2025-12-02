package interface_adapter.multiple_choice_quiz;

import entity.MultipleChoiceQuestion;
import use_case.multiple_choice_quiz.MultipleChoiceQuizInputBoundary;
import use_case.multiple_choice_quiz.MultipleChoiceQuizInputData;
import use_case.multiple_choice_quiz.MultipleChoiceQuizInteractor;

import java.util.List;

public class MultipleChoiceQuizController {
    private final MultipleChoiceQuizInteractor interactor;

    public MultipleChoiceQuizController(MultipleChoiceQuizInteractor interactor) {
        this.interactor = interactor;
    }

    public void startQuiz(String deckId) {
        MultipleChoiceQuizInputData inputData = new MultipleChoiceQuizInputData(deckId);
        interactor.execute(inputData);
    }

    public void startRetakeQuiz(List<MultipleChoiceQuestion> questions) {
        interactor.executeRetake(questions);
    }

    public void answerSelected(int selectedIndex) {
        MultipleChoiceQuizInputData inputData = new MultipleChoiceQuizInputData(selectedIndex);
        interactor.execute(inputData);
    }

    public void nextPressed() {
        MultipleChoiceQuizInputData inputData = new MultipleChoiceQuizInputData();
        interactor.execute(inputData);
    }

    public List<MultipleChoiceQuestion> getIncorrectQuestions() {
        if (interactor instanceof MultipleChoiceQuizInteractor) {
            return ((MultipleChoiceQuizInteractor) interactor).getIncorrectQuestions();
        }
        return List.of();
    }

    public List<MultipleChoiceQuestion> getAllQuestions() {
        if (interactor instanceof MultipleChoiceQuizInteractor) {
            return ((MultipleChoiceQuizInteractor) interactor).getAllQuestions();
        }
        return List.of();
    }
}