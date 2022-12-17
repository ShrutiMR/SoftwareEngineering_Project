/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.components.Associations;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author munee
 */
public class AssociationsFeedTableModel extends AbstractTableModel {
  List feeds;

  public AssociationsFeedTableModel(List feeds) {
    this.feeds = feeds;
  }

  public Class getColumnClass(int columnIndex) { return AssociationsFeed.class; }
    public int getColumnCount() { return 1; }
    public String getColumnName(int columnIndex) { return "Feed"; }
    public int getRowCount() { return (feeds == null) ? 0 : feeds.size(); }
    public Object getValueAt(int rowIndex, int columnIndex) { return (feeds == null) ? null : feeds.get(rowIndex); }
    public boolean isCellEditable(int columnIndex, int rowIndex) { return true; }
}
