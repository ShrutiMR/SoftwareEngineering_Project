/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.components;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;
import org.json.JSONObject;
import rest.RestAPIHook;

/**
 *
 * @author munee
 */
public class JInteractiveTableExample extends JFrame {
  public JInteractiveTableExample() {
    super("Interactive Table Cell Example");
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    setSize(500, 300);
    
    String url = "http://localhost:9001/associations/?type=administrator";
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
        
        temp.put("association_id", key);
        JSONObject val = p.getJSONObject(key);
        Iterator<String> childKeys = val.keys();
        while(childKeys.hasNext()){
            String childKey = childKeys.next();
            temp.put(childKey, val.get(childKey));
        }
        System.out.println("Hi2");
        System.out.println(temp);
        System.out.println("Hi3");
        feeds.add(new RssFeed(temp));
        
    }
    System.out.println("Hi1");
    
    
    
    JTable table = new JTable(new RssFeedTableModel(feeds));
    RssFeedCell cell = new RssFeedCell();
    table.setDefaultRenderer(RssFeed.class, cell);
    table.setDefaultEditor(RssFeed.class, cell);
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
