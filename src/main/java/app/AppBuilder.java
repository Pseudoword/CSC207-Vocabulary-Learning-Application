package app;

import data_access.FileUserDataAccessObject;
import entity.MultipleChoiceQuestion;
import entity.UserFactory;
import entity.Vocabulary;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.ChangePasswordController;
import interface_adapter.logged_in.ChangePasswordPresenter;
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

    // DAO version using a shared external database
    private final DictionaryAPIDataAccess dataAccessObject = new DictionaryAPIDataAccess();

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private LoggedInViewModel loggedInViewModel;
    private LoggedInView loggedInView;
    private LoginView loginView;
    private final AddFlashcardToDeckViewModel addFlashcardToDeckViewModel = new AddFlashcardToDeckViewModel();
    private AddFlashcardToDeckView addFlashcardToDeckView;
    private MultipleChoiceQuizViewModel multipleChoiceQuizViewModel;
    private MultipleChoiceQuizController multipleChoiceQuizController;
    private MultipleChoiceQuizInteractor multipleChoiceQuizInteractor;
    private ArrayList<MultipleChoiceQuestion> originalQuestions;
    private MultipleChoiceQuizView multipleChoiceQuizView;
    private DecksView decksView;




    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
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
        decksView = new DecksView(viewManagerModel);
        cardPanel.add(decksView, decksView.getViewName());
        return this;
    }

    public AppBuilder addMultipleChoiceQuizUseCase() {
        multipleChoiceQuizViewModel = new MultipleChoiceQuizViewModel();

        originalQuestions = new ArrayList<>(List.of( // Store as originalQuestions
                new MultipleChoiceQuestion(
                        new Vocabulary("apple", "A fruit that is typically red or green", false),
                        List.of("A fruit that is typically red or green", "alt definition 1", "alt definition 2", "alt definition 3"),
                        0
                ),
                new MultipleChoiceQuestion(
                        new Vocabulary("dog", "A common domesticated animal", false),
                        List.of("alt definition 1", "A common domesticated animal", "alt definition 2", "alt definition 3"),
                        1
                ),
                new MultipleChoiceQuestion(
                        new Vocabulary("red", "The color of fire and blood", false),
                        List.of("The color of fire and blood", "alt definition 1", "alt definition 2", "alt definition 3"),
                        0
                )
        ));

        MultipleChoiceQuizOutputBoundary outputBoundary = new MultipleChoiceQuizPresenter(multipleChoiceQuizViewModel);
        multipleChoiceQuizInteractor = new MultipleChoiceQuizInteractor(originalQuestions, outputBoundary);
        multipleChoiceQuizController = new MultipleChoiceQuizController(multipleChoiceQuizInteractor);
        multipleChoiceQuizView = new MultipleChoiceQuizView(
                multipleChoiceQuizController,
                multipleChoiceQuizViewModel,
                viewManagerModel,
                this
        );

        cardPanel.add(multipleChoiceQuizView, multipleChoiceQuizView.getViewName());

        return this;
    }

    public List<MultipleChoiceQuestion> getOriginalQuestions() {
        return new ArrayList<>(originalQuestions);
    }

    public void createRetakeQuiz(List<MultipleChoiceQuestion> questions) {
        cardPanel.remove(multipleChoiceQuizView);

        multipleChoiceQuizViewModel = new MultipleChoiceQuizViewModel();
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
        cardPanel.revalidate();
        cardPanel.repaint();

        viewManagerModel.setState("MultipleChoiceQuiz");
        viewManagerModel.firePropertyChange();
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
