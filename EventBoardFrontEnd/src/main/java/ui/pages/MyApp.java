/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.pages;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author mkonidala
 */
public class MyApp extends JFrame{
public static void main(String[] args){    
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