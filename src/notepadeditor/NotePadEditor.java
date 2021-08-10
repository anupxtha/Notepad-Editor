/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notepadeditor;

import java.awt.Color;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

/**
 *
 * @author Anup-Xtha
 */
public class NotePadEditor implements ActionListener, WindowListener
{
    JMenuItem neww,open,save, saveas, cut, copy, paste, font, font_color, background_color;
    JTextArea textarea;
    JFrame jf, font_frame;
    JComboBox font_family, font_size, font_style;
    JButton ok;
    File file;
            
    public NotePadEditor() {
        try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        jf = new JFrame("*Untitled* - NotePad");
        jf.setSize(700,400);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setLocationRelativeTo(null);
        
        JMenuBar jmenubar = new JMenuBar();
        
        JMenu file = new JMenu("File");
        neww = new JMenuItem("New");
        neww.addActionListener(this);
        file.add(neww);
        
        open = new JMenuItem("Open");
        open.addActionListener(this);
        file.add(open);
        
        save = new JMenuItem("Save");
        save.addActionListener(this);
        file.add(save);
        
        saveas = new JMenuItem("Save As");
        saveas.addActionListener(this);
        file.add(saveas);
        
        jmenubar.add(file);
        
        JMenu edit = new JMenu("Edit");
        
        cut = new JMenuItem("Cut");
        cut.addActionListener(this);
        edit.add(cut);
        
        copy = new JMenuItem("Copy");
        copy.addActionListener(this);
        edit.add(copy);
        
        paste = new JMenuItem("Paste");
        paste.addActionListener(this);
        edit.add(paste);
        
        jmenubar.add(edit);
        
        JMenu format = new JMenu("Format");
        
        font = new JMenuItem("Font");
        font.addActionListener(this);
        format.add(font);
        
        font_color = new JMenuItem("Font Color");
        font_color.addActionListener(this);
        format.add(font_color);
        
        background_color = new JMenuItem("Background Color");
        background_color.addActionListener(this);
        format.add(background_color);
        
        jmenubar.add(format);
        
        jf.setJMenuBar(jmenubar);
        
        textarea = new JTextArea();
        JScrollPane scrollpane = new JScrollPane(textarea);
        scrollpane.setHorizontalScrollBarPolicy(scrollpane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollpane.setVerticalScrollBarPolicy(scrollpane.VERTICAL_SCROLLBAR_ALWAYS);
        jf.add(scrollpane);
        
        jf.addWindowListener(this);
        
        jf.setVisible(true);  
    }
    
    
    public static void main(String[] args) {
        new NotePadEditor();
    } 

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==neww){
            newwfile();
        }
        
        if(e.getSource()==open){
            openfile();
        }
        
        if(e.getSource()==save){
            savefile();
        }
        
        if(e.getSource()==saveas){
            saveasfile();
        }
        
        if(e.getSource()==cut){
            textarea.cut();
        }
        
        if(e.getSource()== copy){
            textarea.copy();
        }
        
        if(e.getSource()== paste){
            textarea.paste();
        }
        
        if(e.getSource() == font){
            openFontFrame();
        }
        
        if(e.getSource() == ok){
            setFontonTextArea();
        }
        
        if(e.getSource()== font_color){
            Color c = JColorChooser.showDialog(jf, "Choose Font Color", Color.black);
            textarea.setForeground(c);
        }
        
        if(e.getSource() == background_color){
            Color c = JColorChooser.showDialog(jf, "Choose Font Color", Color.white);
            textarea.setBackground(c);
        }
    }
    
    void setFontonTextArea(){
        String fontfamily = (String)font_family.getSelectedItem();
            String fontsize = (String)font_size.getSelectedItem();  /// 10,20, 30 will directly converted into integer 
            String fontstyle = (String)font_style.getSelectedItem();  /// plain, bold , italic can't be directly converted into integer so taking style variable
            
            int style = 0;
            if(fontstyle.equals("plain")){
                style = 0;
            }
            
            else if(fontstyle.equals("bold")){
                style = 1;
            }
            
            else{
                style = 2;
            }
            
            Font fontinsert = new Font(fontfamily, style , Integer.parseInt(fontsize));
            textarea.setFont(fontinsert);
            
            font_frame.setVisible(false);
    }
    
    void openFontFrame(){
         font_frame = new  JFrame("Choose Font");
         font_frame.setSize(500,500);
         font_frame.setLocationRelativeTo(jf);
         font_frame.setLayout(null); 
         
         String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
         font_family = new JComboBox(fonts);
         font_family.setBounds(50,100,100,30);
         font_frame.add(font_family);
         
         String[] sizes = {"10","12","14","18","24","28","34","42","72"};;
         font_size = new JComboBox(sizes);
         font_size.setBounds(190,100,100,30); 
         font_frame.add(font_size);
         
         String[] styles = {"plain", "bold", "italic"};
         font_style = new JComboBox(styles);
         font_style.setBounds(330,100,100,30);
         font_frame.add(font_style);
         
         ok = new JButton("OK");
         ok.setBounds(200,200,80,25);
         ok.addActionListener(this);
         font_frame.add(ok);
         
         font_frame.setVisible(true);
         
    }
    
    void openfile(){
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(jf);
            //System.out.println(result);
            if(result == 0){
                textarea.setText("");
                file = fileChooser.getSelectedFile();
                jf.setTitle(file.getName());
                try(FileInputStream fis = new FileInputStream(file)){
                   int i;
                   while((i=fis.read()) != -1){
                       //System.out.print(i);
                       textarea.append(String.valueOf((char)i));
                   }
                }
                catch(Exception ee){
                    ee.printStackTrace();
                }
            }
    }
    
    void savefile(){
        String title = jf.getTitle();
        if(title.equals("*Untitled* - NotePad")){
           saveasfile(); 
        }
        else{
            String text = textarea.getText();
            //System.out.print(file.getAbsolutePath());
            try(FileOutputStream fos = new FileOutputStream(file))
               {
                   byte[] b = text.getBytes();
                   fos.write(b);
                }
            catch(Exception ee)
                {
                    ee.printStackTrace();
                }
        }
    }
    
    void saveasfile(){
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showSaveDialog(jf);
            
            if(result == 0){
                String text = textarea.getText();
                file = fileChooser.getSelectedFile();
                jf.setTitle(file.getName());
                
                try(FileOutputStream fos = new FileOutputStream(file))
                {
                   byte[] b = text.getBytes();
                   fos.write(b);
                }
                catch(Exception ee)
                {
                    ee.printStackTrace();
                }
            }
    }
    
    void newwfile(){
        String text = textarea.getText();
            if(!text.equals("")){
                int i = JOptionPane.showConfirmDialog(jf, "Do you want to save this file?");
                if(i==0){
                    saveasfile();
                    textarea.setText("");
                    jf.setTitle("*Untitled* - NotePad");
                }
                else if(i == 1){
                    textarea.setText("");
                }
            }
    }
    

    @Override
    public void windowOpened(WindowEvent e) {
        
    }

    @Override
    public void windowClosing(WindowEvent e) {
        String text = textarea.getText();
            if(!text.equals("")){
                int i = JOptionPane.showConfirmDialog(jf, "Do you want to save this file?");
                if(i==0){
                    saveasfile();
                    jf.setVisible(false);
                }
                else if(i == 1){
                    jf.setVisible(false);
                }
            }
    }

    @Override
    public void windowClosed(WindowEvent e) {
        
    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        
    }

    @Override
    public void windowActivated(WindowEvent e) {
        
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        
    }
    
}
