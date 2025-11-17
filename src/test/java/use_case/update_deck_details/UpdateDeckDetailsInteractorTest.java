package use_case.update_deck_details;

import data_access.InMemoryUserDataAccessObject;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UpdateDeckDetailsInteractorTest {

    @Test
    void successfulUpdateDeckDetails() {
        UpdateDeckDetailsInputData inputData = new UpdateDeckDetailsInputData(
                "Old Title",
                "New Title",
                "New Description"
        );
        InMemoryUserDataAccessObject userDAO = new InMemoryUserDataAccessObject();

        UpdateDeckDetailsOutputBoundary successPresenter = new UpdateDeckDetailsOutputBoundary() {
            @Override
            public void prepareSuccessView(UpdateDeckDetailsOutputData deck) {
                assertEquals(newTitle, deck.getTitle());
                assertEquals(newDescription, deck.getDescription());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                fail("Use case failure is unexpected.");
            }
        };

        UpdateDeckDetailsInputBoundary interactor = new UpdateDeckDetailsInteractor(userDAO, successPresenter);
        interactor.execute();
    }

}
