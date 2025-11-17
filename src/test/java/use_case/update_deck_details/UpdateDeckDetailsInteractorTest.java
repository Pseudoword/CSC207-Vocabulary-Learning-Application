package use_case.update_deck_details;

import data_access.InMemoryUserDataAccessObject;
import entity.Deck;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UpdateDeckDetailsInteractorTest {

    @Test
    void successfulTest() {
        UpdateDeckDetailsInputData inputData = new UpdateDeckDetailsInputData(
                "Old Title",
                "New Title",
                "New Description"
        );
        InMemoryUserDataAccessObject userDAO = new InMemoryUserDataAccessObject();

        UpdateDeckDetailsOutputBoundary successPresenter = new UpdateDeckDetailsOutputBoundary() {
            @Override
            public void prepareSuccessView(UpdateDeckDetailsOutputData deck) {
                assertEquals("New Title", deck.getTitle());
                assertEquals("New Description", deck.getDescription());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        UpdateDeckDetailsInputBoundary interactor = new UpdateDeckDetailsInteractor(userDAO, successPresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureDeckTitleIsEmpty() {
        UpdateDeckDetailsInputData inputData = new UpdateDeckDetailsInputData(
                "Old Title",
                "",
                "New Description"
        );
        InMemoryUserDataAccessObject userDAO = new InMemoryUserDataAccessObject();

        UpdateDeckDetailsOutputBoundary failurePresenter = new UpdateDeckDetailsOutputBoundary() {
            @Override
            public void prepareSuccessView(UpdateDeckDetailsOutputData deck) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Deck title cannot be empty.", errorMessage);
            }
        };

        UpdateDeckDetailsInputBoundary interactor = new UpdateDeckDetailsInteractor(userDAO, failurePresenter);
        interactor.execute(inputData);
    }

    @Test
    void failureDeckTitleAlreadyExists() {
        UpdateDeckDetailsInputData inputData = new UpdateDeckDetailsInputData(
                "Old Title",
                "Existing Title",
                "New Description"
        );
        InMemoryUserDataAccessObject userDAO = new InMemoryUserDataAccessObject();

        userDAO.addDeck(new Deck("Existing Title", "Some Description"));

        UpdateDeckDetailsOutputBoundary failurePresenter = new UpdateDeckDetailsOutputBoundary() {
            @Override
            public void prepareSuccessView(UpdateDeckDetailsOutputData deck) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("A deck with this title already exists.", errorMessage);
            }
        };

        UpdateDeckDetailsInputBoundary interactor = new UpdateDeckDetailsInteractor(userDAO, failurePresenter);
        interactor.execute(inputData);
    }

}
