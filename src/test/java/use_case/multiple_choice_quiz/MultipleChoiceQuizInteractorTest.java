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

        final int[] questionCallCount = {0};

        MultipleChoiceQuizOutputBoundary presenter = new MultipleChoiceQuizOutputBoundary() {
            @Override
            public void prepareView(MultipleChoiceQuizOutputData outputData) {
                if (outputData.getViewType() == MultipleChoiceQuizOutputData.ViewType.QUESTION_VIEW) {
                    questionCallCount[0]++;
                    assertEquals("Apple", outputData.getWord());
                    assertEquals(3, outputData.getChoices().size());
                    assertEquals("A fruit", outputData.getCorrectAnswer());
                }
            }
        };

        MultipleChoiceQuizInteractor interactor = new MultipleChoiceQuizInteractor(
                List.of(question), presenter
        );

        // Start quiz - shows first question
        interactor.execute(new MultipleChoiceQuizInputData("test-deck"));
        assertEquals(1, questionCallCount[0]);

        // Select correct answer
        interactor.execute(new MultipleChoiceQuizInputData(0));

        // Verify vocabulary is marked as correct
        assertFalse(vocab.isIncorrect());
    }

    @Test
    void selectIncorrectAnswerTest() {
        Vocabulary vocab = new Vocabulary("Banana", "A fruit", false);
        List<String> choices = Arrays.asList("A car", "A fruit", "A tool");
        MultipleChoiceQuestion question = new MultipleChoiceQuestion(vocab, choices, 1);

        final int[] questionCallCount = {0};

        MultipleChoiceQuizOutputBoundary presenter = new MultipleChoiceQuizOutputBoundary() {
            @Override
            public void prepareView(MultipleChoiceQuizOutputData outputData) {
                if (outputData.getViewType() == MultipleChoiceQuizOutputData.ViewType.QUESTION_VIEW) {
                    questionCallCount[0]++;
                    assertEquals("A fruit", outputData.getCorrectAnswer());
                }
            }
        };

        MultipleChoiceQuizInteractor interactor = new MultipleChoiceQuizInteractor(
                List.of(question), presenter
        );

        // Start quiz
        interactor.execute(new MultipleChoiceQuizInputData("test-deck"));
        assertEquals(1, questionCallCount[0]);

        // Select incorrect answer
        interactor.execute(new MultipleChoiceQuizInputData(0));

        // Verify vocabulary is marked as incorrect
        assertTrue(vocab.isIncorrect());
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
            public void prepareView(MultipleChoiceQuizOutputData outputData) {
                if (outputData.getViewType() == MultipleChoiceQuizOutputData.ViewType.QUESTION_VIEW) {
                    presentQuestionCallCount[0]++;
                    assertTrue(outputData.getWord().equals("Dog") || outputData.getWord().equals("Cat"));
                    assertEquals("Animal", outputData.getCorrectAnswer());
                } else if (outputData.getViewType() == MultipleChoiceQuizOutputData.ViewType.QUIZ_FINISHED_VIEW) {
                    quizFinishedCalled[0] = true;
                    assertFalse(outputData.hasMistakes());
                }
            }
        };

        MultipleChoiceQuizInteractor interactor = new MultipleChoiceQuizInteractor(
                List.of(q1, q2), presenter
        );

        // Start quiz - shows first question
        interactor.execute(new MultipleChoiceQuizInputData("test-deck"));
        assertEquals(1, presentQuestionCallCount[0]);

        // Move to next question
        interactor.execute(new MultipleChoiceQuizInputData());
        assertEquals(2, presentQuestionCallCount[0]);

        // Finish quiz
        interactor.execute(new MultipleChoiceQuizInputData());
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
            public void prepareView(MultipleChoiceQuizOutputData outputData) {
                if (outputData.getViewType() == MultipleChoiceQuizOutputData.ViewType.QUIZ_FINISHED_VIEW) {
                    hasMistakesResult[0] = outputData.hasMistakes();
                }
            }
        };

        MultipleChoiceQuizInteractor interactor = new MultipleChoiceQuizInteractor(
                List.of(q1), presenter
        );

        // Start quiz
        interactor.execute(new MultipleChoiceQuizInputData("test-deck"));

        // Select incorrect answer
        interactor.execute(new MultipleChoiceQuizInputData(1));

        // Finish quiz
        interactor.execute(new MultipleChoiceQuizInputData());

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
            public void prepareView(MultipleChoiceQuizOutputData outputData) {
                if (outputData.getViewType() == MultipleChoiceQuizOutputData.ViewType.QUIZ_FINISHED_VIEW) {
                    hasMistakesResult[0] = outputData.hasMistakes();
                }
            }
        };

        MultipleChoiceQuizInteractor interactor = new MultipleChoiceQuizInteractor(
                List.of(q1), presenter
        );

        // Start quiz
        interactor.execute(new MultipleChoiceQuizInputData("test-deck"));

        // Select correct answer
        interactor.execute(new MultipleChoiceQuizInputData(0));

        // Finish quiz
        interactor.execute(new MultipleChoiceQuizInputData());

        assertFalse(hasMistakesResult[0]);
    }

    @Test
    void getIncorrectQuestionsTest() {
        Vocabulary vocab1 = new Vocabulary("Dog", "An animal", false);
        Vocabulary vocab2 = new Vocabulary("Cat", "Another animal", false);
        Vocabulary vocab3 = new Vocabulary("Bird", "A flying animal", false);

        MultipleChoiceQuestion q1 = new MultipleChoiceQuestion(vocab1, Arrays.asList("An animal", "A plant"), 0);
        MultipleChoiceQuestion q2 = new MultipleChoiceQuestion(vocab2, Arrays.asList("Another animal", "A tool"), 0);
        MultipleChoiceQuestion q3 = new MultipleChoiceQuestion(vocab3, Arrays.asList("A flying animal", "A car"), 0);

        MultipleChoiceQuizOutputBoundary presenter = new MultipleChoiceQuizOutputBoundary() {
            @Override
            public void prepareView(MultipleChoiceQuizOutputData outputData) {
                // No assertions needed
            }
        };

        MultipleChoiceQuizInteractor interactor = new MultipleChoiceQuizInteractor(
                List.of(q1, q2, q3), presenter
        );

        // Start quiz
        interactor.execute(new MultipleChoiceQuizInputData("test-deck"));

        // Answer first question incorrectly
        interactor.execute(new MultipleChoiceQuizInputData(1));

        // Move to next question
        interactor.execute(new MultipleChoiceQuizInputData());

        // Answer second question correctly
        interactor.execute(new MultipleChoiceQuizInputData(0));

        // Move to next question
        interactor.execute(new MultipleChoiceQuizInputData());

        // Answer third question incorrectly
        interactor.execute(new MultipleChoiceQuizInputData(1));

        // Get incorrect questions
        List<MultipleChoiceQuestion> incorrectQuestions = interactor.getIncorrectQuestions();

        // Should have 2 incorrect questions (q1 and q3)
        assertEquals(2, incorrectQuestions.size());
        assertTrue(incorrectQuestions.contains(q1));
        assertFalse(incorrectQuestions.contains(q2));
        assertTrue(incorrectQuestions.contains(q3));
    }

    @Test
    void getAllQuestionsTest() {
        Vocabulary vocab1 = new Vocabulary("Dog", "An animal", false);
        Vocabulary vocab2 = new Vocabulary("Cat", "Another animal", false);

        MultipleChoiceQuestion q1 = new MultipleChoiceQuestion(vocab1, Arrays.asList("An animal", "A plant"), 0);
        MultipleChoiceQuestion q2 = new MultipleChoiceQuestion(vocab2, Arrays.asList("Another animal", "A tool"), 0);

        MultipleChoiceQuizOutputBoundary presenter = new MultipleChoiceQuizOutputBoundary() {
            @Override
            public void prepareView(MultipleChoiceQuizOutputData outputData) {
                // No assertions needed
            }
        };

        MultipleChoiceQuizInteractor interactor = new MultipleChoiceQuizInteractor(
                List.of(q1, q2), presenter
        );

        // Get all questions
        List<MultipleChoiceQuestion> allQuestions = interactor.getAllQuestions();

        // Should return all questions
        assertEquals(2, allQuestions.size());
        assertTrue(allQuestions.contains(q1));
        assertTrue(allQuestions.contains(q2));
    }

    @Test
    void handleSelectAnswerWithNoQuestionTest() {
        MultipleChoiceQuizOutputBoundary presenter = new MultipleChoiceQuizOutputBoundary() {
            @Override
            public void prepareView(MultipleChoiceQuizOutputData outputData) {
                if (outputData.getViewType() == MultipleChoiceQuizOutputData.ViewType.ERROR_VIEW) {
                    assertEquals("No question available.", outputData.getErrorMessage());
                }
            }
        };

        MultipleChoiceQuizInteractor interactor = new MultipleChoiceQuizInteractor(
                List.of(), presenter
        );

        // Try to select answer with no questions
        interactor.execute(new MultipleChoiceQuizInputData(0));
    }

    @Test
    void showCurrentQuestionWithNoQuestionsTest() {
        final boolean[] errorCalled = {false};

        MultipleChoiceQuizOutputBoundary presenter = new MultipleChoiceQuizOutputBoundary() {
            @Override
            public void prepareView(MultipleChoiceQuizOutputData outputData) {
                if (outputData.getViewType() == MultipleChoiceQuizOutputData.ViewType.ERROR_VIEW) {
                    errorCalled[0] = true;
                    assertEquals("No more questions available.", outputData.getErrorMessage());
                }
            }
        };

        MultipleChoiceQuizInteractor interactor = new MultipleChoiceQuizInteractor(
                List.of(), presenter
        );

        // Start quiz with empty question list
        interactor.execute(new MultipleChoiceQuizInputData("test-deck"));

        assertTrue(errorCalled[0]);
    }
}