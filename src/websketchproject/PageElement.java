/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websketchproject;

import java.util.Vector;

/**
 *
 * @author meghn
 */
public class PageElement {
  
    private Vector points;
    private String elementType;
    private int[] bounds;

    private int x1;
    private int y1;
    private int x2;
    private int y2;
    
    private EditMenu ema;
    
    public PageElement(Vector points, String elem, int[] elemBounds) 
    {
        this.points = points;
        this.elementType = elem;
        this.bounds = elemBounds;
        
        x1 = elemBounds[0];
        y1 = elemBounds[1];
        x2 = elemBounds[2];
        y2 = elemBounds[3];
    }
    
    public void setEditMenu(EditMenu newEM) {
        this.ema = newEM;
    }
    
    public EditMenu getEditMenu() {
        return this.ema;
    }
    
    public boolean editingMenuSet() {
        if (ema != null) {
            return true;
        }
        return false;
    }
    
    public void updateElementProperties() {
        //will go through editing menu class and update accordingly
        System.out.println("Reached for elem with bounds " + x1 + " " + y1 + " " + x2 + " " + y2);
    }
    
    public int[] getBounds() {
        int[] newBounds = {x1, y1, x2, y2};
        return newBounds; 
    }
    
    public int getX1() {
        return x1;
    }
    
    public int getY1() {
        return y1;
    }
    
    public int getX2() {
        return x2;
    }
    
    public int getY2() {
        return y2;
    }
    
    public Vector getPoints() {
        return points;
    }
    
    public String getElementType() {
        return elementType;
    }
}

