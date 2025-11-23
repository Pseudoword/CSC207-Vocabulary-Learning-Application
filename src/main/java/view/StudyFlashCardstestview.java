package view;

import entity.Deck;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudyFlashCardstestview implements ActionListener {
    private final Deck deck;
    private int index = 0;
    private boolean isFlagged  = false;
    private boolean isWord = true;

    private final JButton defnButton;
    private final JButton nextButton;
    private final JButton prevButton;
    private final JButton flagButton;

    private JLabel outputLabel;

    private JFrame frame;

    //private JPanel panel;

    private String defn;
    private String word;

    private final int screenWidth = 900;
    private final int screenHeight = 700;


    public StudyFlashCardstestview(Deck deck) {
        this.deck = deck;

        //window
        frame = new JFrame("Study FlashCards");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(screenWidth, screenHeight);
        frame.setLayout(new BorderLayout());



        //other panels
        JPanel panel1 = new JPanel();
        JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();

        panel1.setBackground(new Color(255, 105, 180));
        panel2.setBackground(new Color(255, 105, 180));
        panel3.setBackground(new Color(255, 105, 180));
        panel4.setBackground(new Color(255, 105, 180));
        panel5.setBackground(new Color(249, 221, 253));

        panel1.setPreferredSize(new Dimension(200, 200));
        panel2.setPreferredSize(new Dimension(200, 200));
        panel3.setPreferredSize(new Dimension(200, 200));
        panel4.setPreferredSize(new Dimension(200, 200));
        panel5.setPreferredSize(new Dimension(200, 200));

        frame.add(panel1, BorderLayout.NORTH);
        frame.add(panel2, BorderLayout.WEST);
        frame.add(panel3, BorderLayout.EAST);
        frame.add(panel4, BorderLayout.SOUTH);
        frame.add(panel5, BorderLayout.CENTER);

        //main panel
        panel5.setLayout(new FlowLayout());
        JLabel title = new JLabel("Study Flash Cards");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


        //Dimension buttonSize = new Dimension(150, 40);
        Font buttonFont = new Font("Arial", Font.PLAIN, 16);

        defnButton = new JButton("Definition");
        defnButton.setFont(buttonFont);

        nextButton = new JButton("Next");
        nextButton.setFont(buttonFont);

        prevButton = new JButton("Previous");
        prevButton.setFont(buttonFont);

        flagButton = new JButton("Flag");
        flagButton.setFont(buttonFont);

        defnButton.addActionListener(this);
        nextButton.addActionListener(this);
        prevButton.addActionListener(this);
        flagButton.addActionListener(this);

        panel1.add(title);
        panel4.add(defnButton);
        panel3.add(nextButton);
        panel2.add(prevButton);
        panel4.add(flagButton);

        if (index >= 0 && index < deck.getVocabularies().size()) {
            word = deck.getVocabularies().get(index).getWord();
            defn = deck.getVocabularies().get(index).getDefinition();

            outputLabel = new JLabel((word), SwingConstants.CENTER);
            outputLabel.setFont(new Font("Arial", Font.BOLD, 18));
        }
        else System.out.println("return main menu");

        panel5.add(outputLabel);
        frame.add(panel5);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == defnButton ) {
            if(isWord) {outputLabel.setText(defn);}
            else{outputLabel.setText(word);}
            isWord = !isWord;
        }
        else if (source == nextButton && index < deck.getVocabularies().size() - 1) {
            index++;
            word = deck.getVocabularies().get(index).getWord();
            defn = deck.getVocabularies().get(index).getDefinition();
            outputLabel.setText(word);
        }
        else if (source == prevButton && index > 0) {
            index--;
            word = deck.getVocabularies().get(index).getWord();
            defn = deck.getVocabularies().get(index).getDefinition();
            outputLabel.setText(word);
        }
        else if (source == flagButton && index >= 0 && index < deck.getVocabularies().size()) {
            isFlagged = !isFlagged;
            deck.getVocabularies().get(index).setFlagged(isFlagged);
        }
    }
}
