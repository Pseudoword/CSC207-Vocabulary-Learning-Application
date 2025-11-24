package use_case.delete_flashcard_from_deck;

import data_access.InMemoryAddFlashcardDataAccess;
import entity.Deck;
import entity.Vocabulary;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteFlashcardFromDeckInteractorTest {

    @Test
    void successTest() {
        InMemoryAddFlashcardDataAccess dao = new InMemoryAddFlashcardDataAccess();
        Deck deck = new Deck("Science", "Scientific terms");
        Vocabulary vocab = new Vocabulary("Atom", "The basic unit of a chemical element.");
        deck.addWord(vocab);
        dao.addDeck(deck);

        DeleteFlashcardFromDeckInputData inputData = new DeleteFlashcardFromDeckInputData("Science", "Atom");

        DeleteFlashcardFromDeckOutputBoundary successPresenter = new DeleteFlashcardFromDeckOutputBoundary() {

            @Override
            public void prepareSuccessView(DeleteFlashcardFromDeckOutputData outputData) {
                assertEquals("Atom", outputData.getFlashcardWord());
                assertEquals("Science", outputData.getDeckTitle());

                // Verify the deck was actually updated in the DAO
                Deck updatedDeck = dao.getDeck("Science");
                assertEquals(0, updatedDeck.getVocabularies().size());
            }

            @Override
            public void prepareFailView(String error) { fail("Use case failure is unexpected: " + error);}

        };

        DeleteFlashcardFromDeckInteractor interactor = new DeleteFlashcardFromDeckInteractor(dao, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureDeckNotFoundTest() {
        InMemoryAddFlashcardDataAccess dao = new InMemoryAddFlashcardDataAccess();

        DeleteFlashcardFromDeckInputData inputData = new DeleteFlashcardFromDeckInputData("NonExistentDeck", "Word");

        DeleteFlashcardFromDeckOutputBoundary failurePresenter = new DeleteFlashcardFromDeckOutputBoundary() {
            @Override
            public void prepareSuccessView(DeleteFlashcardFromDeckOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Deck 'NonExistentDeck' does not exist.", error);
            }
        };

        DeleteFlashcardFromDeckInteractor interactor = new DeleteFlashcardFromDeckInteractor(dao, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureEmptyDeckTitleTest() {
        InMemoryAddFlashcardDataAccess dao = new InMemoryAddFlashcardDataAccess();

        DeleteFlashcardFromDeckInputData inputData = new DeleteFlashcardFromDeckInputData("", "Word");

        DeleteFlashcardFromDeckOutputBoundary failurePresenter = new DeleteFlashcardFromDeckOutputBoundary() {
            @Override
            public void prepareSuccessView(DeleteFlashcardFromDeckOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Deck title cannot be empty.", error);
            }
        };

        DeleteFlashcardFromDeckInteractor interactor = new DeleteFlashcardFromDeckInteractor(dao, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureEmptyFlashcardWordTest() {
        InMemoryAddFlashcardDataAccess dao = new InMemoryAddFlashcardDataAccess();
        DeleteFlashcardFromDeckInputData inputData = new DeleteFlashcardFromDeckInputData("Science", "");
        DeleteFlashcardFromDeckOutputBoundary failurePresenter = new DeleteFlashcardFromDeckOutputBoundary() {
            @Override
            public void prepareSuccessView(DeleteFlashcardFromDeckOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Flashcard word cannot be empty.", error);
            }
        };
        DeleteFlashcardFromDeckInteractor interactor = new DeleteFlashcardFromDeckInteractor(dao, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureFlashcardNotFoundTest() {
        InMemoryAddFlashcardDataAccess dao = new InMemoryAddFlashcardDataAccess();
        Deck deck = new Deck("Science", "Scientific terms");
        dao.addDeck(deck);

        DeleteFlashcardFromDeckInputData inputData = new DeleteFlashcardFromDeckInputData("Science", "NonExistentWord");

        DeleteFlashcardFromDeckOutputBoundary failurePresenter = new DeleteFlashcardFromDeckOutputBoundary() {
            @Override
            public void prepareSuccessView(DeleteFlashcardFromDeckOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Flashcard with word 'NonExistentWord' does not exist in deck 'Science'.", error);
            }
        };

        DeleteFlashcardFromDeckInteractor interactor = new DeleteFlashcardFromDeckInteractor(dao, failurePresenter);
        interactor.execute(inputData);
    }

}
