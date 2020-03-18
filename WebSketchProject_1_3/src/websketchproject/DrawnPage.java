/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websketchproject;

import onrdollar.HelloDollar;

/**
 *
 * @author meghn
 */
public class DrawnPage {
    private HelloDollar hd;
    private String pageName;
    
    public DrawnPage(HelloDollar h, String p) {
        this.hd = h;
        this.pageName = p;
    }
    
    public HelloDollar getDisplay() {
        return this.hd;
    }
    
    public String getPageName() {
        return this.pageName;
    }
            
    public void setPageName(String newName) {
        this.pageName = newName;
    }
    
    public void clearPageContents() {
        this.hd.clearPage();
    }
}
