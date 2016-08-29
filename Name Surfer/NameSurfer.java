/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;

import java.awt.event.*;

import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {
	JTextField name;
	JButton graphButton;
	JButton clearButton;
	private NameSurferGraph graph;
	private NameSurferEntry entry;
	private NameSurferDataBase dataBase;


/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the top of the window.
 */
	public void init() {
		JLabel label = new JLabel("Name");
		add(label, NORTH);
		
	    name = new JTextField(15);
	    name.addActionListener(this);
	    name.setActionCommand("Text");
	    add(name, NORTH);
	    
	    graphButton = new JButton("Graph");
	    clearButton = new JButton("Clear");
	    add(graphButton, NORTH);
	    add(clearButton, NORTH);
	    addActionListeners();
	    
	    graph = new NameSurferGraph();
	    add(graph);
	    
//	    entry = new NameSurferEntry();
	    
	    dataBase = new NameSurferDataBase(NAMES_DATA_FILE);

	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("Graph")) {
			graph.addEntry(dataBase.findEntry(name.getText().toUpperCase()));
		}
		else if(cmd.equals("Clear")) {
			graph.clear();
		}
	}
}
