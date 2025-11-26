package use_case.multiple_choice_quiz;

import entity.MultipleChoiceQuestion;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceQuizInteractor implements MultipleChoiceQuizInputBoundary {

    private final List<MultipleChoiceQuestion> questions;
    private final List<MultipleChoiceQuestion> incorrectQuestions;
    private final MultipleChoiceQuizOutputBoundary presenter;
    private int currentIndex = 0;

    public MultipleChoiceQuizInteractor(List<MultipleChoiceQuestion> questions, MultipleChoiceQuizOutputBoundary presenter) {
        this.questions = questions;
        this.presenter = presenter;
        this.incorrectQuestions = new ArrayList<>();
        showCurrentQuestion();
    }

    private void showCurrentQuestion() {
        MultipleChoiceQuestion question = questions.get(currentIndex);
        presenter.presentQuestion(question.getWord(), question.getChoices());
        presenter.presentCorrectAnswer(question.getChoices().get(question.getAnswerIndex()));
    }

    @Override
    public void selectAnswer(int selectedIndex) {
        MultipleChoiceQuestion question = questions.get(currentIndex);
        boolean correct = question.checkAnswer(selectedIndex);
        if (!correct) {
            incorrectQuestions.add(question);
        }
    }

    @Override
    public void nextQuestion() {
        currentIndex++;
        if (currentIndex >= questions.size()) {
            presenter.presentQuizFinished(!incorrectQuestions.isEmpty());
        } else {
            showCurrentQuestion();
        }
    }

    public List<MultipleChoiceQuestion> getIncorrectQuestions() {
        return new ArrayList<>(incorrectQuestions);
    }
}
