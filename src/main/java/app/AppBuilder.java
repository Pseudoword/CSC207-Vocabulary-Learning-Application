package app;

import data_access.DictionaryAPIDataAccess;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_flashcard_to_deck.AddFlashcardToDeckController;
import interface_adapter.add_flashcard_to_deck.AddFlashcardToDeckPresenter;
import interface_adapter.add_flashcard_to_deck.AddFlashcardToDeckViewModel;
import use_case.add_flashcard_to_deck.AddFlashcardToDeckInputBoundary;
import use_case.add_flashcard_to_deck.AddFlashcardToDeckInteractor;
import use_case.add_flashcard_to_deck.AddFlashcardToDeckOutputBoundary;
import view.AddFlashcardToDeckView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    // Instantiate the custom ViewManagerModel and ViewManager we just created
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // DAO
    private final DictionaryAPIDataAccess dictionaryAPIDataAccess = new DictionaryAPIDataAccess();

    // Add Flashcard Feature Components
    private AddFlashcardToDeckViewModel addFlashcardToDeckViewModel;
    private AddFlashcardToDeckView addFlashcardToDeckView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * adds the AddFlashcardToDeckView to the application.
     */
    public AppBuilder addAddFlashcardToDeckView() {
        addFlashcardToDeckViewModel = new AddFlashcardToDeckViewModel();
        addFlashcardToDeckView = new AddFlashcardToDeckView(addFlashcardToDeckViewModel);
        cardPanel.add(addFlashcardToDeckView, addFlashcardToDeckView.getViewName());
        return this;
    }

    /**
     * adds the AddFlashcardToDeck Use Case to the application.
     */
    public AppBuilder addAddFlashcardToDeckUseCase() {
        final AddFlashcardToDeckOutputBoundary outputBoundary = new AddFlashcardToDeckPresenter(viewManagerModel,
                addFlashcardToDeckViewModel);

        final AddFlashcardToDeckInputBoundary interactor = new AddFlashcardToDeckInteractor(
                dictionaryAPIDataAccess, outputBoundary);

        final AddFlashcardToDeckController controller = new AddFlashcardToDeckController(interactor);
        addFlashcardToDeckView.setController(controller);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Vocabulary Learning - Add Flashcard Demo");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        // Automatically switch to the Add Flashcard view
        viewManagerModel.setState(addFlashcardToDeckView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }
}