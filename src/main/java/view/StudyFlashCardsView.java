package view;

import interface_adapter.StudyFlashCards.StudyFlashCardsController;
import interface_adapter.StudyFlashCards.StudyFlashCardsState;
import interface_adapter.StudyFlashCards.StudyFlashCardsViewModel;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Objects;

public class StudyFlashCardsView extends JPanel implements ActionListener, PropertyChangeListener {

    private final JButton defnButton;
    private final JButton nextButton;
    private final JButton prevButton;
    private final JButton flagButton;


    private StudyFlashCardsController controller;
    private StudyFlashCardsViewModel viewModel;
    private JButton mainMenuButton;
    private final ViewManagerModel viewManagerModel;

    private final JLabel noWordErrorLabel;
    private final JLabel noDeckErrorLabel;
    private final JLabel outputLabel;
    private final String deckName;


    public StudyFlashCardsView(StudyFlashCardsViewModel viewModel, StudyFlashCardsController controller, ViewManagerModel viewManagerModel, String deckName) {
        this.viewModel = viewModel;
        this.controller = controller;
        this.viewManagerModel = viewManagerModel;
        viewModel.addPropertyChangeListener(this);
        this.deckName = deckName;

        int screenWidth = 900;
        int screenHeight = 700;
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(255, 240, 245));
        this.setLayout(new BorderLayout(0, 20));

        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();

        panel1.setBackground(new Color(255, 240, 245));
        panel2.setBackground(new Color(255, 240, 245));
        panel3.setBackground(new Color(255, 240, 245));
        panel4.setBackground(new Color(255, 240, 245));
        panel5.setBackground(new Color(255, 255, 255));

        panel1.setPreferredSize(new Dimension(200, 200));
        panel2.setPreferredSize(new Dimension(200, 200));
        panel3.setPreferredSize(new Dimension(200, 200));
        panel4.setPreferredSize(new Dimension(200, 200));
        panel5.setPreferredSize(new Dimension(200, 200));

        panel5.setLayout(new FlowLayout());
        JLabel title = new JLabel("Study Flash Cards");
        title.setFont(new Font("Arial", Font.BOLD, 60));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        defnButton = new JButton("Definition");
        defnButton.setFont(new Font("Arial", Font.BOLD, 18));
        defnButton.setPreferredSize(new Dimension(150, 50));
        defnButton.setBackground(new Color(255, 105, 180));
        defnButton.setForeground(Color.WHITE);

        nextButton = new JButton("Next");
        nextButton.setFont(new Font("Arial", Font.BOLD, 18));
        nextButton.setPreferredSize(new Dimension(150, 50));
        nextButton.setBackground(new Color(255, 105, 180));
        nextButton.setForeground(Color.WHITE);

        prevButton = new JButton("Previous");
        prevButton.setFont(new Font("Arial", Font.BOLD, 18));
        prevButton.setPreferredSize(new Dimension(150, 50));
        prevButton.setBackground(new Color(255, 105, 180));
        prevButton.setForeground(Color.WHITE);

        flagButton = new JButton("Flag");
        flagButton.setFont(new Font("Arial", Font.BOLD, 18));
        flagButton.setPreferredSize(new Dimension(150, 50));
        flagButton.setBackground(new Color(255, 105, 180));
        flagButton.setForeground(Color.WHITE);

        defnButton.addActionListener(this);
        nextButton.addActionListener(this);
        prevButton.addActionListener(this);
        flagButton.addActionListener(this);


        mainMenuButton = new JButton("Main Menu");
        mainMenuButton.setFont(new Font("Arial", Font.BOLD, 18));
        mainMenuButton.setPreferredSize(new Dimension(150, 50));
        mainMenuButton.setBackground(new Color(255, 105, 180));
        mainMenuButton.setForeground(Color.WHITE);

        noWordErrorLabel = new JLabel("No Words in Deck", SwingConstants.CENTER);
        noWordErrorLabel.setFont(new Font("Arial", Font.BOLD, 40));
        noWordErrorLabel.setVisible(false);

        noDeckErrorLabel = new JLabel("Error", SwingConstants.CENTER);
        noDeckErrorLabel.setFont(new Font("Arial", Font.BOLD, 40));
        noDeckErrorLabel.setVisible(false);
        updateFlagButtonColor();
        addButtonHoverEffect(mainMenuButton, new Color(255, 105, 180));
        addButtonHoverEffect(defnButton, new Color(255, 105, 180));
        addButtonHoverEffect(nextButton, new Color(255, 105, 180));
        addButtonHoverEffect(prevButton, new Color(255, 105, 180));
        addButtonHoverEffect(flagButton, new Color(255, 105, 180));
        mainMenuButton.addActionListener(e -> {
            viewManagerModel.setState("logged in");
            viewManagerModel.firePropertyChange();
        });

        panel1.add(title);
        panel4.add(defnButton);
        panel3.add(nextButton);
        panel2.add(prevButton);
        panel4.add(flagButton);
        panel5.add(noWordErrorLabel);
        panel5.add(noDeckErrorLabel);
        panel4.add(mainMenuButton);
        this.add(panel1, BorderLayout.NORTH);
        this.add(panel2, BorderLayout.WEST);
        this.add(panel3, BorderLayout.EAST);
        this.add(panel4, BorderLayout.SOUTH);
        this.add(panel5, BorderLayout.CENTER);

        outputLabel = new JLabel((""),  SwingConstants.CENTER);
        outputLabel.setFont(new Font("Arial", Font.BOLD, 20));
        panel5.add(outputLabel);
        controller.execute(deckName);
    }

    private void addButtonHoverEffect(JButton button, Color originalColor) {
        Color hoverColor = originalColor.darker();

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (!button.isVisible() || !button.isEnabled()) {return;}

                if (button == flagButton && viewModel.getFlag()) {return;}

                button.setBackground(hoverColor);
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }


            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (!button.isVisible() || !button.isEnabled()) {return;}
                if (button == flagButton && viewModel.getFlag()) {return;}

                button.setBackground(originalColor);
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));}
        });
    }

    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == defnButton) {
            controller.reveal(deckName);

        } else if (source == nextButton) {
            controller.next(deckName);
            updateFlagButtonColor();

        } else if (source == prevButton) {
            controller.prev(deckName);
            updateFlagButtonColor();

        } else if (source == flagButton) {
            controller.flag(deckName);
            updateFlagButtonColor();

        }
    }
    private void updateFlagButtonColor() {
        if (viewModel.getFlag()) {
            flagButton.setBackground(new Color(255, 0, 0));
        } else {
            flagButton.setBackground(new Color(255, 105, 180));
        }
    }
    public void setController(StudyFlashCardsController controller) {
        this.controller = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            StudyFlashCardsState state = viewModel.getState();
            if (Objects.equals(state.getError(), "noWords")) {
                nextButton.setVisible(false);
                prevButton.setVisible(false);
                flagButton.setVisible(false);
                defnButton.setVisible(false);
                noWordErrorLabel.setVisible(true);
                noDeckErrorLabel.setVisible(false);
            }else if (Objects.equals(state.getError(), "noDeck")) {
                nextButton.setVisible(false);
                prevButton.setVisible(false);
                flagButton.setVisible(false);
                defnButton.setVisible(false);
                noDeckErrorLabel.setVisible(true);
                noWordErrorLabel.setVisible(false);
            }else {
                outputLabel.setText(state.getDisplayText());
                revalidate();
                repaint();
                updateFlagButtonColor();
            }
        }
    }
    public String getViewName() {
        return "StudyFlashCards";
    }

}
