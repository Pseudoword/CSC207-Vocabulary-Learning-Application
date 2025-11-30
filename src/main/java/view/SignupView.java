package view;

import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(20);
    private final JPasswordField passwordInputField = new JPasswordField(20);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(20);
    private SignupController signupController = null;

    private final JButton signUp;
    private final JButton exit;
    private final JButton toLogin;

    public SignupView(SignupViewModel signupViewModel) {
        this.signupViewModel = signupViewModel;
        signupViewModel.addPropertyChangeListener(this);

        this.setPreferredSize(new Dimension(500, 400));
        this.setBackground(new Color(255, 240, 245));
        this.setLayout(new BorderLayout(0, 20));

        final JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(255, 240, 245));
        final JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(199, 21, 133));
        titlePanel.add(title);
        this.add(titlePanel, BorderLayout.NORTH);

        final JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(255, 240, 245));
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        JLabel usernameLabel = new JLabel(SignupViewModel.USERNAME_LABEL);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(new Color(199, 21, 133));
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        usernameInputField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameInputField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(usernameInputField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        JLabel passwordLabel = new JLabel(SignupViewModel.PASSWORD_LABEL);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(new Color(199, 21, 133));
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        passwordInputField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordInputField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(passwordInputField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        JLabel repeatPasswordLabel = new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL);
        repeatPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        repeatPasswordLabel.setForeground(new Color(199, 21, 133));
        formPanel.add(repeatPasswordLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        repeatPasswordInputField.setFont(new Font("Arial", Font.PLAIN, 14));
        repeatPasswordInputField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(repeatPasswordInputField, gbc);

        this.add(formPanel, BorderLayout.CENTER);

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 240, 245));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        toLogin = createPinkButton(SignupViewModel.TO_LOGIN_BUTTON_LABEL);
        buttonPanel.add(toLogin);

        signUp = createPinkButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        buttonPanel.add(signUp);

        exit = createPinkButton("Exit");
        buttonPanel.add(exit);

        this.add(buttonPanel, BorderLayout.SOUTH);

        signUp.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(signUp)) {
                            final SignupState currentState = signupViewModel.getState();
                            signupController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword(),
                                    currentState.getRepeatPassword()
                            );
                        }
                    }
                }
        );

        toLogin.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        Container parent = SignupView.this.getParent();
                        if (parent.getLayout() instanceof CardLayout) {
                            CardLayout layout = (CardLayout) parent.getLayout();
                            layout.show(parent, "log in"); // making sure it's the same as LoginView.getViewName()
                        }
                    }
                }
        );

        exit.addActionListener(this);

        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();
    }

    private JButton createPinkButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 35));
        button.setFont(new Font("Arial", Font.BOLD, 14));
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

    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                signupViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                signupViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    private void addRepeatPasswordListener() {
        repeatPasswordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final SignupState currentState = signupViewModel.getState();
                currentState.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
                signupViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(exit)) {
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to exit?",
                    "Confirm Exit",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignupState state = (SignupState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setSignupController(SignupController controller) {
        this.signupController = controller;
    }
}
