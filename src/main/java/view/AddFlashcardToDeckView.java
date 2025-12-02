// src/main/java/view/AddFlashcardToDeckView.java
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

public class AddFlashcardToDeckView extends JPanel implements ActionListener, PropertyChangeListener {

    private static final String viewName = "add flashcard";
    private final AddFlashcardToDeckViewModel viewModel;

    private final JTextField deckNameInputField = new JTextField(15);
    private final JTextField wordInputField = new JTextField(15);

    private final JButton addButton;
    private final JButton backButton;

    private AddFlashcardToDeckController controller;

    public AddFlashcardToDeckView(AddFlashcardToDeckViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        this.setPreferredSize(new Dimension(600, 400));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(255, 240, 245));

        final JLabel title = new JLabel(AddFlashcardToDeckViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(199, 21, 133));

        final LabelTextPanel deckNameInfo = new LabelTextPanel(
                new JLabel(AddFlashcardToDeckViewModel.DECK_NAME_LABEL), deckNameInputField);
        final LabelTextPanel wordInfo = new LabelTextPanel(
                new JLabel(AddFlashcardToDeckViewModel.WORD_LABEL), wordInputField);

        deckNameInfo.setOpaque(false);
        wordInfo.setOpaque(false);

        deckNameInputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        wordInputField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        deckNameInputField.setBackground(Color.WHITE);
        wordInputField.setBackground(Color.WHITE);

        final JPanel buttons = new JPanel();
        buttons.setOpaque(false);
        buttons.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        Dimension buttonSize = new Dimension(200, 40);
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);

        addButton = createPinkButton(AddFlashcardToDeckViewModel.ADD_BUTTON_LABEL, buttonSize, buttonFont);
        backButton = createPinkButton(AddFlashcardToDeckViewModel.CANCEL_BUTTON_LABEL, buttonSize, buttonFont);
        buttons.add(addButton);
        buttons.add(backButton);

        addButton.addActionListener(evt -> {
            if (controller == null) {
                JOptionPane.showMessageDialog(this, "AddFlashcardToDeckController not set", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            final AddFlashcardToDeckState currentState = viewModel.getState();
            controller.execute(
                    currentState.getDeckTitle(),
                    currentState.getWord()
            );
        });

        backButton.addActionListener(evt -> switchToEditDeckView());

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

        this.add(Box.createVerticalStrut(30));
        this.add(title);
        this.add(Box.createVerticalStrut(30));
        this.add(deckNameInfo);
        this.add(Box.createVerticalStrut(15));
        this.add(wordInfo);
        this.add(Box.createVerticalStrut(30));
        this.add(buttons);
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
        // Optional: hook up cancel behavior through controller or view manager if needed
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final AddFlashcardToDeckState state = (AddFlashcardToDeckState) evt.getNewValue();

        deckNameInputField.setText(state.getDeckTitle());
        wordInputField.setText(state.getWord());

        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
            state.setError(null);
            viewModel.setState(state);
        } else if (state.getSuccessMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getSuccessMessage());
            wordInputField.setText("");
            state.setWord("");
            state.setSuccessMessage(null);
            viewModel.setState(state);
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setController(AddFlashcardToDeckController controller) {
        this.controller = controller;
    }

    public void switchToEditDeckView() {
        Container parent = AddFlashcardToDeckView.this.getParent();
        if (parent.getLayout() instanceof CardLayout) {
            CardLayout layout = (CardLayout) parent.getLayout();
            layout.show(parent, "edit deck");
        }
    }
}