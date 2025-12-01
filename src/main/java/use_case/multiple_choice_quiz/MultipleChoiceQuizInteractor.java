package use_case.multiple_choice_quiz;

import entity.MultipleChoiceQuestion;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceQuizInteractor implements MultipleChoiceQuizInputBoundary {

    private List<MultipleChoiceQuestion> questions;
    private final List<MultipleChoiceQuestion> incorrectQuestions;
    private final MultipleChoiceQuizOutputBoundary presenter;
    private int currentIndex = 0;
    private int correctCount = 0;

    public MultipleChoiceQuizInteractor(List<MultipleChoiceQuestion> questions,
                                        MultipleChoiceQuizOutputBoundary presenter) {
        this.questions = questions;
        this.presenter = presenter;
        this.incorrectQuestions = new ArrayList<>();
    }

    @Override
    public void execute(MultipleChoiceQuizInputData inputData) {
        switch (inputData.getActionType()) {
            case START_QUIZ:
                handleStartQuiz();
                break;
            case SELECT_ANSWER:
                handleSelectAnswer(inputData.getSelectedIndex());
                break;
            case NEXT_QUESTION:
                handleNextQuestion();
                break;
        }
    }

    private void handleStartQuiz() {
        showCurrentQuestion();
    }

    private void handleSelectAnswer(int selectedIndex) {
        if (currentIndex >= questions.size()) {
            MultipleChoiceQuizOutputData outputData =
                    new MultipleChoiceQuizOutputData("No question available.");
            presenter.prepareView(outputData);
            return;
        }

        MultipleChoiceQuestion question = questions.get(currentIndex);
        boolean correct = question.checkAnswer(selectedIndex);

        if (correct) {
            correctCount++;
            question.getVocabularyObject().markAsCorrect();
        } else {
            incorrectQuestions.add(question);
            question.getVocabularyObject().markAsIncorrect();
        }
    }

    private void handleNextQuestion() {
        currentIndex++;
        if (currentIndex >= questions.size()) {
            // Quiz finished
            boolean hasMistakes = !incorrectQuestions.isEmpty();
            MultipleChoiceQuizOutputData outputData = new MultipleChoiceQuizOutputData(
                    hasMistakes,
                    correctCount,
                    questions.size()
            );
            presenter.prepareView(outputData);
        } else {
            // Show next question
            showCurrentQuestion();
        }
    }

    private void showCurrentQuestion() {
        if (currentIndex >= questions.size()) {
            MultipleChoiceQuizOutputData outputData =
                    new MultipleChoiceQuizOutputData("No more questions available.");
            presenter.prepareView(outputData);
            return;
        }

        MultipleChoiceQuestion question = questions.get(currentIndex);
        MultipleChoiceQuizOutputData outputData = new MultipleChoiceQuizOutputData(
                question.getWord(),
                question.getChoices(),
                question.getChoices().get(question.getAnswerIndex()),
                currentIndex + 1,
                questions.size()
        );
        presenter.prepareView(outputData);
    }

    public List<MultipleChoiceQuestion> getIncorrectQuestions() {
        return new ArrayList<>(incorrectQuestions);
    }

    public List<MultipleChoiceQuestion> getAllQuestions() {
        return new ArrayList<>(questions);
    }
}