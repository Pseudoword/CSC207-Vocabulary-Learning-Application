package app;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addSignupView()
                .addLoginView()
                .addLoggedInView()
                .addDecksView()
                .addSignupUseCase()
                .addLoginUseCase()
                .addChangePasswordUseCase()
                .addLogoutUseCase()
                .addAddFlashcardToDeckView()
                .addAddFlashcardToDeckUseCase()
                .addUpdateDeckDetailsView()
                .addUpdateDeckDetailsUseCase()
                .addMultipleChoiceQuizUseCase()
                .build();

       application.pack();
       application.setLocationRelativeTo(null);
       application.setVisible(true);
    }
}