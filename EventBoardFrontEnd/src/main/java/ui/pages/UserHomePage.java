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
        this.inputJSON = input;
        initComponents();
        active();
    }

    public void allHidden(){
        profilePanel.setVisible(false);
        profilePanel.setEnabled(false);
        exploreAssociationsPanel.setVisible(false);
        exploreAssociationsPanel.setEnabled(false);
        myAssociationsPanel.setVisible(false);
        myAssociationsPanel.setEnabled(false);
        homePanel.setVisible(false);
        homePanel.setEnabled(false);
        upcomEvePanel.setVisible(false);
        upcomEvePanel.setEnabled(false);
        pastEvePanel.setVisible(false);
        pastEvePanel.setEnabled(false);
//        homeFeedsTable.setVisible(false);
//        homeFeedsTable.setEnabled(false);
        navPanel.setVisible(true);
        navPanel.setEnabled(true);
        jLabel3.setVisible(false);
        jLabel3.setEnabled(false);
        menu.setVisible(false);
        menu.setEnabled(false);
        exploreEventsPanel.setVisible(false);
        exploreEventsPanel.setEnabled(false);
    }
    
    public void active(){
        navPanel.setVisible(true);
        navPanel.setEnabled(true);
        jLabel3.setVisible(true);
        jLabel3.setEnabled(true);
        menu.setVisible(false);
        menu.setEnabled(false);
        homePanel.setVisible(true);
        homePanel.setEnabled(true);
    }
    
    public void pastFeedsTable(){
        String pastUrl = "http://localhost:9002/events/?type=past&user_id="+this.inputJSON.get("user_id");
        RestAPIHook pastHook = new RestAPIHook();
        JSONObject pastJSON = pastHook.invokeGetMethod(pastUrl);
        Iterator<String> pastKeys = pastJSON.keys();
        List pastfeeds = new ArrayList();
        while(pastKeys.hasNext()){
            String pastKey = pastKeys.next();
            if("isSuccess".equals(pastKey)){
                continue;
            }
            HashMap pasttemp = new HashMap();
            pasttemp.put("event_id", pastKey);
            JSONObject pastval = pastJSON.getJSONObject(pastKey);
            Iterator<String> pastchildKeys = pastval.keys();
            while(pastchildKeys.hasNext()){
                String pastchildKey = pastchildKeys.next();
                pasttemp.put(pastchildKey, pastval.get(pastchildKey));
            }
            pasttemp.put("isRendered", false);
            pasttemp.put("isFollow", false);
            pasttemp.put("user_id", this.inputJSON.get("user_id").toString());
            pastfeeds.add(new EventsFeed(pasttemp));
        }
        pastFeedsTable = new JTable (new EventsFeedTableModel(pastfeeds));
        pastFeedsTable.setDefaultRenderer(EventsFeed.class, new EventsFeedCell());
        pastFeedsTable.setDefaultEditor(EventsFeed.class, new EventsFeedCell());
        pastFeedsTable.setRowHeight(60);
        pastFeedsTable.getColumnModel().getColumn(0).setHeaderValue("Past Events");
        pastFeedsTable.getTableHeader().resizeAndRepaint();
        pastFeedsTable.setModel(pastFeedsTable.getModel());
        pastFeedsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pastFeedsTable.setFocusable(false);
        pastFeedsTable.setOpaque(false);
        pastFeedsTable.setRequestFocusEnabled(false);
        pastFeedsTable.setRowSelectionAllowed(false);
        pastFeedsTable.setSurrendersFocusOnKeystroke(true);
        pastFeedsTable.setUpdateSelectionOnSort(false);
        pastFeedsTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane2.setViewportView(pastFeedsTable);
        allHidden();
        pastEvePanel.setVisible(true);
        pastEvePanel.setEnabled(true);
    }
    
    public void upcomFeedsTable(){
        String upcomEveUrl = "http://localhost:9002/events/?type=upcoming&user_id="+this.inputJSON.get("user_id");
        RestAPIHook upcomEveHook = new RestAPIHook();
        JSONObject upcomEveJSON = upcomEveHook.invokeGetMethod(upcomEveUrl);
        Iterator<String> upcomEveKeys = upcomEveJSON.keys();
        List upcomEvefeeds = new ArrayList();
        while(upcomEveKeys.hasNext()){
            String upcomEveKey = upcomEveKeys.next();
            if("isSuccess".equals(upcomEveKey)){
                continue;
            }
            HashMap upcomEvetemp = new HashMap();
            upcomEvetemp.put("event_id", upcomEveKey);
            JSONObject upcomEveval = upcomEveJSON.getJSONObject(upcomEveKey);
            Iterator<String> upcomEvechildKeys = upcomEveval.keys();
            while(upcomEvechildKeys.hasNext()){
                String upcomEvechildKey = upcomEvechildKeys.next();
                upcomEvetemp.put(upcomEvechildKey, upcomEveval.get(upcomEvechildKey));
            }
            upcomEvetemp.put("isRendered", true);
            upcomEvetemp.put("isFollow", false);
            upcomEvetemp.put("user_id", this.inputJSON.get("user_id").toString());
            upcomEvefeeds.add(new EventsFeed(upcomEvetemp));
        }
        upcomEveFeedsTable = new JTable(new EventsFeedTableModel(upcomEvefeeds));
        upcomEveFeedsTable.setDefaultRenderer(EventsFeed.class, new EventsFeedCell());
        upcomEveFeedsTable.setDefaultEditor(EventsFeed.class, new EventsFeedCell());
        upcomEveFeedsTable.setRowHeight(60);
        upcomEveFeedsTable.getColumnModel().getColumn(0).setHeaderValue("Upcoming Events");
        upcomEveFeedsTable.getTableHeader().resizeAndRepaint();
        upcomEveFeedsTable.setModel(upcomEveFeedsTable.getModel());
        upcomEveFeedsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        upcomEveFeedsTable.setFocusable(false);
        upcomEveFeedsTable.setOpaque(false);
        upcomEveFeedsTable.setRequestFocusEnabled(false);
        upcomEveFeedsTable.setRowSelectionAllowed(false);
        upcomEveFeedsTable.setSurrendersFocusOnKeystroke(true);
        upcomEveFeedsTable.setUpdateSelectionOnSort(false);
        upcomEveFeedsTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane3.setViewportView(upcomEveFeedsTable);
        allHidden();
        upcomEvePanel.setVisible(true);
        upcomEvePanel.setEnabled(true);
    }
    
    public void homeTable(){
        String homeFeedUrl = "http://localhost:9002/events/?type=feed&user_id="+this.inputJSON.get("user_id");
        RestAPIHook homeFeedHook = new RestAPIHook();
        JSONObject homeFeedJSON = homeFeedHook.invokeGetMethod(homeFeedUrl);
        Iterator<String> homeFeedKeys = homeFeedJSON.keys();
        List homefeeds = new ArrayList();
        while(homeFeedKeys.hasNext()){
            String homeFeedKey = homeFeedKeys.next();
            if("isSuccess".equals(homeFeedKey)){
                continue;
            }
            HashMap homeFeedtemp = new HashMap();
            homeFeedtemp.put("event_id", homeFeedKey);
            JSONObject homeFeedval = homeFeedJSON.getJSONObject(homeFeedKey);
            Iterator<String> homeFeedchildKeys = homeFeedval.keys();
            while(homeFeedchildKeys.hasNext()){
                String homeFeedchildKey = homeFeedchildKeys.next();
                homeFeedtemp.put(homeFeedchildKey, homeFeedval.get(homeFeedchildKey));
            }
            homeFeedtemp.put("isRendered", true);
            homeFeedtemp.put("isFollow", true);
            homeFeedtemp.put("user_id", this.inputJSON.get("user_id").toString());
            System.out.println(homeFeedtemp);
            homefeeds.add(new EventsFeed(homeFeedtemp));
        }
        homeFeedsTable = new JTable(new EventsFeedTableModel(homefeeds));
        homeFeedsTable.setDefaultRenderer(EventsFeed.class, new EventsFeedCell());
        homeFeedsTable.setDefaultEditor(EventsFeed.class, new EventsFeedCell());
        homeFeedsTable.setRowHeight(60);
        homeFeedsTable.getColumnModel().getColumn(0).setHeaderValue("Home");
        homeFeedsTable.getTableHeader().resizeAndRepaint();
        homeFeedsTable.setModel(homeFeedsTable.getModel());
        homeFeedsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        homeFeedsTable.setFocusable(false);
        homeFeedsTable.setOpaque(false);
        homeFeedsTable.setRequestFocusEnabled(false);
        homeFeedsTable.setRowSelectionAllowed(false);
        homeFeedsTable.setSurrendersFocusOnKeystroke(true);
        homeFeedsTable.setUpdateSelectionOnSort(false);
        homeFeedsTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane1.setViewportView(homeFeedsTable);
        allHidden();
        homePanel.setVisible(true);
        homePanel.setEnabled(true);
        
    }
    
    public void myAssociationsTable(){
        String myAssociationsUrl = "http://localhost:9001/associations/?type=oldAssociations&user_id="+this.inputJSON.get("user_id");
        RestAPIHook myAssociationsHook = new RestAPIHook();
        JSONObject myAssociationsJSON = myAssociationsHook.invokeGetMethod(myAssociationsUrl);
        Iterator<String> myAssociationsKeys = myAssociationsJSON.keys();
        List myAssociationsfeeds = new ArrayList();
        while(myAssociationsKeys.hasNext()){
            String myAssociationsKey = myAssociationsKeys.next();
            if("isSuccess".equals(myAssociationsKey)){
                continue;
            }
            HashMap myAssociationTemp = new HashMap();
            myAssociationTemp.put("event_id", myAssociationsKey);
            JSONObject myAssociationVal = myAssociationsJSON.getJSONObject(myAssociationsKey);
            Iterator<String> myAssociationChildKeys = myAssociationVal.keys();
            while(myAssociationChildKeys.hasNext()){
                String myAssociationChildKey = myAssociationChildKeys.next();
                myAssociationTemp.put(myAssociationChildKey, myAssociationVal.get(myAssociationChildKey));
            }
            myAssociationTemp.put("isAdmin", false);
            myAssociationTemp.put("isFollow", false);
            myAssociationTemp.put("association_id",myAssociationsKey);
            myAssociationTemp.put("user_id", this.inputJSON.get("user_id").toString());
            myAssociationsfeeds.add(new AssociationsFeed(myAssociationTemp));
        }
        myAssociationsFeedsTable = new JTable(new AssociationsFeedTableModel(myAssociationsfeeds));
        myAssociationsFeedsTable.setDefaultRenderer(AssociationsFeed.class, new AssociationsFeedCell());
        myAssociationsFeedsTable.setDefaultEditor(AssociationsFeed.class, new AssociationsFeedCell());
        myAssociationsFeedsTable.setRowHeight(60);  
        myAssociationsFeedsTable.getColumnModel().getColumn(0).setHeaderValue("My Associations");
        myAssociationsFeedsTable.getTableHeader().resizeAndRepaint();      
        myAssociationsFeedsTable.setModel(myAssociationsFeedsTable.getModel());
        myAssociationsFeedsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        myAssociationsFeedsTable.setFocusable(false);
        myAssociationsFeedsTable.setOpaque(false);
        myAssociationsFeedsTable.setRequestFocusEnabled(false);
        myAssociationsFeedsTable.setRowSelectionAllowed(false);
        myAssociationsFeedsTable.setSurrendersFocusOnKeystroke(true);
        myAssociationsFeedsTable.setUpdateSelectionOnSort(false);
        myAssociationsFeedsTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane4.setViewportView(myAssociationsFeedsTable);
        allHidden();
        myAssociationsPanel.setVisible(true);
        myAssociationsPanel.setEnabled(true);
    }
    
    public void exploreAssociationsTable(){
        String exploreAssociationsUrl = "http://localhost:9001/associations/?type=newAssociations&user_id="+this.inputJSON.get("user_id");
        RestAPIHook exploreAssociationsHook = new RestAPIHook();
        JSONObject exploreAssociationsJSON = exploreAssociationsHook.invokeGetMethod(exploreAssociationsUrl);
        Iterator<String> exploreAssociationsKeys = exploreAssociationsJSON.keys();
        List exploreAssociationsfeeds = new ArrayList();
        while(exploreAssociationsKeys.hasNext()){
            String exploreAssociationsKey = exploreAssociationsKeys.next();
            if("isSuccess".equals(exploreAssociationsKey)){
                continue;
            }
            HashMap exploreAssociationTemp = new HashMap();
            exploreAssociationTemp.put("event_id", exploreAssociationsKey);
            JSONObject exploreAssociationVal = exploreAssociationsJSON.getJSONObject(exploreAssociationsKey);
            Iterator<String> exploreAssociationChildKeys = exploreAssociationVal.keys();
            while(exploreAssociationChildKeys.hasNext()){
                String exploreAssociationChildKey = exploreAssociationChildKeys.next();
                exploreAssociationTemp.put(exploreAssociationChildKey, exploreAssociationVal.get(exploreAssociationChildKey));
            }
            exploreAssociationTemp.put("isAdmin", false);
            exploreAssociationTemp.put("isFollow", true);
            exploreAssociationTemp.put("association_id",exploreAssociationsKey);
            exploreAssociationTemp.put("user_id", this.inputJSON.get("user_id").toString());
            exploreAssociationsfeeds.add(new AssociationsFeed(exploreAssociationTemp));
        }
        exploreAssociationsFeedsTable = new JTable(new AssociationsFeedTableModel(exploreAssociationsfeeds));
        exploreAssociationsFeedsTable.setDefaultRenderer(AssociationsFeed.class, new AssociationsFeedCell());
        exploreAssociationsFeedsTable.setDefaultEditor(AssociationsFeed.class, new AssociationsFeedCell());
        exploreAssociationsFeedsTable.setRowHeight(60);       
        exploreAssociationsFeedsTable.getColumnModel().getColumn(0).setHeaderValue("Explore Associations");
        exploreAssociationsFeedsTable.getTableHeader().resizeAndRepaint();       
        exploreAssociationsFeedsTable.setModel(exploreAssociationsFeedsTable.getModel());
        exploreAssociationsFeedsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        exploreAssociationsFeedsTable.setFocusable(false);
        exploreAssociationsFeedsTable.setOpaque(false);
        exploreAssociationsFeedsTable.setRequestFocusEnabled(false);
        exploreAssociationsFeedsTable.setRowSelectionAllowed(false);
        exploreAssociationsFeedsTable.setSurrendersFocusOnKeystroke(true);
        exploreAssociationsFeedsTable.setUpdateSelectionOnSort(false);
        exploreAssociationsFeedsTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane5.setViewportView(exploreAssociationsFeedsTable);
        allHidden();
        exploreAssociationsPanel.setVisible(true);
        exploreAssociationsPanel.setEnabled(true);
    }
    
    public void exploreEventsTable(){
        String exploreEventsUrl = "http://localhost:9002/events/?type=all&user_id="+this.inputJSON.get("user_id");
        RestAPIHook exploreEventsHook = new RestAPIHook();
        JSONObject exploreEventsJSON = exploreEventsHook.invokeGetMethod(exploreEventsUrl);
        Iterator<String> exploreEventsKeys = exploreEventsJSON.keys();
        List exploreEventsfeeds = new ArrayList();
        while(exploreEventsKeys.hasNext()){
            String exploreEventsKey = exploreEventsKeys.next();
            if("isSuccess".equals(exploreEventsKey)){
                continue;
            }
            HashMap exploreEventsTemp = new HashMap();
            exploreEventsTemp.put("event_id", exploreEventsKey);
            JSONObject exploreEventsVal = exploreEventsJSON.getJSONObject(exploreEventsKey);
            Iterator<String> exploreEventsChildKeys = exploreEventsVal.keys();
            while(exploreEventsChildKeys.hasNext()){
                String exploreEventsChildKey = exploreEventsChildKeys.next();
                exploreEventsTemp.put(exploreEventsChildKey, exploreEventsVal.get(exploreEventsChildKey));
            }
            exploreEventsTemp.put("isRendered", true);
            exploreEventsTemp.put("isFollow", true);
            exploreEventsTemp.put("user_id", this.inputJSON.get("user_id").toString());
//            System.out.println("muneer"+exploreEventsTemp);
            exploreEventsfeeds.add(new EventsFeed(exploreEventsTemp));
        }
        System.out.println("muneer"+exploreEventsfeeds);
        exploreEventsFeedsTable = new JTable(new EventsFeedTableModel(exploreEventsfeeds));
        exploreEventsFeedsTable.setDefaultRenderer(EventsFeed.class, new EventsFeedCell());
        exploreEventsFeedsTable.setDefaultEditor(EventsFeed.class, new EventsFeedCell());
        exploreEventsFeedsTable.setRowHeight(60);       
        exploreEventsFeedsTable.getColumnModel().getColumn(0).setHeaderValue("Explore Events");
        exploreEventsFeedsTable.getTableHeader().resizeAndRepaint();       
        exploreEventsFeedsTable.setModel(exploreEventsFeedsTable.getModel());
        exploreEventsFeedsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        exploreEventsFeedsTable.setFocusable(false);
        exploreEventsFeedsTable.setOpaque(false);
        exploreEventsFeedsTable.setRequestFocusEnabled(false);
        exploreEventsFeedsTable.setRowSelectionAllowed(false);
        exploreEventsFeedsTable.setSurrendersFocusOnKeystroke(true);
        exploreEventsFeedsTable.setUpdateSelectionOnSort(false);
        exploreEventsFeedsTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane6.setViewportView(exploreEventsFeedsTable);
        allHidden();
        exploreEventsPanel.setVisible(true);
        exploreEventsPanel.setEnabled(true);
        exploreEventsFeedsTable.setVisible(true);
        exploreEventsFeedsTable.setEnabled(true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        navPanel = new javax.swing.JPanel();
        profileButton = new javax.swing.JButton();
        pastEveButton = new javax.swing.JButton();
        upcomEveButton = new javax.swing.JButton();
        homeButton = new javax.swing.JButton();
        myAssociations = new javax.swing.JButton();
        exploreAssociations = new javax.swing.JButton();
        exploreEvents = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        logoutLabel = new javax.swing.JLabel();
        menu = new javax.swing.JLabel();
        profilePanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        pastEvePanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        pastFeedsTable = new javax.swing.JTable();
        upcomEvePanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        upcomEveFeedsTable = new javax.swing.JTable();
        homePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        homeFeedsTable = new javax.swing.JTable();
        myAssociationsPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        myAssociationsFeedsTable = new javax.swing.JTable();
        exploreAssociationsPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        exploreAssociationsFeedsTable = new javax.swing.JTable();
        exploreEventsPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        exploreEventsFeedsTable = new javax.swing.JTable();

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
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, 870, 560));

        navPanel.setBackground(new java.awt.Color(102, 102, 102));

        profileButton.setBackground(new java.awt.Color(102, 102, 102));
        profileButton.setForeground(new java.awt.Color(255, 255, 255));
        profileButton.setText("Profile Settings");
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

        javax.swing.GroupLayout navPanelLayout = new javax.swing.GroupLayout(navPanel);
        navPanel.setLayout(navPanelLayout);
        navPanelLayout.setHorizontalGroup(
            navPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pastEveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(upcomEveButton, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
            .addComponent(homeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(myAssociations, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(exploreAssociations, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(exploreEvents, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        navPanelLayout.setVerticalGroup(
            navPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navPanelLayout.createSequentialGroup()
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

        jPanel1.add(navPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 270, 620));

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));
        jPanel2.setPreferredSize(new java.awt.Dimension(1559, 70));

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

        menu.setText("test");
        menu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(menu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 650, Short.MAX_VALUE)
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(430, 430, 430)
                .addComponent(logoutLabel)
                .addGap(42, 42, 42))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(logoutLabel))
                    .addComponent(menu))
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1560, 70));

        profilePanel.setPreferredSize(new java.awt.Dimension(1560, 560));

        jLabel2.setText("User Type :        User");

        jLabel4.setText("Email :        "+ this.inputJSON.get("email").toString());

        jLabel5.setText("First Name :       "+this.inputJSON.get("first_name").toString());

        javax.swing.GroupLayout profilePanelLayout = new javax.swing.GroupLayout(profilePanel);
        profilePanel.setLayout(profilePanelLayout);
        profilePanelLayout.setHorizontalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addGap(291, 291, 291)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 302, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(964, Short.MAX_VALUE))
        );
        profilePanelLayout.setVerticalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(372, Short.MAX_VALUE))
        );

        jPanel1.add(profilePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 620));

        pastEvePanel.setPreferredSize(new java.awt.Dimension(1560, 560));

        pastFeedsTable.setModel(pastFeedsTable.getModel());
        jScrollPane2.setViewportView(pastFeedsTable);
        pastFeedsTable();

        javax.swing.GroupLayout pastEvePanelLayout = new javax.swing.GroupLayout(pastEvePanel);
        pastEvePanel.setLayout(pastEvePanelLayout);
        pastEvePanelLayout.setHorizontalGroup(
            pastEvePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pastEvePanelLayout.createSequentialGroup()
                .addGap(327, 327, 327)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1090, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(143, Short.MAX_VALUE))
        );
        pastEvePanelLayout.setVerticalGroup(
            pastEvePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pastEvePanelLayout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        jPanel1.add(pastEvePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 620));

        upcomEveFeedsTable.setModel(upcomEveFeedsTable.getModel());
        jScrollPane3.setViewportView(upcomEveFeedsTable);
        upcomFeedsTable();

        javax.swing.GroupLayout upcomEvePanelLayout = new javax.swing.GroupLayout(upcomEvePanel);
        upcomEvePanel.setLayout(upcomEvePanelLayout);
        upcomEvePanelLayout.setHorizontalGroup(
            upcomEvePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1560, Short.MAX_VALUE)
            .addGroup(upcomEvePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(upcomEvePanelLayout.createSequentialGroup()
                    .addGap(327, 327, 327)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(68, Short.MAX_VALUE)))
        );
        upcomEvePanelLayout.setVerticalGroup(
            upcomEvePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 620, Short.MAX_VALUE)
            .addGroup(upcomEvePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(upcomEvePanelLayout.createSequentialGroup()
                    .addGap(56, 56, 56)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(75, Short.MAX_VALUE)))
        );

        jPanel1.add(upcomEvePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 620));

        jLabel1.setText("Home");

        homeFeedsTable.setModel(homeFeedsTable.getModel());
        jScrollPane1.setViewportView(homeFeedsTable);
        homeTable();

        javax.swing.GroupLayout homePanelLayout = new javax.swing.GroupLayout(homePanel);
        homePanel.setLayout(homePanelLayout);
        homePanelLayout.setHorizontalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGroup(homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel1))
                    .addGroup(homePanelLayout.createSequentialGroup()
                        .addGap(339, 339, 339)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1090, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(131, Short.MAX_VALUE))
        );
        homePanelLayout.setVerticalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );

        jPanel1.add(homePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 620));

        jLabel6.setText("Home");

        myAssociationsFeedsTable.setModel(myAssociationsFeedsTable.getModel());
        jScrollPane4.setViewportView(myAssociationsFeedsTable);
        myAssociationsTable();

        javax.swing.GroupLayout myAssociationsPanelLayout = new javax.swing.GroupLayout(myAssociationsPanel);
        myAssociationsPanel.setLayout(myAssociationsPanelLayout);
        myAssociationsPanelLayout.setHorizontalGroup(
            myAssociationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(myAssociationsPanelLayout.createSequentialGroup()
                .addGroup(myAssociationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(myAssociationsPanelLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel6))
                    .addGroup(myAssociationsPanelLayout.createSequentialGroup()
                        .addGap(339, 339, 339)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1090, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(131, Short.MAX_VALUE))
        );
        myAssociationsPanelLayout.setVerticalGroup(
            myAssociationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(myAssociationsPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );

        jPanel1.add(myAssociationsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 620));

        jLabel7.setText("Home");

        exploreAssociationsFeedsTable.setModel(exploreAssociationsFeedsTable.getModel());
        jScrollPane5.setViewportView(exploreAssociationsFeedsTable);
        exploreAssociationsTable();

        javax.swing.GroupLayout exploreAssociationsPanelLayout = new javax.swing.GroupLayout(exploreAssociationsPanel);
        exploreAssociationsPanel.setLayout(exploreAssociationsPanelLayout);
        exploreAssociationsPanelLayout.setHorizontalGroup(
            exploreAssociationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exploreAssociationsPanelLayout.createSequentialGroup()
                .addGroup(exploreAssociationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(exploreAssociationsPanelLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel7))
                    .addGroup(exploreAssociationsPanelLayout.createSequentialGroup()
                        .addGap(339, 339, 339)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 1090, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(131, Short.MAX_VALUE))
        );
        exploreAssociationsPanelLayout.setVerticalGroup(
            exploreAssociationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exploreAssociationsPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );

        jPanel1.add(exploreAssociationsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 620));

        jLabel8.setText("Home");

        exploreEventsFeedsTable.setModel(exploreEventsFeedsTable.getModel());
        jScrollPane6.setViewportView(exploreEventsFeedsTable);
        exploreEventsTable();

        javax.swing.GroupLayout exploreEventsPanelLayout = new javax.swing.GroupLayout(exploreEventsPanel);
        exploreEventsPanel.setLayout(exploreEventsPanelLayout);
        exploreEventsPanelLayout.setHorizontalGroup(
            exploreEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exploreEventsPanelLayout.createSequentialGroup()
                .addGroup(exploreEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(exploreEventsPanelLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addComponent(jLabel8))
                    .addGroup(exploreEventsPanelLayout.createSequentialGroup()
                        .addGap(339, 339, 339)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 1090, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(131, Short.MAX_VALUE))
        );
        exploreEventsPanelLayout.setVerticalGroup(
            exploreEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exploreEventsPanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
        this.dispose();
        LoginPage form = new LoginPage();
        form.setExtendedState(JFrame.MAXIMIZED_BOTH);
        form.setVisible(true);
        
    }//GEN-LAST:event_logoutLabelMouseClicked

    private void menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuMouseClicked
        // TODO add your handling code here:
        active();
    }//GEN-LAST:event_menuMouseClicked

    private void homeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeButtonActionPerformed
        // TODO add your handling code here:
        homeTable();
    }//GEN-LAST:event_homeButtonActionPerformed

    private void upcomEveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upcomEveButtonActionPerformed
        // TODO add your handling code here:
        upcomFeedsTable();
    }//GEN-LAST:event_upcomEveButtonActionPerformed

    private void profileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileButtonActionPerformed
        // TODO add your handling code here:
        profilePanel.setVisible(true);
        profilePanel.setEnabled(true);
        exploreAssociationsPanel.setVisible(false);
        exploreAssociationsPanel.setEnabled(false);
        myAssociationsPanel.setVisible(false);
        myAssociationsPanel.setEnabled(false);
        homePanel.setVisible(false);
        homePanel.setEnabled(false);
        upcomEvePanel.setVisible(false);
        upcomEvePanel.setEnabled(false);
        pastEvePanel.setVisible(false);
        pastEvePanel.setEnabled(false);
        homeFeedsTable.setVisible(false);
        homeFeedsTable.setEnabled(false);
    }//GEN-LAST:event_profileButtonActionPerformed

    private void pastEveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pastEveButtonActionPerformed
        // TODO add your handling code here:
        pastFeedsTable();
    }//GEN-LAST:event_pastEveButtonActionPerformed

    private void myAssociationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_myAssociationsActionPerformed
        // TODO add your handling code here:
        myAssociationsTable();
    }//GEN-LAST:event_myAssociationsActionPerformed
     
    private void exploreAssociationsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exploreAssociationsActionPerformed
        // TODO add your handling code here:
        exploreAssociationsTable();
    }//GEN-LAST:event_exploreAssociationsActionPerformed

    private void exploreEventsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exploreEventsActionPerformed
        // TODO add your handling code here:
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
            java.util.logging.Logger.getLogger(HomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton exploreAssociations;
    public javax.swing.JTable exploreAssociationsFeedsTable;
    private javax.swing.JPanel exploreAssociationsPanel;
    private javax.swing.JButton exploreEvents;
    public javax.swing.JTable exploreEventsFeedsTable;
    private javax.swing.JPanel exploreEventsPanel;
    private javax.swing.JButton homeButton;
    public javax.swing.JTable homeFeedsTable;
    private javax.swing.JPanel homePanel;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JLabel logoutLabel;
    private javax.swing.JLabel menu;
    private javax.swing.JButton myAssociations;
    public javax.swing.JTable myAssociationsFeedsTable;
    private javax.swing.JPanel myAssociationsPanel;
    private javax.swing.JPanel navPanel;
    private javax.swing.JButton pastEveButton;
    private javax.swing.JPanel pastEvePanel;
    public javax.swing.JTable pastFeedsTable;
    private javax.swing.JButton profileButton;
    private javax.swing.JPanel profilePanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton upcomEveButton;
    public javax.swing.JTable upcomEveFeedsTable;
    private javax.swing.JPanel upcomEvePanel;
    // End of variables declaration//GEN-END:variables
}
