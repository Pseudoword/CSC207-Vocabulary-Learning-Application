package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_deck.CreateDeckController;
import interface_adapter.create_deck.CreateDeckState;
import interface_adapter.create_deck.CreateDeckViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class CreateDeckView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "create deck";
    private final CreateDeckViewModel createDeckViewModel;
    private final ViewManagerModel viewManagerModel;
    private CreateDeckController createDeckController;

    private final JTextField deckTitleInputField = new JTextField(20);
    private final JTextArea descriptionInputArea = new JTextArea(3, 20);
    private final JLabel errorMessageLabel = new JLabel();
    private final JLabel successMessageLabel = new JLabel();

    private final JButton createButton;
    private final JButton cancelButton;

    public CreateDeckView(CreateDeckViewModel createDeckViewModel, ViewManagerModel viewManagerModel) {
        this.createDeckViewModel = createDeckViewModel;
        this.viewManagerModel = viewManagerModel;
        this.createDeckViewModel.addPropertyChangeListener(this);

        // Set panel properties with pink theme
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setPreferredSize(new Dimension(900, 700));
        this.setBackground(new Color(255, 240, 245));

        // Title
        final JLabel title = new JLabel(CreateDeckViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(199, 21, 133));

        // Deck Title Input
        JPanel titleInputPanel = new JPanel();
        titleInputPanel.setBackground(new Color(255, 240, 245));
        JLabel deckTitleLabel = new JLabel(CreateDeckViewModel.DECK_TITLE_LABEL);
        deckTitleLabel.setForeground(new Color(199, 21, 133));
        deckTitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        deckTitleInputField.setFont(new Font("Arial", Font.PLAIN, 14));
        deckTitleInputField.setPreferredSize(new Dimension(300, 30));
        titleInputPanel.add(deckTitleLabel);
        titleInputPanel.add(deckTitleInputField);

        // Description Input
        final JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BoxLayout(descriptionPanel, BoxLayout.Y_AXIS));
        descriptionPanel.setBackground(new Color(255, 240, 245));
        descriptionPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel descriptionLabel = new JLabel(CreateDeckViewModel.DESCRIPTION_LABEL);
        descriptionLabel.setForeground(new Color(199, 21, 133));
        descriptionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        descriptionInputArea.setLineWrap(true);
        descriptionInputArea.setWrapStyleWord(true);
        descriptionInputArea.setFont(new Font("Arial", Font.PLAIN, 14));
        descriptionInputArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(199, 21, 133), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));

        final JScrollPane descriptionScrollPane = new JScrollPane(descriptionInputArea);
        descriptionScrollPane.setPreferredSize(new Dimension(400, 100));
        descriptionScrollPane.setMaximumSize(new Dimension(400, 100));

        descriptionPanel.add(descriptionLabel);
        descriptionPanel.add(Box.createVerticalStrut(10));
        descriptionPanel.add(descriptionScrollPane);

        // Buttons
        Dimension buttonSize = new Dimension(150, 40);
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);

        createButton = createPinkButton(CreateDeckViewModel.CREATE_BUTTON_LABEL, buttonSize, buttonFont);
        cancelButton = createPinkButton(CreateDeckViewModel.CANCEL_BUTTON_LABEL, buttonSize, buttonFont);

        createButton.addActionListener(this);
        cancelButton.addActionListener(this);

        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setBackground(new Color(255, 240, 245));
        buttonPanel.add(createButton);
        buttonPanel.add(cancelButton);

        // Error and Success Messages
        errorMessageLabel.setForeground(Color.RED);
        errorMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        errorMessageLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        successMessageLabel.setForeground(new Color(0, 128, 0));
        successMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        successMessageLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        // Layout
        this.add(Box.createVerticalStrut(60));
        this.add(title);
        this.add(Box.createVerticalStrut(40));
        this.add(titleInputPanel);
        this.add(Box.createVerticalStrut(20));
        this.add(descriptionPanel);
        this.add(Box.createVerticalStrut(30));
        this.add(buttonPanel);
        this.add(Box.createVerticalStrut(20));
        this.add(errorMessageLabel);
        this.add(Box.createVerticalStrut(5));
        this.add(successMessageLabel);
        this.add(Box.createVerticalGlue());
    }

    private JButton createPinkButton(String text, Dimension size, Font font) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setFont(font);
        button.setBackground(new Color(255, 105, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(199, 21, 133), 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(219, 112, 147));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 105, 180));
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        return button;
    }


    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(createButton)) {
            final String deckTitle = deckTitleInputField.getText();
            final String description = descriptionInputArea.getText();

            if (createDeckController != null) {
                createDeckController.execute(deckTitle, description);
            }

        }
        else if (evt.getSource().equals(cancelButton)) {
            deckTitleInputField.setText("");
            descriptionInputArea.setText("");
            errorMessageLabel.setText("");
            successMessageLabel.setText("");

            viewManagerModel.setState("logged in");
            viewManagerModel.firePropertyChange();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final CreateDeckState state = (CreateDeckState) evt.getNewValue();

        if (state.getError() != null && !state.getError().isEmpty()) {
            errorMessageLabel.setText(state.getError());
            successMessageLabel.setText("");
        }
        else if (state.getSuccessMessage() != null && !state.getSuccessMessage().isEmpty()) {
            successMessageLabel.setText(state.getSuccessMessage());
            errorMessageLabel.setText("");

            deckTitleInputField.setText("");
            descriptionInputArea.setText("");
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setController(CreateDeckController controller) {
        this.createDeckController = controller;
    }
}