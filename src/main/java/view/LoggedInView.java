package view;

import interface_adapter.logged_in.ChangePasswordController;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.logout.LogoutController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoggedInView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "logged in";
    private final LoggedInViewModel loggedInViewModel;
    private ChangePasswordController changePasswordController = null;
    private LogoutController logoutController = null;
    private final JLabel username;
    private final JButton decksButton;
    private final JButton newDeckButton;
    private final JButton takeQuizButton;
    private final JButton changePasswordButton;
    private final JButton logoutButton;

    public LoggedInView(LoggedInViewModel loggedInViewModel) {
        this.loggedInViewModel = loggedInViewModel;
        this.loggedInViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(900, 700));
        this.setBackground(new Color(255, 240, 245));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(new Color(255, 240, 245));
        topPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        JLabel title = new JLabel("Main Menu");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(new Color(199, 21, 133));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        username = new JLabel("Currently logged in: ");
        username.setFont(new Font("Arial", Font.PLAIN, 16));
        username.setForeground(new Color(199, 21, 133));
        username.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(title);
        topPanel.add(Box.createVerticalStrut(20));
        topPanel.add(username);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(255, 240, 245));

        Dimension buttonSize = new Dimension(200, 50);
        Font buttonFont = new Font("Arial", Font.PLAIN, 18);

        decksButton = createPinkButton("Decks", buttonSize, buttonFont);
        newDeckButton = createPinkButton("New Deck", buttonSize, buttonFont);
        takeQuizButton = createPinkButton("Take Quiz", buttonSize, buttonFont);

        JButton[] mainButtons = {decksButton, newDeckButton, takeQuizButton};
        for (JButton b : mainButtons) {
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            b.addActionListener(this);
        }

        centerPanel.add(Box.createVerticalStrut(50));
        centerPanel.add(decksButton);
        centerPanel.add(Box.createVerticalStrut(25));
        centerPanel.add(newDeckButton);
        centerPanel.add(Box.createVerticalStrut(25));
        centerPanel.add(takeQuizButton);
        centerPanel.add(Box.createVerticalGlue());

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(255, 240, 245));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        bottomPanel.setPreferredSize(new Dimension(900, 80));

        changePasswordButton = createPinkButton("Change Password", new Dimension(140, 35), new Font("Arial", Font.PLAIN, 14));
        logoutButton = createPinkButton("Logout", new Dimension(100, 35), new Font("Arial", Font.PLAIN, 14));

        changePasswordButton.addActionListener(this);
        logoutButton.addActionListener(this);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(new Color(255, 240, 245));
        leftPanel.add(changePasswordButton);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(new Color(255, 240, 245));
        rightPanel.add(logoutButton);

        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.EAST);

        this.add(topPanel, BorderLayout.NORTH);
        this.add(centerPanel, BorderLayout.CENTER);
        this.add(bottomPanel, BorderLayout.SOUTH);
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
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(199, 21, 133), 1),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(219, 112, 147));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(255, 105, 180));
                button.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        return button;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        if (source == decksButton) {
            try {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                frame.setContentPane(new DecksView());
                frame.revalidate();
                frame.repaint();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error loading decks view: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (source == newDeckButton) {
            JOptionPane.showMessageDialog(this, "Use case 3 not implemented yet", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else if (source == takeQuizButton) {
            JOptionPane.showMessageDialog(this, "Use case 7 not implemented yet", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else if (source == changePasswordButton) {
            showChangePasswordDialog();
        } else if (source == logoutButton) {
            if (logoutController != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Confirm Logout", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    logoutController.execute();
                }
            } else {
                JOptionPane.showMessageDialog(this, "LogoutController not set", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showChangePasswordDialog() {
        if (changePasswordController == null) {
            JOptionPane.showMessageDialog(this, "ChangePasswordController not set", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JDialog passwordDialog = new JDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                "Change Password",
                true
        );
        passwordDialog.setLayout(new BorderLayout());
        passwordDialog.setSize(400, 200);
        passwordDialog.setLocationRelativeTo(this);
        passwordDialog.getContentPane().setBackground(new Color(255, 240, 245));

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(255, 240, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Enter New Password");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(new Color(199, 21, 133));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        inputPanel.setBackground(new Color(255, 240, 245));
        JLabel passwordLabel = new JLabel("New Password:");
        passwordLabel.setForeground(new Color(199, 21, 133));
        JPasswordField passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        inputPanel.add(passwordLabel);
        inputPanel.add(passwordField);
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(255, 240, 245));
        JButton confirmButton = createPinkButton("Confirm", new Dimension(100, 35), new Font("Arial", Font.PLAIN, 14));
        JButton cancelButton = createPinkButton("Cancel", new Dimension(100, 35), new Font("Arial", Font.PLAIN, 14));

        confirmButton.addActionListener(e -> {
            String newPassword = new String(passwordField.getPassword());
            if (newPassword.trim().isEmpty()) {
                JOptionPane.showMessageDialog(passwordDialog, "Password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            LoggedInState currentState = loggedInViewModel.getState();
            changePasswordController.execute(
                    currentState.getUsername(),
                    newPassword
            );
            passwordDialog.dispose();
        });

        cancelButton.addActionListener(e -> {
            passwordDialog.dispose();
        });

        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel);

        passwordDialog.add(mainPanel);
        passwordDialog.getRootPane().setDefaultButton(confirmButton);
        passwordDialog.setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            LoggedInState state = (LoggedInState) evt.getNewValue();
            username.setText("Currently logged in: " + state.getUsername());
        } else if (evt.getPropertyName().equals("password")) {
            LoggedInState state = (LoggedInState) evt.getNewValue();
            if (state.getPasswordError() == null) {
                JOptionPane.showMessageDialog(this, "Password updated successfully for " + state.getUsername(), "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, state.getPasswordError(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setChangePasswordController(ChangePasswordController changePasswordController) {
        this.changePasswordController = changePasswordController;
    }

    public void setLogoutController(LogoutController logoutController) {
        this.logoutController = logoutController;
    }
}