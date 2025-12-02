package view;

import entity.Vocabulary;

import javax.swing.*;
import java.awt.*;

public class FlashcardListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                  boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value instanceof Vocabulary) {
            Vocabulary flashcard = (Vocabulary) value;

            label.setText(flashcard.getWord());
            label.setFont(new Font("Arial", Font.PLAIN, 16));

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