package lx.interaction.dollar;

/*
 *	Java / Java ME port of the $1 Gesture Recognizer by  
 *	Jacob O. Wobbrock, Andrew D. Wilson, Yang Li.
 * 
 *	A quick port that needs to be polished, documented and optimized...!
 *	Send me an e-mail (address can be found at olwal.com) if you'd like to get an update when the library is updated, 
 *  and feel free to send any updates or changes you make!  
 *
 *	@author Alex Olwal
 *
 *	@version 0.1
 *
 *	@see http://depts.washington.edu/aimgroup/proj/dollar/
 *
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.font.TextAttribute;
import java.awt.geom.RoundRectangle2D;
import java.text.AttributedString;
import java.util.ArrayList;

import lx.interaction.touch.TouchListener;
import websketchproject.EditMenu;
import websketchproject.PageElement;

public class Dollar implements TouchListener
{
	protected int x, y;
	protected int state;
		
	protected int _key = -1;
	
	protected boolean gesture = true; 
	protected Vector points = new Vector(1000);
	
	protected Recognizer recognizer;
	protected Result result = new Result("no gesture", 0, -1);
	
	protected boolean active = false;
	
	protected DollarListener listener = null;

	public static final int GESTURES_DEFAULT = 1;
	public static final int GESTURES_SIMPLE = 2;
	public static final int GESTURES_CIRCLES = 3;
        
        private static String DIV_TYPE = "DIV";
        private static String TEXT_BOX_TYPE = "TEXT BOX";
        private static String CHECK_BOX_TYPE = "CHECK BOX";
        private static String IMAGE_TYPE = "IMAGE";
        private static String BUTTON_TYPE = "BUTTON";
        
        private static String UNDO_ACTION = "UNDO";
        private static String DELETE_ACTION = "DELETE";
	
	protected int gestureSet;
        
        private ArrayList<PageElement> allPageObjects = new ArrayList<PageElement>();
        private PageElement lastDrawnItem;
        private ArrayList<PageElement> deletedItems = new ArrayList<PageElement>();
	
	public Dollar()
	{
		this(GESTURES_SIMPLE);
	}
	
	public Dollar(int gestureSet)
	{
		this.gestureSet = gestureSet;
		recognizer = new Recognizer(gestureSet);
	}
        
        public PageElement getElementAtIndex(int index) {
            return allPageObjects.get(index);
        }
        
        public void updateElementAtIndex(int index) {
            allPageObjects.get(index).updateElementProperties();
        }
        
        public void addEditMenuAtIndex(int index, EditMenu em) {
            allPageObjects.get(index).setEditMenu(em);
        }
	
	public void setListener(DollarListener listener)
	{
		this.listener = listener;
	}
        
        public void clearPageElements() {
            allPageObjects.clear();
            deletedItems.clear();
        }
	
	public void render(Graphics g) 
	{
                Graphics2D g2 = (Graphics2D) g;
                g2.setStroke(new BasicStroke(3));
                g.setColor(Color.BLACK);
		if (!active)
			return;
		
		Point p1, p2;
				
//		g.setColor(0x999999);
		
		for (int i = 0; i < points.size()-1; i++)
		{
			p1 = (Point)points.elementAt(i);
			p2 = (Point)points.elementAt(i+1);
			g2.drawLine((int)p1.X, (int)p1.Y, (int)p2.X, (int)p2.Y);
		}	
	}
        
        public void renderAllShapes(Graphics g) {
            for(PageElement elem : allPageObjects) {
                String name = elem.getElementType();
                int[] b = elem.getBounds();
                renderShape(g, name, b, elem);
            }
        }
        
        public void undoDeleteElement(Graphics g) {
            int index = 0;
            for(PageElement elem : deletedItems) {
                //System.out.println("INDEX : " + index);
                allPageObjects.add(elem);
                index++;
            }
            deletedItems.clear();
        }
        
        public void undoDraw() {
            allPageObjects.remove(allPageObjects.size()-1);
        }
        
        public void renderShape(Graphics g, String name, int[] b, PageElement elem) 
        {
            if(!active) {
                return;
            }
            
            if (name.equals(DIV_TYPE)) {
                renderDiv(g, b);
            } else if (name.equals(TEXT_BOX_TYPE)) {
                renderTextBox(g, b, elem);
            } else if (name.equals(BUTTON_TYPE)) {
                renderButton(g, b);
            } else if (name.equals(IMAGE_TYPE)) {
                renderImageBox(g, b);
            }
        }
        
        public void renderImageBox(Graphics g, int[] b) {
            g.setColor(Color.BLACK);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2));
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            
            int width = b[2] - b[0];
            int height = b[3] - b[1];
            g2.drawRect(b[0], b[1], width, height);
            
            int margin = 25;
            int midY = b[1] + (height/2);
            g2.drawLine(b[0], b[1], b[0] + margin, midY);
            g2.drawLine(b[0], b[3], b[0] + margin, midY);
            
            int margin2 = margin + 15;
            
            g2.drawString("{ Image }", b[0]+margin2, midY);
        }
        
        public void renderDiv(Graphics g, int[] b) {
            g.setColor(Color.BLACK);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(3));
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            
            int width = b[2] - b[0];
            int height = b[3] - b[1];
            g2.drawRect(b[0], b[1], width, height);
        }
        
        public void renderTextBox(Graphics g, int[] b, PageElement elem) {
            EditMenu elemMenu = elem.getEditMenu();
            
            Color textColor = Color.BLUE;
            int fontSize = 14;
            String fontType = "TimesRoman";
            String textString = "Text goes here";
            
            if (elemMenu != null) {
                textColor = elemMenu.getTextColor();
                fontSize = elemMenu.getFontSize();
                fontType = elemMenu.getFontType();
                
                String temp = elemMenu.getHeaderEntry();
                if (!temp.equals("")) {
                    textString = temp;
                }
            }
            
            g.setColor(textColor);
            Graphics2D g2 = (Graphics2D) g;
            BasicStroke dashedStroke = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g2.setStroke(dashedStroke);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            
            //System.out.println("Bounds: " + b[0] + " " + b[1] + " " + b[2] + " " + b[3]);
            
            int width = b[2] - b[0];
            int height = b[3] - b[1];
            
            g2.drawRect(b[0], b[1], width, height);
            int halfWidth = width/2;
            int halfHeight = height/2;
            int stringWidth = g.getFontMetrics().stringWidth(textString);
            halfWidth -= (stringWidth/2);
            
            int fontFamily = Font.PLAIN;
            
            if ((elemMenu != null) && (elemMenu.isTextType())) {
                fontFamily = elemMenu.getFontStyle();
                
            }
            
            Font textFont = new Font(fontType, fontFamily, fontSize);
            
            //System.out.println(fontType);
            
            if ((elemMenu != null) && (elemMenu.isTextType()) && (elemMenu.getUnderlineStatus())) {
                AttributedString as = new AttributedString(textString);
                as.addAttribute(TextAttribute.FONT, textFont);
                as.addAttribute(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
                Graphics2D g2D = (Graphics2D)g.create();
                g2D.setFont(textFont);
                g2D.drawString(as.getIterator(), b[0]+halfWidth, b[1]+halfHeight);
            } else {
                Graphics2D g2D = (Graphics2D)g.create();
                g2D.setFont(textFont);
                g2D.drawString(textString, b[0]+halfWidth, b[1]+halfHeight);
            }
        }
        
        public void renderButton(Graphics g, int[] b) {
            g.setColor(Color.BLACK);
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2));
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
            
            int width = b[2] - b[0];
            int height = b[3] - b[1];
            //g.setColor(Color.BLUE);
            //g.drawRect(bounds[0], bounds[1], width, height);
            //g.drawString("Text goes here", bounds[0]+20, bounds[1]+20);
            
            RoundRectangle2D roundedRectangle = new RoundRectangle2D.Float(b[0], b[1], width, height, 10, 10);
            g2.draw(roundedRectangle);
            
            int halfWidth = width/2;
            int halfHeight = height/2;
            int stringWidth = g.getFontMetrics().stringWidth("Button");
            halfWidth -= (stringWidth/2);
            
            g2.drawString("Button", b[0]+halfWidth, b[1]+halfHeight);
        }
        
        public int getTextElementByBounds() {
            int index = -1;
            int[] bounds = getBounds();
            int checkx1 = bounds[0];
            int checky1 = bounds[1];
            int checkx2 = bounds[2];
            int checky2 = bounds[3];
            
            int tempIndex = 0;
            for (PageElement elem : allPageObjects) {
                int x1 = elem.getX1();
                int y1 = elem.getY1();
                int x2 = elem.getX2();
                int y2 = elem.getY2();
                
                if ((checkx1 >= x1) && (checkx2 <= x2)) {
                    if ((checky1 >= y1) && (checky2 <= y2)) {
                        if (elem.getElementType().equals(TEXT_BOX_TYPE)) {
                            //System.out.println("T bounds: " + bounds[0] + " " + bounds[1] + " " + bounds[2] + " " + bounds[3]);
                            //System.out.println("elem bounds: " + b2[0] + " " + b2[1] + " " + b2[2] + " " + b2[3]);
                            index = tempIndex;
                        }
                    }
                }
            }
            tempIndex++;
            
            return index;
        }
        
        public void deleteObject() {
            deletedItems.clear();
            
            int[] bounds = getBounds();
            int delx1 = bounds[0];
            int dely1 = bounds[1];
            int delx2 = bounds[2];
            int dely2 = bounds[3];
            
            int delMidX = delx1 + ((bounds[2] - bounds[0])/2);
            int delMidY = dely1 + ((bounds[3] - bounds[1])/2);
            
            //System.out.println(delMidX + " " + delMidY);
            
            int index = 0;
            int deletionIndex = -1; 
            boolean divDeleted = false;
            
            for(PageElement elem : allPageObjects) {
                int x1 = elem.getX1();
                int y1 = elem.getY1();
                int x2 = elem.getX2();
                int y2 = elem.getY2();
                
                if ((dely1 <= y1) && (dely2 >= y2)) {
                    if((delMidX > x1) && (delMidX < x2)) {
                        if((delMidY > y1) && (delMidY < y2)) {
                            if (elem.getElementType().equals(DIV_TYPE)) {
                                divDeleted = true;
                                deletionIndex = index;
                            } else if (!divDeleted) {
                                deletionIndex = index;
                                //allPageObjects.remove(index);
                                System.out.println(deletionIndex);
                                //break;
                            }
                            //return true;
                        }
                    }
                }
                
                //System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
                index++;
            }
            
            if (divDeleted) {
                deleteDivElements(deletionIndex);
            } else if (deletionIndex >= 0) {
                //System.out.println("Reached: index = " + deletionIndex);
                PageElement elem = allPageObjects.get(deletionIndex);
                deletedItems.add(elem);
                allPageObjects.remove(deletionIndex);
            }
        }
        
        private void deleteDivElements(int divIndex) {
            PageElement elem = allPageObjects.get(divIndex);
            
            int boundX1 = elem.getX1();
            int boundY1 = elem.getY1();
            int boundX2 = elem.getX2();
            int boundY2 = elem.getY2();
            
            ArrayList<Integer> deletionList = new ArrayList<Integer>();
            
            //for (PageElement element : allPageObjects) {
            for (int index = 0; index < allPageObjects.size(); index++) {
                PageElement element = allPageObjects.get(index);
                
                int elemBoundX1 = element.getX1();
                int elemBoundY1 = element.getY1();
                int elemBoundX2 = element.getX2();
                int elemBoundY2 = element.getY2(); 
                
                if ((elemBoundX1 >= boundX1) && (elemBoundY1 >= boundY1)
                        && (elemBoundX2 <= boundX2) && (elemBoundY2 <= boundY2)) {
                    //deletionList.add(index);
                    deletedItems.add(element);
                    allPageObjects.remove(index);
                    index--;
                } 
            }
            
            //System.out.println("DELETED ITEMS : " + deletedItems.size());;
        }
	
	public void addPoint(int x, int y)
	{
		if (!active)
			return;
		
		points.addElement(new Point(x, y));
//		System.out.println(x + " " + y + " " + points.size());
	}	
	
	public void recognize()
	{
		if (!active)
			return;
		
		if (points.size() == 0) //the recognizer will crash if we try to process an empty set of points...
			return;
		
		result = recognizer.Recognize(points);		
//		points.removeAllElements();
		
		if (listener != null)
			listener.dollarDetected(this);
	}
        
        public void printItems() {
            int size = points.size();
            System.out.print("[");
            for(int i = 0; i < size; i++)
            {
                Point p = (Point) points.elementAt(i);
                int x = (int) p.X;
                int y = (int) p.Y;
                System.out.print(x + ", " + y + ", ");
            }
            System.out.println("]");
        }
        
        public Vector getPoints() {
            return points;
        }

	public Rectangle getBoundingBox()
	{
		return recognizer.boundingBox;
	}
	
	public int[] getBounds()
	{
		return recognizer.bounds;
	}
	
	public Point getPosition()
	{
		return recognizer.centroid;
	}
	
	public String getName()
	{		
		return result.Name;
	}
	
	public double getScore()
	{
		return result.Score;
	}

	public int getIndex()
	{
		return result.Index;
	}

	public void setActive(boolean state)
	{
		active = state;
	}
	
	public boolean getActive()
	{
		return active;
	}
        
        public void addPageObject(PageElement elem) {
            allPageObjects.add(elem);
        }
	
	public void pointerPressed(int x, int y)
	{
		clear();
	}
	
	public void pointerReleased(int x, int y)
	{
		recognize();
	}
	
	public void pointerDragged(int x, int y)
	{
		addPoint(x, y);
	}
	
	public void clear()
	{
		points.removeAllElements();
		result.Name = "";
		result.Score = 0;
		result.Index = -1;
	}
	
}


