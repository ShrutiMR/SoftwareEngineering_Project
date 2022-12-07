/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.components;
import java.util.ArrayList;
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
    System.out.println("Hi1");
    System.out.println(p.get("5"));
    JSONObject x =(JSONObject) p.get("5");
    System.out.println(x.get("address"));
    
    List feeds = new ArrayList();
    feeds.add(new RssFeed("pekalicious", "http://feeds2.feedburner.com/pekalicious",
      new Article[] {
        new Article("Title1", "http://title1.com", "Content 1"),
        new Article("Title2", "http://title2.com", "Content 2"),
        new Article("Title3", "http://title3.com", "Content 3"),
        new Article("Title4", "http://title4.com", "Content 4"),
    }));
    System.out.println(feeds);
    feeds.add(new RssFeed("Various Thoughts on Photography", "http://various-photography-thoughts.blogspot.com/feeds/posts/default",
      new Article[] {
        new Article("Title1", "http://title1.com", "Content 1"),
        new Article("Title2", "http://title2.com", "Content 2"),
        new Article("Title3", "http://title3.com", "Content 3"),
        new Article("Title4", "http://title4.com", "Content 4"),
    }));
    
    feeds.add(new RssFeed("Various Thoughts on Photography", "http://various-photography-thoughts.blogspot.com/feeds/posts/default",
      new Article[] {
        new Article("Title1", "http://title1.com", "Content 1"),
        new Article("Title2", "http://title2.com", "Content 2"),
        new Article("Title3", "http://title3.com", "Content 3"),
        new Article("Title4", "http://title4.com", "Content 4"),
    }));

    feeds.add(new RssFeed("Various Thoughts on Photography", "http://various-photography-thoughts.blogspot.com/feeds/posts/default",
      new Article[] {
        new Article("Title1", "http://title1.com", "Content 1"),
        new Article("Title2", "http://title2.com", "Content 2"),
        new Article("Title3", "http://title3.com", "Content 3"),
        new Article("Title4", "http://title4.com", "Content 4"),
    }));

    feeds.add(new RssFeed("Various Thoughts on Photography", "http://various-photography-thoughts.blogspot.com/feeds/posts/default",
      new Article[] {
        new Article("Title1", "http://title1.com", "Content 1"),
        new Article("Title2", "http://title2.com", "Content 2"),
        new Article("Title3", "http://title3.com", "Content 3"),
        new Article("Title4", "http://title4.com", "Content 4"),
    }));

    feeds.add(new RssFeed("Various Thoughts on Photography", "http://various-photography-thoughts.blogspot.com/feeds/posts/default",
      new Article[] {
        new Article("Title1", "http://title1.com", "Content 1"),
        new Article("Title2", "http://title2.com", "Content 2"),
        new Article("Title3", "http://title3.com", "Content 3"),
        new Article("Title4", "http://title4.com", "Content 4"),
    }));

    feeds.add(new RssFeed("Various Thoughts on Photography", "http://various-photography-thoughts.blogspot.com/feeds/posts/default",
      new Article[] {
        new Article("Title1", "http://title1.com", "Content 1"),
        new Article("Title2", "http://title2.com", "Content 2"),
        new Article("Title3", "http://title3.com", "Content 3"),
        new Article("Title4", "http://title4.com", "Content 4"),
    }));


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
