package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The View for displaying all decks.
 * Matches the provided sketch: list of decks + Study All / Review / Edit buttons.
 */
public class DecksView extends JPanel implements ActionListener {

    private final String viewName = "decks";

    private final JList<String> deckList;
    private final JButton studyAllButton;
    private final JButton reviewButton;
    private final JButton editButton;
    private final JButton backButton;

    public DecksView() {
        this.setPreferredSize(new Dimension(900, 700));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // Title
        JLabel title = new JLabel("Decks");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Deck list (mock data for now)
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Deck 1");
        listModel.addElement("Deck 2");
        listModel.addElement("Deck 3");

        deckList = new JList<>(listModel);
        deckList.setFont(new Font("Arial", Font.PLAIN, 16));
        deckList.setFixedCellHeight(30);
        deckList.setVisibleRowCount(5);
        JScrollPane scrollPane = new JScrollPane(deckList);
        scrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollPane.setPreferredSize(new Dimension(300, 150));

        // Buttons
        Dimension buttonSize = new Dimension(150, 40);
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);

        studyAllButton = new JButton("Study All");
        studyAllButton.setPreferredSize(buttonSize);
        studyAllButton.setMaximumSize(buttonSize);
        studyAllButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        studyAllButton.setFont(buttonFont);

        reviewButton = new JButton("Review");
        reviewButton.setPreferredSize(buttonSize);
        reviewButton.setMaximumSize(buttonSize);
        reviewButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        reviewButton.setFont(buttonFont);

        editButton = new JButton("Edit");
        editButton.setPreferredSize(buttonSize);
        editButton.setMaximumSize(buttonSize);
        editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        editButton.setFont(buttonFont);

        // Back to Main Menu button
        backButton = new JButton("Back to Main Menu");
        backButton.setPreferredSize(buttonSize);
        backButton.setMaximumSize(buttonSize);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.setFont(buttonFont);

        // Add listeners
        studyAllButton.addActionListener(this);
        reviewButton.addActionListener(this);
        editButton.addActionListener(this);
        backButton.addActionListener(this);

        // Layout structure
        this.add(Box.createVerticalStrut(60));
        this.add(title);
        this.add(Box.createVerticalStrut(40));
        this.add(scrollPane);
        this.add(Box.createVerticalStrut(40));

        // Button row for Study All and Review
        JPanel buttonRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 10));
        buttonRow.setOpaque(false);
        buttonRow.add(studyAllButton);
        buttonRow.add(reviewButton);

        this.add(buttonRow);
        this.add(Box.createVerticalStrut(20));
        this.add(editButton);
        this.add(Box.createVerticalStrut(20));
        this.add(backButton);
        this.add(Box.createVerticalGlue());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == studyAllButton) {
            JOptionPane.showMessageDialog(this,
                    "Use Case 5 not implemented yet",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (src == reviewButton) {
            JOptionPane.showMessageDialog(this,
                    "Use Case 6 not implemented yet",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (src == editButton) {
            JOptionPane.showMessageDialog(this,
                    "Use Case 4 not implemented yet",
                    "Information",
                    JOptionPane.INFORMATION_MESSAGE);
        } else if (src == backButton) {

            JOptionPane.showMessageDialog(this,
                    "Returning to Main Menu",
                    "Back",
                    JOptionPane.INFORMATION_MESSAGE);

        }
    }

    public String getViewName() {
        return viewName;
    }
}