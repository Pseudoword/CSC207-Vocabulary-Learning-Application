package view;

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
    private CreateDeckController createDeckController;  // Remove 'final'

    private final JTextField deckTitleInputField = new JTextField(20);
    private final JTextArea descriptionInputArea = new JTextArea(3, 20);
    private final JLabel errorMessageLabel = new JLabel();
    private final JLabel successMessageLabel = new JLabel();

    private final JButton createButton;
    private final JButton cancelButton;

    public CreateDeckView(CreateDeckViewModel createDeckViewModel) {
        this.createDeckViewModel = createDeckViewModel;
        this.createDeckViewModel.addPropertyChangeListener(this);

        // Title
        final JLabel title = new JLabel(CreateDeckViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 20));

        // Deck Title Input
        final LabelTextPanel deckTitleInfo = new LabelTextPanel(
                new JLabel(CreateDeckViewModel.DECK_TITLE_LABEL), deckTitleInputField);

        // Description Input
        final JPanel descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BorderLayout());
        final JLabel descriptionLabel = new JLabel(CreateDeckViewModel.DESCRIPTION_LABEL);
        descriptionPanel.add(descriptionLabel, BorderLayout.NORTH);

        descriptionInputArea.setLineWrap(true);
        descriptionInputArea.setWrapStyleWord(true);
        descriptionInputArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        final JScrollPane descriptionScrollPane = new JScrollPane(descriptionInputArea);
        descriptionPanel.add(descriptionScrollPane, BorderLayout.CENTER);

        // Buttons
        final JPanel buttons = new JPanel();
        createButton = new JButton(CreateDeckViewModel.CREATE_BUTTON_LABEL);
        buttons.add(createButton);
        cancelButton = new JButton(CreateDeckViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancelButton);

        createButton.addActionListener(this);
        cancelButton.addActionListener(this);

        // Error and Success Messages
        errorMessageLabel.setForeground(Color.RED);
        errorMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        successMessageLabel.setForeground(new Color(0, 128, 0));
        successMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Layout
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalStrut(20));
        this.add(title);
        this.add(Box.createVerticalStrut(20));
        this.add(deckTitleInfo);
        this.add(Box.createVerticalStrut(10));
        this.add(descriptionPanel);
        this.add(Box.createVerticalStrut(20));
        this.add(buttons);
        this.add(Box.createVerticalStrut(10));
        this.add(errorMessageLabel);
        this.add(successMessageLabel);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(createButton)) {
            final String deckTitle = deckTitleInputField.getText();
            final String description = descriptionInputArea.getText();

            createDeckController.execute(deckTitle, description);
        }
        else if (evt.getSource().equals(cancelButton)) {
            deckTitleInputField.setText("");
            descriptionInputArea.setText("");
            errorMessageLabel.setText("");
            successMessageLabel.setText("");
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