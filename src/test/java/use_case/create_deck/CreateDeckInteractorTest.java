package use_case.create_deck;

import entity.Deck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateDeckInteractorTest {

    private TestCreateDeckDataAccess dataAccess;
    private TestCreateDeckPresenter presenter;
    private CreateDeckInteractor interactor;

    @BeforeEach
    void setUp() {
        dataAccess = new TestCreateDeckDataAccess();
        presenter = new TestCreateDeckPresenter();
        interactor = new CreateDeckInteractor(dataAccess, presenter);
    }

    @Test
    void testSuccessfulDeckCreation() {
        final String deckTitle = "Vocabulary Deck";
        final String description = "A deck for learning vocabulary";
        final CreateDeckInputData inputData = new CreateDeckInputData(deckTitle, description);

        interactor.execute(inputData);

        assertTrue(presenter.isSuccessViewPrepared(), "Success view should be prepared");
        assertFalse(presenter.isFailViewPrepared(), "Fail view should not be prepared");

        final CreateDeckOutputData outputData = presenter.getOutputData();
        assertNotNull(outputData, "Output data should not be null");
        assertEquals(deckTitle, outputData.getDeckTitle(), "Deck title should match");
        assertEquals(description, outputData.getDescription(), "Description should match");

        assertTrue(dataAccess.isSaveCalled(), "Save should have been called");
        final Deck savedDeck = dataAccess.getSavedDeck();
        assertNotNull(savedDeck, "Saved deck should not be null");
        assertEquals(deckTitle, savedDeck.getTitle(), "Saved deck title should match");
        assertEquals(description, savedDeck.getDescription(), "Saved deck description should match");
    }

    @Test
    void testSuccessfulDeckCreationWithEmptyDescription() {
        final String deckTitle = "Math Deck";
        final String description = "";
        final CreateDeckInputData inputData = new CreateDeckInputData(deckTitle, description);

        interactor.execute(inputData);

        assertTrue(presenter.isSuccessViewPrepared(), "Success view should be prepared");
        assertFalse(presenter.isFailViewPrepared(), "Fail view should not be prepared");

        final CreateDeckOutputData outputData = presenter.getOutputData();
        assertEquals(deckTitle, outputData.getDeckTitle(), "Deck title should match");
        assertEquals("", outputData.getDescription(), "Description should be empty string");

        final Deck savedDeck = dataAccess.getSavedDeck();
        assertEquals("", savedDeck.getDescription(), "Saved deck should have empty description");
    }

    @Test
    void testSuccessfulDeckCreationWithNullDescription() {
        final String deckTitle = "Science Deck";
        final String description = null;
        final CreateDeckInputData inputData = new CreateDeckInputData(deckTitle, description);

        interactor.execute(inputData);

        assertTrue(presenter.isSuccessViewPrepared(), "Success view should be prepared");
        assertFalse(presenter.isFailViewPrepared(), "Fail view should not be prepared");

        final CreateDeckOutputData outputData = presenter.getOutputData();
        assertEquals(deckTitle, outputData.getDeckTitle(), "Deck title should match");
        assertEquals("", outputData.getDescription(), "Null description should become empty string");

        final Deck savedDeck = dataAccess.getSavedDeck();
        assertEquals("", savedDeck.getDescription(), "Saved deck should have empty description");
    }

    @Test
    void testDeckCreationWithNullTitle() {
        final String deckTitle = null;
        final String description = "Some description";
        final CreateDeckInputData inputData = new CreateDeckInputData(deckTitle, description);

        interactor.execute(inputData);

        assertFalse(presenter.isSuccessViewPrepared(), "Success view should not be prepared");
        assertTrue(presenter.isFailViewPrepared(), "Fail view should be prepared");
        assertEquals("Deck title cannot be empty.", presenter.getErrorMessage());
        assertFalse(dataAccess.isSaveCalled(), "Save should not have been called");
    }

    @Test
    void testDeckCreationWithEmptyTitle() {
        final String deckTitle = "";
        final String description = "Some description";
        final CreateDeckInputData inputData = new CreateDeckInputData(deckTitle, description);

        interactor.execute(inputData);

        assertFalse(presenter.isSuccessViewPrepared(), "Success view should not be prepared");
        assertTrue(presenter.isFailViewPrepared(), "Fail view should be prepared");
        assertEquals("Deck title cannot be empty.", presenter.getErrorMessage());
        assertFalse(dataAccess.isSaveCalled(), "Save should not have been called");
    }

    @Test
    void testDeckCreationWithWhitespaceOnlyTitle() {
        final String deckTitle = "   ";
        final String description = "Some description";
        final CreateDeckInputData inputData = new CreateDeckInputData(deckTitle, description);

        interactor.execute(inputData);

        assertFalse(presenter.isSuccessViewPrepared(), "Success view should not be prepared");
        assertTrue(presenter.isFailViewPrepared(), "Fail view should be prepared");
        assertEquals("Deck title cannot be empty.", presenter.getErrorMessage());
        assertFalse(dataAccess.isSaveCalled(), "Save should not have been called");
    }

    @Test
    void testDeckCreationWithDuplicateTitle() {
        final String deckTitle = "Existing Deck";
        final String description = "Description";
        dataAccess.addExistingDeck(deckTitle);
        final CreateDeckInputData inputData = new CreateDeckInputData(deckTitle, description);

        interactor.execute(inputData);

        assertFalse(presenter.isSuccessViewPrepared(), "Success view should not be prepared");
        assertTrue(presenter.isFailViewPrepared(), "Fail view should be prepared");
        assertEquals("Deck 'Existing Deck' already exists.", presenter.getErrorMessage());
        assertFalse(dataAccess.isSaveCalled(), "Save should not have been called");
    }

    @Test
    void testDeckCreationWithTitleHavingLeadingTrailingSpaces() {
        final String deckTitle = "  History Deck  ";
        final String description = "Ancient history";
        final CreateDeckInputData inputData = new CreateDeckInputData(deckTitle, description);

        interactor.execute(inputData);

        assertTrue(presenter.isSuccessViewPrepared(), "Success view should be prepared");
        assertFalse(presenter.isFailViewPrepared(), "Fail view should not be prepared");

        final Deck savedDeck = dataAccess.getSavedDeck();
        assertEquals("  History Deck  ", savedDeck.getTitle());
    }

    @Test
    void testMultipleDeckCreations() {
        interactor.execute(new CreateDeckInputData("Deck 1", "First deck"));
        interactor.execute(new CreateDeckInputData("Deck 2", "Second deck"));
        interactor.execute(new CreateDeckInputData("Deck 3", "Third deck"));

        assertTrue(presenter.isSuccessViewPrepared(), "Last operation should be successful");
        assertEquals(3, dataAccess.getSaveCallCount(), "Save should be called 3 times");
    }

    @Test
    void testDeckCreationAfterFailedAttempt() {
        interactor.execute(new CreateDeckInputData("", "Description"));
        assertTrue(presenter.isFailViewPrepared(), "First attempt should fail");

        presenter.reset();

        interactor.execute(new CreateDeckInputData("Valid Deck", "Description"));

        assertTrue(presenter.isSuccessViewPrepared(), "Second attempt should succeed");
        assertFalse(presenter.isFailViewPrepared(), "Fail view should not be prepared");
        assertTrue(dataAccess.isSaveCalled(), "Save should have been called");
    }

    private static class TestCreateDeckDataAccess implements CreateDeckDataAccessInterface {
        private boolean saveCalled = false;
        private Deck savedDeck = null;
        private int saveCallCount = 0;
        private final java.util.Set<String> existingDecks = new java.util.HashSet<>();

        @Override
        public boolean existsByTitle(String deckTitle) {
            return existingDecks.contains(deckTitle);
        }

        @Override
        public void save(Deck deck) {
            this.saveCalled = true;
            this.savedDeck = deck;
            this.saveCallCount++;
            existingDecks.add(deck.getTitle());
        }

        public boolean isSaveCalled() {
            return saveCalled;
        }

        public Deck getSavedDeck() {
            return savedDeck;
        }

        public int getSaveCallCount() {
            return saveCallCount;
        }

        public void addExistingDeck(String deckTitle) {
            existingDecks.add(deckTitle);
        }
    }

    private static class TestCreateDeckPresenter implements CreateDeckOutputBoundary {
        private boolean successViewPrepared = false;
        private boolean failViewPrepared = false;
        private CreateDeckOutputData outputData = null;
        private String errorMessage = null;

        @Override
        public void prepareSuccessView(CreateDeckOutputData outputData) {
            this.successViewPrepared = true;
            this.failViewPrepared = false;
            this.outputData = outputData;
            this.errorMessage = null;
        }

        @Override
        public void prepareFailView(String error) {
            this.failViewPrepared = true;
            this.successViewPrepared = false;
            this.errorMessage = error;
            this.outputData = null;
        }

        public boolean isSuccessViewPrepared() {
            return successViewPrepared;
        }

        public boolean isFailViewPrepared() {
            return failViewPrepared;
        }

        public CreateDeckOutputData getOutputData() {
            return outputData;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public void reset() {
            this.successViewPrepared = false;
            this.failViewPrepared = false;
            this.outputData = null;
            this.errorMessage = null;
        }
    }
}