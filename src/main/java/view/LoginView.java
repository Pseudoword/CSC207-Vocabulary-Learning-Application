package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "log in";
    private final LoginViewModel loginViewModel;

    private final JTextField usernameInputField = new JTextField(20);
    private final JLabel usernameErrorField = new JLabel();

    private final JPasswordField passwordInputField = new JPasswordField(20);
    private final JLabel passwordErrorField = new JLabel();

    private final JButton logIn;
    private final JButton cancel;
    private LoginController loginController = null;

    public LoginView(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);

        this.setPreferredSize(new Dimension(500, 400));
        this.setBackground(new Color(255, 240, 245));
        this.setLayout(new BorderLayout(0, 20));

        final JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(255, 240, 245));
        final JLabel title = new JLabel("Login Screen");
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
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameLabel.setForeground(new Color(199, 21, 133));
        formPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        usernameInputField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameInputField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(usernameInputField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        usernameErrorField.setForeground(Color.RED);
        usernameErrorField.setFont(new Font("Arial", Font.PLAIN, 12));
        formPanel.add(usernameErrorField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.3;
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordLabel.setForeground(new Color(199, 21, 133));
        formPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.7;
        passwordInputField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordInputField.setPreferredSize(new Dimension(200, 30));
        formPanel.add(passwordInputField, gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        passwordErrorField.setForeground(Color.RED);
        passwordErrorField.setFont(new Font("Arial", Font.PLAIN, 12));
        formPanel.add(passwordErrorField, gbc);

        this.add(formPanel, BorderLayout.CENTER);

        final JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255, 240, 245));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        logIn = createPinkButton("log in");
        buttonPanel.add(logIn);

        cancel = createPinkButton("Back to Last Page");
        buttonPanel.add(cancel);

        this.add(buttonPanel, BorderLayout.SOUTH);

        logIn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(logIn)) {
                    final LoginState currentState = loginViewModel.getState();
                    loginController.execute(
                            currentState.getUsername(),
                            currentState.getPassword()
                    );
                }
            }
        });

        cancel.addActionListener(this);

        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }

            @Override
            public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }

            @Override
            public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });

        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {
            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) { documentListenerHelper(); }

            @Override
            public void removeUpdate(DocumentEvent e) { documentListenerHelper(); }

            @Override
            public void changedUpdate(DocumentEvent e) { documentListenerHelper(); }
        });
    }

    private JButton createPinkButton(String text) {
        JButton button = new JButton(text);
        if (text.equals("Back to Last Page")) {
            button.setPreferredSize(new Dimension(180, 35));
        } else {
            button.setPreferredSize(new Dimension(120, 35));
        }
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

    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource().equals(cancel)) {
            switchToSignupView();
            usernameInputField.setText("");
            passwordInputField.setText("");
            final LoginState currentState = loginViewModel.getState();
            currentState.setUsername("");
            currentState.setPassword("");
            loginViewModel.setState(currentState);
        }
    }

    public void switchToSignupView() {
        Container parent = this.getParent();
        if (parent.getLayout() instanceof CardLayout) {
            CardLayout layout = (CardLayout) parent.getLayout();
            layout.show(parent, "sign up");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
        usernameErrorField.setText(state.getLoginError());
        passwordInputField.setText("");
    }

    private void setFields(LoginState state) {
        usernameInputField.setText(state.getUsername());
    }

    public String getViewName() {
        return viewName;
    }

    public void setLoginController(LoginController loginController) {
        this.loginController = loginController;
    }
}
