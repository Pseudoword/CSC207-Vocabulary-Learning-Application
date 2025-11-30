package use_case.multiple_choice_quiz;

public interface MultipleChoiceQuizInputBoundary {
    void selectAnswer(int selectedIndex);
    void nextQuestion();
}
