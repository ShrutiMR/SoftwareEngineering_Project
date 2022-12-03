/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.pages;

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


public class RegistrationPage extends JFrame implements ActionListener {
	private JTextField fistNameTextField;
	private JTextField middleNameTextField;
	private JTextField lastNameTextField;
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
		
		JLabel firstName = new JLabel("First Name");
		firstName.setBounds(248, 105, 70, 17);
		firstName.setFont(new Font("Arial", Font.PLAIN, 14));
		registerPanel.add(firstName);
		
		JLabel middleName = new JLabel("Middle Name");
		middleName.setBounds(233, 147, 84, 17);
		middleName.setFont(new Font("Arial", Font.PLAIN, 14));
		registerPanel.add(middleName);
		
		JLabel lastName = new JLabel("Last Name");
		lastName.setBounds(248, 195, 69, 17);
		lastName.setFont(new Font("Arial", Font.PLAIN, 14));
		registerPanel.add(lastName);
		
		JLabel email = new JLabel("Email");
		email.setBounds(280, 243, 35, 17);
		email.setFont(new Font("Arial", Font.PLAIN, 14));
		registerPanel.add(email);
		
		JLabel password = new JLabel("Password");
		password.setBounds(254, 290, 63, 17);
		password.setFont(new Font("Arial", Font.PLAIN, 14));
		registerPanel.add(password);
		
		JLabel verifyPassword = new JLabel("Verify Password");
		verifyPassword.setBounds(214, 343, 103, 17);
		verifyPassword.setFont(new Font("Arial", Font.PLAIN, 14));
		registerPanel.add(verifyPassword);
		
		fistNameTextField = new JTextField();
		fistNameTextField.setFont(new Font("Arial", Font.PLAIN, 14));
		fistNameTextField.setBounds(372, 104, 96, 19);
		registerPanel.add(fistNameTextField);
		fistNameTextField.setColumns(10);
		
		middleNameTextField = new JTextField();
		middleNameTextField.setFont(new Font("Arial", Font.PLAIN, 14));
		middleNameTextField.setColumns(10);
		middleNameTextField.setBounds(372, 146, 96, 19);
		registerPanel.add(middleNameTextField);
		
		lastNameTextField = new JTextField();
		lastNameTextField.setFont(new Font("Arial", Font.PLAIN, 14));
		lastNameTextField.setColumns(10);
		lastNameTextField.setBounds(372, 194, 96, 19);
		registerPanel.add(lastNameTextField);
		
		emailTextField = new JTextField();
		emailTextField.setFont(new Font("Arial", Font.PLAIN, 14));
		emailTextField.setColumns(10);
		emailTextField.setBounds(372, 242, 96, 19);
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
		
	}
}

