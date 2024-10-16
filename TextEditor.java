import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame implements ActionListener{

    JTextArea textArea;
    JScrollPane scrollPane;
    JSpinner fontSize;
    JLabel fontLabel;
    JButton fontColorButton;
    JComboBox fontBox;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem exitItem;

    TextEditor(){
        //-------Setarile Frame-ului initial--------
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Stef's Text Editor");
        this.setLocationRelativeTo(null);
        this.setSize(500,550);
        this.setLayout(new FlowLayout());
        this.setResizable(false);
        //-------/Setarile Frame-ului initial--------

        //------Zona de text--------
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial",Font.PLAIN,20));
        //------/Zona de text--------

        //-----Bara de scroll---------
        scrollPane = new JScrollPane(textArea);//*Asa se adauga o bara de scroll */
        scrollPane.setPreferredSize(new Dimension(450,450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        //-----/Bara de scroll---------

        //----Schimbarea dimensiunii textului---
        fontLabel = new JLabel("Font: ");
        fontSize = new JSpinner();
        fontSize.setPreferredSize(new Dimension(50,25));
        fontSize.setValue(20);
        fontSize.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int)fontSize.getValue()));
            }
            
        });
        //----/Schimbarea dimensiunii textului---

        //-------Schimbarea culorii textului--------
        fontColorButton = new JButton("Culoare");
        fontColorButton.addActionListener(this);
        //-------/Schimbarea culorii textului--------
    

        //---------Schimbarea fontului----------
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        //iei toate numele de fonturi
        fontBox = new JComboBox(fonts);
        fontBox.addActionListener(this);
        fontBox.setSelectedItem("Arial");
        //---------/Schimbarea fontului----------


        //----------MenuBar-------------
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");

        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        //----------/MenuBar-------------


        this.setJMenuBar(menuBar);
        this.add(fontLabel);
        this.add(fontSize);
        this.add(fontColorButton);
        this.add(fontBox);
        this.add(scrollPane);
        this.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==fontColorButton){
            JColorChooser colorChooser = new JColorChooser();
            Color color = colorChooser.showDialog(null, "Choose a culoare", Color.black);
            textArea.setForeground(color);
        }
        if(e.getSource()== fontBox){
            textArea.setFont(new Font((String)fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
        }
        if(e.getSource()==openItem){
            JFileChooser fileChooser =new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files", "txt");
            fileChooser.setFileFilter(filter);
            int response = fileChooser.showOpenDialog(null);

            if(response == JFileChooser.APPROVE_OPTION){
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;
                try {
                    fileIn = new Scanner(file);
                    if(file.isFile()){
                        textArea.setText("");
                        while (fileIn.hasNextLine()) {
                            String line = fileIn.nextLine() + '\n';
                            textArea.append(line);
                        }
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } finally{
                    fileIn.close();
                }
            }
        }
        if(e.getSource()==saveItem && !textArea.getText().isBlank()){
            JFileChooser fileChooser =new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));
            int response = fileChooser.showSaveDialog(null);
            if(response == JFileChooser.APPROVE_OPTION){
                File file;
                PrintWriter fileOut = null;
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    fileOut = new PrintWriter(file);//faci asta ca sa il putem salva...
                    fileOut.println(textArea.getText());
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }finally{
                    fileOut.close();
                }

            }
        }
        if(e.getSource()==exitItem){
            System.exit(0);
        }

    }
    
}
