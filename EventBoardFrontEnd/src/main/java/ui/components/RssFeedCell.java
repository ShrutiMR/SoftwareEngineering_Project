/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.components;

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
public class RssFeedCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{
  JPanel panel;
  JLabel text;
  JButton approveButton;
  JButton rejectButton;
  RssFeed feed;

  public RssFeedCell() {
    text = new JLabel();
      RestAPIHook rest = new RestAPIHook();
      
    approveButton = new JButton("Approve");
    rejectButton = new JButton("Reject");
    approveButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
          String url = "http://localhost:9001/associations/?approval=Y&association_id="+feed.association_id;
          JSONObject output = rest.invokePostMethod(url,null);
          if((boolean)output.get("isSuccess")){
        JOptionPane.showMessageDialog(null, "Approved " + feed.association_name);
          } else{
              JOptionPane.showMessageDialog(null, "Approval failed for " + feed.association_name);
          }
        approveButton.setText("Approved");
        approveButton.setEnabled(false);
        
        rejectButton.setEnabled(false);
        rejectButton.setVisible(false);
      }
    });
    
    rejectButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
          String url = "http://localhost:9001/associations/?approval=N&association_id="+feed.association_id;
          JSONObject output = rest.invokePostMethod(url,null);
          if((boolean)output.get("isSuccess")){
        JOptionPane.showMessageDialog(null, "Rejected " + feed.association_name);
          } else{
              JOptionPane.showMessageDialog(null, "Reject failed for " + feed.association_name);
          }
         rejectButton.setText("Rejected");
        rejectButton.setEnabled(false);
        
        approveButton.setEnabled(false);
        approveButton.setVisible(false);
      }
    });
    
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    text.setSize(new Dimension(400, 400));
    panel.add(text);
    panel.add(approveButton);
    panel.add(rejectButton);
    
  }

  private void updateData(RssFeed feed, boolean isSelected, JTable table) {
    this.feed = feed;

    text.setText("<html>Association Name: " + feed.association_name + "<br/>Description: " + feed.description + "<br/>Contact Information: " + feed.contact_info + ", Email: "+feed.email+"</html>");
    //"<html>Hello World!<br/>blahblahblah</html>"
    if (isSelected) {
      panel.setBackground(table.getSelectionBackground());
    }else{
      panel.setBackground(table.getBackground());
    }
  }

  public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column) {
    RssFeed feed = (RssFeed)value;
    updateData(feed, true, table);
    return panel;
  }

  public Object getCellEditorValue() {
    return null;
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
    RssFeed feed = (RssFeed)value;
    updateData(feed, isSelected, table);
    return panel;
  }
}
