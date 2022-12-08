package ui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import ui.pages.LoginPage;
import ui.pages.RegistrationPage;
 
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.*;  
import java.awt.*;  
import java.awt.event.*;
import java.io.IOException;
import java.lang.Exception;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JComponent;
import java.awt.Window.Type;
import ui.pages.RegistrationPageAssociation;
 

public class CustomHyperLink extends JLabel {

	private String url;
//    private String html = "<html><a href=''>%s</a></html>";
    String page;
     
    public CustomHyperLink(String text) {       
        this(text, null, null, null);
    }
     
    public CustomHyperLink(String text, String page, JFrame prevPage) {
        this(text, page, prevPage, null);
    }
     
    public void setURL(String page) {
        this.page = page;
    }  
     
    public CustomHyperLink(String text, String page, JFrame prevPage, String tooltip) {
        super(text);
        this.page = page;
         
        setForeground(Color.CYAN.darker());
                 
        setToolTipText(tooltip);       
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
         
        addMouseListener(new MouseAdapter() {

			@Override
            public void mouseEntered(MouseEvent e) {
                setText(String.format(page, text));
            }
             
            @Override
            public void mouseExited(MouseEvent e) {
                setText(text);
            }
             
            @Override
            public void mouseClicked(MouseEvent e) {
                //Desktop.getDesktop().browse(new URI(JHyperlinkClass.this.url));
                if(page == "RegistrationPage"){
                   prevPage.dispose();
                   RegistrationPage registerPage = new RegistrationPage();
                   registerPage.setExtendedState(JFrame.MAXIMIZED_BOTH); 
                   registerPage.setUndecorated(true);
                   registerPage.setVisible(true); //make form visible to the use
                }              
                else if(page == "LoginPage"){
                   prevPage.dispose();
                   LoginPage loginPage = new LoginPage();
                   loginPage.setExtendedState(JFrame.MAXIMIZED_BOTH); 
                   loginPage.setUndecorated(true);
                   loginPage.setVisible(true); //make form visible to the use
                }
                else if(page == "RegistrationPageAssociation"){
                   prevPage.dispose();
                   RegistrationPageAssociation regPage = new RegistrationPageAssociation();
                   regPage.setExtendedState(JFrame.MAXIMIZED_BOTH); 
//                   regPage.setUndecorated(true);
                   regPage.setVisible(true);
                }
                else{
                    JOptionPane.showInputDialog("Form Loading...");
                }
				 
            }
             
        });
         
    }
	
}
