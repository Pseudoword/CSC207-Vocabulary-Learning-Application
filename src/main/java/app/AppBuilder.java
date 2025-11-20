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

    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final AddFlashcardToDeckViewModel addFlashcardToDeckViewModel = new AddFlashcardToDeckViewModel();

    private final DictionaryAPIDataAccess dataAccessObject = new DictionaryAPIDataAccess();

    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private AddFlashcardToDeckView addFlashcardToDeckView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addAddFlashcardToDeckView() {
        addFlashcardToDeckView = new AddFlashcardToDeckView(addFlashcardToDeckViewModel);
        cardPanel.add(addFlashcardToDeckView, addFlashcardToDeckView.getViewName());
        return this;
    }

    public AppBuilder addAddFlashcardToDeckUseCase() {
        final AddFlashcardToDeckOutputBoundary outputBoundary = new AddFlashcardToDeckPresenter(
                viewManagerModel,
                addFlashcardToDeckViewModel
        );

        final AddFlashcardToDeckInputBoundary interactor = new AddFlashcardToDeckInteractor(
                dataAccessObject,
                outputBoundary
        );

        final AddFlashcardToDeckController controller = new AddFlashcardToDeckController(interactor);
        addFlashcardToDeckView.setController(controller);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Vocabulary Learning - Add Flashcard Demo");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(addFlashcardToDeckView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }
}