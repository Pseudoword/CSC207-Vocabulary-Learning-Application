package main.java.interface_adapter;

import main.java.entity.Deck;
import main.java.entity.MultipleChoiceQuestion;
import main.java.entity.Vocabulary;
import main.java.use_case.QuizUseCase;
import main.java.view.QuizView;

import java.util.List;

public class QuizController {
    private QuizUseCase quizUseCase;
    private QuizView quizView;

    public QuizController(Deck deck) {
        this.quizUseCase = new QuizUseCase(deck);
        this.quizView = new QuizView();
    }

    public void startQuiz() {
        List<MultipleChoiceQuestion> questions = quizUseCase.generateQuestion();

        for  (MultipleChoiceQuestion question : questions) {
            int userChoice = quizView.askQuestion(question);

            if (!question.checkAnswer(userChoice)) {
                quizView.showCorrectAnswer(question);
                quizUseCase.markIncorrect(question.getWord());
            } else {
                quizView.showCorrectFeedback();
            }
        }

        quizUseCase.markDeckIfMastered();

        List<Vocabulary> incorrectWords = quizUseCase.getIncorrectWords();
        quizView.showFinalResult(incorrectWords);
    }
}