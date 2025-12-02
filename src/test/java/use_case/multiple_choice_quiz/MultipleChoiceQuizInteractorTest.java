package use_case.multiple_choice_quiz;

import entity.Deck;
import entity.MultipleChoiceQuestion;
import entity.Vocabulary;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MultipleChoiceQuizInteractorTest {

    private static class MockQuizDAO implements MultipleChoiceQuizDataAccessInterface {
        private final List<MultipleChoiceQuestion> questions;
        public MockQuizDAO(List<MultipleChoiceQuestion> questions) { this.questions = questions; }

        @Override
        public Deck getDeck(String deckTitle) {
            return null;
        }

        @Override
        public List<MultipleChoiceQuestion> getQuestionsForDeck(String deckTitle) { return questions; }

        @Override
        public void saveDeck(Deck deck) {

        }
    }

    private static class MockPresenter implements MultipleChoiceQuizOutputBoundary {
        MultipleChoiceQuizOutputData lastOutput;
        @Override
        public void prepareView(MultipleChoiceQuizOutputData outputData) { lastOutput = outputData; }
    }

    @Test
    void testFullCoverage() {
        Vocabulary vocab1 = new Vocabulary("Dog", "An animal", false);
        Vocabulary vocab2 = new Vocabulary("Cat", "Another animal", false);

        MultipleChoiceQuestion q1 = new MultipleChoiceQuestion(vocab1, Arrays.asList("Animal", "Plant"), 0);
        MultipleChoiceQuestion q2 = new MultipleChoiceQuestion(vocab2, Arrays.asList("Animal", "Plant"), 0);

        Deck deck = new Deck("Full Deck", "desc");
        deck.getVocabularies().addAll(Arrays.asList(vocab1, vocab2));

        MockPresenter presenter = new MockPresenter();
        MockQuizDAO dao = new MockQuizDAO(Arrays.asList(q1, q2));

        MultipleChoiceQuizInteractor interactor = new MultipleChoiceQuizInteractor(deck, dao, presenter);

        // START_QUIZ
        interactor.execute(new MultipleChoiceQuizInputData("Full Deck"));
        assertEquals(2, interactor.getAllQuestions().size());
        assertNotNull(presenter.lastOutput);

        // SELECT_ANSWER correct for q1
        interactor.execute(new MultipleChoiceQuizInputData(0));
        assertTrue(interactor.getIncorrectQuestions().isEmpty());
        assertFalse(vocab1.isIncorrect());

        // NEXT_QUESTION
        interactor.execute(new MultipleChoiceQuizInputData());

        // SELECT_ANSWER incorrect for q2
        interactor.execute(new MultipleChoiceQuizInputData(1));
        assertEquals(1, interactor.getIncorrectQuestions().size());
        assertTrue(vocab2.isIncorrect());

        // NEXT_QUESTION -> finishes quiz
        interactor.execute(new MultipleChoiceQuizInputData());
        assertNotNull(presenter.lastOutput);
        assertFalse(deck.isMastered() && interactor.getIncorrectQuestions().size() == 0); // mistakes exist

        // RETAKE_INCORRECT with wrong answer
        interactor.executeRetake(interactor.getIncorrectQuestions());
        assertEquals(1, interactor.getAllQuestions().size());

        // SELECT_ANSWER correct now
        interactor.execute(new MultipleChoiceQuizInputData(0));
        interactor.execute(new MultipleChoiceQuizInputData()); // NEXT_QUESTION finishes retake
        assertTrue(deck.isMastered());
        assertFalse(vocab2.isIncorrect());
        assertEquals(0, interactor.getIncorrectQuestions().size());

        // Edge case: empty deck
        Deck emptyDeck = new Deck("Empty", "desc");
        MultipleChoiceQuizInteractor emptyInteractor = new MultipleChoiceQuizInteractor(emptyDeck, new MockQuizDAO(Collections.emptyList()), presenter);
        emptyInteractor.execute(new MultipleChoiceQuizInputData("Empty"));
        assertEquals("No more questions available.", presenter.lastOutput.getErrorMessage());

        // Edge case: SELECT_ANSWER before START_QUIZ
        MultipleChoiceQuizInteractor newInteractor = new MultipleChoiceQuizInteractor(deck, dao, presenter);
        newInteractor.execute(new MultipleChoiceQuizInputData(0)); // should not crash
        assertNotNull(presenter.lastOutput);

        // Edge case: NEXT_QUESTION before START_QUIZ
        newInteractor.execute(new MultipleChoiceQuizInputData());
        assertNotNull(presenter.lastOutput);

        // Edge case: SELECT_ANSWER after all questions completed to hit handleSelectAnswer branch
        interactor.execute(new MultipleChoiceQuizInputData(0));
        assertEquals("No question available.", presenter.lastOutput.getErrorMessage());
    }
}


