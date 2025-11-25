package use_case.multiple_choice_quiz;

import java.util.List;

public interface MultipleChoiceQuizOutputBoundary {
    void presentQuestion(String word, List<String> choices);
    void presentCorrectAnswer(String correctAnswer);
    void presentQuizFinished();
}
