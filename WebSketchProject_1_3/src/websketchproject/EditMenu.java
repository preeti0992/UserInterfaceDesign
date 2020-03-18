/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package websketchproject;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractListModel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import onrdollar.HelloDollar;
import websketchproject.PageElement;

/**
 *
 * @author meghn
 */
public class EditMenu extends JComponent {
    private javax.swing.JCheckBox boldCheckBox;
    private javax.swing.JCheckBox italicsCheckBox;
    private javax.swing.JCheckBox underlineCheckBox;
    
    private javax.swing.JList<String> fontSize;
    private javax.swing.JList<String> fontFamily;
    
    private javax.swing.JList<String> headerType;
    private javax.swing.JList<String> headerAlignment;
    
    private javax.swing.JLabel headerLabel;
    private javax.swing.JLabel headerAlignmentLabel;
    
    private JTextField headerField;
    private JTextArea textField;
    
    private JLabel headerFieldLabel;
    private JLabel textFieldLabel;
    
    private javax.swing.JLabel fontSizeLabel;
    private javax.swing.JLabel fontFamilyLabel;
    private javax.swing.JLabel fontColorLabel;
    
    private javax.swing.JPanel textColorPanel;
    private javax.swing.JPanel textMenuPanel;
    
    private javax.swing.JPanel headerMenuPanel;
    
    private javax.swing.JButton textSaveButton;
    private javax.swing.JButton headerSaveButton;
    
    private Color textColor = Color.BLUE;
    
    private JPanel editingPanel;
    
    private String menuType = "";
    
    private String textType = "TEXT";
    private String headerStringType = "HEADER";
    
    private static int header1FontSize = 24;
    private static int header2FontSize = 18;
    private static int header3FontSize = 14;
    
    private String header1String = "Heading 1";
    private String header2String = "Heading 2";
    private String header3String = "Heading 3";
    
    private int textFontSize = 14;
    private String textFontType = "TimesRoman";
    private String headerTextAlignment = "Center";
    
    private String headerFieldTextEntry = "";
    private String textFieldEntry = "";
    
    private int fontStyleB = Font.BOLD;
    private int fontStyleI = Font.ITALIC;
    private int fontStyleBI = (Font.BOLD | Font.ITALIC);
    
    private boolean needsUnderline = false;
    
    private String fontStyleU = "UNDERLINE";
    private String fontStyleBU = "BOLD_UNDERLINE";
    private String fontStyleIU = "ITALICS_UNDERLINE";
    private String fontStyleBIU = "BOLD_ITALICS_UNDERLINE";
    
    private int textFontStyle = Font.PLAIN;
    
    private HelloDollar hd;
    
    public EditMenu(JPanel parent) {
        initTextMenu();
        initHeaderMenu();
        
        this.editingPanel = parent;
    }
    
    public String getTextEntry() {
        return textFieldEntry;
    }
    
    public String getHeaderEntry() {
        return headerFieldTextEntry; 
    }
    
    public int getFontStyle() {
        return this.textFontStyle;
    }
    
    public boolean isTextType() {
        if ((menuType.equals("")) || (menuType.equals(textType))) {
            return true;
        }
        return false;
    }
    
    public boolean isHeaderType() {
        if ((menuType.equals("")) || (menuType.equals(headerStringType))) {
            return true;
        }
        return false;
    }
    
    public int getFontSize() {
        return textFontSize;
    }
    
    public String getFontType() {
        return textFontType;
    }
    
    public void setMenuType(String type) {
        this.menuType = type;
    }
    
    public JPanel getTextMenu() {
        return textMenuPanel;
    }
    
    public JPanel getHeaderMenu() {
        return headerMenuPanel;
    }
    
    public void setParentDisplay(HelloDollar h) {
        this.hd = h;
    }
    
    public void initHeaderMenu() {
        headerMenuPanel = new JPanel();
        headerMenuPanel.setLayout(new BoxLayout(headerMenuPanel, BoxLayout.Y_AXIS));
        
        JPanel headerFieldPanel = new JPanel();
        headerFieldPanel.setLayout(new FlowLayout());
        
        headerFieldLabel = new javax.swing.JLabel();
        headerFieldLabel.setText("Header Text: ");
        headerField = new javax.swing.JTextField();
        headerField.setForeground(Color.BLACK);
        headerField.setText("");
        headerField.setMinimumSize(new Dimension(150, 30));
        headerField.setPreferredSize(new Dimension(150, 30));
        headerField.setMaximumSize(new Dimension(150, 30));
        
        headerFieldPanel.add(headerFieldLabel);
        headerFieldPanel.add(headerField);
        
        headerType = new javax.swing.JList<>();
        
        headerType.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {"Heading 1", "Heading 2", "Heading 3"};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        
        JScrollPane headerList = new JScrollPane(headerType, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        headerList.setPreferredSize(new Dimension(200, 100));
        headerList.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerAlignment = new javax.swing.JList<>();
        headerAlignment.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {"Left", "Center", "Right"};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        
        headerLabel = new javax.swing.JLabel();
        headerLabel.setText("Header Type");
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerAlignmentLabel = new javax.swing.JLabel();
        headerAlignmentLabel.setText("Header Alignment");
        headerAlignmentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JScrollPane headerAlignmentList = new JScrollPane(headerAlignment, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        headerAlignmentList.setPreferredSize(new Dimension(200, 100));
        headerAlignmentList.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerSaveButton = new JButton();
        headerSaveButton.setText("Save");
        headerSaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveHeaderInfo();
            }
        });
        headerSaveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        headerMenuPanel.add(headerFieldPanel);
        headerMenuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerMenuPanel.add(headerLabel);
        headerMenuPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        headerMenuPanel.add(headerList);
        headerMenuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerMenuPanel.add(headerAlignmentLabel);
        headerMenuPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        headerMenuPanel.add(headerAlignmentList);
        headerMenuPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerMenuPanel.add(headerSaveButton);
    }
    
    private void saveHeaderInfo() {
        int index = headerType.getSelectedIndex();
        if (index >= 0) {
            String selectedHeaderType;
            AbstractListModel<String> headerTypeModel = (AbstractListModel<String>) headerType.getModel();
            selectedHeaderType = headerTypeModel.getElementAt(index);
            if (selectedHeaderType.equals(header1String)) {
                textFontSize = header1FontSize;
            } else if (selectedHeaderType.equals(header2String)) {
                textFontSize = header2FontSize;
            } else if (selectedHeaderType.equals(header3String)) {
                textFontSize = header3FontSize;
            }
        }
        
        String textEntry = headerField.getText();
        if(!textEntry.equals("")) {
            headerFieldTextEntry = textEntry; 
        }
        
        int alignmentIndex = headerAlignment.getSelectedIndex();
        if (alignmentIndex >= 0) {
            String alignmentType;
            AbstractListModel<String> alignmentModel = (AbstractListModel<String>) headerAlignment.getModel();
            alignmentType = alignmentModel.getElementAt(index);
            headerTextAlignment = alignmentType;
        }
        
        editingPanel.removeAll();
        editingPanel.validate();
        editingPanel.repaint();
        
        if(hd != null) {
            hd.repaint();
        }
    }
    
    public boolean getUnderlineStatus() {
        return needsUnderline;
    }
    
    private void saveTextInfo() {
        String textEntry = textField.getText();
        if(!textEntry.equals("")) {
            textFieldEntry = textEntry; 
        }
        
        int index = fontSize.getSelectedIndex();
        if (index >= 0) {
            String selectedFontSize;
            AbstractListModel<String> fontSizeModel = (AbstractListModel<String>) fontSize.getModel();
            selectedFontSize = fontSizeModel.getElementAt(index);
            int selectedSize = Integer.parseInt(selectedFontSize);
            textFontSize = selectedSize;
        }
        
        int styleIndex = 0;
        if(boldCheckBox.isSelected()) {
            styleIndex += 1; 
        }
        if (italicsCheckBox.isSelected()) {
            styleIndex += 3;
        }
        if (underlineCheckBox.isSelected()) {
            needsUnderline = true;
            styleIndex += 5;
        } else {
            needsUnderline = false;
        }
        
        if (styleIndex == 1) {
            textFontStyle = fontStyleB;
        } else if (styleIndex == 3) {
            textFontStyle = fontStyleI;
        } else if (styleIndex == 4) {
            textFontStyle = fontStyleBI;
        } else if (styleIndex == 6) {
            textFontStyle = fontStyleB;
        } else if (styleIndex == 8) {
            textFontStyle = fontStyleI;
        } else if (styleIndex == 9) {
            textFontStyle = fontStyleBI;
        }
        
        int fontIndex = fontFamily.getSelectedIndex();
        if (fontIndex >= 0) {
            String selectedFont;
            AbstractListModel<String> fontFamilyModel = (AbstractListModel<String>) fontFamily.getModel();
            selectedFont = fontFamilyModel.getElementAt(fontIndex);
            //"Arial", "Lucida Sans", "Times New Roman"};
            if (selectedFont.equals("Arial")) {
                textFontType = "Arial";
            } else if (selectedFont.equals("Lucida Sans")) {
                textFontType = "LucidaSans";
            }
        }
        
        editingPanel.removeAll();
        editingPanel.validate();
        editingPanel.repaint();
        
        if(hd != null) {
            hd.repaint();
        }
    }
    
    public void initTextMenu() {
        textMenuPanel = new javax.swing.JPanel(); 
        textMenuPanel.setLayout(new BoxLayout(textMenuPanel, BoxLayout.Y_AXIS));
        
        boldCheckBox = new javax.swing.JCheckBox();
        italicsCheckBox = new javax.swing.JCheckBox();
        underlineCheckBox = new javax.swing.JCheckBox();
        
        textFieldLabel = new javax.swing.JLabel();
        textFieldLabel.setText("Text:");
        textFieldLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        textField = new javax.swing.JTextArea();
        textField.setLineWrap(true);
        textField.setWrapStyleWord(true);
        textField.setForeground(Color.BLACK);
        textField.setText("");
        
        JScrollPane textFieldScroll = new JScrollPane(textField, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        textFieldScroll.setPreferredSize(new Dimension(200, 75));
        
        JPanel textEntryPanel = new JPanel();
        textEntryPanel.setLayout(new BoxLayout(textEntryPanel, BoxLayout.Y_AXIS));
        textEntryPanel.add(textFieldLabel);
        textEntryPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        textEntryPanel.add(textFieldScroll);
        
        fontSize = new javax.swing.JList<>();
        
        fontSize.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {"12", "13", "14", "15"};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        fontSize.setPreferredSize(new Dimension(100, 60));
        fontSize.setMaximumSize(new Dimension(100, 60));
        
        JScrollPane fontSizeList = new JScrollPane(fontSize, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        fontSizeList.setPreferredSize(new Dimension(75, 75));
        
        fontFamily = new javax.swing.JList<>();
        fontFamily.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = {"Arial", "Lucida Sans", "Times New Roman"};
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        
        JScrollPane fontFamilyList = new JScrollPane(fontFamily, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        fontFamilyList.setPreferredSize(new Dimension(75, 75));
        
        fontSizeLabel = new javax.swing.JLabel();
        fontSizeLabel.setText("Font Size");
        
        fontFamilyLabel = new javax.swing.JLabel();
        fontFamilyLabel.setText("Font Type");
        fontFamilyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        fontColorLabel = new javax.swing.JLabel();
        
        textColorPanel = new javax.swing.JPanel();
        
        boldCheckBox.setText("Bold");
        italicsCheckBox.setText("Italics");
        underlineCheckBox.setText("Underline");
        
        JPanel fontSizePanel = new JPanel();
        fontSizePanel.setLayout(new BoxLayout(fontSizePanel, BoxLayout.Y_AXIS));
        fontSizePanel.add(fontSizeLabel);
        fontSizePanel.add(Box.createRigidArea(new Dimension(0, 5)));
        fontSizePanel.add(fontSizeList);
        
        JPanel fontFamilyPanel = new JPanel();
        fontFamilyPanel.setLayout(new BoxLayout(fontFamilyPanel, BoxLayout.Y_AXIS));
        fontFamilyPanel.add(fontFamilyLabel);
        fontFamilyPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        fontFamilyPanel.add(fontFamilyList);
        fontFamilyPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JPanel fontStyle = new JPanel();
        fontStyle.setLayout(new BoxLayout(fontStyle, BoxLayout.Y_AXIS));
        fontStyle.add(boldCheckBox);
        fontStyle.add(italicsCheckBox);
        fontStyle.add(underlineCheckBox);
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.add(fontSizePanel);
        topPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        topPanel.add(fontStyle);
        
        textSaveButton = new JButton();
        textSaveButton.setText("Save");
        textSaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveTextInfo();
            }
        });
        textSaveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        textMenuPanel.add(textEntryPanel);
        textMenuPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        textMenuPanel.add(topPanel);
        textMenuPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        textMenuPanel.add(fontFamilyPanel);
        textMenuPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        textMenuPanel.add(textSaveButton);
    }
    
    public Color getTextColor() {
        return this.textColor;
    }
}
