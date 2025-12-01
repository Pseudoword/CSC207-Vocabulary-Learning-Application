package view;

import interface_adapter.StudyFlashCards.StudyFlashCardsController;
import app.AppBuilder;
import entity.Deck;
import interface_adapter.ViewManagerModel;
import use_case.StudyFlashCards.StudyFlashCardsInputData;

import interface_adapter.update_deck_details.UpdateDeckDetailsState;
import interface_adapter.update_deck_details.UpdateDeckDetailsViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DecksView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "decks";
    private final JList<Deck> deckList;
    private final DefaultListModel<Deck> listModel;
    private final JButton studyAllButton;
    private final JButton reviewButton;
    private final JButton editButton;
    private final JButton takeQuizButton;
    private final JButton backButton;
    private final ViewManagerModel viewManagerModel;
    private final UpdateDeckDetailsViewModel updateDeckDetailsViewModel;
    private final AppBuilder appBuilder;

    public DecksView(ViewManagerModel viewManagerModel, AppBuilder appBuilder,
                     UpdateDeckDetailsViewModel updateDeckDetailsViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.appBuilder = appBuilder;
        this.viewManagerModel.addPropertyChangeListener(this);
        this.updateDeckDetailsViewModel = updateDeckDetailsViewModel;

        this.setPreferredSize(new Dimension(900, 700));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(255, 240, 245));

        JLabel title = new JLabel("Decks");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(199, 21, 133));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        listModel = new DefaultListModel<>();
        refreshDecks();

        deckList = new JList<>(listModel);
        deckList.setCellRenderer(new DeckListCellRenderer());
        deckList.setFixedCellHeight(30);
        deckList.setVisibleRowCount(5);
        deckList.setSelectionBackground(new Color(255, 240, 245));
        deckList.setSelectionForeground(new Color(255, 105, 180));
        JScrollPane scrollPane = new JScrollPane(deckList);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setPreferredSize(new Dimension(300, 150));

        Dimension buttonSize = new Dimension(150, 40);
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);

        studyAllButton = createPinkButton("Study All", buttonSize, buttonFont);
        reviewButton = createPinkButton("Review", buttonSize, buttonFont);
        takeQuizButton = createPinkButton("Take Quiz", buttonSize, buttonFont);
        editButton = createPinkButton("Edit", buttonSize, buttonFont);
        backButton = createPinkButton("Back", buttonSize, buttonFont);

        JButton[] buttons = {studyAllButton, reviewButton, takeQuizButton, editButton, backButton};
        for (JButton b : buttons) {
            b.setPreferredSize(buttonSize);
            b.setMaximumSize(buttonSize);
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.setFont(buttonFont);
            b.addActionListener(this);
        }

        this.add(Box.createVerticalStrut(60));
        this.add(title);
        this.add(Box.createVerticalStrut(40));
        this.add(scrollPane);
        this.add(Box.createVerticalStrut(40));

        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        buttonRow.setBackground(new Color(255, 240, 245));
        buttonRow.add(studyAllButton);
        buttonRow.add(reviewButton);
        buttonRow.add(takeQuizButton);
        this.add(buttonRow);
        this.add(Box.createVerticalStrut(20));
        this.add(editButton);
        this.add(Box.createVerticalStrut(20));
        this.add(backButton);
        this.add(Box.createVerticalGlue());
    }

    /**
     * Refreshes the deck list from the data access object.
     */
    private void refreshDecks() {
        listModel.clear();
        for (Deck deck : appBuilder.getAllDecks()) {
            listModel.addElement(deck);
        }
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

        if (src == studyAllButton) {
            Deck selectedDeck = deckList.getSelectedValue();
            if (selectedDeck == null) {
                JOptionPane.showMessageDialog(this, "Please select a deck", "No Deck Selected", JOptionPane.WARNING_MESSAGE);
            } else {
                appBuilder.startStudyFlashCardsForDeck(selectedDeck);
            }
        } else if (src == reviewButton) {
            JOptionPane.showMessageDialog(this, "Use Case 6 not implemented yet", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else if (src == takeQuizButton) {
            Deck selectedDeck = deckList.getSelectedValue();
            if (selectedDeck == null) {
                JOptionPane.showMessageDialog(this, "Please select a deck", "No Deck Selected", JOptionPane.WARNING_MESSAGE);
            } else {
                // Start quiz for the selected deck
                appBuilder.startQuizForDeck(selectedDeck);
            }
        } else if (src == editButton) {
            Deck selectedDeck = deckList.getSelectedValue();
            if (selectedDeck == null){
                JOptionPane.showMessageDialog(this, "Please select a deck", "No Deck Selected", JOptionPane.WARNING_MESSAGE);
            } else {
                UpdateDeckDetailsState state = updateDeckDetailsViewModel.getState();

                state.setOriginalDeckTitle(selectedDeck.getTitle());
                state.setDeckTitle(selectedDeck.getTitle());
                state.setDeckDescription(selectedDeck.getDescription());

                updateDeckDetailsViewModel.setState(state);
                updateDeckDetailsViewModel.firePropertyChange();

                viewManagerModel.setState("update deck details");
                viewManagerModel.firePropertyChange();
            }
        } else if (src == backButton) {
            viewManagerModel.setState("logged in");
            viewManagerModel.firePropertyChange();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            String newState = (String) evt.getNewValue();
            // Refresh the deck list when navigating to this view
            if (newState.equals(viewName)) {
                refreshDecks();
            }
        }
    }

    public String getViewName() {
        return viewName;
    }
}
