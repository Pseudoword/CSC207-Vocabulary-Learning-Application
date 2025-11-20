package use_case.add_flashcard_to_deck;

import data_access.InMemoryAddFlashcardDataAccess;
import entity.Deck;
import entity.Vocabulary;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddFlashcardToDeckInteractorTest {

    @Test
    void successTest() {
        // 1. Setup Data Access Stub
        InMemoryAddFlashcardDataAccess dao = new InMemoryAddFlashcardDataAccess();
        Deck deck = new Deck("History", "Historical events");
        dao.addDeck(deck);
        dao.addDefinition("Empire", "A group of nations or peoples ruled over by an emperor.");

        // 2. Define Input Data
        AddFlashcardToDeckInputData inputData = new AddFlashcardToDeckInputData("History", "Empire");

        // 3. Create Presenter to assert success
        AddFlashcardToDeckOutputBoundary successPresenter = new AddFlashcardToDeckOutputBoundary() {
            @Override
            public void prepareSuccessView(AddFlashcardToDeckOutputData outputData) {
                assertEquals("Empire", outputData.getWord());
                assertEquals("History", outputData.getDeckName());
                assertEquals("A group of nations or peoples ruled over by an emperor.", outputData.getDefinition());

                // Verify the deck was actually updated in the DAO
                Deck updatedDeck = dao.getDeck("History");
                assertEquals(1, updatedDeck.getVocabularies().size());
                assertEquals("Empire", updatedDeck.getVocabularies().get(0).getWord());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected: " + error);
            }
        };

        // 4. Execute Use Case
        AddFlashcardToDeckInteractor interactor = new AddFlashcardToDeckInteractor(dao, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureDeckNotFoundTest() {
        InMemoryAddFlashcardDataAccess dao = new InMemoryAddFlashcardDataAccess();
        // No decks added

        AddFlashcardToDeckInputData inputData = new AddFlashcardToDeckInputData("NonExistentDeck", "Word");

        AddFlashcardToDeckOutputBoundary failurePresenter = new AddFlashcardToDeckOutputBoundary() {
            @Override
            public void prepareSuccessView(AddFlashcardToDeckOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Deck 'NonExistentDeck' does not exist.", error);
            }
        };

        AddFlashcardToDeckInteractor interactor = new AddFlashcardToDeckInteractor(dao, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureDefinitionNotFoundTest() {
        InMemoryAddFlashcardDataAccess dao = new InMemoryAddFlashcardDataAccess();
        dao.addDeck(new Deck("Science", "Science terms"));
        // Dictionary is empty, so fetchDefinition will return null

        AddFlashcardToDeckInputData inputData = new AddFlashcardToDeckInputData("Science", "FakeWord");

        AddFlashcardToDeckOutputBoundary failurePresenter = new AddFlashcardToDeckOutputBoundary() {
            @Override
            public void prepareSuccessView(AddFlashcardToDeckOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Could not find a definition for 'FakeWord'.", error);
            }
        };

        AddFlashcardToDeckInteractor interactor = new AddFlashcardToDeckInteractor(dao, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureDuplicateWordTest() {
        InMemoryAddFlashcardDataAccess dao = new InMemoryAddFlashcardDataAccess();
        Deck deck = new Deck("Math", "Math terms");
        // Add existing word manually
        deck.addWord(new Vocabulary("Algebra", "A branch of mathematics", false));
        dao.addDeck(deck);
        dao.addDefinition("Algebra", "A branch of mathematics");

        AddFlashcardToDeckInputData inputData = new AddFlashcardToDeckInputData("Math", "Algebra");

        AddFlashcardToDeckOutputBoundary failurePresenter = new AddFlashcardToDeckOutputBoundary() {
            @Override
            public void prepareSuccessView(AddFlashcardToDeckOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Word 'Algebra' is already in the deck.", error);
            }
        };

        AddFlashcardToDeckInteractor interactor = new AddFlashcardToDeckInteractor(dao, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureEmptyInputTest() {
        InMemoryAddFlashcardDataAccess dao = new InMemoryAddFlashcardDataAccess();
        AddFlashcardToDeckOutputBoundary failurePresenter = new AddFlashcardToDeckOutputBoundary() {
            @Override
            public void prepareSuccessView(AddFlashcardToDeckOutputData outputData) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertTrue(error.contains("cannot be empty"));
            }
        };

        AddFlashcardToDeckInteractor interactor = new AddFlashcardToDeckInteractor(dao, failurePresenter);

        // Test empty deck name
        interactor.execute(new AddFlashcardToDeckInputData("", "Word"));

        // Test empty word
        interactor.execute(new AddFlashcardToDeckInputData("Deck", ""));
    }
}