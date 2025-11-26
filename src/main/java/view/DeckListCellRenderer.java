package view;

import entity.Deck;
import javax.swing.*;
import java.awt.*;

public class DeckListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof Deck) {
            Deck deck = (Deck) value;

            if (deck.isMastered()) {
                label.setText(deck.getTitle() + " [mastered!]");
                label.setFont(new Font("Arial", Font.PLAIN, 16));
            } else {
                label.setText(deck.getTitle());
                label.setFont(new Font("Arial", Font.PLAIN, 16));
            }

            if (isSelected) {
                label.setBackground(new Color(255, 182, 193)); // Light pink
                label.setForeground(new Color(199, 21, 133)); // Dark pink
            } else {
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
            }
        }

        return label;
    }
}