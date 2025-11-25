package view;

import interface_adapter.multiple_choice_quiz.MultipleChoiceQuizController;
import interface_adapter.multiple_choice_quiz.MultipleChoiceQuizViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MultipleChoiceQuizView extends JPanel {

    private MultipleChoiceQuizController controller;
    private final MultipleChoiceQuizViewModel viewModel;

    private JLabel wordLabel;
    private JButton[] choiceButtons;
    private JLabel feedbackLabel;
    private JButton nextButton;

    public MultipleChoiceQuizView(MultipleChoiceQuizViewModel viewModel) {
        this.viewModel = viewModel;
        setupUI();
        updateUI();
    }

    public void setController(MultipleChoiceQuizController controller) {
        this.controller = controller;
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        wordLabel = new JLabel("", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(wordLabel, BorderLayout.NORTH);

        JPanel choicesPanel = new JPanel(new GridLayout(4, 1));
        choiceButtons = new JButton[4];

        for (int i = 0; i < 4; i++) {
            int index = i;
            choiceButtons[i] = new JButton();
            choiceButtons[i].addActionListener(e -> {
                if (controller != null) controller.answerSelected(index);
                updateUI();
            });
            choicesPanel.add(choiceButtons[i]);
        }

        add(choicesPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        nextButton = new JButton("Next");
        nextButton.addActionListener((ActionEvent e) -> {
            if (controller != null) controller.nextPressed();
            updateUI();
        });

        bottomPanel.add(feedbackLabel, BorderLayout.CENTER);
        bottomPanel.add(nextButton, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void updateUI() {
        super.updateUI();
        wordLabel.setText(viewModel.getCurrentWord());
        if (viewModel.getChoices() != null) {
            for (int i = 0; i < viewModel.getChoices().size(); i++) {
                choiceButtons[i].setText(viewModel.getChoices().get(i));
                choiceButtons[i].setEnabled(!viewModel.isQuizFinished());
            }
        }
        if (viewModel.getCorrectAnswer() != null) {
            feedbackLabel.setText("Correct answer: " + viewModel.getCorrectAnswer());
        } else {
            feedbackLabel.setText("");
        }
        nextButton.setEnabled(!viewModel.isQuizFinished());
        if (viewModel.isQuizFinished()) {
            wordLabel.setText("Quiz Finished!");
        }
    }

    public String getViewName() {
        return "multiple_choice_quiz";
    }
}
