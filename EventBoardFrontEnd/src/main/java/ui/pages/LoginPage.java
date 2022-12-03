package ui.pages;

//import required classes and packages  
import javax.swing.*;  
import java.awt.*;  
import java.awt.event.*;
import java.io.IOException;
import java.lang.Exception;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JComponent;
import java.awt.Window.Type;
import org.json.JSONObject;
import ui.components.CustomHyperLink;
import rest.RestAPIHook;


//create the main class  
//class events_board_gui  
//{     
    public class LoginPage extends JFrame implements ActionListener  
    {  
        //initialize button, panel, label, and text field  
        JButton b1;  
        JPanel newPanel;  
        
        JLabel userLabel, passLabel;  
        JTextField textField2 = new JTextField();  
        JTextField textField1 = new JTextField();  
        String text = "Sign Up for Free";
        CustomHyperLink hyperLinkSignUp = new CustomHyperLink(text, "RegistrationPage", this);
          
        //calling constructor  
        public LoginPage()  
        {
        	setFont(null);
        	setForeground(new Color(139, 0, 0));
              
            //create label for username   
            userLabel = new JLabel();  
            userLabel.setForeground(new Color(0, 0, 0));
            userLabel.setBounds(5, 120, 281, 50);
            userLabel.setHorizontalAlignment(SwingConstants.CENTER);
            userLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            userLabel.setBackground(new Color(255, 255, 255));
            userLabel.setText("USERNAME");      //set label value for textField1  
              
            //create text field to get username from the user  
            textField1 = new JTextField(15);    //set length of the text  
            textField1.setBounds(296, 123, 248, 45);
      
            //create label for password  
            passLabel = new JLabel();
            passLabel.setToolTipText("");
            passLabel.setBackground(new Color(255, 255, 255));
            passLabel.setBounds(5, 203, 281, 50);
            passLabel.setHorizontalAlignment(SwingConstants.CENTER);
            passLabel.setForeground(new Color(0, 0, 0));
            passLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            passLabel.setText("PASSWORD");      //set label value for textField2  
              
            //create text field to get password from the user  
            textField2 = new JPasswordField(15);    //set length for the password  
            textField2.setBounds(296, 206, 248, 45);
              
            //create submit button  
            b1 = new JButton("LOGIN");
            b1.setBackground(new Color(255, 255, 255));
            b1.setBounds(195, 307, 202, 45);
            b1.setFont(new Font("Arial", Font.PLAIN, 14));
              
            //create panel to put form elements  
            newPanel = new JPanel();  
            newPanel.setBackground(new Color(139, 0, 0));
            newPanel.setLayout(null);
            newPanel.add(userLabel);    //set username label to panel  
            newPanel.add(textField1);   //set text field to panel  
            newPanel.add(passLabel);    //set password label to panel  
            newPanel.add(textField2);   //set text field to panel  
            newPanel.add(b1);
            getContentPane().add(newPanel, BorderLayout.CENTER);
            hyperLinkSignUp.setFont(new Font("Arial", Font.PLAIN, 14));
            newPanel.add(hyperLinkSignUp);
            
            hyperLinkSignUp.setToolTipText("Sign Up for Free");
            hyperLinkSignUp.setBorder(null);
            hyperLinkSignUp.setBounds(241, 362, 112, 36);
              
            //perform action on button click   
            b1.addActionListener(this);     //add action listener to button  
            setTitle("LOGIN FORM");         //set title to the login form 
            
            
        }  
          
        //define abstract method actionPerformed() which will be called on button click   
        public void actionPerformed(ActionEvent ae)     //pass action listener as a parameter  
        {  
    		String userValue = textField1.getText();        //get user entered username from the textField1  
        	String passValue = textField2.getText();        //get user entered pasword from the textField2  
                String url = "http://localhost:9000/products/hi/?product_name="+userValue+"&param2="+passValue;
                RestAPIHook a = new RestAPIHook();
                JSONObject p = a.restCall(url, "GET");
                System.out.println("Hi1");
                System.out.println(p);
                System.out.println("Hi2");
              
            //check whether the credentials are authentic or not  
            if (userValue.equals("test1@gmail.com") && passValue.equals("test")) {  //if authentic, navigate user to a new page  
                  
                JOptionPane.showMessageDialog(newPanel, "You have successfully logged in!");
//              HomePage hFrame = new HomePage();
//            	hFrame.setVisible(true);
            }  
            else{  
                //show error message  
            	JOptionPane.showMessageDialog(newPanel, "Please enter valid username and password");
//                System.out.println("Please enter valid username and password");  
            }  
        }  
        
        public static void main(String[] args) {
        	try  
            {  
                //create instance of the CreateLoginForm  
                LoginPage form = new LoginPage(); 
                form.setExtendedState(JFrame.MAXIMIZED_BOTH); 
                form.setUndecorated(true);
                
                form.setVisible(true); //make form visible to the use
                
            }  
            catch(Exception e)  
            {     
                //handle exception   
                JOptionPane.showMessageDialog(null, e.getMessage());  
            }  
        	
        }
    }  
//}  
