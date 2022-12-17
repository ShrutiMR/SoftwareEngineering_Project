/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui.pages;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import org.json.JSONObject;
import rest.RestAPIHook;
import ui.components.Events.EventsFeed;
import ui.components.Events.EventsFeedCell;
import ui.components.Events.EventsFeedTableModel;


/**
 *
 * @author shrut
 */
public final class AssociationHomePage extends javax.swing.JFrame {

    /**
     * Creates new form HomePage
     */
    private JSONObject inputJSON;
    private JSONObject associationDetailsJSON;
 
    public AssociationHomePage(JSONObject input, JSONObject details) {
        //fetching the JSON file which contains information about user loging in.
        //Also initializing the UI components calling the panels to be hidden and visible.
        this.inputJSON = input;
        this.associationDetailsJSON = details;
        initComponents();
        active();
    }

    public void allHidden(){
        //Hidding all the panels and only setting the navigation panels to be visible and enabling them.
        navigationPanel.setVisible(true);
        navigationPanel.setEnabled(true);
        profilePanel.setVisible(false);
        profilePanel.setEnabled(false);
        upcomingEventsPanel.setVisible(false);
        upcomingEventsPanel.setEnabled(false);
        pastEventsPanel.setVisible(false);
        pastEventsPanel.setEnabled(false);
        postEventsPanel.setVisible(false);
        postEventsPanel.setEnabled(false);
    }
    
    public void active(){
        //Hidding all the panels and setting navigation and upcoming events panel to be visible.
        allHidden();
        upcomingEventsPanel.setVisible(true);
        upcomingEventsPanel.setEnabled(true);
    }

    public void upcomingEventsTable(){
        //function to fetch and populate the data for upcoming events for that association.
        String upcomingEventsUrl= "http://localhost:9002/events/?type=associationUpcoming&association_id="+ this.associationDetailsJSON.get("association_id").toString();
        //initializing the RestAPI an fetching the data as a JSON file.
        RestAPIHook upcomingEventsHook = new RestAPIHook();
        JSONObject upcomingEventsData = upcomingEventsHook.invokeGetMethod(upcomingEventsUrl);
        //iterating over the JSON file.
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
            //creating a JSON and appending data to be displayed in the table.
            JSONObject upcomingEventsVal = upcomingEventsData.getJSONObject(upcomingEventsKey);
            Iterator<String> upcomingEventsChildKeys = upcomingEventsVal.keys();
            while(upcomingEventsChildKeys.hasNext()){
                String upcomingEventsChildKey = upcomingEventsChildKeys.next();
                upcomingEventsTemp.put(upcomingEventsChildKey, upcomingEventsVal.get(upcomingEventsChildKey));
            }
            // setting parameters to send this to populate the table.
            // isRendered false to not get a follow button
            upcomingEventsTemp.put("isRendered", false);
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
        jScrollPane2.setViewportView(upcomingEventsTable);
        //hidding all the panels and only making the upcoming events panel visible.
        allHidden();
        upcomingEventsPanel.setVisible(true);
        upcomingEventsPanel.setEnabled(true);
    }
    
    public void pastEventsTable(){
        //function to fetch and populate the data for past events for that association.
        String pastEventsUrl= "http://localhost:9002/events/?type=associationPast&association_id="+ this.associationDetailsJSON.get("association_id").toString();
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
        pastEventsTable = new JTable(new EventsFeedTableModel(pastEventsFeeds));
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
        jScrollPane3.setViewportView(pastEventsTable);
        //hidding all the panels and only making the past events panel visible.
        allHidden();
        pastEventsPanel.setVisible(true);
        pastEventsPanel.setEnabled(true);
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
        postEventsButton = new javax.swing.JButton();
        upcomingEventsButton = new javax.swing.JButton();
        pastEventsButton = new javax.swing.JButton();
        headerPanel = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        logoutLabel = new javax.swing.JLabel();
        profilePanel = new javax.swing.JPanel();
        userType = new javax.swing.JLabel();
        description = new javax.swing.JLabel();
        fname = new javax.swing.JLabel();
        upcomingEventsPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        upcomingEventsTable = new javax.swing.JTable();
        pastEventsPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        pastEventsTable = new javax.swing.JTable();
        postEventsPanel = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        descriptionLabel = new javax.swing.JLabel();
        venueLabel = new javax.swing.JLabel();
        startTimeLabel = new javax.swing.JLabel();
        endTimeLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        decriptionTextArea = new javax.swing.JTextArea();
        postButton = new javax.swing.JButton();
        nameTextField = new javax.swing.JTextField();
        venueTextField = new javax.swing.JTextField();
        startDateChooser = new com.toedter.calendar.JDateChooser();
        endDateChooser = new com.toedter.calendar.JDateChooser();
        Date date = new Date();
        SpinnerDateModel startModel = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        startSpinner = new javax.swing.JSpinner(startModel);
        Date date1 = new Date();
        SpinnerDateModel endModel = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        endSpinner = new javax.swing.JSpinner(endModel);
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

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
        jPanel1.setPreferredSize(new java.awt.Dimension(1560, 630));
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

        postEventsButton.setBackground(new java.awt.Color(102, 102, 102));
        postEventsButton.setForeground(new java.awt.Color(255, 255, 255));
        postEventsButton.setText("Create an Event");
        postEventsButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        postEventsButton.setMaximumSize(new java.awt.Dimension(140, 29));
        postEventsButton.setMinimumSize(new java.awt.Dimension(140, 29));
        postEventsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postEventsButtonActionPerformed(evt);
            }
        });

        upcomingEventsButton.setBackground(new java.awt.Color(102, 102, 102));
        upcomingEventsButton.setForeground(new java.awt.Color(255, 255, 255));
        upcomingEventsButton.setText("Upcoming Events");
        upcomingEventsButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        upcomingEventsButton.setMaximumSize(new java.awt.Dimension(140, 29));
        upcomingEventsButton.setMinimumSize(new java.awt.Dimension(140, 29));
        upcomingEventsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upcomingEventsButtonActionPerformed(evt);
            }
        });

        pastEventsButton.setBackground(new java.awt.Color(102, 102, 102));
        pastEventsButton.setForeground(new java.awt.Color(255, 255, 255));
        pastEventsButton.setText("Past Events");
        pastEventsButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        pastEventsButton.setMaximumSize(new java.awt.Dimension(140, 29));
        pastEventsButton.setMinimumSize(new java.awt.Dimension(140, 29));
        pastEventsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pastEventsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout navigationPanelLayout = new javax.swing.GroupLayout(navigationPanel);
        navigationPanel.setLayout(navigationPanelLayout);
        navigationPanelLayout.setHorizontalGroup(
            navigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profileButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
            .addComponent(pastEventsButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navigationPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(upcomingEventsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(navigationPanelLayout.createSequentialGroup()
                .addComponent(postEventsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        navigationPanelLayout.setVerticalGroup(
            navigationPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navigationPanelLayout.createSequentialGroup()
                .addComponent(profileButton, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(upcomingEventsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pastEventsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(postEventsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(133, 133, 133))
        );

        jPanel1.add(navigationPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 270, 560));

        headerPanel.setBackground(new java.awt.Color(204, 0, 0));

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
                .addContainerGap(638, Short.MAX_VALUE)
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(507, 507, 507)
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

        userType.setText("User Type :        Association");

        description.setText("Description :        "+ this.associationDetailsJSON.get("description").toString());

        fname.setText("First Name :       "+this.associationDetailsJSON.get("association_name").toString());

        javax.swing.GroupLayout profilePanelLayout = new javax.swing.GroupLayout(profilePanel);
        profilePanel.setLayout(profilePanelLayout);
        profilePanelLayout.setHorizontalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addGap(294, 294, 294)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(userType, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                    .addComponent(fname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(description, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(961, Short.MAX_VALUE))
        );
        profilePanelLayout.setVerticalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(userType, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(fname, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(312, Short.MAX_VALUE))
        );

        jPanel1.add(profilePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 560));

        upcomingEventsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        upcomingEventsTable.setFocusable(false);
        upcomingEventsTable.setOpaque(false);
        upcomingEventsTable.setRequestFocusEnabled(false);
        upcomingEventsTable.setRowSelectionAllowed(false);
        upcomingEventsTable.setSurrendersFocusOnKeystroke(true);
        upcomingEventsTable.setUpdateSelectionOnSort(false);
        upcomingEventsTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane2.setViewportView(upcomingEventsTable);
        upcomingEventsTable();

        javax.swing.GroupLayout upcomingEventsPanelLayout = new javax.swing.GroupLayout(upcomingEventsPanel);
        upcomingEventsPanel.setLayout(upcomingEventsPanelLayout);
        upcomingEventsPanelLayout.setHorizontalGroup(
            upcomingEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(upcomingEventsPanelLayout.createSequentialGroup()
                .addGap(333, 333, 333)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );
        upcomingEventsPanelLayout.setVerticalGroup(
            upcomingEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, upcomingEventsPanelLayout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jPanel1.add(upcomingEventsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 560));

        pastEventsTable.setModel(pastEventsTable.getModel());
        pastEventsTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pastEventsTable.setFocusable(false);
        pastEventsTable.setOpaque(false);
        pastEventsTable.setRequestFocusEnabled(false);
        pastEventsTable.setRowSelectionAllowed(false);
        pastEventsTable.setSurrendersFocusOnKeystroke(true);
        pastEventsTable.setUpdateSelectionOnSort(false);
        pastEventsTable.setVerifyInputWhenFocusTarget(false);
        jScrollPane3.setViewportView(pastEventsTable);
        pastEventsTable();

        javax.swing.GroupLayout pastEventsPanelLayout = new javax.swing.GroupLayout(pastEventsPanel);
        pastEventsPanel.setLayout(pastEventsPanelLayout);
        pastEventsPanelLayout.setHorizontalGroup(
            pastEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pastEventsPanelLayout.createSequentialGroup()
                .addGap(333, 333, 333)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );
        pastEventsPanelLayout.setVerticalGroup(
            pastEventsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pastEventsPanelLayout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jPanel1.add(pastEventsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 560));

        postEventsPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        nameLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        nameLabel.setText("Name");
        postEventsPanel.add(nameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, -1, -1));

        descriptionLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        descriptionLabel.setText("Description");
        postEventsPanel.add(descriptionLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 140, -1, -1));

        venueLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        venueLabel.setText("Venue");
        postEventsPanel.add(venueLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 270, -1, -1));

        startTimeLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        startTimeLabel.setText("Start Time");
        postEventsPanel.add(startTimeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 320, -1, -1));

        endTimeLabel.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        endTimeLabel.setText("End Time");
        postEventsPanel.add(endTimeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 370, 70, -1));

        decriptionTextArea.setColumns(20);
        decriptionTextArea.setRows(5);
        jScrollPane1.setViewportView(decriptionTextArea);

        postEventsPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 140, 210, -1));

        postButton.setText("POST");
        postButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postButtonActionPerformed(evt);
            }
        });
        postEventsPanel.add(postButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 450, 100, 40));
        postEventsPanel.add(nameTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 210, -1));
        postEventsPanel.add(venueTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 270, 210, -1));

        startDateChooser.setDateFormatString("yyyy-MM-dd");
        startDateChooser.setMinSelectableDate(new Date());
        postEventsPanel.add(startDateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 320, 210, -1));

        endDateChooser.setDateFormatString("yyyy-MM-dd");
        endDateChooser.setMinSelectableDate(new Date());
        postEventsPanel.add(endDateChooser, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 370, 210, -1));

        JSpinner.DateEditor startEditor = new JSpinner.DateEditor(startSpinner, "HH:mm:ss");
        startSpinner.setEditor(startEditor);
        postEventsPanel.add(startSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 320, 90, -1));

        JSpinner.DateEditor endEditor = new JSpinner.DateEditor(endSpinner, "HH:mm:ss");
        endSpinner.setEditor(endEditor);
        postEventsPanel.add(endSpinner, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 370, 90, -1));

        jLabel11.setForeground(new java.awt.Color(255, 0, 0));
        jLabel11.setText("*");
        postEventsPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 370, 30, 10));

        jLabel12.setForeground(new java.awt.Color(255, 0, 0));
        jLabel12.setText("*");
        postEventsPanel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 90, 20, 10));

        jLabel13.setForeground(new java.awt.Color(255, 0, 0));
        jLabel13.setText("*");
        postEventsPanel.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 130, 20, 20));

        jLabel14.setForeground(new java.awt.Color(255, 0, 0));
        jLabel14.setText("*");
        postEventsPanel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 320, 30, 10));

        jPanel1.add(postEventsPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 560));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void postEventsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postEventsButtonActionPerformed
        // TODO add your handling code here:
        //hidding all the panels and setting the post events panel to visible.
        allHidden();
        postEventsPanel.setVisible(true);
        postEventsPanel.setEnabled(true);
    }//GEN-LAST:event_postEventsButtonActionPerformed

    private void upcomingEventsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upcomingEventsButtonActionPerformed
        // TODO add your handling code here:
        //calling the upcoming events table when clicking the button. 
        upcomingEventsTable();
    }//GEN-LAST:event_upcomingEventsButtonActionPerformed

    private void pastEventsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pastEventsButtonActionPerformed
        // TODO add your handling code here:
        //calling the past events table when clicking the button. 
        pastEventsTable();
    }//GEN-LAST:event_pastEventsButtonActionPerformed

    private void profileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileButtonActionPerformed
        // TODO add your handling code here:
        //hidding all the panels and setting the profile panel to visible.
        allHidden();
        profilePanel.setVisible(true);
        profilePanel.setEnabled(true);
    }//GEN-LAST:event_profileButtonActionPerformed

    //The date fields should not be null
    public boolean validateDate(Date start, Date end){
        boolean isValid = true;
        startDateChooser.setBackground(Color.white);
        endDateChooser.setBackground(Color.white);
        
        if(start == null){
            startDateChooser.setBackground(Color.red);
            isValid = false;
        }
        if(end == null){
            endDateChooser.setBackground(Color.red);
            isValid = false;
        }
        
        return isValid;
    }
    
    public boolean validations(String startDateTime, String endDateTime) throws ParseException{
        boolean isValid = true;
        nameTextField.setBackground(Color.white);
        decriptionTextArea.setBackground(Color.white);
        startDateChooser.setBackground(Color.white);
        endDateChooser.setBackground(Color.white);

        Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").parse(startDateTime);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS").parse(endDateTime);

        //Check for any empty fields
        if(nameTextField.getText().length() == 0){
            nameTextField.setBackground(Color.red);
            isValid = false;
        }
        if(decriptionTextArea.getText().length() == 0){
            decriptionTextArea.setBackground(Color.red);
            isValid = false;
        }
        //Check if start date is before end date
        if(startDate.after(endDate)){
            startDateChooser.setBackground(Color.red);
            endDateChooser.setBackground(Color.red);
            isValid = false;
        }

        return isValid;
    }
    
    //Actions performed to Create a new Event
    private void postButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postButtonActionPerformed
        // TODO add your handling code here:
        String name = nameTextField.getText();
        String desc = decriptionTextArea.getText();
        String venue = venueTextField.getText();
        Date start = startDateChooser.getDate();
        Date end = endDateChooser.getDate();
        
        //Validate Dates fields first. Date fields should never be null
        if(validateDate(start, end)){
            try {
                //Format the start date and time
                SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                String startDate = dateFormat1.format(start);
                String startTime = startSpinner.getValue().toString().split(" ",6)[3];
                String startDateTime = startDate+" "+startTime;
                
                //Format the end date and time
                String endDate = dateFormat1.format(end);
                String endTime = endSpinner.getValue().toString().split(" ",6)[3];
                String endDateTime = endDate+" "+endTime;
                
                //Enter loop if all fields are valid
                if(validations(startDateTime, endDateTime)){
                    //Clear the fields after clicking button
                    nameTextField.setText("");
                    decriptionTextArea.setText("");
                    venueTextField.setText("");
                    startDateChooser.setCalendar(null);
                    endDateChooser.setCalendar(null);
                    
                    //Create a POST call to insert a new event
                    String url = "http://localhost:9002/events/?";
                    RestAPIHook a = new RestAPIHook();
                    HashMap<String, String> params = new HashMap<>();
                    params.put("association_id", associationDetailsJSON.get("association_id").toString());
                    params.put("start_time", startDateTime);
                    params.put("end_time", endDateTime);
                    params.put("name", name);
                    if(!"".equals(venue)){
                        params.put("venue", venue);
                    }
                    params.put("description", desc);
                    
                    JSONObject p = a.invokePostMethod(url, params);
                    //Display success messsage once POST call is a success
                    if(p.get("isSuccess").toString().equals("true")){
                        JOptionPane.showMessageDialog(postEventsPanel, "Successfully created an event!");
                    }
                    else{
                        //Display an error message if new event is not created
                        JOptionPane.showMessageDialog(postEventsPanel, "Error in creating an event!");
                    }
                }
            } catch (ParseException ex) {
                Logger.getLogger(AssociationHomePage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_postButtonActionPerformed

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
            java.util.logging.Logger.getLogger(AssociationHomePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea decriptionTextArea;
    private javax.swing.JLabel description;
    private javax.swing.JLabel descriptionLabel;
    private com.toedter.calendar.JDateChooser endDateChooser;
    private javax.swing.JSpinner endSpinner;
    private javax.swing.JLabel endTimeLabel;
    private javax.swing.JLabel fname;
    private javax.swing.JPanel headerPanel;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel logoutLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JPanel navigationPanel;
    private javax.swing.JButton pastEventsButton;
    private javax.swing.JPanel pastEventsPanel;
    public javax.swing.JTable pastEventsTable;
    private javax.swing.JButton postButton;
    private javax.swing.JButton postEventsButton;
    private javax.swing.JPanel postEventsPanel;
    private javax.swing.JButton profileButton;
    private javax.swing.JPanel profilePanel;
    private com.toedter.calendar.JDateChooser startDateChooser;
    private javax.swing.JSpinner startSpinner;
    private javax.swing.JLabel startTimeLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton upcomingEventsButton;
    private javax.swing.JPanel upcomingEventsPanel;
    public javax.swing.JTable upcomingEventsTable;
    private javax.swing.JLabel userType;
    private javax.swing.JLabel venueLabel;
    private javax.swing.JTextField venueTextField;
    // End of variables declaration//GEN-END:variables
}
