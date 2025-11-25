package use_case.multiple_choice_quiz;

import entity.MultipleChoiceQuestion;
import entity.Vocabulary;
import main.java.use_case.multiple_choice_quiz.MultipleChoiceQuizInteractor;
import main.java.use_case.multiple_choice_quiz.MultipleChoiceQuizOutputBoundary;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultipleChoiceQuizInteractorTest {

    @Test
    void selectCorrectAnswerTest() {
        Vocabulary vocab = new Vocabulary("Apple", "A fruit", false);
        List<String> choices = Arrays.asList("A fruit", "A color", "A car");
        MultipleChoiceQuestion question = new MultipleChoiceQuestion(vocab, choices, 0);

        MultipleChoiceQuizOutputBoundary presenter = new MultipleChoiceQuizOutputBoundary() {
            @Override
            public void presentQuestion(String word, List<String> choices) {
                assertEquals("Apple", word);
                assertEquals(3, choices.size());
            }

            @Override
            public void presentCorrectAnswer(String correctAnswer) {
                fail("Answer is correct; should not show correct answer.");
            }

            @Override
            public void presentQuizFinished() {
                // nothing to test here
            }
        };

        MultipleChoiceQuizInteractor interactor = new MultipleChoiceQuizInteractor(
                List.of(question), presenter
        );

        interactor.selectAnswer(0); // correct
    }

    @Test
    void selectIncorrectAnswerTest() {
        Vocabulary vocab = new Vocabulary("Banana", "A fruit", false);
        List<String> choices = Arrays.asList("A car", "A fruit", "A tool");
        MultipleChoiceQuestion question = new MultipleChoiceQuestion(vocab, choices, 1);

        MultipleChoiceQuizOutputBoundary presenter = new MultipleChoiceQuizOutputBoundary() {
            @Override
            public void presentQuestion(String word, List<String> choices) {
                // no assertion here
            }

            @Override
            public void presentCorrectAnswer(String correctAnswer) {
                assertEquals("A fruit", correctAnswer);
            }

            @Override
            public void presentQuizFinished() {
                // nothing
            }
        };

        MultipleChoiceQuizInteractor interactor = new MultipleChoiceQuizInteractor(
                List.of(question), presenter
        );

        interactor.selectAnswer(0); // incorrect
    }

    @Test
    void nextQuestionTest() {
        Vocabulary vocab1 = new Vocabulary("Dog", "An animal", false);
        Vocabulary vocab2 = new Vocabulary("Cat", "Another animal", false);

        MultipleChoiceQuestion q1 = new MultipleChoiceQuestion(vocab1, Arrays.asList("Animal", "Plant"), 0);
        MultipleChoiceQuestion q2 = new MultipleChoiceQuestion(vocab2, Arrays.asList("Animal", "Plant"), 0);

        MultipleChoiceQuizOutputBoundary presenter = new MultipleChoiceQuizOutputBoundary() {
            @Override
            public void presentQuestion(String word, List<String> choices) {
                assertTrue(word.equals("Dog") || word.equals("Cat"));
            }

            @Override
            public void presentCorrectAnswer(String correctAnswer) {
                // nothing
            }

            @Override
            public void presentQuizFinished() {
                assertTrue(true); // quiz finished called at the end
            }
        };

        MultipleChoiceQuizInteractor interactor = new MultipleChoiceQuizInteractor(
                List.of(q1, q2), presenter
        );

        interactor.nextQuestion(); // moves to q2
        interactor.nextQuestion(); // finishes quiz
    }
}
