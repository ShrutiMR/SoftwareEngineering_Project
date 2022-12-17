/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.components.Events;

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
public class EventsFeedCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {

    JPanel panel;
    JLabel text;
    JButton followButton;
    JButton unfollowButton;
    EventsFeed feed;

    public EventsFeedCell() {
        //This is the cell of the events feed table: Text + Follow Button + Un Follow Button
        text = new JLabel();
        RestAPIHook rest = new RestAPIHook();

        followButton = new JButton("Follow");
        unfollowButton = new JButton("Unfollow");
        followButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String url = "http://localhost:9002/events/?follow=Y&user_id=" + feed.user_id + "&event_id=" + feed.event_id;
                JSONObject output = rest.invokePostMethod(url, null);
                if ((boolean) output.get("isSuccess")) {
                    JOptionPane.showMessageDialog(null, "Followed the event: " + feed.name);
                    feed.isChanged = true;
                    followButton.setText("Followed");
                    followButton.setEnabled(false);
                    followButton.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Follow failed for the event: " + feed.name);
                }
            }
        });

        unfollowButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String url = "http://localhost:9002/events/?follow=N&user_id=" + feed.user_id + "&event_id=" + feed.event_id;
                JSONObject output = rest.invokePostMethod(url, null);
                if ((boolean) output.get("isSuccess")) {
                    JOptionPane.showMessageDialog(null, "Unfollowed the event: " + feed.name);
                    feed.isChanged = true;
                    unfollowButton.setText("Unfollowed");
                    unfollowButton.setEnabled(false);
                    unfollowButton.setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(null, "Unfollow failed for the event: " + feed.name);
                }

            }
        });

        panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        text.setSize(new Dimension(400, 400));
        panel.add(text);
        panel.add(followButton);
        panel.add(unfollowButton);

    }

    private void updateData(EventsFeed feed, boolean isSelected, JTable table) {
        this.feed = feed;

        if (!feed.isRendered) {
            followButton.setVisible(false);
            unfollowButton.setVisible(false);
        } else {
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
        
        // We parse start_time and display time in AM/PM. 
        int start_hour = Integer.parseInt(feed.start_time.substring(11,13));
        String start_time = feed.start_time;
        if(start_hour>12){
            start_hour -=12;
            String temp = Integer.toString(start_hour);
            
            if(start_hour>9){          
                start_time = start_time.substring(0,11)+ temp + start_time.substring(13)+" PM";
                
            } else{
                start_time = start_time.substring(0,11)+ "0" + temp + start_time.substring(13)+" PM";    
            }    
        } else{
            start_time+=" AM";
        }
        
        // We parse end_time and display time in AM/PM. 
        int end_hour = Integer.parseInt(feed.end_time.substring(11,13));
        String end_time = feed.end_time;
        if(end_hour>12){
            end_hour -=12;
            String temp = Integer.toString(end_hour);
            
            if(end_hour>9){
                end_time = end_time.substring(0,11)+ temp + end_time.substring(13)+" PM";
                
            } else{
                end_time = end_time.substring(0,11)+ "0" + temp + end_time.substring(13)+" PM";    
            }    
        } else{
            end_time+=" AM";
        }
        
        String textString = "<html>Event: " + feed.name + " , Description: " + feed.description + "<br/>"
                + "Start Time: " + start_time + ", End Time: " + end_time + "<br/>";
        String venue = feed.venue;

        if (venue != null) {
            textString += "Venue: " + venue;
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
        EventsFeed feed = (EventsFeed) value;
        updateData(feed, true, table);
        return panel;
    }

    public Object getCellEditorValue() {
        return null;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        EventsFeed feed = (EventsFeed) value;
        updateData(feed, isSelected, table);
        return panel;
    }
}
