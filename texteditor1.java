// Implementation of a simple text editor using java.
//Importing the required AWT and SWING Packages.

import java.awt.*;
import java.util.Hashtable;
import java.lang.Object;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.AbstractUndoableEdit;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.*;
import javax.swing.text.Highlighter.HighlightPainter;

// Creating a new window by extending JFrame class.
public class texteditor1 extends JFrame {
	private static JTextArea area = new JTextArea(25,65);
	private static JTextArea lines;
    private static JMenuBar JMB;
	private JFileChooser dialog = new JFileChooser(System.getProperty("user.dir"));
	private String currentFile = "Untitled";
	private boolean changed = false;
	int fontsize=16;
	int fontstyle=Font.PLAIN;
	String fontfamily="Times Roman";
	UndoManager manager;
	static JScrollPane scroll;
	
	public texteditor1() {
		// Set the default font as "Monospaced"
		area.setFont(new Font(fontfamily,fontstyle,fontsize));
		area.setBackground(Color.gray);
		scroll = new JScrollPane(area,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scroll,BorderLayout.CENTER);
		
		// Creating a Menubar using JMenuBar class provided by swing.
	     JMB = new JMenuBar();
		 manager = new UndoManager();
		area.getDocument().addUndoableEditListener(manager);
		setJMenuBar(JMB);
		JMenu file = new JMenu("File"); // Create File Menu.
		JMenu edit = new JMenu("Edit"); // Create Edit Menu.
		JMenu font = new JMenu("Font");
		JMenu about = new JMenu("About");
		JMenu font_family = new JMenu("Font Family");
		JMenu font_size = new JMenu("Font Size");
		JMenu font_style = new JMenu("Font Style");
		JMenu fgcolor = new JMenu("Font colour");
		JMenu bgcolor = new JMenu("Background colour");
		JMB.add(file);   
		JMB.add(edit);
		JMB.add(font);
		JMB.add(about);
		file.add(New);
		file.add(Open);
		file.add(Save);
		file.add(SaveAs);
		file.addSeparator();
		file.add(Quit);
		
		
		
		edit.add(Cut);
		edit.add(Copy);
		edit.add(Paste);
		edit.add(Find);
		edit.add(Replace);
		edit.add(Undo);
		edit.add(Redo);
		edit.add(Clear);
		edit.add(selectAll);
		
		font.add(font_family);
		font.add(font_size);
		font.add(font_style);
		font.add(bgcolor);
		font.add(fgcolor);
		font.add(Default);
		JMenuItem ff1,ff2,ff3,ff4,ff5,fs1,fs2,fs3,fs4,fs5,fl1,fl2,fl3,abt1,abt2,bgcol1,bgcol2,bgcol3,bgcol4,bgcol5,fgcol1,fgcol2,fgcol3,fgcol4,fgcol5;
		about.add(abt1=new JMenuItem("Help"));
		about.add(abt2=new JMenuItem("Authors"));
		font_family.add(ff1=new JMenuItem("Monospaced"));
		font_family.add(ff2=new JMenuItem("Serif"));
		font_family.add(ff3=new JMenuItem("Sans Serif"));
		font_family.add(ff4=new JMenuItem("Dialog"));
		font_family.add(ff5=new JMenuItem("DialogInput"));
		font_size.add(fs1=new JMenuItem("12"));
		font_size.add(fs2=new JMenuItem("14"));
        font_size.add(fs3=new JMenuItem("16"));
        font_size.add(fs4=new JMenuItem("20"));
        font_size.add(fs5=new JMenuItem("30"));
        font_style.add(fl1=new JMenuItem("Bold"));
        font_style.add(fl2=new JMenuItem("Italics"));
        font_style.add(fl3=new JMenuItem("Plain"));
        bgcolor.add(bgcol1=new JMenuItem("White"));
        bgcolor.add(bgcol2=new JMenuItem("Blue"));
        bgcolor.add(bgcol3=new JMenuItem("Pink"));
        bgcolor.add(bgcol4=new JMenuItem("Gray"));
        bgcolor.add(bgcol5=new JMenuItem("Green"));
        fgcolor.add(fgcol1=new JMenuItem("Black"));
        fgcolor.add(fgcol2=new JMenuItem("Cyan"));
        fgcolor.add(fgcol3=new JMenuItem("Red"));
        fgcolor.add(fgcol4=new JMenuItem("Magenta"));
        fgcolor.add(fgcol5=new JMenuItem("Yellow"));
  
		Mymenuhandler handler = new Mymenuhandler();
		bgcol1.addActionListener(handler);
		bgcol2.addActionListener(handler);
		bgcol3.addActionListener(handler);
		bgcol4.addActionListener(handler);
		bgcol5.addActionListener(handler);
		fgcol1.addActionListener(handler);
		fgcol2.addActionListener(handler);
		fgcol3.addActionListener(handler);
		fgcol4.addActionListener(handler);
		fgcol5.addActionListener(handler);
		abt1.addActionListener(handler);
		abt2.addActionListener(handler);
		ff1.addActionListener(handler);
		ff2.addActionListener(handler);
		ff3.addActionListener(handler);
		ff4.addActionListener(handler);
		ff5.addActionListener(handler);
		fs1.addActionListener(handler);
		fs2.addActionListener(handler);
		fs3.addActionListener(handler);
		fs4.addActionListener(handler);
		fs5.addActionListener(handler);
		fl1.addActionListener(handler);
		fl2.addActionListener(handler);
		fl3.addActionListener(handler);
		
		edit.getItem(0).setText("Cut");
		edit.getItem(1).setText("Copy");
		edit.getItem(2).setText("Paste");
		edit.getItem(3).setText("Find");
		edit.getItem(4).setText("Replace");
		edit.getItem(5).setText("Undo");
		edit.getItem(6).setText("Redo");
		edit.getItem(8).setText("Select All");
		edit.getItem(7).setText("Clear");
		
		JToolBar tool = new JToolBar();
		add(tool,BorderLayout.NORTH);
		tool.add(New);
		tool.add(Open);
		tool.add(Save);
		tool.addSeparator();
		
		JButton cut = tool.add(Cut), cop = tool.add(Copy),pas = tool.add(Paste),undo=tool.add(Undo),redo=tool.add(Redo);
		
		cut.setText(null); cut.setIcon(new ImageIcon("cut.gif"));
		cop.setText(null); cop.setIcon(new ImageIcon("copy.gif"));
		pas.setText(null); pas.setIcon(new ImageIcon("paste.gif"));
		undo.setText(null); undo.setIcon(new ImageIcon("undo.jpeg"));
		redo.setText(null); redo.setIcon(new ImageIcon("redo.png"));
		
		Save.setEnabled(false);
		SaveAs.setEnabled(false);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		area.addKeyListener(k1);  // Add Listeners for certain keyboard actions
		setTitle(currentFile);
		setVisible(true); 
			
		 // Assign the actions to keys
	    area.registerKeyboardAction(Undo, KeyStroke.getKeyStroke(
	        KeyEvent.VK_Z, InputEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);
	    area.registerKeyboardAction(Redo, KeyStroke.getKeyStroke(
	        KeyEvent.VK_Y, InputEvent.CTRL_MASK), JComponent.WHEN_FOCUSED);
	}
	
	public static void addLinenumbers(){
		lines = new JTextArea("1 ");
		lines.setBackground(Color.LIGHT_GRAY);
		lines.setEditable(false);
 
		area.getDocument().addDocumentListener(new DocumentListener(){
			public String getText(){
				int caretPosition = area.getDocument().getLength();
				Element root = area.getDocument().getDefaultRootElement();
				String text = "1 " + System.getProperty("line.separator");
				for(int i = 2; i < root.getElementIndex( caretPosition ) + 2; i++){
					text += i + System.getProperty("line.separator");
				}
				return text;
			}
			@Override
			public void changedUpdate(DocumentEvent de) {
				lines.setText(getText());
			}
 
			@Override
			public void insertUpdate(DocumentEvent de) {
				lines.setText(getText());
			}
 
			@Override
			public void removeUpdate(DocumentEvent de) {
				lines.setText(getText());
			}
 
		});
 
		scroll.getViewport().add(area);
		scroll.setRowHeaderView(lines);
	}
 
	private KeyListener k1 = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {     // Event handling when a key is pressed.
			changed = true;
			Save.setEnabled(true);
			SaveAs.setEnabled(true);
		}
	};
	
	Action Open = new AbstractAction("Open",new ImageIcon("open.gif")) {
		public void actionPerformed(ActionEvent e) {
			saveOld();
			if(dialog.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
			}				readInFile(dialog.getSelectedFile().getAbsolutePath());    // Open a file by selecting its path from the system.

			SaveAs.setEnabled(true);
		}
	};
	
	Action Save = new AbstractAction("Save", new ImageIcon("save.gif")) {
		public void actionPerformed(ActionEvent e) {
			if(!currentFile.equals("Untitled"))
				saveOld();
			else
				saveFileAs();
		}
	};
	
	Action SaveAs = new AbstractAction("Save as...", new ImageIcon("save.gif")) {
		public void actionPerformed(ActionEvent e) {
			saveFileAs();
		}
	};
	
	Action Quit = new AbstractAction("Quit",new ImageIcon("exit.png")) {
		public void actionPerformed(ActionEvent e) {
			saveOld();
			System.exit(0);
		}
	};
	
	Action New=new AbstractAction("New", new ImageIcon("new.gif")){
		public void actionPerformed(ActionEvent e){
			if(!currentFile.equals("Untitled"))
				saveOld();//saveFile(currentFile);
			else
				saveFileAs();
			
			new texteditor1();
		}
	};
	
	Action Find=new AbstractAction("Find",new ImageIcon("find.png")){
		public void actionPerformed(ActionEvent e){
			//String command=e.getActionCommand();
			int p0=0;
			int p1=0;
			String text=area.getText();
			String word=JOptionPane.showInputDialog("Enter a string to find");
			Highlighter highlighter=area.getHighlighter();
			HighlightPainter painter= new DefaultHighlighter.DefaultHighlightPainter(Color.pink);
			do
			{
				p0=text.indexOf(word,p0);
				if(p0==-1 && p1==0){
					JOptionPane.showMessageDialog(area,"Word not found");break;
				}
				else if(p0==-1 &&p1!=0)break;
				p1=p0+word.length();
				try{
					highlighter.addHighlight(p0, p1,painter);
					p0=p0+1;
					}
				catch(BadLocationException ex){
					ex.printStackTrace();
				}
				if(JOptionPane.showConfirmDialog(area,"Find Next")==JOptionPane.OK_OPTION){highlighter.removeAllHighlights();continue;}
				else break;
				}while(true);
			area.setText(text);
			area.setCaretPosition(0);
			
		}
	};
	
	Action Replace= new AbstractAction("Replace",new ImageIcon("replace.png")){
		public void actionPerformed(ActionEvent e){
			String text=area.getText();
			int p0=0;
			String oldstring=JOptionPane.showInputDialog(area,"Enter the string to be replaced");
			p0=text.indexOf(oldstring,p0);
			if(p0==-1){
				JOptionPane.showMessageDialog(area,"Entered String not found");
			}
			else {
			String newstring=JOptionPane.showInputDialog(area, "Enter the new String");
			
			area.setText(text.replaceAll(oldstring, newstring));}
			area.setCaretPosition(0);	
		}
	};
	
	Action Undo=new AbstractAction("Undo",new ImageIcon("undo.jpeg")){
		public void actionPerformed(ActionEvent e){
			try{ 
			manager.undo();}
			catch (CannotUndoException ex) {
		        Toolkit.getDefaultToolkit().beep();
               System.out.println("Cannot UNDO");}
		}
	};
	
	Action Redo=new AbstractAction("Redo",new ImageIcon("redo.png")){
		public void actionPerformed(ActionEvent e){
			try {
			manager.redo();}
			catch (CannotRedoException ex) {
		        Toolkit.getDefaultToolkit().beep();
              System.out.println("Cannot REDO");}
		}
	};
	
	Action Clear=new AbstractAction("Clear",new ImageIcon("clear.jpeg")){
		public void actionPerformed(ActionEvent e){
			String text=area.getText();
			int start=0;
			int end=text.length();
			if(end==0){
				JOptionPane.showMessageDialog(area,"No characters to be cleared ");
			}
			else
			area.replaceRange(null, start, end);
		}
	};
	
	Action selectAll=new AbstractAction("Select All",new ImageIcon("all.png")){
		public void actionPerformed(ActionEvent e){
			area.selectAll();
		}
	};
	Action Default=new AbstractAction("Default"){
		public void actionPerformed(ActionEvent e){
		Font f=new Font("Monospaced",Font.PLAIN,12);
		area.setFont(f);	
		}	
	};

	ActionMap m = area.getActionMap();
	Action Cut = m.get(DefaultEditorKit.cutAction);
	Action Copy = m.get(DefaultEditorKit.copyAction);
	Action Paste = m.get(DefaultEditorKit.pasteAction);
		
	private void saveFileAs() {
		if(dialog.showSaveDialog(null)==JFileChooser.APPROVE_OPTION)
			saveFile(dialog.getSelectedFile().getAbsolutePath());
	}
	
	private void saveOld() {
		if(changed) {
			if(JOptionPane.showConfirmDialog(this, "Would you like to save "+ currentFile +" ?","Save",JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION)
				saveFile(currentFile);
		}
	}
	
	private void readInFile(String fileName) {
		try {
			FileReader r = new FileReader(fileName);
			area.read(r,null);
			r.close();
			currentFile = fileName;
			setTitle(currentFile);
			changed = false;
		}
		catch(IOException e) {
			Toolkit.getDefaultToolkit().beep();
			JOptionPane.showMessageDialog(this,"Editor can't find the file called "+fileName);
		}
	}
	
	private void saveFile(String fileName) {
		try {
			FileWriter w = new FileWriter(fileName);
			area.write(w);
			w.close();
			currentFile = fileName;
			setTitle(currentFile);
			changed = false;
			Save.setEnabled(false);
		}
		catch(IOException e) {
		}
	}
	
public class Mymenuhandler implements ActionListener{
    public void actionPerformed(ActionEvent e){
    	String arg=e.getActionCommand();
    	System.out.println("Selected="+arg);
    	String text=area.getText();
    	if(arg.equals("Monospced")){
    		fontfamily="Monospaced";
    		Font f=new Font(fontfamily, fontstyle, fontsize);
    		area.setFont(f);
    	}
    	else if(arg.equals("Serif")){
    		fontfamily="Serif";
    		Font f=new Font(fontfamily,fontstyle,fontsize);
    		area.setFont(f);	
    	}
    	else if(arg.equals("Sans Serif")){
    		fontfamily="Sans Serif";
    		Font f=new Font(fontfamily,fontstyle,fontsize);
    		area.setFont(f);	
    	}
    	else if(arg.equals("Dialog")){
    		fontfamily="Dialog";
    		Font f=new Font(fontfamily,fontstyle,fontsize);
    		area.setFont(f);	
    	}
    	else if(arg.equals("DialogInput")){
    		fontfamily="DialogInput";
    		Font f=new Font(fontfamily,fontstyle,fontsize);
    		area.setFont(f);	
    	}

    	else if(arg.equals("Bold")){
    		fontstyle=Font.BOLD;
    		Font f=new Font(fontfamily,fontstyle,fontsize);
    		area.setFont(f);	
    	}
    	else if(arg.equals("Italics")){
    		fontstyle=Font.ITALIC;
    		Font f=new Font(fontfamily,fontstyle,fontsize);
    		area.setFont(f);	
    	}
    	else if(arg.equals("Plain")){
    		fontstyle=Font.PLAIN;
    		Font f=new Font(fontfamily,fontstyle,fontsize);
    		area.setFont(f);	
    	}
    	
    	else if(arg.equals("12")){
    		fontsize=12;
    		Font f=new Font(fontfamily,fontstyle,fontsize);
    		area.setFont(f);	
    	}
    	else if(arg.equals("14")){
    		fontsize=14;
    		Font f=new Font(fontfamily,fontstyle,fontsize);
    		area.setFont(f);	
    	}
    	else if(arg.equals("16")){
    		fontsize=16;
    		Font f=new Font(fontfamily,fontstyle,fontsize);
    		area.setFont(f);	
    	}
    	else if(arg.equals("20")){
    		fontsize=20;
    		Font f=new Font(fontfamily,fontstyle,fontsize);
    		area.setFont(f);
    	}
    	else if(arg.equals("30")){
    		fontsize=30;
    		Font f=new Font(fontfamily,fontstyle,fontsize);
    		area.setFont(f);
    	}
    	else if(arg.equals("Help")){
    		String info="This is a simple text editor performing basic text editing operations.\n For more info visit \n https://github.com/akarshprabhu/ScratchPad ";
    		JOptionPane.showMessageDialog(area,info);
    	}
    	else if(arg.equals("Authors")){
    		String info="Project Guide: Prof.Trisiladevinagavi\nCreators:\nVaibhav Guptha(V sem CS)\nSripathi Bhat(V sem CS)\nAkarsh Prabhu K.(V sem CS)";
    		JOptionPane.showMessageDialog(area, info);
    	}
    	if(arg.equals("White")){
    		area.setBackground(Color.WHITE);
    	}
    	else if(arg.equals("Blue")){
    		area.setBackground(Color.BLUE);
    	}
    	else if(arg.equals("Pink")){
    		area.setBackground(Color.PINK);
    	}
    	else if(arg.equals("Gray")){
    		area.setBackground(Color.GRAY);
    	}
    	else if(arg.equals("Green")){
    		area.setBackground(Color.GREEN);
    	}
    	if(arg.equals("Cyan")){
    		area.setForeground(Color.CYAN);
    	}
    	else if(arg.equals("Red")){
    		area.setForeground(Color.red);
    	}
    	else if(arg.equals("Magenta")){
    		area.setForeground(Color.MAGENTA);
    	}
    	else if(arg.equals("Black")){
    		area.setForeground(Color.BLACK);
    	}  
    	else if(arg.equals("Yellow")){
    		area.setForeground(Color.YELLOW);
    	}
    }	
}
	
public static void main(String[] args) 
  {
      new texteditor1();
      addLinenumbers();
  }
}
       
        
       

