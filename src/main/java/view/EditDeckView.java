// src/main/java/view/EditDeckView.java
package view;

import entity.Deck;
import entity.Vocabulary;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_flashcard_to_deck.AddFlashcardToDeckState;
import interface_adapter.add_flashcard_to_deck.AddFlashcardToDeckViewModel;
import interface_adapter.delete_flashcard_in_deck.DeleteFlashcardFromDeckController;
import interface_adapter.delete_flashcard_in_deck.DeleteFlashcardFromDeckState;
import interface_adapter.delete_flashcard_in_deck.DeleteFlashcardFromDeckViewModel;
import interface_adapter.update_deck_details.UpdateDeckDetailsState;
import interface_adapter.update_deck_details.UpdateDeckDetailsViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class EditDeckView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "edit deck";
    private final DefaultListModel<Vocabulary> flashcardListModel;
    private final JList<Vocabulary> flashcardList;
    private final JButton editDeckDetailsButton;
    private final JButton addFlashcardButton;
    private final JButton deleteFlashcardButton;
    private final JButton backButton;
    private final ViewManagerModel viewManagerModel;
    private final UpdateDeckDetailsViewModel updateDeckDetailsViewModel;
    private final DeleteFlashcardFromDeckViewModel deleteFlashcardFromDeckViewModel;
    private final AddFlashcardToDeckViewModel addFlashcardToDeckViewModel;
    private DeleteFlashcardFromDeckController deleteFlashcardFromDeckController = null;
    private final Deck targetDeck;

    public EditDeckView(ViewManagerModel viewManagerModel, Deck targetDeck,
                        UpdateDeckDetailsViewModel updateDeckDetailsViewModel,
                        DeleteFlashcardFromDeckViewModel deleteFlashcardFromDeckViewModel,
                        AddFlashcardToDeckViewModel addFlashcardToDeckViewModel) {

        this.viewManagerModel = viewManagerModel;
        this.targetDeck = targetDeck;
        this.updateDeckDetailsViewModel = updateDeckDetailsViewModel;
        this.deleteFlashcardFromDeckViewModel = deleteFlashcardFromDeckViewModel;
        this.addFlashcardToDeckViewModel = addFlashcardToDeckViewModel;

        this.deleteFlashcardFromDeckViewModel.addPropertyChangeListener(this);
        this.addFlashcardToDeckViewModel.addPropertyChangeListener(this);

        this.setPreferredSize(new Dimension(900, 700));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(255, 240, 245));

        JLabel title = new JLabel("Flashcards");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(199, 21, 133));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        flashcardListModel = new DefaultListModel<>();
        refreshFlashcardList();

        flashcardList = new JList<>(flashcardListModel);
        flashcardList.setCellRenderer(new FlashcardListCellRenderer());
        flashcardList.setFixedCellHeight(30);
        flashcardList.setVisibleRowCount(5);
        flashcardList.setSelectionBackground(new Color(255, 240, 245));
        flashcardList.setSelectionForeground(new Color(255, 105, 180));
        JScrollPane scrollPane = new JScrollPane(flashcardList);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setPreferredSize(new Dimension(300, 300));

        Dimension largeButtonSize = new Dimension(300, 40);
        Dimension smallButtonSize = new Dimension(150, 40);
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);

        editDeckDetailsButton = createPinkButton("Edit Deck Details", largeButtonSize, buttonFont);
        addFlashcardButton = createPinkButton("Add Flashcards", largeButtonSize, buttonFont);
        deleteFlashcardButton = createPinkButton("Delete Selected", largeButtonSize, buttonFont);
        backButton = createPinkButton("Back", smallButtonSize, buttonFont);

        JButton[] buttons = {addFlashcardButton, deleteFlashcardButton, editDeckDetailsButton, backButton};
        for (JButton b : buttons) {
            if (b == backButton) {
                b.setPreferredSize(smallButtonSize);
                b.setMaximumSize(smallButtonSize);
            } else {
                b.setPreferredSize(largeButtonSize);
                b.setMaximumSize(largeButtonSize);
            }
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setFont(buttonFont);
            b.addActionListener(this);
        }

        this.add(Box.createVerticalStrut(30));
        this.add(title);
        this.add(Box.createVerticalStrut(20));
        this.add(scrollPane);
        this.add(Box.createVerticalStrut(20));

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        buttonRow.setBackground(new Color(255, 240, 245));
        buttonRow.add(addFlashcardButton);
        buttonRow.add(deleteFlashcardButton);
        this.add(buttonRow);
        this.add(editDeckDetailsButton);
        this.add(Box.createVerticalStrut(50));
        this.add(backButton);
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
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src == editDeckDetailsButton) {
            UpdateDeckDetailsState state = updateDeckDetailsViewModel.getState();

            state.setOriginalDeckTitle(targetDeck.getTitle());
            state.setDeckTitle(targetDeck.getTitle());
            state.setDeckDescription(targetDeck.getDescription());

            updateDeckDetailsViewModel.setState(state);
            updateDeckDetailsViewModel.firePropertyChange();

            viewManagerModel.setState("update deck details");
            viewManagerModel.firePropertyChange();
        } else if (src == backButton) {
            viewManagerModel.setState("decks");
            viewManagerModel.firePropertyChange();
        } else if (src == addFlashcardButton) {
            AddFlashcardToDeckState addState = addFlashcardToDeckViewModel.getState();
            addState.setDeckTitle(targetDeck.getTitle());
            addState.setWord("");
            addState.setError(null);
            addState.setSuccessMessage(null);
            addFlashcardToDeckViewModel.setState(addState);
            addFlashcardToDeckViewModel.firePropertyChange();

            viewManagerModel.setState("add flashcard");
            viewManagerModel.firePropertyChange();
        } else if (src == deleteFlashcardButton) {
            Vocabulary vocab = flashcardList.getSelectedValue();

            if (vocab == null) {
                JOptionPane.showMessageDialog(this, "Please select a flashcard", "No Flashcard Selected", JOptionPane.WARNING_MESSAGE);
            } else {
                DeleteFlashcardFromDeckState state = deleteFlashcardFromDeckViewModel.getState();

                state.setWord(vocab.getWord());
                state.setDeckTitle(targetDeck.getTitle());

                deleteFlashcardFromDeckViewModel.setState(state);
                deleteFlashcardFromDeckViewModel.firePropertyChange();

                if (deleteFlashcardFromDeckController != null) {
                    deleteFlashcard();
                    flashcardListModel.removeElement(vocab);
                } else {
                    JOptionPane.showMessageDialog(this, "DeleteFlashcardFromDeckController not set", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public void deleteFlashcard() {
        final DeleteFlashcardFromDeckState currentState = deleteFlashcardFromDeckViewModel.getState();
        deleteFlashcardFromDeckController.execute(
                currentState.getDeckTitle(),
                currentState.getWord()
        );

    }

    public void setDeleteFlashcardController(DeleteFlashcardFromDeckController deleteFlashcardFromDeckController) {
        this.deleteFlashcardFromDeckController = deleteFlashcardFromDeckController;
    }

    public String getViewName() {
        return viewName;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        Object newValue = evt.getNewValue();

        if (newValue instanceof DeleteFlashcardFromDeckState) {
            handleDeleteFlashcardState((DeleteFlashcardFromDeckState) newValue);
        } else if (newValue instanceof AddFlashcardToDeckState) {
            handleAddFlashcardState((AddFlashcardToDeckState) newValue);
        }
    }

    private void handleDeleteFlashcardState(DeleteFlashcardFromDeckState state) {
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
            state.setError(null);
            deleteFlashcardFromDeckViewModel.setState(state);
        } else if (state.getSuccessMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getSuccessMessage());
            state.setSuccessMessage(null);
            deleteFlashcardFromDeckViewModel.setState(state);
        }
    }

    private void handleAddFlashcardState(AddFlashcardToDeckState state) {
        if (state.getSuccessMessage() == null) {
            return;
        }

        if (targetDeck.getTitle().equals(state.getDeckTitle())) {
            refreshFlashcardList();
        }
    }

    private void refreshFlashcardList() {
        flashcardListModel.clear();
        for (Vocabulary v : targetDeck.getVocabularies()) {
            flashcardListModel.addElement(v);
        }
    }
}