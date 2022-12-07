/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.components;

import java.util.HashMap;
/**
 *
 * @author munee
 */
public class RssFeed {
  public String association_name;
  public String association_id;
  public String tag_id;
  public String description;
  public String address;
  public String contact_info;
  public String email;
  

  public RssFeed(HashMap params) {
    this.association_name = (String) params.get("association_name");
    this.association_id = (String) params.get("association_id");
    this.tag_id = (String) params.get("tag_id");
    this.description = (String) params.get("description");
    this.address = (String) params.get("address");
    this.contact_info = (String) params.get("contact_info");
    this.email = (String) params.get("email");
  }
}