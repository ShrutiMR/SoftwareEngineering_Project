/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.pages;
import rest.RestAPIHook;
/**
 *
 * @author mkonidala
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.lang.Exception;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.Window.Type;
import java.util.HashMap;
import org.json.JSONObject;

public class RegistrationPage extends JFrame implements ActionListener {
    private JTextField fistNameTextField;
    private JTextField lastNameTextField;
    private JTextField userNameTextField;
    private JTextField emailTextField;
    private JTextField passwordTextField;
    private JTextField verifyPasswordTextField;

    public RegistrationPage() {
        setTitle("REGISTRATION FORM");
        getContentPane().setForeground(new Color(0, 0, 0));

        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.NORTH);

        JPanel registerPanel = new JPanel();
        registerPanel.setBackground(new Color(139, 0, 0));
        registerPanel.setForeground(new Color(0, 0, 0));
        getContentPane().add(registerPanel, BorderLayout.CENTER);
        registerPanel.setLayout(null);

        JLabel firstName = new JLabel("First Name *");
        firstName.setBounds(240, 105, 80, 17);
        firstName.setFont(new Font("Arial", Font.PLAIN, 14));
        registerPanel.add(firstName);

        JLabel lastName = new JLabel("Last Name *");
        lastName.setBounds(240, 145, 80, 17);
        lastName.setFont(new Font("Arial", Font.PLAIN, 14));
        registerPanel.add(lastName);
        
        JLabel userName = new JLabel("User Name *");
        userName.setBounds(240, 210, 85, 17);
        userName.setFont(new Font("Arial", Font.PLAIN, 14));
        registerPanel.add(userName);

        JLabel email = new JLabel("Email *");
        email.setBounds(280, 253, 50, 17);
        email.setFont(new Font("Arial", Font.PLAIN, 14));
        registerPanel.add(email);

        JLabel password = new JLabel("Password *");
        password.setBounds(254, 290, 75, 17);
        password.setFont(new Font("Arial", Font.PLAIN, 14));
        registerPanel.add(password);

        JLabel verifyPassword = new JLabel("Verify Password *");
        verifyPassword.setBounds(214, 343, 113, 17);
        verifyPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        registerPanel.add(verifyPassword);

        fistNameTextField = new JTextField();
        fistNameTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        fistNameTextField.setBounds(372, 104, 96, 19);
        fistNameTextField.setColumns(10);
        registerPanel.add(fistNameTextField);

        lastNameTextField = new JTextField();
        lastNameTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        lastNameTextField.setColumns(10);
        lastNameTextField.setBounds(372, 144, 96, 19);
        registerPanel.add(lastNameTextField);

        userNameTextField = new JTextField();
        userNameTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        userNameTextField.setColumns(10);
        userNameTextField.setBounds(372, 209, 96, 19);
        registerPanel.add(userNameTextField);
        
        emailTextField = new JTextField();
        emailTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        emailTextField.setColumns(10);
        emailTextField.setBounds(372, 250, 96, 19);
        registerPanel.add(emailTextField);

        passwordTextField = new JTextField();
        passwordTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordTextField.setColumns(10);
        passwordTextField.setBounds(372, 289, 96, 19);
        registerPanel.add(passwordTextField);

        verifyPasswordTextField = new JTextField();
        verifyPasswordTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        verifyPasswordTextField.setColumns(10);
        verifyPasswordTextField.setBounds(372, 342, 96, 19);
        registerPanel.add(verifyPasswordTextField);

        JButton registerButton = new JButton("REGISTER");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {

                if (validations()) {
                    String username = userNameTextField.getText();
                    String password = passwordTextField.getText();
                    String firstName = fistNameTextField.getText();
                    String lastName = lastNameTextField.getText();
                    String email = emailTextField.getText();
                    
                    String url = "http://localhost:9000/users/?";
                    RestAPIHook a = new RestAPIHook();
                    HashMap<String, String> params = new HashMap<>();
                    params.put("user_name", username);
                    params.put("password", password);
                    params.put("first_name", firstName);
                    params.put("last_name", lastName);
                    params.put("email", email);
                    System.out.println(params);
                    JSONObject p = a.invokePostMethod(url, params);
                    System.out.println(p);
                }
                System.out.println("Hi");
            }
        });

        registerButton.setFont(new Font("Arial", Font.PLAIN, 14));
        registerButton.setBounds(284, 394, 123, 29);
        registerPanel.add(registerButton);

        JLabel userLabel = new JLabel("USER REGISTRATION");
        userLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        userLabel.setBounds(248, 38, 194, 29);
        registerPanel.add(userLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        System.out.println("Hi");

    }

    public boolean validations() {
        boolean isValid = true;
        fistNameTextField.setBackground(Color.white);
        lastNameTextField.setBackground(Color.white);
        userNameTextField.setBackground(Color.white);
        emailTextField.setBackground(Color.white);
        passwordTextField.setBackground(Color.white);
        verifyPasswordTextField.setBackground(Color.white);
        System.out.println(fistNameTextField.getText());
        System.out.println("Hi"+lastNameTextField.getText());
        System.out.println(userNameTextField.getText());
        
        if (fistNameTextField.getText().length() == 0) {
            fistNameTextField.setBackground(Color.red);
            isValid = false;
        }
        if (this.lastNameTextField.getText().length() == 0) {
            lastNameTextField.setBackground(Color.red);
            isValid = false;
        }
        if (this.emailTextField.getText().length() == 0) {
            emailTextField.setBackground(Color.red);
            isValid = false;
        }
        if (this.userNameTextField.getText().length() == 0) {
            userNameTextField.setBackground(Color.red);
            isValid = false;
        }
        if (this.passwordTextField.getText().length() == 0) {
            passwordTextField.setBackground(Color.red);
            isValid = false;
        }
        if (this.verifyPasswordTextField.getText().length() == 0) {
            verifyPasswordTextField.setBackground(Color.red);
            isValid = false;
        }

        return isValid;
    }
}
