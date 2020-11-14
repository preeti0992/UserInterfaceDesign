package onrdollar;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import lx.interaction.dollar.*;
import lx.interaction.touch.*;
import websketchproject.EditMenu;
import websketchproject.PageElement;

/*
<applet code ="HelloDollar.class" width=500 height=500></applet>
*/

//public class HelloDollar extends Applet implements MouseListener, MouseMotionListener, DollarListener
public class HelloDollar extends JComponent implements MouseListener, MouseMotionListener, DollarListener
{
	int x;
	int y;
	int state;
	
	Dollar dollar = new Dollar(Dollar.GESTURES_DEFAULT);
	String name = "";
        int[] bounds;
	double score = 0;
	boolean ok = false;;
        
        String lastAction;
        PageElement lastDrawnItem;
        String lastRecognizedShape = ""; 
        Vector lastShapePoints;
	
	Image offScreen;
        
        private static String UNDO_ACTION = "UNDO";
        private static String DELETE_ACTION = "DELETE";
        private static String TEXT_EDIT_ACTION = "TEXT EDIT";
        private static String HEADER_EDIT_ACTION = "HEADER EDIT";
        
        private String TEXT_TYPE = "TEXT";
        private String HEADER_TYPE = "HEADER";
        
        private JScrollPane parentDisplay = new JScrollPane();
        private boolean editingEnabled;
        
        private JPanel parentEditingPanel;
        
        /**
     * Class constructor for PageComponent
     */
        public HelloDollar() {
            // Adds listeners that will be required for
            // page to function correctly 
            addMouseListener(this);
            addMouseMotionListener(this);
            init();

            setBackground(Color.WHITE);
            setVisible(true);        
        }
	
	public void init() 
	{
		//offScreen = createImage(getSize().width, getSize().height);
			
		addMouseListener(this);
		addMouseMotionListener(this);
		
		dollar.setListener(this);
		dollar.setActive(true);
	}
        
        public void setEditingPanel(JPanel panel) {
            this.parentEditingPanel = panel;
        }
        
        public void setParentInfo(JScrollPane parent) {
            this.parentDisplay = parent;
        }
	
	public void mouseEntered(MouseEvent e) //mouse entered canvas
	{	}
	
	public void mouseExited(MouseEvent e) //mouse left canvas
	{	}
	
	public void mouseClicked(MouseEvent e) //mouse pressed-depressed (no motion in between), if there's motion -> mouseDragged
	{   }
		
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            g.setColor(Color.WHITE); 
            g.fillRect(0, 0, getWidth(), getHeight());
            g.setColor(Color.BLACK);
            g.drawString("[" + x + " " + y + "] [" + state + "]", 10, 20);
            g.drawString(lastRecognizedShape, 10, 60);
            dollar.render(g);
            dollar.renderAllShapes(g);
        }
        
        public void updateEditingStatus(boolean editStatus) {
            editingEnabled = editStatus;
        }
        
        public boolean getEditingStatus() {
            return editingEnabled;
        }
        
	public void update(MouseEvent e)
	{	
		x = e.getX();
		y = e.getY();
	
                repaint();
		e.consume();
	}	

	public void mousePressed(MouseEvent e) 
	{
                if (ok) {
                    ok = false;
                }
		state = 1;
		dollar.pointerPressed(e.getX(), e.getY());		
		update(e);
	}
	public void mouseReleased(MouseEvent e) 
	{ 
		state = 0;
		dollar.pointerReleased(e.getX(), e.getY());
                //dollar.printItems();
                checkForShape(getGraphics());
                update(e);
	}
	public void mouseMoved(MouseEvent e) 
	{  
		state = 0;
		update(e);
	}
	
	public void mouseDragged(MouseEvent e) 
	{		
		state = 2;
		dollar.pointerDragged(e.getX(), e.getY());				
		update(e);
	}
        
        public void checkForShape(Graphics g) {
            dollarDetected(dollar);
            if (ok) {
                    //int[] bounds = dollar.getBounds();
                    //g.drawString(lastRecognizedShape, 10, 60);
                    //g.drawString("bounds: " + bounds[0] + " " + bounds[1] + " " + bounds[2] + " " + bounds[3], 10, 120);
                    
                    if(editingEnabled) {
                        if(name.equals(TEXT_EDIT_ACTION)) {
                            openTextEditMenu(g);
                        } else if (name.equals(HEADER_EDIT_ACTION)) {
                            openHeaderEditMenu(g);
                        }
                    }
                    if (name.equals(DELETE_ACTION)) {
                        lastAction = name;
                        dollar.deleteObject();
                    } else if (name.equals(UNDO_ACTION)) {
                        undoLastAction(g);
                    } else {
                        if (isShapeName(name)) {
                            lastRecognizedShape = "gesture: " + name + " (" + score + ")";
                            lastAction = name;
                            PageElement elem = new PageElement(dollar.getPoints(), dollar.getName(), dollar.getBounds());
                            //System.out.println("ADDING OBJECT HERE!!!!!!!!!!!!!!!!");
                            //System.out.println("BOUNDS : " + bounds[0] + " " + bounds[1] + " " + bounds[2] + " " + bounds[3]);
                            dollar.addPageObject(elem);
                            //System.out.println(dollar.allPageObjects.size());
                            //dollar.renderShape(g, dollar.getName(), dollar.getBounds());
                        }
                    }
                }
            dollar.clear();
        }
        
        private boolean isShapeName(String name) {
            String DIV_TYPE = "DIV";
            String TEXT_BOX_TYPE = "TEXT BOX";
            String CHECK_BOX_TYPE = "CHECK BOX";
            String IMAGE_TYPE = "IMAGE";
            String BUTTON_TYPE = "BUTTON";
            
            return (name.equals(DIV_TYPE) || 
                    name.equals(TEXT_BOX_TYPE) ||
                    name.equals(CHECK_BOX_TYPE) ||
                    name.equals(IMAGE_TYPE) ||
                    name.equals(BUTTON_TYPE));
        }
        
        private void openHeaderEditMenu(Graphics g) {
            int elementIndex = dollar.getTextElementByBounds();
            if (elementIndex >= 0) {
                parentEditingPanel.removeAll();
                PageElement elem = dollar.getElementAtIndex(elementIndex);
                if (!elem.editingMenuSet()) {
                    EditMenu em = new EditMenu(parentEditingPanel);
                    em.setMenuType(HEADER_TYPE);
                    dollar.addEditMenuAtIndex(elementIndex, em);
                    dollar.updateElementAtIndex(elementIndex);
                    elem = dollar.getElementAtIndex(elementIndex);
                    JPanel editDisplayPanel = elem.getEditMenu().getHeaderMenu();
                    parentEditingPanel.add(editDisplayPanel);
                    parentEditingPanel.validate();
                    parentEditingPanel.repaint();
                    em.setParentDisplay(this);
                } else if (elem.getEditMenu().isHeaderType()) {
                    dollar.updateElementAtIndex(elementIndex);
                    elem = dollar.getElementAtIndex(elementIndex);
                    JPanel editDisplayPanel = elem.getEditMenu().getHeaderMenu();
                    parentEditingPanel.add(editDisplayPanel);
                    parentEditingPanel.validate();
                    parentEditingPanel.repaint();
                }
            }
        }
        
        private void openTextEditMenu(Graphics g) {
            int elementIndex = dollar.getTextElementByBounds();
            if (elementIndex >= 0) {
                parentEditingPanel.removeAll();
                PageElement elem = dollar.getElementAtIndex(elementIndex);
                if (!elem.editingMenuSet()) {
                    EditMenu em = new EditMenu(parentEditingPanel);
                    em.setMenuType(TEXT_TYPE);
                    dollar.addEditMenuAtIndex(elementIndex, em);
                    dollar.updateElementAtIndex(elementIndex);
                    elem = dollar.getElementAtIndex(elementIndex);
                    JPanel editDisplayPanel = elem.getEditMenu().getTextMenu();
                    parentEditingPanel.add(editDisplayPanel);
                    parentEditingPanel.validate();
                    parentEditingPanel.repaint();
                    em.setParentDisplay(this);
                } else if (elem.getEditMenu().isTextType()) {
                    dollar.updateElementAtIndex(elementIndex);
                    elem = dollar.getElementAtIndex(elementIndex);
                    JPanel editDisplayPanel = elem.getEditMenu().getTextMenu();
                    parentEditingPanel.add(editDisplayPanel);
                    parentEditingPanel.validate();
                    parentEditingPanel.repaint();
                }
            }
        }
        
        private void undoLastAction(Graphics g) {
            //System.out.println(lastAction);
            if (lastAction.equals(DELETE_ACTION)) {
                dollar.undoDeleteElement(g);
            } else if ((!lastAction.equals(UNDO_ACTION) && (!lastAction.equals("")))){
                dollar.undoDraw();
            }
        }
        
        private void updatePage(Graphics g) {
            if (name.equals(DELETE_ACTION)) {
                dollar.deleteObject();
            }
        }
        
        public void clearPage() {
            dollar.clear();
            dollar.clearPageElements();
            repaint();
        }
	
        /**
	public void paint(Graphics g)
	{
		//g.drawLine(0, y, getWidth(), y);
		//g.drawLine(x, 0, x, getHeight());

		
                dollar.renderAllShapes(g);
	}**/
	
	public void dollarDetected(Dollar dollar)
	{
		score = dollar.getScore();
		name = dollar.getName();
		
		ok = score > 0.80;
	}
}