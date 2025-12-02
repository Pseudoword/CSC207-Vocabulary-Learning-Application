package app;

import java.util.Collections;
import java.util.Random;
import data_access.FileUserDataAccessObject;
import data_access.FileDeckDataAccessObject;
import entity.Deck;
import entity.MultipleChoiceQuestion;
import entity.UserFactory;
import entity.Vocabulary;
import interface_adapter.StudyFlashCards.StudyFlashCardsController;
import interface_adapter.StudyFlashCards.StudyFlashCardsPresenter;
import interface_adapter.StudyFlashCards.StudyFlashCardsViewModel;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.ChangePasswordController;
import interface_adapter.logged_in.ChangePasswordPresenter;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.multiple_choice_quiz.MultipleChoiceQuizController;
import interface_adapter.multiple_choice_quiz.MultipleChoiceQuizPresenter;
import interface_adapter.multiple_choice_quiz.MultipleChoiceQuizViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.StudyFlashCards.StudyFlashCardsDataAccessInterface;
import use_case.StudyFlashCards.StudyFlashCardsInputBoundary;
import use_case.StudyFlashCards.StudyFlashCardsInteractor;
import use_case.StudyFlashCards.StudyFlashCardsOutputBoundary;
import use_case.change_password.ChangePasswordInputBoundary;
import use_case.change_password.ChangePasswordInteractor;
import use_case.change_password.ChangePasswordOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.multiple_choice_quiz.MultipleChoiceQuizInteractor;
import use_case.multiple_choice_quiz.MultipleChoiceQuizOutputBoundary;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import view.*;
import data_access.DictionaryAPIDataAccess;
import interface_adapter.add_flashcard_to_deck.AddFlashcardToDeckController;
import interface_adapter.add_flashcard_to_deck.AddFlashcardToDeckPresenter;
import interface_adapter.add_flashcard_to_deck.AddFlashcardToDeckViewModel;
import use_case.add_flashcard_to_deck.AddFlashcardToDeckInputBoundary;
import use_case.add_flashcard_to_deck.AddFlashcardToDeckInteractor;
import use_case.add_flashcard_to_deck.AddFlashcardToDeckOutputBoundary;
import interface_adapter.create_deck.CreateDeckController;
import interface_adapter.create_deck.CreateDeckPresenter;
import interface_adapter.create_deck.CreateDeckViewModel;
import use_case.create_deck.CreateDeckInputBoundary;
import use_case.create_deck.CreateDeckInteractor;
import use_case.create_deck.CreateDeckOutputBoundary;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();

    private final UserFactory userFactory = new UserFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();


    // set which data access implementation to use, can be any
    // of the classes from the data_access package

    // DAO version using local file storage
    final FileUserDataAccessObject userDataAccessObject = new FileUserDataAccessObject("users.csv", userFactory);

    // DAO version using file-based storage for persistence
    private final FileDeckDataAccessObject dataAccessObject = new FileDeckDataAccessObject();

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private final AddFlashcardToDeckViewModel addFlashcardToDeckViewModel = new AddFlashcardToDeckViewModel();
    private AddFlashcardToDeckView addFlashcardToDeckView;
    private final CreateDeckViewModel createDeckViewModel = new CreateDeckViewModel();
    private CreateDeckView createDeckView;
    private MultipleChoiceQuizViewModel multipleChoiceQuizViewModel;
    private MultipleChoiceQuizController multipleChoiceQuizController;
    private MultipleChoiceQuizInteractor multipleChoiceQuizInteractor;
    private ArrayList<MultipleChoiceQuestion> originalQuestions;
    private MultipleChoiceQuizView multipleChoiceQuizView;
    private DecksView decksView;
    private StudyFlashCardsViewModel studyFlashCardsViewModel;
    private StudyFlashCardsDataAccessInterface studyFlashCardsDataAccessInterface;
    private StudyFlashCardsOutputBoundary StudyFlashCardsOutputBoundary;
    private StudyFlashCardsInteractor StudyFlashCardsInteractor;
    private StudyFlashCardsView studyFlashCardsView;
    private StudyFlashCardsController studyFlashCardsController;
    private Deck currentDeck;




    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public List<Deck> getAllDecks() {
        return dataAccessObject.getAllDecks();
    }

    public void refreshDecksView() {
        // Remove old DecksView
        cardPanel.remove(decksView);

        // Create new DecksView (will use the same deck instances from allDecks)
        decksView = new DecksView(viewManagerModel, this);
        cardPanel.add(decksView, decksView.getViewName());

        // Refresh the panel
        cardPanel.revalidate();
        cardPanel.repaint();
    }

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, signupView.getViewName());
        return this;
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, loginView.getViewName());
        return this;
    }

    public AppBuilder addLoggedInView() {
        loggedInViewModel = new LoggedInViewModel();
        loggedInView = new LoggedInView(loggedInViewModel, viewManagerModel);
        cardPanel.add(loggedInView, loggedInView.getViewName());
        return this;
    }

    public AppBuilder addDecksView() {
        decksView = new DecksView(viewManagerModel, this);
        cardPanel.add(decksView, decksView.getViewName());
        return this;
    }

    public AppBuilder addStudyFlashCardsUseCaseForDeck(Deck deck) {
        this.currentDeck = deck;

        studyFlashCardsViewModel = new StudyFlashCardsViewModel();
        final StudyFlashCardsOutputBoundary outputBoundary = new StudyFlashCardsPresenter(viewManagerModel, studyFlashCardsViewModel);
        final StudyFlashCardsInputBoundary interactor = new StudyFlashCardsInteractor(userDataAccessObject, outputBoundary);
        StudyFlashCardsController controller = new StudyFlashCardsController(interactor);
        studyFlashCardsView = new StudyFlashCardsView(studyFlashCardsViewModel, controller, viewManagerModel, deck.getTitle());

        cardPanel.add(studyFlashCardsView, studyFlashCardsView.getViewName());
        cardPanel.revalidate();
        cardPanel.repaint();
        studyFlashCardsView.setController(controller);
        return this;
    }

    public void startStudyFlashCardsForDeck(Deck deck) {
        if (studyFlashCardsView != null) {
            cardPanel.remove(studyFlashCardsView);
        }

        addStudyFlashCardsUseCaseForDeck(deck);

        viewManagerModel.setState("StudyFlashCards");
        viewManagerModel.firePropertyChange();
    }

    public AppBuilder addMultipleChoiceQuizUseCaseForDeck(Deck deck) {
        this.currentDeck = deck;

        multipleChoiceQuizViewModel = new MultipleChoiceQuizViewModel();

        ArrayList<MultipleChoiceQuestion> questions = new ArrayList<>();
        Random random = new Random();
        for (Vocabulary vocab : deck.getVocabularies()) {
            // Create choices list with correct answer and dummy wrong answers
            List<String> choices = new ArrayList<>();
            choices.add(vocab.getDefinition()); // Correct answer
            choices.add("Wrong definition 1");
            choices.add("Wrong definition 2");
            choices.add("Wrong definition 3");

            // Shuffle the choices
            Collections.shuffle(choices, random);

            // Find the index of the correct answer after shuffling
            int correctIndex = choices.indexOf(vocab.getDefinition());

            questions.add(new MultipleChoiceQuestion(vocab, choices, correctIndex));
        }

        originalQuestions = questions;

        MultipleChoiceQuizOutputBoundary outputBoundary = new MultipleChoiceQuizPresenter(multipleChoiceQuizViewModel);
        multipleChoiceQuizInteractor = new MultipleChoiceQuizInteractor(questions, outputBoundary);
        multipleChoiceQuizController = new MultipleChoiceQuizController(multipleChoiceQuizInteractor);
        multipleChoiceQuizView = new MultipleChoiceQuizView(
                multipleChoiceQuizController,
                multipleChoiceQuizViewModel,
                viewManagerModel,
                this
        );

        cardPanel.add(multipleChoiceQuizView, multipleChoiceQuizView.getViewName());
        multipleChoiceQuizController.startQuiz(deck.getTitle());
        multipleChoiceQuizView.refreshView();

        return this;
    }

    public AppBuilder addMultipleChoiceQuizUseCase() {
        // Create a sample deck
        Deck sampleDeck = new Deck("Sample", "Sample deck");
        sampleDeck.addWord(new Vocabulary("apple", "A fruit that is typically red or green", false));
        sampleDeck.addWord(new Vocabulary("dog", "A common domesticated animal", false));
        sampleDeck.addWord(new Vocabulary("red", "The color of fire and blood", false));

        return addMultipleChoiceQuizUseCaseForDeck(sampleDeck);
    }
/// /
    // Update createRetakeQuiz to mark deck as taken and handle mastery
    public void createRetakeQuiz(List<MultipleChoiceQuestion> questions) {
        // Mark the deck as having been attempted
        if (currentDeck != null) {
            currentDeck.markQuizTaken();
        }

        // Remove old quiz view
        cardPanel.remove(multipleChoiceQuizView);

        // Reset the view model
        multipleChoiceQuizViewModel = new MultipleChoiceQuizViewModel();

        // Create new presenter
        MultipleChoiceQuizOutputBoundary outputBoundary =
                new MultipleChoiceQuizPresenter(multipleChoiceQuizViewModel);

        // Create new interactor with the questions (either incorrect or all)
        multipleChoiceQuizInteractor = new MultipleChoiceQuizInteractor(questions, outputBoundary);

        // Create new controller
        multipleChoiceQuizController =
                new MultipleChoiceQuizController(multipleChoiceQuizInteractor);

        // Create new view
        multipleChoiceQuizView = new MultipleChoiceQuizView(
                multipleChoiceQuizController,
                multipleChoiceQuizViewModel,
                viewManagerModel,
                this
        );

        // Add new view to card panel
        cardPanel.add(multipleChoiceQuizView, multipleChoiceQuizView.getViewName());

        // Revalidate and repaint the card panel
        cardPanel.revalidate();
        cardPanel.repaint();

        multipleChoiceQuizController.startQuiz(currentDeck != null ? currentDeck.getTitle() : "");
        multipleChoiceQuizView.refreshView();

        // Navigate to the new quiz
        viewManagerModel.setState("MultipleChoiceQuiz");
        viewManagerModel.firePropertyChange();
    }

    // Add method to start quiz from a deck
    public void startQuizForDeck(Deck deck) {
        // Remove old quiz if exists
        if (multipleChoiceQuizView != null) {
            cardPanel.remove(multipleChoiceQuizView);
        }

        // Create new quiz for this deck
        addMultipleChoiceQuizUseCaseForDeck(deck);

        // Navigate to quiz
        viewManagerModel.setState("MultipleChoiceQuiz");
        viewManagerModel.firePropertyChange();
    }

    public List<MultipleChoiceQuestion> getOriginalQuestions() {
        return new ArrayList<>(originalQuestions);
    }

    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);

        loggedInViewModel.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("state")) {
                LoggedInState state = (LoggedInState) evt.getNewValue();
                if (state.getUsername() != null && !state.getUsername().isEmpty()) {
                    dataAccessObject.setCurrentUser(state.getUsername());
                }
            }
        });

        return this;
    }

    public AppBuilder addChangePasswordUseCase() {
        final ChangePasswordOutputBoundary changePasswordOutputBoundary = new ChangePasswordPresenter(viewManagerModel,
                loggedInViewModel);

        final ChangePasswordInputBoundary changePasswordInteractor =
                new ChangePasswordInteractor(userDataAccessObject, changePasswordOutputBoundary, userFactory);

        ChangePasswordController changePasswordController = new ChangePasswordController(changePasswordInteractor);
        loggedInView.setChangePasswordController(changePasswordController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel,
                loggedInViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        loggedInView.setLogoutController(logoutController);
        return this;
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

    public AppBuilder addCreateDeckView() {
        createDeckView = new CreateDeckView(createDeckViewModel, viewManagerModel);
        cardPanel.add(createDeckView, createDeckView.getViewName());
        return this;
    }

    public AppBuilder addCreateDeckUseCase() {
        final CreateDeckOutputBoundary outputBoundary = new CreateDeckPresenter(
                viewManagerModel,
                createDeckViewModel
        );

        final CreateDeckInputBoundary interactor = new CreateDeckInteractor(
                dataAccessObject,
                outputBoundary
        );

        final CreateDeckController controller = new CreateDeckController(interactor);
        createDeckView.setController(controller);
        return this;
    }

    public void markCurrentDeckAsTaken() {
        if (currentDeck != null) {
            currentDeck.markQuizTaken();
        }
    }

    public JFrame build() {
        final JFrame application = new JFrame("Vocabulary Learning Application");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        new ViewManager(cardPanel, cardLayout, viewManagerModel);

        viewManagerModel.setState(signupView.getViewName());
        viewManagerModel.firePropertyChange();

        return application;
    }
}
