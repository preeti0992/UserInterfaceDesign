/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websketchproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import onrdollar.HelloDollar;

/**
 *
 * @author meghn
 */
public class WebSketchProject2 {
    // Variables declaration - do not modify  
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private boolean editingMenuDisplayed = false;
    private String defaultText = "Update page name";
    
    private JPanel editingPanel;
    private PageManager pm;
    private JScrollPane mainDisplay;
    private HelloDollar hd;
    // End of variables declaration    
    
    public void initializeGUI()
    {   
        // Create main window of GUI
        JFrame mainFrame = new JFrame("MainWindow");
        
        // Set a minimum size for window 
        mainFrame.setMinimumSize(new Dimension(1300, 800));
        mainFrame.setPreferredSize(new Dimension(1300, 800));
        
        // Name of window = Homework 1 
        mainFrame.setTitle("Web Sketch Project");
        
        // Functionality to close the GUI
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout(1,1));
        
        initComponents();
        
         // Main panel of the application 
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(240, 240, 240));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        JPanel leftSide = setUpLeftWindow();
        centerPanel.add(leftSide);
        
        JPanel rightSide = setUpRightWindow();
        centerPanel.add(Box.createRigidArea(new Dimension(15,0)));
        centerPanel.add(rightSide);
        mainFrame.add(centerPanel, BorderLayout.CENTER);
        
        jLabel1.setVisible(false);
        editingPanel.setVisible(false);
        
        pm.addFirstPage(hd, mainDisplay);
        hd.setParentInfo(mainDisplay);
        hd.setEditingPanel(editingPanel);
        pm.setEditPanel(editingPanel);
        
        mainFrame.setVisible(true);
        mainFrame.pack();
        
        // Centers the application window
        mainFrame.setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jPanel6 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        
        editingPanel = new JPanel();
    }
    
    private JPanel setUpLeftWindow() {
        JPanel leftWindow = new JPanel();
        leftWindow.setMinimumSize(new Dimension(950, 600));
        leftWindow.setPreferredSize(new Dimension(950, 600));
        leftWindow.setLayout(new BorderLayout(1,1));
        
        jButton7.setText("Drawing Mode");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleEditMenu(0);
            }
        });
        
        jButton8.setText("Editing Mode");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toggleEditMenu(1);
            }
        });
        JPanel modeButtonPanel = new JPanel();
        modeButtonPanel.setLayout(new FlowLayout());
        modeButtonPanel.add(jButton7);
        modeButtonPanel.add(jButton8);
        
        leftWindow.add(modeButtonPanel, BorderLayout.NORTH);
        
        hd = new HelloDollar();
        mainDisplay = new JScrollPane(hd, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        mainDisplay.setBackground(Color.WHITE);
        mainDisplay.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        leftWindow.add(mainDisplay, BorderLayout.CENTER);
        
        return leftWindow;
    }
    
    private JPanel setUpRightWindow() {
        JPanel rightScreen = new JPanel();
        rightScreen.setMinimumSize(new Dimension(280, 600));
        rightScreen.setPreferredSize(new Dimension(280, 600));
        rightScreen.setLayout(new BorderLayout(1,1));
        
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Pages");

        jTextField1.setForeground(Color.GRAY);
        jTextField1.setText(defaultText);
        jTextField1.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                jTextField1.setText("");
                jTextField1.setForeground(Color.BLACK);
            }
        });
        
        jButton2.setText("+");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewPage(pm);
            }
        });
        
        jLabel2.setMinimumSize(new Dimension(200, 20));
        jLabel2.setMaximumSize(new Dimension(200, 20));
        jLabel2.setPreferredSize(new Dimension(200, 20));
        
        jButton2.setMinimumSize(new Dimension(45, 25));
        jButton2.setMaximumSize(new Dimension(45, 25));
        jButton2.setPreferredSize(new Dimension(45, 25));
        
        JPanel pagesPanel = new JPanel();
        pagesPanel.setMinimumSize(new Dimension(270, 50));
        pagesPanel.setMaximumSize(new Dimension(270, 50));
        pagesPanel.setPreferredSize(new Dimension(270, 50));
        
        pagesPanel.setLayout(new BoxLayout(pagesPanel, BoxLayout.X_AXIS));
        pagesPanel.add(jLabel2);
        pagesPanel.add(jButton2);
        pagesPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel panePanel = new JPanel();
        panePanel.setLayout(new BoxLayout(panePanel, BoxLayout.Y_AXIS));
        jScrollPane1 = new JScrollPane(panePanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setPreferredSize(new Dimension(280, 100));
        jScrollPane1.setMaximumSize(new Dimension(280, 100));
        pm = new PageManager(jScrollPane1, panePanel);
        
        JPanel topPortion = new JPanel();
        topPortion.setPreferredSize(new Dimension(300, 200));
        topPortion.setMaximumSize(new Dimension(300, 200));
        topPortion.setLayout(new BoxLayout(topPortion, BoxLayout.Y_AXIS));
        topPortion.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPortion.add(pagesPanel);
        topPortion.add(jScrollPane1);
        
        jLabel2.setVerticalAlignment(SwingConstants.CENTER);
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        
        JPanel nameField = new JPanel();
        nameField.setLayout(new BoxLayout(nameField, BoxLayout.X_AXIS));
        nameField.add(jTextField1);
        nameField.add(Box.createRigidArea(new Dimension(10, 0)));
        jTextField1.setPreferredSize(new Dimension(270, 30));
        jTextField1.setMaximumSize(new Dimension(270, 30));
        
        jButton1.setText("Go");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String textFieldEntry = jTextField1.getText();
                if(!(textFieldEntry.equals(defaultText)) && !(textFieldEntry.equals(""))) {
                    updatePageName(textFieldEntry, pm);
                }
            }
        });
        nameField.add(jButton1);
        
        topPortion.add(Box.createRigidArea(new Dimension(0, 10)));
        topPortion.add(nameField);
        
        rightScreen.add(topPortion, BorderLayout.NORTH);
        
        JPanel bottomButtons = setUpBottomButtons();
        rightScreen.add(bottomButtons, BorderLayout.SOUTH);
        
        JPanel editWindow = setUpEditingWindow();
        rightScreen.add(editWindow, BorderLayout.CENTER);
        
        return rightScreen;
    }
    
    private JPanel setUpEditingWindow() {
        JPanel editingWindow = new JPanel();
        editingWindow.setAlignmentX(Component.CENTER_ALIGNMENT);
        editingWindow.setLayout(new BoxLayout(editingWindow, BoxLayout.Y_AXIS));
        editingPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        
        editingPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Editing Menu");
        jLabel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        editingWindow.add(jLabel1, BorderLayout.NORTH);
        editingWindow.add(Box.createRigidArea(new Dimension(0, 10)));
        editingWindow.add(editingPanel);
        return editingWindow; 
    }
    
    private JPanel setUpBottomButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        jButton3.setText("Clear");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pm.clearPage();
            }
        });
        
        jButton3.setMinimumSize(new Dimension(320, 30));
        jButton3.setPreferredSize(new Dimension(320, 30));
        jButton3.setMaximumSize(new Dimension(320, 30));
        
        jButton3.setVerticalAlignment(SwingConstants.CENTER);
        jButton3.setHorizontalAlignment(SwingConstants.CENTER);
        
        jButton3.setAlignmentX(Component.CENTER_ALIGNMENT);
        jButton4.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        jButton4.setText("Generate");
        
        jButton4.setMinimumSize(new Dimension(320, 30));
        jButton4.setPreferredSize(new Dimension(320, 30));
        jButton4.setMaximumSize(new Dimension(320, 30));
        
        jButton4.setVerticalAlignment(SwingConstants.CENTER);
        jButton4.setHorizontalAlignment(SwingConstants.CENTER);
        
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        buttonPanel.add(jButton3);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(jButton4);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        return buttonPanel;
    }
    
    private void addNewPage(PageManager pages) {
        pages.addPageToPane();
    }
    
    private void toggleEditMenu(int tabNum) {
        if (tabNum == 1) {
            jLabel1.setVisible(true);
            editingPanel.setVisible(true);
            editingMenuDisplayed = true;
            pm.updateEditingModeForPage(editingMenuDisplayed);
        } else {
            jLabel1.setVisible(false);
            editingPanel.setVisible(false);
            editingMenuDisplayed = false;
            pm.updateEditingModeForPage(editingMenuDisplayed);
        }
        
    }
    
    private void updatePageName(String textFieldEntry, PageManager pages) {
        String newText = "    " + textFieldEntry;
        pages.updatePageName(newText);
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                WebSketchProject2 wsp = new WebSketchProject2();
                wsp.initializeGUI();
            }
        });
    }
     
}
