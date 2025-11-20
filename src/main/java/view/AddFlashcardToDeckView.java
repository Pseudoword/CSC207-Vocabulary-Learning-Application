package view;

import interface_adapter.add_flashcard_to_deck.AddFlashcardToDeckController;
import interface_adapter.add_flashcard_to_deck.AddFlashcardToDeckState;
import interface_adapter.add_flashcard_to_deck.AddFlashcardToDeckViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for adding a flashcard to a deck.
 */
public class AddFlashcardToDeckView extends JPanel implements ActionListener, PropertyChangeListener {

    private static final String viewName = "add flashcard";
    private final AddFlashcardToDeckViewModel viewModel;

    private final JTextField deckNameInputField = new JTextField(15);
    private final JTextField wordInputField = new JTextField(15);

    private final JButton addButton;
    private final JButton cancelButton;

    private AddFlashcardToDeckController controller;

    public AddFlashcardToDeckView(AddFlashcardToDeckViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(AddFlashcardToDeckViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel deckNameInfo = new LabelTextPanel(
                new JLabel(AddFlashcardToDeckViewModel.DECK_NAME_LABEL), deckNameInputField);
        final LabelTextPanel wordInfo = new LabelTextPanel(
                new JLabel(AddFlashcardToDeckViewModel.WORD_LABEL), wordInputField);

        final JPanel buttons = new JPanel();
        addButton = new JButton(AddFlashcardToDeckViewModel.ADD_BUTTON_LABEL);
        buttons.add(addButton);
        cancelButton = new JButton(AddFlashcardToDeckViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancelButton);

        addButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(addButton)) {
                            final AddFlashcardToDeckState currentState = viewModel.getState();

                            controller.execute(
                                    currentState.getDeckTitle(),
                                    currentState.getWord()
                            );
                        }
                    }
                }
        );

        cancelButton.addActionListener(this);

        // Add Listeners to update state when user types
        deckNameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final AddFlashcardToDeckState currentState = viewModel.getState();
                currentState.setDeckTitle(deckNameInputField.getText());
                viewModel.setState(currentState);
            }
            @Override public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });

        wordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final AddFlashcardToDeckState currentState = viewModel.getState();
                currentState.setWord(wordInputField.getText());
                viewModel.setState(currentState);
            }
            @Override public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }
            @Override public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(deckNameInfo);
        this.add(wordInfo);
        this.add(buttons);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        // Handle generic action events if necessary
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final AddFlashcardToDeckState state = (AddFlashcardToDeckState) evt.getNewValue();

        // Update text fields if state changes externally (e.g., cleared after success)
        deckNameInputField.setText(state.getDeckTitle());
        wordInputField.setText(state.getWord());

        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
            // We typically don't clear the error in the state immediately here,
            // effectively the next action cleans it up or we let the presenter handle it.
        } else if (state.getSuccessMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getSuccessMessage());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setController(AddFlashcardToDeckController controller) {
        this.controller = controller;
    }
}