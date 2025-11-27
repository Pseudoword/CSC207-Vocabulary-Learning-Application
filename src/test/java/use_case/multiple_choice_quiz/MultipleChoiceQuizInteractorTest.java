package use_case.multiple_choice_quiz;

import entity.MultipleChoiceQuestion;
import entity.Vocabulary;
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

        final int[] correctAnswerCallCount = {0};

        MultipleChoiceQuizOutputBoundary presenter = new MultipleChoiceQuizOutputBoundary() {
            @Override
            public void presentQuestion(String word, List<String> choices) {
                assertEquals("Apple", word);
                assertEquals(3, choices.size());
            }

            @Override
            public void presentCorrectAnswer(String correctAnswer) {
                correctAnswerCallCount[0]++;
                assertEquals("A fruit", correctAnswer);
            }

            @Override
            public void presentQuizFinished(boolean hasMistakes) {
                // nothing to test here
            }
        };

        MultipleChoiceQuizInteractor interactor = new MultipleChoiceQuizInteractor(
                List.of(question), presenter
        );

        interactor.selectAnswer(0); // correct

        assertEquals(1, correctAnswerCallCount[0]);
    }

    @Test
    void selectIncorrectAnswerTest() {
        Vocabulary vocab = new Vocabulary("Banana", "A fruit", false);
        List<String> choices = Arrays.asList("A car", "A fruit", "A tool");
        MultipleChoiceQuestion question = new MultipleChoiceQuestion(vocab, choices, 1);

        final int[] correctAnswerCallCount = {0};

        MultipleChoiceQuizOutputBoundary presenter = new MultipleChoiceQuizOutputBoundary() {
            @Override
            public void presentQuestion(String word, List<String> choices) {
            }

            @Override
            public void presentCorrectAnswer(String correctAnswer) {
                correctAnswerCallCount[0]++;
                assertEquals("A fruit", correctAnswer);
            }

            @Override
            public void presentQuizFinished(boolean hasMistakes) {
            }
        };

        MultipleChoiceQuizInteractor interactor = new MultipleChoiceQuizInteractor(
                List.of(question), presenter
        );

        interactor.selectAnswer(0);

        assertEquals(1, correctAnswerCallCount[0]);
    }

    @Test
    void nextQuestionTest() {
        Vocabulary vocab1 = new Vocabulary("Dog", "An animal", false);
        Vocabulary vocab2 = new Vocabulary("Cat", "Another animal", false);

        MultipleChoiceQuestion q1 = new MultipleChoiceQuestion(vocab1, Arrays.asList("Animal", "Plant"), 0);
        MultipleChoiceQuestion q2 = new MultipleChoiceQuestion(vocab2, Arrays.asList("Animal", "Plant"), 0);

        final int[] presentQuestionCallCount = {0};
        final boolean[] quizFinishedCalled = {false};

        MultipleChoiceQuizOutputBoundary presenter = new MultipleChoiceQuizOutputBoundary() {
            @Override
            public void presentQuestion(String word, List<String> choices) {
                presentQuestionCallCount[0]++;
                assertTrue(word.equals("Dog") || word.equals("Cat"));
            }

            @Override
            public void presentCorrectAnswer(String correctAnswer) {
                assertEquals("Animal", correctAnswer);
            }

            @Override
            public void presentQuizFinished(boolean hasMistakes) {
                quizFinishedCalled[0] = true;
                assertFalse(hasMistakes);
            }
        };

        MultipleChoiceQuizInteractor interactor = new MultipleChoiceQuizInteractor(
                List.of(q1, q2), presenter
        );

        assertEquals(1, presentQuestionCallCount[0]);

        interactor.nextQuestion();
        assertEquals(2, presentQuestionCallCount[0]);

        interactor.nextQuestion();
        assertTrue(quizFinishedCalled[0]);
    }

    @Test
    void quizFinishedWithMistakesTest() {
        Vocabulary vocab1 = new Vocabulary("Dog", "An animal", false);
        List<String> choices = Arrays.asList("An animal", "A plant", "A mineral");
        MultipleChoiceQuestion q1 = new MultipleChoiceQuestion(vocab1, choices, 0);

        final boolean[] hasMistakesResult = {false};

        MultipleChoiceQuizOutputBoundary presenter = new MultipleChoiceQuizOutputBoundary() {
            @Override
            public void presentQuestion(String word, List<String> choices) {
                // no assertion
            }

            @Override
            public void presentCorrectAnswer(String correctAnswer) {
                // no assertion
            }

            @Override
            public void presentQuizFinished(boolean hasMistakes) {
                hasMistakesResult[0] = hasMistakes;
            }
        };

        MultipleChoiceQuizInteractor interactor = new MultipleChoiceQuizInteractor(
                List.of(q1), presenter
        );

        interactor.selectAnswer(1);
        interactor.nextQuestion();

        assertTrue(hasMistakesResult[0]);
    }

    @Test
    void quizFinishedWithNoMistakesTest() {
        Vocabulary vocab1 = new Vocabulary("Dog", "An animal", false);
        List<String> choices = Arrays.asList("An animal", "A plant", "A mineral");
        MultipleChoiceQuestion q1 = new MultipleChoiceQuestion(vocab1, choices, 0);

        final boolean[] hasMistakesResult = {true};

        MultipleChoiceQuizOutputBoundary presenter = new MultipleChoiceQuizOutputBoundary() {
            @Override
            public void presentQuestion(String word, List<String> choices) {
                // no assertion
            }

            @Override
            public void presentCorrectAnswer(String correctAnswer) {
                // no assertion
            }

            @Override
            public void presentQuizFinished(boolean hasMistakes) {
                hasMistakesResult[0] = hasMistakes;
            }
        };

        MultipleChoiceQuizInteractor interactor = new MultipleChoiceQuizInteractor(
                List.of(q1), presenter
        );

        interactor.selectAnswer(0);
        interactor.nextQuestion();

        assertFalse(hasMistakesResult[0]);
    }
}