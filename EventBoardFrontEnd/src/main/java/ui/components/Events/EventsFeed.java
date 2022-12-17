/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.components.Events;

import java.util.HashMap;
/**
 *
 * @author munee
 */
public class EventsFeed {
  public String name;
  public String event_id;
  public String association_id;
  public String user_id;
  public String description;
  public String venue;
  public String start_time;
  public String end_time;
  public boolean isRendered = false;
  public boolean isFollow = false;
  public boolean isChanged = false;
  

  public EventsFeed(HashMap params) {
    this.name = (String) params.get("name");
    this.event_id = (String) params.get("event_id");
    this.association_id = (String) params.get("association_id");
    this.user_id = (String) params.get("user_id");
    this.description = (String) params.get("description");
    this.venue = (String) params.get("venue");
    this.start_time = (String) params.get("start_time");
    this.end_time = (String) params.get("end_time");
    this.isRendered = (boolean) params.get("isRendered");
    this.isFollow = (boolean) params.get("isFollow");
    
  }
}