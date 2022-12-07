/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.components;
import ui.components.Events.EventsFeed;
import ui.components.Associations.AssociationsFeedCell;
import ui.components.Associations.AssociationsFeedTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;
import org.json.JSONObject;
import rest.RestAPIHook;
import ui.components.Events.EventsFeedCell;
import ui.components.Events.EventsFeedTableModel;

/**
 *
 * @author munee
 */
public class JInteractiveTableExample extends JFrame {
  public JInteractiveTableExample() {
    super("Interactive Table Cell Example");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(500, 300);
    
    String url = "http://localhost:9002/events/?type=feed&user_id=12";
    RestAPIHook a = new RestAPIHook();
    JSONObject p = a.invokeGetMethod(url);
    Iterator<String> keys = p.keys();
    
    List feeds = new ArrayList();
    while(keys.hasNext()){
        String key = keys.next();
        
        if("isSuccess".equals(key)){
            continue;
        }
        
        HashMap temp = new HashMap();
        
        temp.put("event_id", key);
        JSONObject val = p.getJSONObject(key);
        Iterator<String> childKeys = val.keys();
        while(childKeys.hasNext()){
            String childKey = childKeys.next();
            temp.put(childKey, val.get(childKey));
        }
        temp.put("isRendered", true);
        temp.put("isFollow", true);
        temp.put("user_id", "12");
        System.out.println("Hi2");
        System.out.println(temp);
        System.out.println("Hi3");
        feeds.add(new EventsFeed(temp));
        
    }
    System.out.println("Hi1");
    
    
    
    JTable table = new JTable(new EventsFeedTableModel(feeds));
    //AssociationsFeedCell cell = new AssociationsFeedCell();
    table.setDefaultRenderer(EventsFeed.class, new EventsFeedCell());
    table.setDefaultEditor(EventsFeed.class, new EventsFeedCell());
    table.setRowHeight(60);
    add(new JScrollPane(table));
  }
  
  public static void main(String[] args){
      JFrame f = new JFrame();
      JInteractiveTableExample a = new JInteractiveTableExample();
      //f.add(a);
      a.setVisible(true);
      
  }
}
