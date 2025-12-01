package view;

import interface_adapter.update_deck_details.UpdateDeckDetailsController;
import interface_adapter.update_deck_details.UpdateDeckDetailsState;
import interface_adapter.update_deck_details.UpdateDeckDetailsViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UpdateDeckDetailsView extends JPanel implements ActionListener, PropertyChangeListener {

    private static final String VIEW_NAME = "update deck details";
    private final UpdateDeckDetailsViewModel viewModel;

    private final JTextField deckTitleField = new JTextField(20);
    private final JTextArea deckDescriptionArea = new JTextArea(5, 20);

    private final JButton applyButton;
    private final JButton cancelButton;

    private UpdateDeckDetailsController controller;

    public UpdateDeckDetailsView(UpdateDeckDetailsViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        deckDescriptionArea.setLineWrap(true);
        deckDescriptionArea.setWrapStyleWord(true);

        final JLabel title = new JLabel(UpdateDeckDetailsViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel deckTitlePanel = new LabelTextPanel(
                new JLabel(UpdateDeckDetailsViewModel.DECK_TITLE_LABEL), deckTitleField);
        final JPanel deckDescriptionPanel = new JPanel();
        deckDescriptionPanel.setLayout(new BoxLayout(deckDescriptionPanel, BoxLayout.Y_AXIS));
        deckDescriptionPanel.add(new JLabel(UpdateDeckDetailsViewModel.DECK_DESCRIPTION_LABEL));
        deckDescriptionPanel.add(new JScrollPane(deckDescriptionArea));

        final JPanel buttons = new JPanel();
        applyButton = new JButton(UpdateDeckDetailsViewModel.APPLY_BUTTON_LABEL);
        buttons.add(applyButton);
        cancelButton = new JButton(UpdateDeckDetailsViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancelButton);

        applyButton.addActionListener(evt -> {
            if (controller == null) {
                return;
            }
            final UpdateDeckDetailsState currentState = viewModel.getState();
            controller.execute(
                    currentState.getOriginalDeckTitle(),
                    deckTitleField.getText().trim(),
                    deckDescriptionArea.getText().trim()
            );
        });

        cancelButton.addActionListener(evt -> switchToDecksView());

        deckTitleField.getDocument().addDocumentListener(new DocumentListener() {
            private void syncState() {
                final UpdateDeckDetailsState state = viewModel.getState();
                state.setDeckTitle(deckTitleField.getText());
                viewModel.setState(state);
            }
            @Override public void insertUpdate(DocumentEvent e) { syncState(); }
            @Override public void removeUpdate(DocumentEvent e) { syncState(); }
            @Override public void changedUpdate(DocumentEvent e) { syncState(); }
        });

        deckDescriptionArea.getDocument().addDocumentListener(new DocumentListener() {
            private void syncState() {
                final UpdateDeckDetailsState state = viewModel.getState();
                state.setDeckDescription(deckDescriptionArea.getText());
                viewModel.setState(state);
            }
            @Override public void insertUpdate(DocumentEvent e) { syncState(); }
            @Override public void removeUpdate(DocumentEvent e) { syncState(); }
            @Override public void changedUpdate(DocumentEvent e) { syncState(); }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(deckTitlePanel);
        this.add(deckDescriptionPanel);
        this.add(buttons);
    }

    public void switchToDecksView() {
        Container parent = UpdateDeckDetailsView.this.getParent();
        if (parent.getLayout() instanceof CardLayout) {
            CardLayout layout = (CardLayout) parent.getLayout();
            layout.show(parent, "decks");
        }
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setController(UpdateDeckDetailsController controller) {
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Placeholder: could clear fields or route elsewhere if needed.
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final UpdateDeckDetailsState state = (UpdateDeckDetailsState) evt.getNewValue();

        if (!deckTitleField.getText().equals(state.getDeckTitle())) {
            deckTitleField.setText(state.getDeckTitle());
        }
        if (!deckDescriptionArea.getText().equals(state.getDeckDescription())) {
            deckDescriptionArea.setText(state.getDeckDescription());
        }

        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
        } else if (state.getSuccessMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getSuccessMessage());
        }
    }
}