package use_case.StudyFlashCards;

import entity.Deck;
import entity.Vocabulary;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class StudyFlashCardsInteractorTest {
    private TestDAO dao;
    private TestPresenter presenter;
    private StudyFlashCardsInteractor interactor;

    private Deck deck;
    private Vocabulary v1;
    private Vocabulary v2;
    private StudyFlashCardsInputData input;

    static class TestDAO implements StudyFlashCardsDataAccessInterface {
        private Deck deck;

        public void setDeck(Deck deck) {
            this.deck = deck;
        }

        @Override
        public Deck getDeck(String deckName) {
            return deck;
        }
    }

    static class TestPresenter implements StudyFlashCardsOutputBoundary {
        StudyFlashCardsOutputData lastSuccess;
        String lastFailure;

        @Override
        public void prepareSuccessView(StudyFlashCardsOutputData outputData) {
            lastSuccess = outputData;
        }

        @Override
        public void prepareFailureView(String message) {
            lastFailure = message;
        }
    }

    void setup() {
        dao = new TestDAO();
        presenter = new TestPresenter();
        interactor = new StudyFlashCardsInteractor(dao, presenter);

        v1 = new Vocabulary("apple", "a fruit", false);
        v2 = new Vocabulary("car", "a vehicle", false);

        deck = new Deck("testDeck", "deck for testing");
        deck.addWord(v1);
        deck.addWord(v2);
        dao.setDeck(deck);

        input = new StudyFlashCardsInputData("myDeck");
    }

    @Test
    void testExecuteShowsFirstWord() {
        setup();
        interactor.execute(input);

        assertNotNull(presenter.lastSuccess);
        assertEquals("apple", presenter.lastSuccess.getWord());
        assertEquals("a fruit", presenter.lastSuccess.getDefn());
        assertEquals("apple", presenter.lastSuccess.getDisplayText());
    }

    @Test
    void testExecuteEmptyDeckShowsFailure() {
        setup();
        dao.setDeck(new Deck("empty", ""));

        StudyFlashCardsInputData emptyInput = new StudyFlashCardsInputData("empty");

        interactor.execute(emptyInput);

        assertEquals("noWords", presenter.lastFailure);
        assertNull(presenter.lastSuccess);
    }

    @Test
    void testNextMovesToSecondCard() {
        setup();
        interactor.execute(input);
        interactor.next(input);

        assertEquals("car", presenter.lastSuccess.getWord());
        assertEquals("car", presenter.lastSuccess.getDisplayText());
    }

    @Test
    void testNextStaysAtEnd() {
        setup();
        interactor.execute(input);
        interactor.next(input);
        interactor.next(input);

        assertEquals("car", presenter.lastSuccess.getWord());
    }

    @Test
    void testPrevMovesBackToFirst() {
        setup();
        interactor.execute(input);
        interactor.next(input);
        interactor.prev(input);

        assertEquals("apple", presenter.lastSuccess.getWord());
    }

    @Test
    void testPrevStaysAtStart() {
        setup();
        interactor.execute(input);
        interactor.prev(input);

        assertEquals("apple", presenter.lastSuccess.getWord());
    }

    @Test
    void testRevealShowsDefinition() {
        setup();
        interactor.execute(input);
        interactor.reveal(input);

        assertEquals("a fruit", presenter.lastSuccess.getDisplayText());
    }

    @Test
    void testFlagTogglesFlag() {
        setup();
        interactor.execute(input);
        assertFalse(v1.getFlagged());

        interactor.flag(input);

        assertTrue(v1.getFlagged());
        assertTrue(presenter.lastSuccess.isFlag());
    }
}
