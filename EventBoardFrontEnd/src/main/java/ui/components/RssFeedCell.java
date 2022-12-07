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

/**
 *
 * @author munee
 */
public class RssFeedCell extends AbstractCellEditor implements TableCellEditor, TableCellRenderer{
  JPanel panel;
  JLabel text;
  JButton showButton;

  RssFeed feed;

  public RssFeedCell() {
    text = new JLabel();
    showButton = new JButton("View Articles");
    showButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent arg0) {
        JOptionPane.showMessageDialog(null, "Reading " + feed.name);
      }
    });

    panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    panel.add(text);
    panel.add(showButton);
  }

  private void updateData(RssFeed feed, boolean isSelected, JTable table) {
    this.feed = feed;

    text.setText("" + feed.name + "" + feed.url + "Articles " + feed.articles.length + "");

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
