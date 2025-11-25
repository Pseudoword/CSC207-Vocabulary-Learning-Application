package view;

import interface_adapter.multiple_choice_quiz.MultipleChoiceQuizController;
import interface_adapter.multiple_choice_quiz.MultipleChoiceQuizViewModel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MultipleChoiceQuizView extends JFrame {

    private final MultipleChoiceQuizController controller;
    private final MultipleChoiceQuizViewModel viewModel;

    private JLabel wordLabel;
    private JButton[] choiceButtons;
    private JLabel feedbackLabel;
    private JButton nextButton;

    public MultipleChoiceQuizView(MultipleChoiceQuizController controller, MultipleChoiceQuizViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
        setupUI();
        updateUI();
    }

    private void setupUI() {
        setTitle("Multiple Choice Quiz");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        wordLabel = new JLabel("", SwingConstants.CENTER);
        wordLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(wordLabel, BorderLayout.NORTH);

        JPanel choicesPanel = new JPanel();
        choicesPanel.setLayout(new GridLayout(4, 1));
        choiceButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            int index = i;
            choiceButtons[i] = new JButton();
            choiceButtons[i].addActionListener(e -> {
                controller.answerSelected(index);
                updateUI();
            });
            choicesPanel.add(choiceButtons[i]);
        }
        add(choicesPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        feedbackLabel = new JLabel("", SwingConstants.CENTER);
        nextButton = new JButton("Next");
        nextButton.addActionListener((ActionEvent e) -> {
            controller.nextPressed();
            updateUI();
        });

        bottomPanel.add(feedbackLabel, BorderLayout.CENTER);
        bottomPanel.add(nextButton, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void updateUI() {
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
}
