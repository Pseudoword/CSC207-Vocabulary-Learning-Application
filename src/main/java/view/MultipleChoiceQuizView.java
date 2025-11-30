package view;

import app.AppBuilder;
import interface_adapter.ViewManagerModel;
import interface_adapter.multiple_choice_quiz.MultipleChoiceQuizController;
import interface_adapter.multiple_choice_quiz.MultipleChoiceQuizViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultipleChoiceQuizView extends JPanel {

    private MultipleChoiceQuizController controller;
    private final MultipleChoiceQuizViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final AppBuilder appBuilder;

    private JLabel questionLabel;
    private JButton[] choiceButtons;
    private JButton nextButton;
    private JButton retakeButton;
    private JButton mainMenuButton;
    private boolean answerSelected = false;

    public MultipleChoiceQuizView(MultipleChoiceQuizController multipleChoiceQuizController,
                                  MultipleChoiceQuizViewModel viewModel,
                                  ViewManagerModel viewManagerModel,
                                  AppBuilder appBuilder) {
        this.controller = multipleChoiceQuizController;
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.appBuilder = appBuilder;
        setupUI();
        refreshView();
    }

    private void setupUI() {
        JPanel bottomPanel;
        JPanel choicesPanel;
        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        setBackground(new Color(255, 240, 245));

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(255, 240, 245));
        topPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setFont(new Font("Arial", Font.BOLD, 28));
        questionLabel.setForeground(new Color(199, 21, 133));
        topPanel.add(questionLabel, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        // Four choice buttons in the center
        choicesPanel = new JPanel(new GridLayout(4, 1, 15, 15));
        choicesPanel.setBackground(new Color(255, 240, 245));
        choicesPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        choiceButtons = new JButton[4];

        for (int i = 0; i < 4; i++) {
            int index = i;
            choiceButtons[i] = new JButton();
            choiceButtons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            choiceButtons[i].setPreferredSize(new Dimension(500, 70));
            choiceButtons[i].setBackground(Color.WHITE);
            choiceButtons[i].setForeground(Color.BLACK);
            choiceButtons[i].setFocusPainted(false);
            choiceButtons[i].setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(199, 21, 133), 2),
                    BorderFactory.createEmptyBorder(10, 20, 10, 20)
            ));

            choiceButtons[i].addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    if (choiceButtons[index].isEnabled()) {
                        choiceButtons[index].setBackground(new Color(255, 240, 245));
                        choiceButtons[index].setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (choiceButtons[index].isEnabled() && !answerSelected) {
                        choiceButtons[index].setBackground(Color.WHITE);
                        choiceButtons[index].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            });

            choiceButtons[i].addActionListener(e -> {
                if (!answerSelected && controller != null) {
                    handleAnswerSelection(index);
                }
            });
            choicesPanel.add(choiceButtons[i]);
        }

        add(choicesPanel, BorderLayout.CENTER);

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        bottomPanel.setBackground(new Color(255, 240, 245));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 18));
        nextButton.setPreferredSize(new Dimension(150, 50));
        nextButton.setBackground(new Color(255, 105, 180));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setBorderPainted(false);
        nextButton.setVisible(false);

        addButtonHoverEffect(nextButton, new Color(255, 105, 180));

        nextButton.addActionListener((ActionEvent e) -> {
            if (controller != null) {
                controller.nextPressed();
                answerSelected = false;
                nextButton.setVisible(false);
                refreshView();
            }
        });

        retakeButton = new JButton("Retake Incorrect");
        retakeButton.setFont(new Font("Arial", Font.BOLD, 18));
        retakeButton.setPreferredSize(new Dimension(200, 50));
        retakeButton.setBackground(new Color(255, 165, 0));
        retakeButton.setForeground(Color.WHITE);
        retakeButton.setFocusPainted(false);
        retakeButton.setBorderPainted(false);
        retakeButton.setVisible(false);

        addButtonHoverEffect(retakeButton, new Color(255, 165, 0));

        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setFont(new Font("Arial", Font.BOLD, 18));
        mainMenuButton.setPreferredSize(new Dimension(150, 50));
        mainMenuButton.setBackground(new Color(255, 105, 180));
        mainMenuButton.setForeground(Color.WHITE);
        mainMenuButton.setFocusPainted(false);
        mainMenuButton.setBorderPainted(false);
        mainMenuButton.setVisible(false);

        addButtonHoverEffect(mainMenuButton, new Color(255, 105, 180));

        mainMenuButton.addActionListener(e -> {
            if (appBuilder != null) {
                appBuilder.markCurrentDeckAsTaken();
            }
            viewManagerModel.setState("logged in");
            viewManagerModel.firePropertyChange();
        });

        bottomPanel.add(nextButton);
        bottomPanel.add(retakeButton);
        bottomPanel.add(mainMenuButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addButtonHoverEffect(JButton button, Color originalColor) {
        Color hoverColor = originalColor.darker();

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button.isVisible() && button.isEnabled()) {
                    button.setBackground(hoverColor);
                    button.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button.isVisible() && button.isEnabled()) {
                    button.setBackground(originalColor);
                    button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    private void handleAnswerSelection(int selectedIndex) {
        int correctAnswerIndex;
        answerSelected = true;
        controller.answerSelected(selectedIndex);

        correctAnswerIndex = getCorrectAnswerIndex();

        if (selectedIndex == correctAnswerIndex) {
            choiceButtons[selectedIndex].setBackground(new Color(144, 238, 144));
        } else {
            choiceButtons[selectedIndex].setBackground(new Color(255, 99, 71));
            choiceButtons[correctAnswerIndex].setBackground(new Color(144, 238, 144));
        }

        for (JButton button : choiceButtons) {
            button.setEnabled(false);
        }

        nextButton.setVisible(true);
    }

    private int getCorrectAnswerIndex() {
        String correctAnswer = viewModel.getCorrectAnswer();
        if (correctAnswer != null && viewModel.getChoices() != null) {
            for (int i = 0; i < viewModel.getChoices().size(); i++) {
                if (viewModel.getChoices().get(i).equals(correctAnswer)) {
                    return i;
                }
            }
        }
        return 0;
    }

    public void refreshView() {
        for (JButton button : choiceButtons) {
            button.setBackground(Color.WHITE);
            button.setEnabled(!viewModel.isQuizFinished());
            button.setVisible(true);
        }

        questionLabel.setText(viewModel.getCurrentWord());

        if (viewModel.getChoices() != null) {
            for (int i = 0; i < viewModel.getChoices().size(); i++) {
                choiceButtons[i].setText(viewModel.getChoices().get(i));
            }
        }

        if (viewModel.isQuizFinished()) {
            showFinishScreen();
        }
    }

    private void showFinishScreen() {
        questionLabel.setText("Quiz Finished! Great job!");

        // Hide choice buttons
        for (JButton button : choiceButtons) {
            button.setVisible(false);
        }

        nextButton.setVisible(false);

        if (viewModel.hasMistakes()) {
            retakeButton.setText("Retake Incorrect");
            for (ActionListener al : retakeButton.getActionListeners()) {
                retakeButton.removeActionListener(al);
            }
            retakeButton.addActionListener(e -> {
                if (controller != null) {
                    appBuilder.createRetakeQuiz(controller.getIncorrectQuestions());
                }
            });
        } else {
            retakeButton.setText("Retake Quiz");
            for (ActionListener al : retakeButton.getActionListeners()) {
                retakeButton.removeActionListener(al);
            }
            retakeButton.addActionListener(e ->
                appBuilder.createRetakeQuiz(appBuilder.getOriginalQuestions()) // Use original questions!
            );
        }

        retakeButton.setVisible(true);
        mainMenuButton.setVisible(true);
    }

    public String getViewName() {
        return "MultipleChoiceQuiz";
    }
}