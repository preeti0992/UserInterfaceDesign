/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websketchproject;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.Border;
import onrdollar.HelloDollar;

/**
 *
 * @author meghn
 */
public class PageManager {
    JScrollPane pagePanel;
    JPanel mainPanel;
    private int pageNumber = 1;
    boolean isAlreadyOneClick = false;
    private JLabel currentPage;
    
    private JScrollPane drawingTab;
    boolean firstPageCreated = false;
    
    private DrawnPage currentDisplay;
    
    private ArrayList<DrawnPage> drawnPages = new ArrayList<DrawnPage>(); 
    
    private JPanel editMenuPanel;
    
    public PageManager(JScrollPane pages, JPanel view) {
        this.pagePanel = pages;
        this.mainPanel = view;
    }
    
    public void clearPage() {
        if (currentDisplay != null) {
            currentDisplay.clearPageContents();
        }
    }
    
    public void setEditPanel(JPanel panel) {
        this.editMenuPanel = panel;
    }
    
    public void addFirstPage(HelloDollar hd, JScrollPane drawingPane) {
        addPageToPane();
        String pageIdentifier = "PAGE_INDEX_" + (pageNumber-1);
        
        this.drawingTab = drawingPane;
        
        drawingTab.setViewportView(hd);
            
        drawingTab.revalidate();
        drawingTab.repaint();

        DrawnPage newPage = new DrawnPage(hd, pageIdentifier);
        addToSavedDrawnPages(newPage);
        currentDisplay = newPage;
        firstPageCreated = true;
    }
    
    public void updateEditingModeForPage(boolean newStatus) {
        currentDisplay.getDisplay().updateEditingStatus(newStatus);
    }
    
    public void addToSavedDrawnPages(DrawnPage newScreen) {
        drawnPages.add(newScreen);
    }
    
    public void updateDrawingTabPane(JScrollPane pane) {
        this.drawingTab = pane; 
    }
    
    public void addPageToPane() {
        JLabel label = new JLabel("    PAGE " + pageNumber);
        String pageIdentifier = "PAGE_INDEX_" + pageNumber;
        label.setName(pageIdentifier);
        label.setMinimumSize(new Dimension(245, 25));
        label.setPreferredSize(new Dimension(245, 25));
        label.setMaximumSize(new Dimension(500, 25));
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        label.setBorder(border);
        
        label.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (isAlreadyOneClick) {
                    System.out.println("double click");
                    //updatePageName(label);
                    String labelName = label.getName();
                    //System.out.println(label.getName());
                    updateDisplayScreen(labelName);
                    updateLastSelectedPage(label);
                    updateSelectedPage();
                    isAlreadyOneClick = false;
                } else {
                    isAlreadyOneClick = true;
                    Timer t = new Timer("doubleclickTimer", false);
                    t.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            isAlreadyOneClick = false;
                        }
                    }, 500);
                }
            }
        });
        
        if (currentPage != null) {
            updateLastSelectedPage(label);
        } else {
            currentPage = label;
        }
        updateSelectedPage();
        
        mainPanel.add(label);
        pageNumber++;
        
        //pc.revalidate();
        //pc.repaint();
        
        // Web reference used:
        pagePanel.setViewportView(mainPanel);
        
        pagePanel.revalidate();
        pagePanel.repaint();
        
        if(firstPageCreated) {
            updateDisplayScreenNewPage(pageIdentifier);
        }
    }
    
    private void updateDisplayScreenNewPage(String labelName)
    {
        HelloDollar newHD = new HelloDollar();
        if (drawingTab != null) {
            drawingTab.setViewportView(newHD);
            
            drawingTab.revalidate();
            drawingTab.repaint();
            
            newHD.setEditingPanel(editMenuPanel);
            
            DrawnPage newPage = new DrawnPage(newHD, labelName);
            addToSavedDrawnPages(newPage);
            boolean editingStatus = false;
            if (currentDisplay != null) {
                editingStatus = currentDisplay.getDisplay().getEditingStatus();
            }
            currentDisplay = newPage;
            updateEditingModeForPage(editingStatus);
        }
    }
    
    private void updateDisplayScreen(String labelName)
    {
        for (DrawnPage page : drawnPages) {
            if(page.getPageName().equals(labelName)) {
                drawingTab.setViewportView(page.getDisplay());
                
                drawingTab.revalidate();
                drawingTab.repaint();
                
                boolean editingStatus = currentDisplay.getDisplay().getEditingStatus();
                currentDisplay = page;
                updateEditingModeForPage(editingStatus);
            }
        }
    }
    
    private void updateLastSelectedPage(JLabel label) {
        currentPage.setOpaque(false);
        currentPage.setForeground(Color.BLACK);
        currentPage = label;
    }
    
    private void updateSelectedPage() {
        currentPage.setOpaque(true);
        currentPage.setBackground(Color.GRAY);
        currentPage.setForeground(Color.WHITE);
    }
    
    public void updatePageName(String newText) {
        currentPage.setText(newText);
        pagePanel.setViewportView(mainPanel);
        
        pagePanel.revalidate();
        pagePanel.repaint();
    }
}
