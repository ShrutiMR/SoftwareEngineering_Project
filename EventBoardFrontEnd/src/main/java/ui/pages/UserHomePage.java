/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.swing.JTable;
import org.json.JSONObject;
import rest.RestAPIHook;
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
        nonActive();
    }

    
    public void nonActive(){
        navPanel.setVisible(false);
        navPanel.setEnabled(false);
        jLabel3.setVisible(false);
        jLabel3.setEnabled(false);
        menu.setVisible(true);
        menu.setEnabled(true);
    }
    
    public void active(){
        navPanel.setVisible(true);
        navPanel.setEnabled(true);
        jLabel3.setVisible(true);
        jLabel3.setEnabled(true);
        menu.setVisible(false);
        menu.setEnabled(false);
        homeFeedsTable.setVisible(true);
        homeFeedsTable.setEnabled(true);
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

            HashMap temp = new HashMap();

            temp.put("event_id", pastKey);
            JSONObject val = pastJSON.getJSONObject(pastKey);
            Iterator<String> childKeys = val.keys();
            while(childKeys.hasNext()){
                String childKey = childKeys.next();
                temp.put(childKey, val.get(childKey));
            }
            temp.put("isRendered", false);
            temp.put("isFollow", false);
            temp.put("user_id", this.inputJSON.get("user_id"));
            System.out.println("Hi2");
            System.out.println(temp);
            System.out.println("Hi3");
            pastfeeds.add(new EventsFeed(temp));
        }
        homeFeedsTable1 = new JTable (new EventsFeedTableModel(pastfeeds));
        upcomEvePanel = new javax.swing.JPanel();
        homePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        String feedUrl = "http://localhost:9002/events/?type=feed&user_id="+this.inputJSON.get("user_id");
        RestAPIHook feedHook = new RestAPIHook();
        JSONObject feedJSON = feedHook.invokeGetMethod(feedUrl);
        Iterator<String> feedKeys = feedJSON.keys();

        List homefeeds = new ArrayList();
        while(feedKeys.hasNext()){
            String feedKey = feedKeys.next();

            if("isSuccess".equals(feedKey)){
                continue;
            }

            HashMap temp = new HashMap();

            temp.put("event_id", feedKey);
            JSONObject val = feedJSON.getJSONObject(feedKey);
            Iterator<String> childKeys = val.keys();
            while(childKeys.hasNext()){
                String childKey = childKeys.next();
                temp.put(childKey, val.get(childKey));
            }
            temp.put("isRendered", true);
            temp.put("isFollow", true);
            temp.put("user_id", this.inputJSON.get("user_id").toString());
            System.out.println("Hi2");
            System.out.println(temp);
            System.out.println("Hi3");
            homefeeds.add(new EventsFeed(temp));

        }
        homeFeedsTable = new JTable(new EventsFeedTableModel(homefeeds));

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

        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
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

        javax.swing.GroupLayout navPanelLayout = new javax.swing.GroupLayout(navPanel);
        navPanel.setLayout(navPanelLayout);
        navPanelLayout.setHorizontalGroup(
            navPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(profileButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pastEveButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(upcomEveButton, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
            .addComponent(homeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        navPanelLayout.setVerticalGroup(
            navPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(navPanelLayout.createSequentialGroup()
                .addComponent(profileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(homeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(upcomEveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pastEveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(navPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 270, 560));

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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 371, Short.MAX_VALUE)
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(289, 289, 289)
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

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1140, 70));

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
                .addContainerGap(544, Short.MAX_VALUE))
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
                .addContainerGap(312, Short.MAX_VALUE))
        );

        jPanel1.add(profilePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1140, 560));

        homeFeedsTable1.setDefaultRenderer(EventsFeed.class, new EventsFeedCell());
        homeFeedsTable1.setDefaultEditor(EventsFeed.class, new EventsFeedCell());
        homeFeedsTable1.setRowHeight(60);
        homeFeedsTable1.setModel(homeFeedsTable1.getModel());
        homeFeedsTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        homeFeedsTable1.setFocusable(false);
        homeFeedsTable1.setOpaque(false);
        homeFeedsTable1.setRequestFocusEnabled(false);
        homeFeedsTable1.setRowSelectionAllowed(false);
        homeFeedsTable1.setSurrendersFocusOnKeystroke(true);
        homeFeedsTable1.setUpdateSelectionOnSort(false);
        homeFeedsTable1.setVerifyInputWhenFocusTarget(false);
        jScrollPane2.setViewportView(homeFeedsTable1);

        javax.swing.GroupLayout pastEvePanelLayout = new javax.swing.GroupLayout(pastEvePanel);
        pastEvePanel.setLayout(pastEvePanelLayout);
        pastEvePanelLayout.setHorizontalGroup(
            pastEvePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pastEvePanelLayout.createSequentialGroup()
                .addGap(269, 269, 269)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(419, Short.MAX_VALUE))
        );
        pastEvePanelLayout.setVerticalGroup(
            pastEvePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pastEvePanelLayout.createSequentialGroup()
                .addGap(74, 74, 74)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        jPanel1.add(pastEvePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1140, 560));

        javax.swing.GroupLayout upcomEvePanelLayout = new javax.swing.GroupLayout(upcomEvePanel);
        upcomEvePanel.setLayout(upcomEvePanelLayout);
        upcomEvePanelLayout.setHorizontalGroup(
            upcomEvePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1140, Short.MAX_VALUE)
        );
        upcomEvePanelLayout.setVerticalGroup(
            upcomEvePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 560, Short.MAX_VALUE)
        );

        jPanel1.add(upcomEvePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1140, 560));

        jLabel1.setText("Home");

        homeFeedsTable.setDefaultRenderer(EventsFeed.class, new EventsFeedCell());
        homeFeedsTable.setDefaultEditor(EventsFeed.class, new EventsFeedCell());
        homeFeedsTable.setRowHeight(60);
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
        homeButton.doClick();

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
                        .addGap(281, 281, 281)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(407, Short.MAX_VALUE))
        );
        homePanelLayout.setVerticalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );

        jPanel1.add(homePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 1140, 560));

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

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        // TODO add your handling code here:
        nonActive();
    }//GEN-LAST:event_jLabel3MouseClicked

    private void logoutLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutLabelMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_logoutLabelMouseClicked

    private void menuMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuMouseClicked
        // TODO add your handling code here:
        active();
    }//GEN-LAST:event_menuMouseClicked

    private void homeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_homeButtonActionPerformed
        // TODO add your handling code here:
        String feedUrl = "http://localhost:9002/events/?type=feed&user_id="+this.inputJSON.get("user_id");
        RestAPIHook a = new RestAPIHook();
        JSONObject feedJSON = a.invokeGetMethod(feedUrl);
        Iterator<String> feedKeys = feedJSON.keys();

        List homefeeds = new ArrayList();
        while(feedKeys.hasNext()){
            String feedKey = feedKeys.next();

            if("isSuccess".equals(feedKey)){
                continue;
            }

            HashMap temp = new HashMap();

            temp.put("event_id", feedKey);
            JSONObject val = feedJSON.getJSONObject(feedKey);
            Iterator<String> childKeys = val.keys();
            while(childKeys.hasNext()){
                String childKey = childKeys.next();
                temp.put(childKey, val.get(childKey));
            }
            temp.put("isRendered", true);
            temp.put("isFollow", true);
            temp.put("user_id", this.inputJSON.get("user_id").toString());
            System.out.println("Hi2");
            System.out.println(temp);
            System.out.println("Hi3");
            homefeeds.add(new EventsFeed(temp));

        }
        homeFeedsTable = new JTable(new EventsFeedTableModel(homefeeds));
        homeFeedsTable.setDefaultRenderer(EventsFeed.class, new EventsFeedCell());
        homeFeedsTable.setDefaultEditor(EventsFeed.class, new EventsFeedCell());
        homeFeedsTable.setRowHeight(60);
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
        homePanel.setVisible(true);
        homePanel.setEnabled(true);
        profilePanel.setVisible(false);
        profilePanel.setEnabled(false);
        upcomEvePanel.setVisible(false);
        upcomEvePanel.setEnabled(false);
        pastEvePanel.setVisible(false);
        pastEvePanel.setEnabled(false);
        homeFeedsTable.setVisible(true);
        homeFeedsTable.setEnabled(true);
    }//GEN-LAST:event_homeButtonActionPerformed

    private void upcomEveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upcomEveButtonActionPerformed
        // TODO add your handling code here:
        upcomEvePanel.setVisible(true);
        upcomEvePanel.setEnabled(true);
        profilePanel.setVisible(false);
        profilePanel.setEnabled(false);
        homePanel.setVisible(false);
        homePanel.setEnabled(false);
        pastEvePanel.setVisible(false);
        pastEvePanel.setEnabled(false);
        homeFeedsTable.setVisible(false);
        homeFeedsTable.setEnabled(false);
    }//GEN-LAST:event_upcomEveButtonActionPerformed

    private void profileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profileButtonActionPerformed
        // TODO add your handling code here:
        profilePanel.setVisible(true);
        profilePanel.setEnabled(true);
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
        pastEvePanel.setVisible(true);
        pastEvePanel.setEnabled(true);
        profilePanel.setVisible(false);
        profilePanel.setEnabled(false);
        homePanel.setVisible(false);
        homePanel.setEnabled(false);
        upcomEvePanel.setVisible(false);
        upcomEvePanel.setEnabled(false);
        homeFeedsTable.setVisible(false);
        homeFeedsTable.setEnabled(false);
    }//GEN-LAST:event_pastEveButtonActionPerformed
    
    
    
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

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(() -> {
//            new UserHomePage().setVisible(true);
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton homeButton;
    private javax.swing.JTable homeFeedsTable;
    private javax.swing.JTable homeFeedsTable1;
    private javax.swing.JPanel homePanel;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel logoutLabel;
    private javax.swing.JLabel menu;
    private javax.swing.JPanel navPanel;
    private javax.swing.JButton pastEveButton;
    private javax.swing.JPanel pastEvePanel;
    private javax.swing.JButton profileButton;
    private javax.swing.JPanel profilePanel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JButton upcomEveButton;
    private javax.swing.JPanel upcomEvePanel;
    // End of variables declaration//GEN-END:variables
}
