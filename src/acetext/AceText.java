package acetext;

/**
 *
 * @author Austine D. Odhiambo
 */

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTextArea;
import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import java.io.File;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AceText implements ActionListener {

    public static JFrame myWindow = new JFrame("AceText");

    public static JMenuBar myMenuBar = new JMenuBar();

    public static JMenu file = new JMenu("File");
    public static JMenu edit = new JMenu("Edit");
    public static JMenu help = new JMenu("Help");
    
    public static JPopupMenu myPopup = new JPopupMenu("Edit");

    public static JMenuItem open = new JMenuItem("Open");
    public static JMenuItem save = new JMenuItem("Save");
    public static JMenuItem saveAs = new JMenuItem("Save As");
    public static JMenuItem cut = new JMenuItem("Cut");
    public static JMenuItem cut2 = new JMenuItem("Cut");
    
    public static JMenuItem copy = new JMenuItem("Copy");
    public static JMenuItem copy2 = new JMenuItem("Copy");
    
    public static JMenuItem paste = new JMenuItem("Paste");
    public static JMenuItem paste2 = new JMenuItem("Paste");
    
    public static JMenuItem about = new JMenuItem("About AceText");

    public static JTextArea myTextArea = new JTextArea();
    public static JFileChooser myFileChooser = new JFileChooser();

    private boolean opened = false;
    private boolean saved = false;

    private File openedFile;

    public AceText() {

        myMenuBar.add(file);
        myMenuBar.add(edit);
        myMenuBar.add(help);

        file.add(open);
        file.add(save);
        file.add(saveAs);

        edit.add(cut);
        edit.add(copy);
        edit.add(paste);

        help.add(about);

        open.addActionListener(this);
        save.addActionListener(this);
        saveAs.addActionListener(this);

        open.setEnabled(true);
        save.setEnabled(true);
        saveAs.setEnabled(true);

        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        
        myPopup.add(cut2);
        myPopup.add(copy2);
        myPopup.add(paste2);
        
        cut2.addActionListener(this);
        copy2.addActionListener(this);
        paste2.addActionListener(this);

        about.addActionListener(this);
        
        myTextArea.addMouseListener(new PopupTriggerListener());
        
    }

    private void showWindow() {
        myWindow.pack();
        myWindow.setSize(600, 400);
        myWindow.setLocationRelativeTo(null);
        myWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myWindow.setJMenuBar(myMenuBar);
        myWindow.add(myTextArea);
        myWindow.setVisible(true);
    }

    private void saveFile(File filename) {
        try {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                writer.write(myTextArea.getText());
            }
            saved = true;
            myWindow.setTitle("AceText - " + filename.getName());
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    // Method for quick saving files
    private void quickSave(File filename) {
        try {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
                writer.write(myTextArea.getText());
                JOptionPane.showMessageDialog(null, "saved");
            }
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    // Method for opening files
    private void openingFiles(File filename) {
        try {
            openedFile = filename;
            FileReader reader = new FileReader(filename);
            myTextArea.read(reader, null);
            opened = true;
            myWindow.setTitle("AceText - " + filename.getName());
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {

        if (event.getSource() == open) {
            JFileChooser myOpen = new JFileChooser();
            myOpen.showOpenDialog(null);
            File myfile;
            myfile = myOpen.getSelectedFile();
            openingFiles(myfile);
        } else if (event.getSource() == save) {
            JFileChooser mySave = new JFileChooser();
            File filename = mySave.getSelectedFile();
            if (opened == false && saved == false) {
                mySave.showSaveDialog(null);
                int confirmationResult;
                if (filename.exists()) {
                    confirmationResult = JOptionPane.showConfirmDialog(save, "Replace existing file?");
                    if (confirmationResult == JOptionPane.YES_OPTION) {
                        saveFile(filename);
                    }
                } else {
                    saveFile(filename);
                }
            } else {
                quickSave(openedFile);
            }
        } else if (event.getSource() == saveAs) {
            JFileChooser mySaveAs;
            mySaveAs = new JFileChooser();
            mySaveAs.showSaveDialog(null);
            File filename = mySaveAs.getSelectedFile();
            int confirmationResult;
            if (filename.exists()) {
                confirmationResult = JOptionPane.showConfirmDialog(saveAs, "Replace existing file?");
                if (confirmationResult == JOptionPane.YES_OPTION) {
                    saveFile(filename);
                }
            } else {
                saveFile(filename);
            }
        } else if (event.getSource() == cut) {
            myTextArea.cut();
        } else if (event.getSource() == copy) {
            myTextArea.copy();
        } else if (event.getSource() == paste) {
            myTextArea.paste();
        } else if (event.getSource() == cut2){
            myTextArea.cut();
        } else if (event.getSource() == copy2) {
            myTextArea.copy();
        } else if (event.getSource() == paste2) {
            myTextArea.paste();
        } else if (event.getSource() == about) {
            JOptionPane.showMessageDialog(null, " Welcome to AceText the Notepad clone. \n Written by \n Austine D. Odhiambo: 1031407 \n It can copy, paste, cut and save a .txt file");
        }
    }
    
    class PopupTriggerListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent event){
            if (event.isPopupTrigger()){
                myPopup.show(event.getComponent(), event.getX(), event.getY());
            }
        }
        
        @Override
         public void mouseReleased(MouseEvent event){
            if (event.isPopupTrigger()){
                myPopup.show(event.getComponent(), event.getX(), event.getY());
            }
        }
    }

    public static void main(String[] args) {
        File filename = null;

        AceText aceText = new AceText();

        aceText.showWindow();
        aceText.openingFiles(filename);
        aceText.saveFile(filename);
        aceText.quickSave(filename);
    }
}
