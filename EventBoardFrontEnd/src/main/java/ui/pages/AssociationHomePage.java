/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui.pages;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
public final class AssociationHomePage extends javax.swing.JFrame {

    /**
     * Creates new form HomePage
     */
    private JSONObject inputJSON;
    private String associationId;
    private JSONObject associationDetailsJSON;
 
    public AssociationHomePage(JSONObject input, JSONObject details) {
        this.inputJSON = input;
        this.associationDetailsJSON = details;
        initComponents();
        active();
    }

    public void allHidden(){
        navPanelAssocUser.setVisible(true);
        navPanelAssocUser.setEnabled(true);
        menu.setVisible(false);
        menu.setEnabled(false);
        profilePanel.setVisible(false);
        profilePanel.setEnabled(false);
        upcomEvePanel.setVisible(false);
        upcomEvePanel.setEnabled(false);
        pastEvePanel.setVisible(false);
        pastEvePanel.setEnabled(false);
        postEvePanel.setVisible(false);
        postEvePanel.setEnabled(false);
    }
    
    public void active(){
        allHidden();
        upcomEvePanel.setVisible(true);
        upcomEvePanel.setEnabled(true);
    }

    public void upcomingEventsTable(){
        String url= "http://localhost:9002/events/?type=associationUpcoming&association_id="+ this.associationDetailsJSON.get("association_id").toString();
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
            temp.put("isRendered", false);
            temp.put("isFollow", true);
            temp.put("user_id", "12");
            feeds.add(new EventsFeed(temp));
        }
        upcomingEventsTable = new JTable(new EventsFeedTableModel(feeds));
        upcomingEventsTable.setDefaultRenderer(EventsFeed.class, new EventsFeedCell());
        upcomingEventsTable.setDefaultEditor(EventsFeed.class, new EventsFeedCell());
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
        allHidden();
        upcomEvePanel.setVisible(true);
        upcomEvePanel.setEnabled(true);
    }
    
    public void pastEventsTable(){
        String url= "http://localhost:9002/events/?type=associationPast&association_id="+ this.associationDetailsJSON.get("association_id").toString();
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
            temp.put("isRendered", false);
            temp.put("isFollow", true);
            temp.put("user_id", "12");
            feeds.add(new EventsFeed(temp));
        }
        pastEventsTable = new JTable(new EventsFeedTableModel(feeds));
        pastEventsTable.setDefaultRenderer(EventsFeed.class, new EventsFeedCell());
        pastEventsTable.setDefaultEditor(EventsFeed.class, new EventsFeedCell());
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
        allHidden();
        pastEvePanel.setVisible(true);
        pastEvePanel.setEnabled(true);
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
        navPanelAssocUser = new javax.swing.JPanel();
        profileAssocButton = new javax.swing.JButton();
        postEveAssocButton = new javax.swing.JButton();
        upcomEveAssocButton = new javax.swing.JButton();
        pastEveAssocButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        titleLabel = new javax.swing.JLabel();
        logoutLabel = new javax.swing.JLabel();
        menu = new javax.swing.JLabel();
        profilePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        upcomEvePanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        upcomingEventsTable = new javax.swing.JTable();
        pastEvePanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        pastEventsTable = new javax.swing.JTable();
        postEvePanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        postButton = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jDateChooser2 = new com.toedter.calendar.JDateChooser();
        Date date = new Date();
        SpinnerDateModel startModel = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        jSpinner1 = new javax.swing.JSpinner(startModel);
        Date date1 = new Date();
        SpinnerDateModel endModel = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
        jSpinner2 = new javax.swing.JSpinner(endModel);

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

        navPanelAssocUser.setBackground(new java.awt.Color(102, 102, 102));

        profileAssocButton.setBackground(new java.awt.Color(102, 102, 102));
        profileAssocButton.setForeground(new java.awt.Color(255, 255, 255));
        profileAssocButton.setText("Profile Settings");
        profileAssocButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        profileAssocButton.setMaximumSize(new java.awt.Dimension(140, 29));
        profileAssocButton.setMinimumSize(new java.awt.Dimension(140, 29));
        profileAssocButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profileAssocButtonActionPerformed(evt);
            }
        });

        postEveAssocButton.setBackground(new java.awt.Color(102, 102, 102));
        postEveAssocButton.setForeground(new java.awt.Color(255, 255, 255));
        postEveAssocButton.setText("Create an Event");
        postEveAssocButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        postEveAssocButton.setMaximumSize(new java.awt.Dimension(140, 29));
        postEveAssocButton.setMinimumSize(new java.awt.Dimension(140, 29));
        postEveAssocButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postEveAssocButtonActionPerformed(evt);
            }
        });

        upcomEveAssocButton.setBackground(new java.awt.Color(102, 102, 102));
        upcomEveAssocButton.setForeground(new java.awt.Color(255, 255, 255));
        upcomEveAssocButton.setText("Upcoming Events");
        upcomEveAssocButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        upcomEveAssocButton.setMaximumSize(new java.awt.Dimension(140, 29));
        upcomEveAssocButton.setMinimumSize(new java.awt.Dimension(140, 29));
        upcomEveAssocButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upcomEveAssocButtonActionPerformed(evt);
            }
        });

        pastEveAssocButton.setBackground(new java.awt.Color(102, 102, 102));
        pastEveAssocButton.setForeground(new java.awt.Color(255, 255, 255));
        pastEveAssocButton.setText("Past Events");
        pastEveAssocButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        pastEveAssocButton.setMaximumSize(new java.awt.Dimension(140, 29));
        pastEveAssocButton.setMinimumSize(new java.awt.Dimension(140, 29));
        pastEveAssocButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pastEveAssocButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout navPanelAssocUserLayout = new javax.swing.GroupLayout(navPanelAssocUser);
        navPanelAssocUser.setLayout(navPanelAssocUserLayout);
        navPanelAssocUserLayout.setHorizontalGroup(
            navPanelAssocUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profileAssocButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
            .addComponent(pastEveAssocButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navPanelAssocUserLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(upcomEveAssocButton, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(navPanelAssocUserLayout.createSequentialGroup()
                .addComponent(postEveAssocButton, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        navPanelAssocUserLayout.setVerticalGroup(
            navPanelAssocUserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, navPanelAssocUserLayout.createSequentialGroup()
                .addComponent(profileAssocButton, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(upcomEveAssocButton, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pastEveAssocButton, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(postEveAssocButton, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(133, 133, 133))
        );

        jPanel1.add(navPanelAssocUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 270, 560));

        jPanel2.setBackground(new java.awt.Color(204, 0, 0));

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 573, Short.MAX_VALUE)
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(507, 507, 507)
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

        jLabel1.setText("User Type :        Association");

        jLabel2.setText("Description :        "+ this.associationDetailsJSON.get("description").toString());

        jLabel4.setText("First Name :       "+this.associationDetailsJSON.get("association_name").toString());

        javax.swing.GroupLayout profilePanelLayout = new javax.swing.GroupLayout(profilePanel);
        profilePanel.setLayout(profilePanelLayout);
        profilePanelLayout.setHorizontalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addGap(294, 294, 294)
                .addGroup(profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(961, Short.MAX_VALUE))
        );
        profilePanelLayout.setVerticalGroup(
            profilePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(profilePanelLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
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

        javax.swing.GroupLayout upcomEvePanelLayout = new javax.swing.GroupLayout(upcomEvePanel);
        upcomEvePanel.setLayout(upcomEvePanelLayout);
        upcomEvePanelLayout.setHorizontalGroup(
            upcomEvePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(upcomEvePanelLayout.createSequentialGroup()
                .addGap(333, 333, 333)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );
        upcomEvePanelLayout.setVerticalGroup(
            upcomEvePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, upcomEvePanelLayout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jPanel1.add(upcomEvePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 560));

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

        javax.swing.GroupLayout pastEvePanelLayout = new javax.swing.GroupLayout(pastEvePanel);
        pastEvePanel.setLayout(pastEvePanelLayout);
        pastEvePanelLayout.setHorizontalGroup(
            pastEvePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pastEvePanelLayout.createSequentialGroup()
                .addGap(333, 333, 333)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(79, Short.MAX_VALUE))
        );
        pastEvePanelLayout.setVerticalGroup(
            pastEvePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pastEvePanelLayout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );

        jPanel1.add(pastEvePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 560));

        postEvePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Name");
        postEvePanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 90, -1, -1));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Description");
        postEvePanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 140, -1, -1));

        jLabel8.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel8.setText("Venue");
        postEvePanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 270, -1, -1));

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("Start Time");
        postEvePanel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 320, -1, -1));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("End Time");
        postEvePanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 370, -1, -1));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        postEvePanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 140, 210, -1));

        postButton.setText("POST");
        postButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                postButtonActionPerformed(evt);
            }
        });
        postEvePanel.add(postButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 450, 100, 40));
        postEvePanel.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 90, 210, -1));
        postEvePanel.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 270, 210, -1));

        jDateChooser1.setDateFormatString("yyyy-MM-dd");
        postEvePanel.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 320, 210, -1));

        jDateChooser2.setDateFormatString("yyyy-MM-dd");
        postEvePanel.add(jDateChooser2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 370, 210, -1));

        JSpinner.DateEditor startEditor = new JSpinner.DateEditor(jSpinner1, "HH:mm:ss");
        jSpinner1.setEditor(startEditor);
        postEvePanel.add(jSpinner1, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 320, 90, -1));

        JSpinner.DateEditor endEditor = new JSpinner.DateEditor(jSpinner2, "HH:mm:ss");
        jSpinner2.setEditor(endEditor);
        postEvePanel.add(jSpinner2, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 370, 90, -1));

        jPanel1.add(postEvePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1560, 560));

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
        this.dispose();     
        LoginPage redirectLogin = new LoginPage();
        redirectLogin.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        redirectLogin.setVisible(true);
    }//GEN-LAST:event_logoutLabelMouseClicked

    private void menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuMouseClicked
        // TODO add your handling code here:
        active();
    }//GEN-LAST:event_menuMouseClicked

    private void postEveAssocButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postEveAssocButtonActionPerformed
        // TODO add your handling code here:
        allHidden();
        postEvePanel.setVisible(true);
        postEvePanel.setEnabled(true);
    }//GEN-LAST:event_postEveAssocButtonActionPerformed

    private void upcomEveAssocButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upcomEveAssocButtonActionPerformed
        // TODO add your handling code here:
        upcomingEventsTable();
    }//GEN-LAST:event_upcomEveAssocButtonActionPerformed

    private void pastEveAssocButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pastEveAssocButtonActionPerformed
        // TODO add your handling code here:
        pastEventsTable();
    }//GEN-LAST:event_pastEveAssocButtonActionPerformed

    private void profileAssocButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileAssocButtonActionPerformed
        // TODO add your handling code here:
        allHidden();
        profilePanel.setVisible(true);
        profilePanel.setEnabled(true);
    }//GEN-LAST:event_profileAssocButtonActionPerformed

    private void postButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_postButtonActionPerformed
        // TODO add your handling code here:
        String name = jTextField1.getText();
        String desc = jTextArea1.getText();
        String venue = jTextField7.getText();
        Date start = jDateChooser1.getDate();
        Date end = jDateChooser2.getDate();
        //Check if all fields are populated
        if(name.isEmpty() || desc.isEmpty() || venue.isEmpty()){
            JOptionPane.showMessageDialog(postEvePanel, "Please fill all fields");
        }
        else if(start == null){
            JOptionPane.showMessageDialog(postEvePanel, "Please fill the start time!");
        }
        else if(end == null){
            JOptionPane.showMessageDialog(postEvePanel, "Please fill the end time!");
        }
        //Format the start date and time  
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        String startDate = dateFormat1.format(start);
        String startTime = jSpinner1.getValue().toString().split(" ",6)[3];
        String startDateTime = startDate+" "+startTime;
        System.out.println("startDateTime -- "+startDateTime);
        //Format the end date and time
        String endDate = dateFormat1.format(end);
        String endTime = jSpinner2.getValue().toString().split(" ",6)[3];
        String endDateTime = endDate+" "+endTime;
        System.out.println("startDateTime -- "+endDateTime);
        
        //Clear the fields after clicking button
        jTextField1.setText("");
        jTextArea1.setText("");
        jTextField7.setText("");
        jDateChooser1.setCalendar(null);
        jDateChooser2.setCalendar(null);
        
        String url = "http://localhost:9002/events/?";
        RestAPIHook a = new RestAPIHook();
        HashMap<String, String> params = new HashMap<>();
        params.put("association_id", associationDetailsJSON.get("association_id").toString());
        params.put("start_time", startDateTime);
        params.put("end_time", endDateTime);
        params.put("name", name);
        params.put("venue", venue);
        params.put("description", desc);
        System.out.println(params);
        JSONObject p = a.invokePostMethod(url, params);
        if(p.get("isSuccess").toString().equals("true")){
            JOptionPane.showMessageDialog(postEvePanel, "Successfully created an event");
        }
        else{
            JOptionPane.showMessageDialog(postEvePanel, "Could not create an event");
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
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private com.toedter.calendar.JDateChooser jDateChooser2;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSpinner jSpinner1;
    private javax.swing.JSpinner jSpinner2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JLabel logoutLabel;
    private javax.swing.JLabel menu;
    private javax.swing.JPanel navPanelAssocUser;
    private javax.swing.JButton pastEveAssocButton;
    private javax.swing.JPanel pastEvePanel;
    public javax.swing.JTable pastEventsTable;
    private javax.swing.JButton postButton;
    private javax.swing.JButton postEveAssocButton;
    private javax.swing.JPanel postEvePanel;
    private javax.swing.JButton profileAssocButton;
    private javax.swing.JPanel profilePanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton upcomEveAssocButton;
    private javax.swing.JPanel upcomEvePanel;
    public javax.swing.JTable upcomingEventsTable;
    // End of variables declaration//GEN-END:variables
}
