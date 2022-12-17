package ui.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import ui.pages.LoginPage;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.*;
import ui.pages.RegistrationPageAssociation;
import ui.pages.RegistrationPageUser;

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
        // Custom Hyper Link
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
                //Navigation on click of the hyperlink
                if (page == "RegistrationPage") {
                    prevPage.dispose();
                    RegistrationPageUser registerPage = new RegistrationPageUser();
                    registerPage.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    registerPage.setUndecorated(true);
                    registerPage.setVisible(true); //make form visible to the use
                } else if (page == "LoginPage") {
                    prevPage.dispose();
                    LoginPage loginPage = new LoginPage();
                    loginPage.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    loginPage.setUndecorated(true);
                    loginPage.setVisible(true); //make form visible to the use
                } else if (page == "RegistrationPageAssociation") {
                    prevPage.dispose();
                    RegistrationPageAssociation regPage = new RegistrationPageAssociation();
                    regPage.setExtendedState(JFrame.MAXIMIZED_BOTH);
//                   regPage.setUndecorated(true);
                    regPage.setVisible(true);
                } else {
                    JOptionPane.showInputDialog("Form Loading...");
                }

            }

        });

    }

}
