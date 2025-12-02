package use_case.multiple_choice_quiz;

import entity.Deck;
import entity.MultipleChoiceQuestion;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceQuizInteractor implements MultipleChoiceQuizInputBoundary {

    private final MultipleChoiceQuizDataAccessInterface quizDataAccess;
    private final MultipleChoiceQuizOutputBoundary presenter;
    private final Deck deck;

    private List<MultipleChoiceQuestion> questions;
    private final List<MultipleChoiceQuestion> incorrectQuestions;
    private int currentIndex = 0;
    private int correctCount = 0;


    // New constructor takes a deck + DAO + presenter
    public MultipleChoiceQuizInteractor(
            Deck deck,
            MultipleChoiceQuizDataAccessInterface quizDataAccess,
            MultipleChoiceQuizOutputBoundary presenter) {

        this.deck = deck;
        this.quizDataAccess = quizDataAccess;
        this.presenter = presenter;

        this.questions = quizDataAccess.getQuestionsForDeck(deck.getTitle());
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

    public void executeRetake(List<MultipleChoiceQuestion> incorrectOnly) {
        this.questions = new ArrayList<>(incorrectOnly);
        currentIndex = 0;
        incorrectQuestions.clear();
        correctCount = 0;
        showCurrentQuestion();
    }

    private void handleStartQuiz() {
        // Fetch questions from DAO when starting quiz
        questions = quizDataAccess.getQuestionsForDeck(deck.getTitle());

        currentIndex = 0;
        correctCount = 0;
        incorrectQuestions.clear();
        showCurrentQuestion();
    }

    private void handleSelectAnswer(int selectedIndex) {
        if (currentIndex >= questions.size()) {
            presenter.prepareView(new MultipleChoiceQuizOutputData("No question available."));
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
            boolean allCorrect = incorrectQuestions.isEmpty();
            deck.setLastAttemptAllCorrect(allCorrect);
            MultipleChoiceQuizOutputData outputData = new MultipleChoiceQuizOutputData(
                    !allCorrect, // hasMistakes
                    correctCount,
                    questions.size()
            );
            presenter.prepareView(outputData);
        } else {
            showCurrentQuestion();
        }
    }

    private void showCurrentQuestion() {
        if (currentIndex >= questions.size()) {
            presenter.prepareView(new MultipleChoiceQuizOutputData("No more questions available."));
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
