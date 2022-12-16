/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTable;
import org.json.JSONObject;
import rest.RestAPIHook;
import ui.components.Associations.AssociationsFeed;
import ui.components.Associations.AssociationsFeedCell;
import ui.components.Associations.AssociationsFeedTableModel;
import ui.components.Events.EventsFeed;
import ui.components.Events.EventsFeedCell;
import ui.components.Events.EventsFeedTableModel;


/**
 *
 * @author shrut
 */
public final class UserHomePage extends javax.swing.JFrame {

    /**
     * Creates new form HomePage
     */
    private String users;
    private JSONObject inputJSON;
    
    public UserHomePage(JSONObject input) {
        //fetching the JSON file which contains information about user loging in.
        //Also initializing the UI components calling the panels to be hidden and visible.
        this.inputJSON = input;
        initComponents();
        active();
    }

    public void allHidden(){
        //Hidding all the panels and only setting the navigation panels to be visible and enabling them.
        navigationPanel.setVisible(true);
        navigationPanel.setEnabled(true);
        profilePanel.setVisible(false);
        profilePanel.setEnabled(false);
        exploreAssociationsPanel.setVisible(false);
        exploreAssociationsPanel.setEnabled(false);
        myAssociationsPanel.setVisible(false);
        myAssociationsPanel.setEnabled(false);
        homePanel.setVisible(false);
        homePanel.setEnabled(false);
        upcomingEventsPanel.setVisible(false);
        upcomingEventsPanel.setEnabled(false);
        pastEventsPanel.setVisible(false);
        pastEventsPanel.setEnabled(false);
        exploreEventsPanel.setVisible(false);
        exploreEventsPanel.setEnabled(false);
    }
    
    public void active(){
        //Hidding all the panels and setting navigation and home panel to be visible.
        allHidden();
        homePanel.setVisible(true);
        homePanel.setEnabled(true);
    }
    
    public void pastEventsTable(){
        //function to fetch and populate the data for past events for that user.
        String pastEventsUrl = "http://localhost:9002/events/?type=past&user_id="+this.inputJSON.get("user_id");
        //initializing the RestAPI an fetching the data as a JSON file.
        RestAPIHook pastEventsHook = new RestAPIHook();
        JSONObject pastEventsData = pastEventsHook.invokeGetMethod(pastEventsUrl);
        //iterating over the JSON file.
        Iterator<String> pastEventsKeys = pastEventsData.keys();
        List pastEventsFeeds = new ArrayList();
        while(pastEventsKeys.hasNext()){
            //continuing id isSuccess parameter is true.
            String pastEventsKey = pastEventsKeys.next();
            if("isSuccess".equals(pastEventsKey)){
                continue;
            }
            //initializing hashmap and populating the data from the JSON.
            HashMap pastEventsTemp = new HashMap();
            pastEventsTemp.put("event_id", pastEventsKey);
            //creating a JSON and appending data to be displayed in the table.
            JSONObject pastEventsVal = pastEventsData.getJSONObject(pastEventsKey);
            Iterator<String> pastEventsChildKeys = pastEventsVal.keys();
            while(pastEventsChildKeys.hasNext()){
                String pastEventsChildKey = pastEventsChildKeys.next();
                pastEventsTemp.put(pastEventsChildKey, pastEventsVal.get(pastEventsChildKey));
            }
            // setting parameters to send this to populate the table.
            // isRendered false to not get a follow button
            pastEventsTemp.put("isRendered", false);
            pastEventsTemp.put("isFollow", false);
            pastEventsTemp.put("user_id", this.inputJSON.get("user_id").toString());
            //adding the data to the arraylist to create the table.
            pastEventsFeeds.add(new EventsFeed(pastEventsTemp));
        }
        //creating a new Jtable with custom render and editor methods
        pastEventsTable = new JTable (new EventsFeedTableModel(pastEventsFeeds));
        pastEventsTable.setDefaultRenderer(EventsFeed.class, new EventsFeedCell());
        pastEventsTable.setDefaultEditor(EventsFeed.class, new EventsFeedCell());
        //setting properties for the table
        pastEventsTable.setRowHeight(60);
        pastEventsTable.getColumnModel().getColumn(0).setHeaderValue("Past Events");
        pastEventsTable.getTableHeader().resizeAndRepaint();
        pastEventsTable.setModel(pastEventsTable.getModel());
        pastEventsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pastEventsTable.setFocusable(false);
        pastEventsTable.setOpaque(false);
        pastEventsTable.setRequestFocusEnabled(false);
        pastEventsTable.setRowSelectionAllowed(false);
        pastEventsTable.setSurrendersFocusOnKeystroke(true);
        pastEventsTable.setUpdateSelectionOnSort(false);
        pastEventsTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane2.setViewportView(pastEventsTable);
        //hidding all the panels and only making the past events panel visible.
        allHidden();
        pastEventsPanel.setVisible(true);
        pastEventsPanel.setEnabled(true);
    }
    
    public void upcomingEventsTable(){
        //function to fetch and populate the data for upcoming followed events.
        String upcomingEventsUrl = "http://localhost:9002/events/?type=upcoming&user_id="+this.inputJSON.get("user_id");
        //initializing the RestAPI an fetching the data as a JSON file.
        RestAPIHook upcomingEventsHook = new RestAPIHook();
        //iterating over the JSON file.
        JSONObject upcomingEventsData = upcomingEventsHook.invokeGetMethod(upcomingEventsUrl);
        Iterator<String> upcomingEventsKeys = upcomingEventsData.keys();
        List upcomingEventsFeeds = new ArrayList();
        while(upcomingEventsKeys.hasNext()){
            //continuing id isSuccess parameter is true.
            String upcomingEventsKey = upcomingEventsKeys.next();
            if("isSuccess".equals(upcomingEventsKey)){
                continue;
            }
            //initializing hashmap and populating the data from the JSON.
            HashMap upcomingEventsTemp = new HashMap();
            upcomingEventsTemp.put("event_id", upcomingEventsKey);
            JSONObject upcomingEventsVal = upcomingEventsData.getJSONObject(upcomingEventsKey);
            Iterator<String> upcomingEventsChildKeys = upcomingEventsVal.keys();
            while(upcomingEventsChildKeys.hasNext()){
                String upcomingEventsChildKey = upcomingEventsChildKeys.next();
                upcomingEventsTemp.put(upcomingEventsChildKey, upcomingEventsVal.get(upcomingEventsChildKey));
            }
            // setting parameters to send this to populate the table.
            // isRendered true and isFollow false to get an unfollow button
            upcomingEventsTemp.put("isRendered", true);
            upcomingEventsTemp.put("isFollow", false);
            upcomingEventsTemp.put("user_id", this.inputJSON.get("user_id").toString());
            //adding the data to the arraylist to create the table.
            upcomingEventsFeeds.add(new EventsFeed(upcomingEventsTemp));
        }
        //creating a new Jtable with custom render and editor methods
        upcomingEventsTable = new JTable(new EventsFeedTableModel(upcomingEventsFeeds));
        upcomingEventsTable.setDefaultRenderer(EventsFeed.class, new EventsFeedCell());
        upcomingEventsTable.setDefaultEditor(EventsFeed.class, new EventsFeedCell());
        //setting properties for the table
        upcomingEventsTable.setRowHeight(60);
        upcomingEventsTable.getColumnModel().getColumn(0).setHeaderValue("Upcoming Events");
        upcomingEventsTable.getTableHeader().resizeAndRepaint();
        upcomingEventsTable.setModel(upcomingEventsTable.getModel());
        upcomingEventsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        upcomingEventsTable.setFocusable(false);
        upcomingEventsTable.setOpaque(false);
        upcomingEventsTable.setRequestFocusEnabled(false);
        upcomingEventsTable.setRowSelectionAllowed(false);
        upcomingEventsTable.setSurrendersFocusOnKeystroke(true);
        upcomingEventsTable.setUpdateSelectionOnSort(false);
        upcomingEventsTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane3.setViewportView(upcomingEventsTable);
        //hidding all the panels and only making the upcoming events panel visible.
        allHidden();
        upcomingEventsPanel.setVisible(true);
        upcomingEventsPanel.setEnabled(true);
    }
    
    public void homeTable(){        
        //function to fetch and populate the data for upcoming events from followed associations.
        String homeUrl = "http://localhost:9002/events/?type=feed&user_id="+this.inputJSON.get("user_id");
        //initializing the RestAPI an fetching the data as a JSON file.
        RestAPIHook homeHook = new RestAPIHook();
        JSONObject homeData = homeHook.invokeGetMethod(homeUrl);
        //iterating over the JSON file.
        Iterator<String> homeKeys = homeData.keys();
        List homeFeeds = new ArrayList();
        while(homeKeys.hasNext()){
            //continuing id isSuccess parameter is true.
            String homeKey = homeKeys.next();
            if("isSuccess".equals(homeKey)){
                continue;
            }
            //initializing hashmap and populating the data from the JSON.
            HashMap homeTemp = new HashMap();
            homeTemp.put("event_id", homeKey);
            JSONObject homeVal = homeData.getJSONObject(homeKey);
            Iterator<String> homeChildKeys = homeVal.keys();
            while(homeChildKeys.hasNext()){
                String homeFeedchildKey = homeChildKeys.next();
                homeTemp.put(homeFeedchildKey, homeVal.get(homeFeedchildKey));
            }
            // setting parameters to send this to populate the table.
            // isRendered true and isFollow true to get an follow button.
            homeTemp.put("isRendered", true);
            homeTemp.put("isFollow", true);
            homeTemp.put("user_id", this.inputJSON.get("user_id").toString());
            homeFeeds.add(new EventsFeed(homeTemp));
        }
        //creating a new Jtable with custom render and editor methods
        homeTable = new JTable(new EventsFeedTableModel(homeFeeds));
        homeTable.setDefaultRenderer(EventsFeed.class, new EventsFeedCell());
        homeTable.setDefaultEditor(EventsFeed.class, new EventsFeedCell());
        //setting properties for the table
        homeTable.setRowHeight(60);
        homeTable.getColumnModel().getColumn(0).setHeaderValue("Home");
        homeTable.getTableHeader().resizeAndRepaint();
        homeTable.setModel(homeTable.getModel());
        homeTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        homeTable.setFocusable(false);
        homeTable.setOpaque(false);
        homeTable.setRequestFocusEnabled(false);
        homeTable.setRowSelectionAllowed(false);
        homeTable.setSurrendersFocusOnKeystroke(true);
        homeTable.setUpdateSelectionOnSort(false);
        homeTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane1.setViewportView(homeTable);
        //hidding all the panels and only making the home panel visible.
        allHidden();
        homePanel.setVisible(true);
        homePanel.setEnabled(true);
        
    }
    
    public void myAssociationsTable(){
        //function to fetch and populate the data for followed associations.
        //initializing the RestAPI an fetching the data as a JSON file.
        //iterating over the JSON file.
            //continuing id isSuccess parameter is true.
            //initializing hashmap and populating the data from the JSON.
            // setting parameters to send this to populate the table.
            // isRendered true and isFollow false to get an unfollow button.
        //creating a new Jtable with custom render and editor methods
        //setting properties for the table
        //hidding all the panels and only making the myAssociations panel visible.
        String myAssociationsUrl = "http://localhost:9001/associations/?type=oldAssociations&user_id="+this.inputJSON.get("user_id");
        RestAPIHook myAssociationsHook = new RestAPIHook();
        JSONObject myAssociationsData = myAssociationsHook.invokeGetMethod(myAssociationsUrl);
        Iterator<String> myAssociationsKeys = myAssociationsData.keys();
        List myAssociationsFeeds = new ArrayList();
        while(myAssociationsKeys.hasNext()){
            String myAssociationsKey = myAssociationsKeys.next();
            if("isSuccess".equals(myAssociationsKey)){
                continue;
            }
            HashMap myAssociationTemp = new HashMap();
            myAssociationTemp.put("event_id", myAssociationsKey);
            JSONObject myAssociationVal = myAssociationsData.getJSONObject(myAssociationsKey);
            Iterator<String> myAssociationChildKeys = myAssociationVal.keys();
            while(myAssociationChildKeys.hasNext()){
                String myAssociationChildKey = myAssociationChildKeys.next();
                myAssociationTemp.put(myAssociationChildKey, myAssociationVal.get(myAssociationChildKey));
            }
            myAssociationTemp.put("isAdmin", false);
            myAssociationTemp.put("isFollow", false);
            myAssociationTemp.put("association_id",myAssociationsKey);
            myAssociationTemp.put("user_id", this.inputJSON.get("user_id").toString());
            myAssociationsFeeds.add(new AssociationsFeed(myAssociationTemp));
        }
        myAssociationsTable = new JTable(new AssociationsFeedTableModel(myAssociationsFeeds));
        myAssociationsTable.setDefaultRenderer(AssociationsFeed.class, new AssociationsFeedCell());
        myAssociationsTable.setDefaultEditor(AssociationsFeed.class, new AssociationsFeedCell());
        myAssociationsTable.setRowHeight(60);  
        myAssociationsTable.getColumnModel().getColumn(0).setHeaderValue("My Associations");
        myAssociationsTable.getTableHeader().resizeAndRepaint();      
        myAssociationsTable.setModel(myAssociationsTable.getModel());
        myAssociationsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        myAssociationsTable.setFocusable(false);
        myAssociationsTable.setOpaque(false);
        myAssociationsTable.setRequestFocusEnabled(false);
        myAssociationsTable.setRowSelectionAllowed(false);
        myAssociationsTable.setSurrendersFocusOnKeystroke(true);
        myAssociationsTable.setUpdateSelectionOnSort(false);
        myAssociationsTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane4.setViewportView(myAssociationsTable);
        allHidden();
        myAssociationsPanel.setVisible(true);
        myAssociationsPanel.setEnabled(true);
    }
    
    public void exploreAssociationsTable(){
        //function to fetch and populate the data for all other unfollowed associations.
        //initializing the RestAPI an fetching the data as a JSON file.
        //iterating over the JSON file.
            //continuing id isSuccess parameter is true.
            //initializing hashmap and populating the data from the JSON.
            // setting parameters to send this to populate the table.
            // isRendered true and isFollow false to get an unfollow button.
        //creating a new Jtable with custom render and editor methods
        //setting properties for the table
        //hidding all the panels and only making the exploreAssociations panel visible.
        String exploreAssociationsUrl = "http://localhost:9001/associations/?type=newAssociations&user_id="+this.inputJSON.get("user_id");
        RestAPIHook exploreAssociationsHook = new RestAPIHook();
        JSONObject exploreAssociationsData = exploreAssociationsHook.invokeGetMethod(exploreAssociationsUrl);
        Iterator<String> exploreAssociationsKeys = exploreAssociationsData.keys();
        List exploreAssociationsFeeds = new ArrayList();
        while(exploreAssociationsKeys.hasNext()){
            String exploreAssociationsKey = exploreAssociationsKeys.next();
            if("isSuccess".equals(exploreAssociationsKey)){
                continue;
            }
            HashMap exploreAssociationTemp = new HashMap();
            exploreAssociationTemp.put("event_id", exploreAssociationsKey);
            JSONObject exploreAssociationVal = exploreAssociationsData.getJSONObject(exploreAssociationsKey);
            Iterator<String> exploreAssociationChildKeys = exploreAssociationVal.keys();
            while(exploreAssociationChildKeys.hasNext()){
                String exploreAssociationChildKey = exploreAssociationChildKeys.next();
                exploreAssociationTemp.put(exploreAssociationChildKey, exploreAssociationVal.get(exploreAssociationChildKey));
            }
            exploreAssociationTemp.put("isAdmin", false);
            exploreAssociationTemp.put("isFollow", true);
            exploreAssociationTemp.put("association_id",exploreAssociationsKey);
            exploreAssociationTemp.put("user_id", this.inputJSON.get("user_id").toString());
            exploreAssociationsFeeds.add(new AssociationsFeed(exploreAssociationTemp));
        }
        exploreAssociationsTable = new JTable(new AssociationsFeedTableModel(exploreAssociationsFeeds));
        exploreAssociationsTable.setDefaultRenderer(AssociationsFeed.class, new AssociationsFeedCell());
        exploreAssociationsTable.setDefaultEditor(AssociationsFeed.class, new AssociationsFeedCell());
        exploreAssociationsTable.setRowHeight(60);       
        exploreAssociationsTable.getColumnModel().getColumn(0).setHeaderValue("Explore Associations");
        exploreAssociationsTable.getTableHeader().resizeAndRepaint();       
        exploreAssociationsTable.setModel(exploreAssociationsTable.getModel());
        exploreAssociationsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        exploreAssociationsTable.setFocusable(false);
        exploreAssociationsTable.setOpaque(false);
        exploreAssociationsTable.setRequestFocusEnabled(false);
        exploreAssociationsTable.setRowSelectionAllowed(false);
        exploreAssociationsTable.setSurrendersFocusOnKeystroke(true);
        exploreAssociationsTable.setUpdateSelectionOnSort(false);
        exploreAssociationsTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane5.setViewportView(exploreAssociationsTable);
        allHidden();
        exploreAssociationsPanel.setVisible(true);
        exploreAssociationsPanel.setEnabled(true);
    }
    
    public void exploreEventsTable(){
        //function to fetch and populate the data for all other unfollowed events.
        //initializing the RestAPI an fetching the data as a JSON file.
        //iterating over the JSON file.
            //continuing id isSuccess parameter is true.
            //initializing hashmap and populating the data from the JSON.
            // setting parameters to send this to populate the table.
            // isRendered true and isFollow false to get an unfollow button.
        //creating a new Jtable with custom render and editor methods
        //setting properties for the table
        //hidding all the panels and only making the exploreEvents panel visible.
        String exploreEventsUrl = "http://localhost:9002/events/?type=all&user_id="+this.inputJSON.get("user_id");
        RestAPIHook exploreEventsHook = new RestAPIHook();
        JSONObject exploreEventsData = exploreEventsHook.invokeGetMethod(exploreEventsUrl);
        Iterator<String> exploreEventsKeys = exploreEventsData.keys();
        List exploreEventsFeeds = new ArrayList();
        while(exploreEventsKeys.hasNext()){
            String exploreEventsKey = exploreEventsKeys.next();
            if("isSuccess".equals(exploreEventsKey)){
                continue;
            }
            HashMap exploreEventsTemp = new HashMap();
            exploreEventsTemp.put("event_id", exploreEventsKey);
            JSONObject exploreEventsVal = exploreEventsData.getJSONObject(exploreEventsKey);
            Iterator<String> exploreEventsChildKeys = exploreEventsVal.keys();
            while(exploreEventsChildKeys.hasNext()){
                String exploreEventsChildKey = exploreEventsChildKeys.next();
                exploreEventsTemp.put(exploreEventsChildKey, exploreEventsVal.get(exploreEventsChildKey));
            }
            exploreEventsTemp.put("isRendered", true);
            exploreEventsTemp.put("isFollow", true);
            exploreEventsTemp.put("user_id", this.inputJSON.get("user_id").toString());
            exploreEventsFeeds.add(new EventsFeed(exploreEventsTemp));
        }
        exploreEventsTable = new JTable(new EventsFeedTableModel(exploreEventsFeeds));
        exploreEventsTable.setDefaultRenderer(EventsFeed.class, new EventsFeedCell());
        exploreEventsTable.setDefaultEditor(EventsFeed.class, new EventsFeedCell());
        exploreEventsTable.setRowHeight(60);       
        exploreEventsTable.getColumnModel().getColumn(0).setHeaderValue("Explore Events");
        exploreEventsTable.getTableHeader().resizeAndRepaint();       
        exploreEventsTable.setModel(exploreEventsTable.getModel());
        exploreEventsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        exploreEventsTable.setFocusable(false);
        exploreEventsTable.setOpaque(false);
        exploreEventsTable.setRequestFocusEnabled(false);
        exploreEventsTable.setRowSelectionAllowed(false);
        exploreEventsTable.setSurrendersFocusOnKeystroke(true);
        exploreEventsTable.setUpdateSelectionOnSort(false);
        exploreEventsTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane6.setViewportView(exploreEventsTable);
        allHidden();
        exploreEventsPanel.setVisible(true);
        exploreEventsPanel.setEnabled(true);
        exploreEventsTable.setVisible(true);
        exploreEventsTable.setEnabled(true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    //defining the UI
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        navigationPanel = new javax.swing.JPanel();
        profileButton = new javax.swing.JButton();
        pastEveButton = new javax.swing.JButton();
        upcomEveButton = new javax.swing.JButton();
        homeButton = new javax.swing.JButton();
        myAssociations = new javax.swing.JButton();
        exploreAssociations = new javax.swing.JButton();
        exploreEvents = new javax.swing.JButton();
        headerPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        logoutLabel = new javax.swing.JLabel();
        profilePanel = new javax.swing.JPanel();
        userType = new javax.swing.JLabel();
        description = new javax.swing.JLabel();
        fname = new javax.swing.JLabel();
        pastEventsPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        pastEventsTable = new javax.swing.JTable();
        upcomingEventsPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        upcomingEventsTable = new javax.swing.JTable();
        homePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        homeTable = new javax.swing.JTable();
        myAssociationsPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        myAssociationsTable = new javax.swing.JTable();
        exploreAssociationsPanel = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        exploreAssociationsTable = new javax.swing.JTable();
        exploreEventsPanel = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        exploreEventsTable = new javax.swing.JTable();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        navigationPanel.setBackground(new java.awt.Color(102, 102, 102));

        profileButton.setBackground(new java.awt.Color(102, 102, 102));
        profileButton.setForeground(new java.awt.Color(255, 255, 255));
        profileButton.setText("Profile");
        profileButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        profileButton.setMaximumSize(new java.awt.Dimension(140, 29));
        profileButton.setMinimumSize(new java.awt.Dimension(140, 29));
        profileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profileButtonActionPerformed(evt);
            }
        });

        pastEveButton.setBackground(new java.awt.Color(102, 102, 102));
        pastEveButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        pastEveButton.setForeground(new java.awt.Color(255, 255, 255));
        pastEveButton.setText("Past Events");
        pastEveButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        pastEveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pastEveButtonActionPerformed(evt);
            }
        });

        upcomEveButton.setBackground(new java.awt.Color(102, 102, 102));
        upcomEveButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        upcomEveButton.setForeground(new java.awt.Color(255, 255, 255));
        upcomEveButton.setText("Upcoming Events");
        upcomEveButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        upcomEveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upcomEveButtonActionPerformed(evt);
            }
        });

        homeButton.setBackground(new java.awt.Color(102, 102, 102));
        homeButton.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        homeButton.setForeground(new java.awt.Color(255, 255, 255));
        homeButton.setText("Home       ");
        homeButton.setActionCommand("Home");
        homeButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        homeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                homeButtonActionPerformed(evt);
            }
        });

        myAssociations.setBackground(new java.awt.Color(102, 102, 102));
        myAssociations.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        myAssociations.setForeground(new java.awt.Color(255, 255, 255));
        myAssociations.setText("My Associations");
        myAssociations.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        myAssociations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                myAssociationsActionPerformed(evt);
            }
        });

        exploreAssociations.setBackground(new java.awt.Color(102, 102, 102));
        exploreAssociations.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        exploreAssociations.setForeground(new java.awt.Color(255, 255, 255));
        exploreAssociations.setText("Explore Associations");
        exploreAssociations.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        exploreAssociations.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exploreAssociationsActionPerformed(evt);
            }
        });

        exploreEvents.setBackground(new java.awt.Color(102, 102, 102));
        exploreEvents.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        exploreEvents.setForeground(new java.awt.Color(255, 255, 255));
        exploreEvents.setText("Explore Events");
        exploreEvents.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        exploreEvents.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exploreEventsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout navigationPanelLayout = new javax.swing.GroupLayout(navigationPanel);
        navigationPanel.setLayout(navigationPanelLayout);
        navigationPanelLayout.setHorizontalGroup(
            navigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pastEveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(upcomEveButton, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
            .addComponent(homeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(myAssociations, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(exploreAssociations, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(exploreEvents, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        navigationPanelLayout.setVerticalGroup(
            navigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navigationPanelLayout.createSequentialGroup()
                .addComponent(profileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(homeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(upcomEveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pastEveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(myAssociations, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exploreAssociations, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(exploreEvents, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel1.add(navigationPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 270, 620));

        headerPanel.setBackground(new java.awt.Color(204, 0, 0));
        headerPanel.setPreferredSize(new java.awt.Dimension(1559, 70));

        titleLabel.setFont(new java.awt.Font("Arial", 3, 24)); // NOI18N
        titleLabel.setForeground(new java.awt.Color(255, 255, 255));
        titleLabel.setText("UMASS EVENTS BOARD");

        logoutLabel.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        logoutLabel.setForeground(new java.awt.Color(255, 255, 255));
        logoutLabel.setText("LOG OUT");
        logoutLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout headerPanelLayout = new javax.swing.GroupLayout(headerPanel);
        headerPanel.setLayout(headerPanelLayout);
        headerPanelLayout.setHorizontalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addContainerGap(715, Short.MAX_VALUE)
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(430, 430, 430)
                .addComponent(logoutLabel)
                .addGap(42, 42, 42))
        );
        headerPanelLayout.setVerticalGroup(
            headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerPanelLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(headerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logoutLabel))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel1.add(headerPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1560, 70));

        profilePanel.setPreferredSize(new java.awt.Dimension(1560, 560));

        userType.setText("User Type :        User");

        description.setText("Email :        "+ this.inputJSON.get("email").toString());

        fname.setText("First Name :       "+this.inputJSON.get("first_name").toString());

        javax.swing.GroupLayout profilePanelLayout = new javax.swing.GroupLayout(profilePanel);
        profilePanel.setLayout(profilePanelLayout);
        profilePanelLayout.setHorizontalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addGap(291, 291, 291)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(userType, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(964, Short.MAX_VALUE))
        );
        profilePanelLayout.setVerticalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(userType, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(372, Short.MAX_VALUE))
        );

        jPanel1.add(profilePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 620));

        pastEventsPanel.setPreferredSize(new java.awt.Dimension(1560, 560));

        pastEventsTable.setModel(pastEventsTable.getModel());
        jScrollPane2.setViewportView(pastEventsTable);
        pastEventsTable();

        javax.swing.GroupLayout pastEventsPanelLayout = new javax.swing.GroupLayout(pastEventsPanel);
        pastEventsPanel.setLayout(pastEventsPanelLayout);
        pastEventsPanelLayout.setHorizontalGroup(
            pastEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pastEventsPanelLayout.createSequentialGroup()
                .addGap(327, 327, 327)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1090, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(143, Short.MAX_VALUE))
        );
        pastEventsPanelLayout.setVerticalGroup(
            pastEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pastEventsPanelLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jPanel1.add(pastEventsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 620));

        upcomingEventsTable.setModel(upcomingEventsTable.getModel());
        jScrollPane3.setViewportView(upcomingEventsTable);
        upcomingEventsTable();

        javax.swing.GroupLayout upcomingEventsPanelLayout = new javax.swing.GroupLayout(upcomingEventsPanel);
        upcomingEventsPanel.setLayout(upcomingEventsPanelLayout);
        upcomingEventsPanelLayout.setHorizontalGroup(
            upcomingEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1560, Short.MAX_VALUE)
            .addGroup(upcomingEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(upcomingEventsPanelLayout.createSequentialGroup()
                    .addGap(327, 327, 327)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(68, Short.MAX_VALUE)))
        );
        upcomingEventsPanelLayout.setVerticalGroup(
            upcomingEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
            .addGroup(upcomingEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(upcomingEventsPanelLayout.createSequentialGroup()
                    .addGap(56, 56, 56)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(75, Short.MAX_VALUE)))
        );

        jPanel1.add(upcomingEventsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 620));

        homeTable.setModel(homeTable.getModel());
        jScrollPane1.setViewportView(homeTable);
        homeTable();

        javax.swing.GroupLayout homePanelLayout = new javax.swing.GroupLayout(homePanel);
        homePanel.setLayout(homePanelLayout);
        homePanelLayout.setHorizontalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(339, 339, 339)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1090, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
        );
        homePanelLayout.setVerticalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );

        jPanel1.add(homePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 620));

        myAssociationsTable.setModel(myAssociationsTable.getModel());
        jScrollPane4.setViewportView(myAssociationsTable);
        myAssociationsTable();

        javax.swing.GroupLayout myAssociationsPanelLayout = new javax.swing.GroupLayout(myAssociationsPanel);
        myAssociationsPanel.setLayout(myAssociationsPanelLayout);
        myAssociationsPanelLayout.setHorizontalGroup(
            myAssociationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(myAssociationsPanelLayout.createSequentialGroup()
                .addGap(339, 339, 339)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1090, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
        );
        myAssociationsPanelLayout.setVerticalGroup(
            myAssociationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(myAssociationsPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );

        jPanel1.add(myAssociationsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 620));

        exploreAssociationsTable.setModel(exploreAssociationsTable.getModel());
        jScrollPane5.setViewportView(exploreAssociationsTable);
        exploreAssociationsTable();

        javax.swing.GroupLayout exploreAssociationsPanelLayout = new javax.swing.GroupLayout(exploreAssociationsPanel);
        exploreAssociationsPanel.setLayout(exploreAssociationsPanelLayout);
        exploreAssociationsPanelLayout.setHorizontalGroup(
            exploreAssociationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exploreAssociationsPanelLayout.createSequentialGroup()
                .addGap(339, 339, 339)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1090, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
        );
        exploreAssociationsPanelLayout.setVerticalGroup(
            exploreAssociationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exploreAssociationsPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );

        jPanel1.add(exploreAssociationsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 620));

        exploreEventsTable.setModel(exploreEventsTable.getModel());
        jScrollPane6.setViewportView(exploreEventsTable);
        exploreEventsTable();

        javax.swing.GroupLayout exploreEventsPanelLayout = new javax.swing.GroupLayout(exploreEventsPanel);
        exploreEventsPanel.setLayout(exploreEventsPanelLayout);
        exploreEventsPanelLayout.setHorizontalGroup(
            exploreEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exploreEventsPanelLayout.createSequentialGroup()
                .addGap(339, 339, 339)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1090, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
        );
        exploreEventsPanelLayout.setVerticalGroup(
            exploreEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exploreEventsPanelLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );

        jPanel1.add(exploreEventsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 620));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 689, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutLabelMouseClicked
        // TODO add your handling code here:
        //disposing the current frame and creating a new login page.
        this.dispose();
        LoginPage newLoginPage = new LoginPage();
        newLoginPage.setExtendedState(JFrame.MAXIMIZED_BOTH);
        newLoginPage.setVisible(true);    
    }//GEN-LAST:event_logoutLabelMouseClicked

    private void menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_menuMouseClicked

    private void homeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeButtonActionPerformed
        // TODO add your handling code here:
        //calling the home table when clicking the button. 
        homeTable();
    }//GEN-LAST:event_homeButtonActionPerformed

    private void upcomEveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upcomEveButtonActionPerformed
        // TODO add your handling code here:
        //calling the upcoming events table when clicking the button. 
        upcomingEventsTable();
    }//GEN-LAST:event_upcomEveButtonActionPerformed

    private void profileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileButtonActionPerformed
        // TODO add your handling code here:
        //hidding all the panels and setting the profile panel to visible.
        allHidden();
        profilePanel.setVisible(true);
        profilePanel.setEnabled(true);
    }//GEN-LAST:event_profileButtonActionPerformed

    private void pastEveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pastEveButtonActionPerformed
        // TODO add your handling code here:
        //calling the past events table when clicking the button. 
        pastEventsTable();
    }//GEN-LAST:event_pastEveButtonActionPerformed

    private void myAssociationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myAssociationsActionPerformed
        // TODO add your handling code here:
        //calling the followed associations table when clicking the button. 
        myAssociationsTable();
    }//GEN-LAST:event_myAssociationsActionPerformed
     
    private void exploreAssociationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exploreAssociationsActionPerformed
        // TODO add your handling code here:
        //calling the all other unfollowed associations table when clicking the button. 
        exploreAssociationsTable();
    }//GEN-LAST:event_exploreAssociationsActionPerformed

    private void exploreEventsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exploreEventsActionPerformed
        // TODO add your handling code here:
        //calling the all unfollowed event table when clicking the button. 
        exploreEventsTable();
    }//GEN-LAST:event_exploreEventsActionPerformed
    
     
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UserHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel description;
    private javax.swing.JButton exploreAssociations;
    private javax.swing.JPanel exploreAssociationsPanel;
    public javax.swing.JTable exploreAssociationsTable;
    private javax.swing.JButton exploreEvents;
    private javax.swing.JPanel exploreEventsPanel;
    public javax.swing.JTable exploreEventsTable;
    private javax.swing.JLabel fname;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JButton homeButton;
    private javax.swing.JPanel homePanel;
    public javax.swing.JTable homeTable;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel logoutLabel;
    private javax.swing.JButton myAssociations;
    private javax.swing.JPanel myAssociationsPanel;
    public javax.swing.JTable myAssociationsTable;
    private javax.swing.JPanel navigationPanel;
    private javax.swing.JButton pastEveButton;
    private javax.swing.JPanel pastEventsPanel;
    public javax.swing.JTable pastEventsTable;
    private javax.swing.JButton profileButton;
    private javax.swing.JPanel profilePanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton upcomEveButton;
    private javax.swing.JPanel upcomingEventsPanel;
    public javax.swing.JTable upcomingEventsTable;
    private javax.swing.JLabel userType;
    // End of variables declaration//GEN-END:variables
}
