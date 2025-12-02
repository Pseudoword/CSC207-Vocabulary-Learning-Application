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
    private static final Color PINK_BACKGROUND = new Color(255, 240, 245);
    private static final Color PINK_PRIMARY = new Color(255, 105, 180);
    private static final Color PINK_DARK = new Color(199, 21, 133);
    private static final Color PINK_HOVER = new Color(219, 112, 147);

    private final UpdateDeckDetailsViewModel viewModel;

    private final JTextField deckTitleField = new JTextField(20);
    private final JTextArea deckDescriptionArea = new JTextArea(5, 20);

    private final JButton applyButton;
    private final JButton backButton;

    private UpdateDeckDetailsController controller;

    public UpdateDeckDetailsView(UpdateDeckDetailsViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        this.setPreferredSize(new Dimension(900, 700));
        this.setBackground(PINK_BACKGROUND);

        deckDescriptionArea.setLineWrap(true);
        deckDescriptionArea.setWrapStyleWord(true);

        final JLabel title = new JLabel(UpdateDeckDetailsViewModel.TITLE_LABEL);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(PINK_DARK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        final LabelTextPanel deckTitlePanel = new LabelTextPanel(
                new JLabel(UpdateDeckDetailsViewModel.DECK_TITLE_LABEL), deckTitleField);
        deckTitlePanel.setFont(new Font("Arial", Font.BOLD, 20));
        deckTitlePanel.setForeground(PINK_DARK);
        deckTitlePanel.setBackground(PINK_BACKGROUND);
        deckTitlePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JLabel deckDescriptionLabel = new JLabel(UpdateDeckDetailsViewModel.DECK_DESCRIPTION_LABEL);
        deckDescriptionLabel.setFont(new Font("Arial", Font.BOLD, 20));
        deckDescriptionLabel.setForeground(PINK_DARK);
        deckDescriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        final JPanel deckDescriptionPanel = new JPanel();
        deckDescriptionPanel.setLayout(new BoxLayout(deckDescriptionPanel, BoxLayout.Y_AXIS));
        deckDescriptionPanel.add(deckDescriptionLabel);

        final JScrollPane descriptionScrollPane = new JScrollPane(deckDescriptionArea);
        descriptionScrollPane.getViewport().setBackground(Color.WHITE);
        descriptionScrollPane.setBorder(BorderFactory.createLineBorder(PINK_DARK));
        deckDescriptionPanel.add(descriptionScrollPane);
        deckDescriptionPanel.setBackground(PINK_BACKGROUND);

        Dimension buttonSize = new Dimension(150, 40);
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);

        final JPanel buttons = new JPanel();
        buttons.setBackground(PINK_BACKGROUND);
        applyButton = createPinkButton(UpdateDeckDetailsViewModel.APPLY_BUTTON_LABEL, buttonSize, buttonFont);
        backButton = createPinkButton(UpdateDeckDetailsViewModel.CANCEL_BUTTON_LABEL, buttonSize, buttonFont);
        buttons.add(applyButton);
        buttons.add(backButton);

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
            switchToEditDeckView();
        });

        backButton.addActionListener(evt -> switchToEditDeckView());

        deckTitleField.getDocument().addDocumentListener(new DocumentListener() {
            private void syncState() {
                final UpdateDeckDetailsState state = viewModel.getState();
                state.setDeckTitle(deckTitleField.getText());
                viewModel.setState(state);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                syncState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                syncState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                syncState();
            }
        });

        deckDescriptionArea.getDocument().addDocumentListener(new DocumentListener() {
            private void syncState() {
                final UpdateDeckDetailsState state = viewModel.getState();
                state.setDeckDescription(deckDescriptionArea.getText());
                viewModel.setState(state);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                syncState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                syncState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                syncState();
            }
        });

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(Box.createVerticalStrut(40));
        this.add(title);
        this.add(Box.createVerticalStrut(20));
        this.add(deckTitlePanel);
        this.add(Box.createVerticalStrut(20));
        this.add(deckDescriptionPanel);
        this.add(Box.createVerticalStrut(30));
        this.add(buttons);
        this.add(Box.createVerticalGlue());
    }

    private JButton createPinkButton(String text, Dimension size, Font font) {
        JButton button = new JButton(text);
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        button.setFont(font);
        button.setBackground(PINK_PRIMARY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PINK_DARK, 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(PINK_HOVER);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PINK_PRIMARY);
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        return button;
    }

    public void switchToEditDeckView() {
        Container parent = UpdateDeckDetailsView.this.getParent();
        if (parent.getLayout() instanceof CardLayout) {
            CardLayout layout = (CardLayout) parent.getLayout();
            layout.show(parent, "edit deck");
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
        Object src = e.getSource();

        if (src == applyButton) {
            final UpdateDeckDetailsState currentState = viewModel.getState();
            controller.execute(
                    currentState.getOriginalDeckTitle(),
                    currentState.getDeckTitle(),
                    currentState.getDeckDescription()
            );
        } else if (src == backButton) {
            switchToEditDeckView();
        }
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
            state.setError(null);
            viewModel.setState(state);
        } else if (state.getSuccessMessage() != null) {
            JOptionPane.showMessageDialog(this, state.getSuccessMessage());
            state.setSuccessMessage(null);
            viewModel.setState(state);
        }
    }
}
