/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ui.components;

/**
 *
 * @author munee
 */
public class RssFeed {
  public String name;
  public String url;
  public Article[] articles;

  public RssFeed(String name, String url, Article[] articles) {
    this.name = name;
    this.url = url;
    this.articles = articles;
  }
}