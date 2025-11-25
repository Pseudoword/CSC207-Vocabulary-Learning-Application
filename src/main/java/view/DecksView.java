package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DecksView extends JPanel implements ActionListener {

    private final String viewName = "decks";
    private final JList<String> deckList;
    private final JButton studyAllButton;
    private final JButton reviewButton;
    private final JButton editButton;
    private final JButton backButton;
    private final LoggedInView loggedInView; // 保存 Main Menu 引用

    public DecksView(LoggedInView loggedInView) {
        this.loggedInView = loggedInView;

        this.setPreferredSize(new Dimension(900, 700));
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Decks");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

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

        Dimension buttonSize = new Dimension(150, 40);
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);

        studyAllButton = new JButton("Study All");
        reviewButton = new JButton("Review");
        editButton = new JButton("Edit");
        backButton = new JButton("Exit");

        JButton[] buttons = {studyAllButton, reviewButton, editButton, backButton};
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
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);

        if (src == studyAllButton) {
            JOptionPane.showMessageDialog(this, "Use Case 5 not implemented yet", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else if (src == reviewButton) {
            JOptionPane.showMessageDialog(this, "Use Case 6 not implemented yet", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else if (src == editButton) {
            JOptionPane.showMessageDialog(this, "Use Case 4 not implemented yet", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else if (src == backButton) {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to exit?",
                    "Exit Confirmation",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
}
