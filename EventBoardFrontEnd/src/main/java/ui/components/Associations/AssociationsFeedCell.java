/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.components.Associations;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.*;
import javax.swing.table.TableCellEditor;
import org.json.JSONObject;
import rest.RestAPIHook;
import java.awt.Dimension;

/**
 *
 * @author munee
 */
public class AssociationsFeedCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    JPanel panel;
    JLabel text;
    JButton approveButton;
    JButton rejectButton;
    JButton followButton;
    JButton unfollowButton;
    AssociationsFeed feed;

    public AssociationsFeedCell() {
        //This is the cell of the associations feed table: Text + Approve & Reject Button / Follow & Un Follow Button
        text = new JLabel();
        RestAPIHook rest = new RestAPIHook();

        approveButton = new JButton("Approve");
        rejectButton = new JButton("Reject");
        followButton = new JButton("Follow");
        unfollowButton = new JButton("Unfollow");

        approveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String url = "http://localhost:9001/associations/?approval=Y&association_id=" + feed.association_id;
                JSONObject output = rest.invokePostMethod(url, null);
                if ((boolean) output.get("isSuccess")) {
                    JOptionPane.showMessageDialog(null, "Approved " + feed.association_name);
                    feed.isApproved = true;
                    approveButton.setText("Approved");
                    approveButton.setEnabled(false);
                    approveButton.setVisible(true);

                    rejectButton.setEnabled(false);
                    rejectButton.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Approval failed for " + feed.association_name);
                }
            }
        });

        rejectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String url = "http://localhost:9001/associations/?approval=N&association_id=" + feed.association_id;
                JSONObject output = rest.invokePostMethod(url, null);
                if ((boolean) output.get("isSuccess")) {
                    JOptionPane.showMessageDialog(null, "Rejected " + feed.association_name);
                    feed.isRejected = true;
                    rejectButton.setText("Rejected");
                    rejectButton.setEnabled(false);
                    rejectButton.setVisible(true);

                    approveButton.setEnabled(false);
                    approveButton.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Reject failed for " + feed.association_name);
                }

            }
        });
        
        followButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String url = "http://localhost:9001/associations/?follow=Y&user_id=" + feed.user_id + "&association_id=" + feed.association_id;
                JSONObject output = rest.invokePostMethod(url, null);
                if ((boolean) output.get("isSuccess")) {
                    JOptionPane.showMessageDialog(null, "Followed the association: " + feed.association_name);
                    feed.isChanged = true;
                    followButton.setText("Followed");
                    followButton.setEnabled(false);
                    followButton.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Follow failed for the association: " + feed.association_name);
                }
            }
        });

        unfollowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String url = "http://localhost:9001/associations/?follow=N&user_id=" + feed.user_id + "&association_id=" + feed.association_id;
                JSONObject output = rest.invokePostMethod(url, null);
                if ((boolean) output.get("isSuccess")) {
                    JOptionPane.showMessageDialog(null, "Unfollowed the association: " + feed.association_name);
                    feed.isChanged = true;
                    unfollowButton.setText("Unfollowed");
                    unfollowButton.setEnabled(false);
                    unfollowButton.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(null, "Unfollow failed for the association: " + feed.association_name);
                }

            }
        });


        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        text.setSize(new Dimension(400, 400));
        panel.add(text);
        panel.add(approveButton);
        panel.add(rejectButton);
        panel.add(followButton);
        panel.add(unfollowButton);

    }

    private void updateData(AssociationsFeed feed, boolean isSelected, JTable table) {
        this.feed = feed;
        if (feed.isAdmin) {
            followButton.setVisible(false);
            unfollowButton.setVisible(false);
            if (feed.isApproved || feed.isRejected) {
                if (feed.isApproved) {
                    approveButton.setText("Approved");
                    approveButton.setEnabled(false);
                    approveButton.setVisible(true);

                    rejectButton.setEnabled(false);
                    rejectButton.setVisible(false);
                } else {
                    rejectButton.setText("Rejected");
                    rejectButton.setEnabled(false);
                    rejectButton.setVisible(true);

                    approveButton.setEnabled(false);
                    approveButton.setVisible(false);
                }

            } else {
                approveButton.setText("Approve");
                approveButton.setEnabled(true);
                approveButton.setVisible(true);

                rejectButton.setText("Reject");
                rejectButton.setEnabled(true);
                rejectButton.setVisible(true);
            }
        } else{
            approveButton.setVisible(false);
            rejectButton.setVisible(false);
            if (feed.isFollow) {
                unfollowButton.setVisible(false);
                if (feed.isChanged) {
                    followButton.setText("Followed");
                    followButton.setEnabled(false);
                    followButton.setVisible(true);
                } else{
                    followButton.setText("Follow");
                    followButton.setEnabled(true);
                    followButton.setVisible(true);
                }
            } else {
                followButton.setVisible(false);
                if (feed.isChanged) {
                    unfollowButton.setText("Unfollowed");
                    unfollowButton.setEnabled(false);
                    unfollowButton.setVisible(true);
                } else {
                    unfollowButton.setText("Unfollow");
                    unfollowButton.setEnabled(true);
                    unfollowButton.setVisible(true);
                }
            }
        }

        String textString = "<html>Association Name: " + feed.association_name + "<br/>Description: " + feed.description + "<br/>";
        String address = feed.address;
        String contact_info = feed.contact_info;
        String email = feed.email;
        if (address != null) {
            textString += "Address: " + address+" ";
        }
        if (contact_info != null) {
            textString += "Contact Information: " + contact_info+" ";
        }
        if (email != null) {
            textString += "Email: " + email;
        }

        textString += "</html>";
        text.setText(textString);
        //"<html>Hello World!<br/>blahblahblah</html>"
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(table.getBackground());
        }
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        AssociationsFeed feed = (AssociationsFeed) value;
        updateData(feed, true, table);
        return panel;
    }

    public Object getCellEditorValue() {
        return null;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        AssociationsFeed feed = (AssociationsFeed) value;
        updateData(feed, isSelected, table);
        return panel;
    }
}
