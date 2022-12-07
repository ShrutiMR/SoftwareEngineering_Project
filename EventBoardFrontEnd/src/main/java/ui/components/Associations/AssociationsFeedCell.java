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
public class AssociationsFeedCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{
  JPanel panel;
  JLabel text;
    JButton approveButton;
    JButton rejectButton;
  AssociationsFeed feed;

  public AssociationsFeedCell() {
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
         
      }
    });
    
    panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    text.setSize(new Dimension(400, 400));
    panel.add(text);
    panel.add(approveButton);
    panel.add(rejectButton);
    
  }

  private void updateData(AssociationsFeed feed, boolean isSelected, JTable table) {
    this.feed = feed;
    String textString = "<html>Association Name: " + feed.association_name + "<br/>Description: " + feed.description + "<br/>";
    String address = feed.address;
    String contact_info = feed.contact_info;
    String email = feed.email;
    if(address!=null){
        textString += "Address: "+ address;
    }
    if(contact_info!=null){
        textString += "Contact Information: "+ contact_info;
    }
    if(email!=null){
        textString += "Email: "+ email;
    }
    
    textString += "</html>";
    text.setText(textString);
    //"<html>Hello World!<br/>blahblahblah</html>"
    if (isSelected) {
      panel.setBackground(table.getSelectionBackground());
    }else{
      panel.setBackground(table.getBackground());
    }
  }

  public Component getTableCellEditorComponent(JTable table, Object value,
      boolean isSelected, int row, int column) {
    AssociationsFeed feed = (AssociationsFeed)value;
    updateData(feed, true, table);
    return panel;
  }

  public Object getCellEditorValue() {
    return null;
  }

  public Component getTableCellRendererComponent(JTable table, Object value,
      boolean isSelected, boolean hasFocus, int row, int column) {
    AssociationsFeed feed = (AssociationsFeed)value;
    updateData(feed, isSelected, table);
    return panel;
  }
}