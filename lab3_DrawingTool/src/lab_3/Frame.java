package lab_3;

import java.io.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;

    private final JMenuBar menuBar;
    private final JMenu file;
    private final JMenuItem chooseFile;
    private final JMenuItem saveFile;
    private final JMenuItem quit;
    private final JMenu edit;
    private final JMenuItem undo;
    private final JMenuItem redo;
    private final JMenuItem clear;

    private final JComboBox<String> colors;
    private final JComboBox<String> shapes;

    private final JLabel strokeWidthLabel;
    private final JSlider strokeWidth;

    protected Panel panel;
    private final JLabel statusLabel;

    private Color currentColor;
    private int lineWidth;
    
    private KeyEventHandler handler;
    
    private final String[] colorOptions = {"Black", "Blue", "Cyan", "Dark Gray",
        "Gray", "Green", "Light Gray", "Magenta", "Orange", "Pink", "Red",
        "White", "Yellow"};
    private final String[] shapeOptions = {"Line", "Oval",
        "Rectangle", "Text","Select"};

    public Frame() {
        menuBar = new JMenuBar();
        file = new JMenu("File");
        file.setMnemonic(KeyEvent.VK_F);
        chooseFile = new JMenuItem("Open");
        chooseFile.addActionListener(new FileChooserHandler());
        chooseFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        saveFile = new JMenuItem("Save");
        saveFile.addActionListener(new FileSaveHandler());
        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
        quit = new JMenuItem("quit");
        quit.addActionListener(new QuitHandler());
        quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK));
        edit = new JMenu("Edit");
        edit.setMnemonic(KeyEvent.VK_E);
        undo = new JMenuItem("Undo");
        undo.addActionListener(new UndoHandler());
        undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK));
        redo = new JMenuItem("Redo");
        redo.addActionListener(new RedoHandler());
        redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, InputEvent.CTRL_DOWN_MASK));
        clear = new JMenuItem("Clear");
        clear.addActionListener(new ClearHandler());
        clear.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK));
        file.add(chooseFile);
        file.add(saveFile);
        file.add(quit);
        file.addSeparator();
        edit.add(undo);
        edit.add(redo);
        edit.add(clear);
        menuBar.add(file);
        menuBar.add(edit);
        setJMenuBar(menuBar);

        currentColor = Color.BLACK;
        lineWidth = 1;

        colors = new JComboBox<>(colorOptions);
        colors.addItemListener(new ColorHandler());
        shapes = new JComboBox<>(shapeOptions);
        shapes.addItemListener(new ShapeHandler());

        strokeWidthLabel = new JLabel("Line Width:");
        strokeWidth = new JSlider(0, 10, 0);
        strokeWidth.setMajorTickSpacing(2);
        strokeWidth.setMinorTickSpacing(1);
        strokeWidth.setPaintLabels(true);
        strokeWidth.setPaintTicks(true);
        strokeWidth.addChangeListener(new StrokeWidthHandler());

        statusLabel = new JLabel();
        
        panel = new Panel(statusLabel);

        setLayout(new BorderLayout());

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new FlowLayout());
        optionsPanel.add(colors);
        optionsPanel.add(shapes);
        optionsPanel.add(strokeWidthLabel);
        optionsPanel.add(strokeWidth);

        add(optionsPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);

        handler  = new KeyEventHandler();
        shapes.addKeyListener(handler);
        colors.addKeyListener(handler);
        strokeWidth.addKeyListener(handler);
}
    
    private class KeyEventHandler extends KeyAdapter{
    	
   	 	public void keyTyped(KeyEvent e) {
   	    }
   	    
   	    /** Handle the key pressed event from the text field. */
   	    public void keyPressed(KeyEvent e) {
   	    	if (e.getKeyCode() == 8) {
   	   	    	panel.delSelection();
			}
   	    }
   	    
   	    /** Handle the key released event from the text field. */
   	    public void keyReleased(KeyEvent e) {
   	    }
   }
    
    private class FileChooserHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
        	JFileChooser fileChooser = new JFileChooser();
    		FileNameExtensionFilter extentions = new FileNameExtensionFilter(".shapelist", "shapelist");
    		fileChooser.setFileFilter(extentions);
    		int retunedVal = fileChooser.showOpenDialog(panel);
    		File file = null;
    		if(retunedVal == JFileChooser.APPROVE_OPTION){
    			file = fileChooser.getSelectedFile();
    		}
    		//only continue if a file is selected
    		if(file != null){
    			Shapes newSet = null;
    			try {
    				//read in file
    				ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
    				newSet = (Shapes) input.readObject();
    				input.close();
    				System.out.println("Open Success");
    			} catch (Exception e1) {
    				e1.printStackTrace();
    			}
    			if(newSet != null){
    				panel.setShapes(newSet);
    			}
    		}
        }

    }
    
    private class FileSaveHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
        	JFileChooser fileChooser = new JFileChooser();
    		FileNameExtensionFilter extentions = new FileNameExtensionFilter(".shapelist", "shapelist");
    		fileChooser.setFileFilter(extentions);
    		int retunedVal = fileChooser.showSaveDialog(panel);
    		File file = null;
    		if(retunedVal == JFileChooser.APPROVE_OPTION){
    			file = fileChooser.getSelectedFile();
    			//make sure file has the right extention
    			file = new File(file.getAbsolutePath()+".shapelist");
    		}
    		//only continue if a file is selected
    		if(file != null){
    			try {
    				//read in file
    				ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
    				Shapes s = panel.getShapes();
     				if ( s != null) {
        				output.writeObject(s);
					}
    				output.close();
    				System.out.println("Save Success");
    				
    			} catch (Exception e1) {
    				e1.printStackTrace();
    			}
    			
    		}
        }

    }
    
    private class QuitHandler extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
        	
        }

    }
    
    private class UndoHandler extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.clearLastShape();
        }

    }

    private class RedoHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.redoLastShape();
        }
        
    }

    private class ClearHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            panel.clearDrawing();
        }

    }

    private class ColorHandler implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                switch (colors.getSelectedIndex()) {
                    case 0:
                        currentColor = Color.BLACK;
                        panel.setCurrentColor(Color.BLACK);
                        break;
                    case 1:
                        currentColor = Color.BLUE;
                        panel.setCurrentColor(Color.BLUE);
                        break;
                    case 2:
                        currentColor = Color.CYAN;
                        panel.setCurrentColor(Color.CYAN);
                        break;
                    case 3:
                        currentColor = Color.DARK_GRAY;
                        panel.setCurrentColor(Color.DARK_GRAY);
                        break;
                    case 4:
                        currentColor = Color.GRAY;
                        panel.setCurrentColor(Color.GRAY);
                        break;
                    case 5:
                        currentColor = Color.GREEN;
                        panel.setCurrentColor(Color.GREEN);
                        break;
                    case 6:
                        currentColor = Color.LIGHT_GRAY;
                        panel.setCurrentColor(Color.LIGHT_GRAY);
                        break;
                    case 7:
                        currentColor = Color.MAGENTA;
                        panel.setCurrentColor(Color.MAGENTA);
                        break;
                    case 8:
                        currentColor = Color.ORANGE;
                        panel.setCurrentColor(Color.ORANGE);
                        break;
                    case 9:
                        currentColor = Color.PINK;
                        panel.setCurrentColor(Color.PINK);
                        break;
                    case 10:
                        currentColor = Color.RED;
                        panel.setCurrentColor(Color.RED);
                        break;
                    case 11:
                        currentColor = Color.WHITE;
                        panel.setCurrentColor(Color.WHITE);
                        break;
                    default:    //Last option is yellow
                        currentColor = Color.YELLOW;
                        panel.setCurrentColor(Color.YELLOW);
                        break;
                }
            }
        }

    }

    private class ShapeHandler implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                panel.setShapeType(shapes.getSelectedIndex());
            }
        }

    }


   

    private class StrokeWidthHandler implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent e) {
            lineWidth = strokeWidth.getValue();
            lineWidth = lineWidth <= 0 ? 1 : lineWidth;
            strokeWidth.setToolTipText(String.valueOf(lineWidth));
            panel.setWidth(strokeWidth.getValue());
            
        }

    }
}
