/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes
 * or the window is resized.
 */

import acm.graphics.*;
import acm.util.RandomGenerator;

import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {
	ArrayList<NameSurferEntry> entryArr = new ArrayList<NameSurferEntry>();
	private RandomGenerator rgen = new RandomGenerator();

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
//		NameSurferGraph graph = new NameSurferGraph();
//		add(graph);
	}
	
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		entryArr.clear();
		update();
	}
	
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		entryArr.add(entry);
		update();
	}
	
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		removeAll();
		GridDisplay();
		GraphDisplay();
	}
	
	private void GridDisplay() {
		int decade = START_DECADE-10;
		GLine verticalGrid;
		for(int i=0; i<NDECADES; i++) {
			verticalGrid = new GLine(i*((double)getWidth()/NDECADES), 0, 
					i*((double)getWidth()/NDECADES), getHeight());
			add(verticalGrid);
			String decadeStr = "";
			decade += 10;
			decadeStr += decade;
			add(new GLabel(decadeStr), (i*((double)getWidth()/NDECADES)), 
					getHeight()-(GRAPH_MARGIN_SIZE)/3); 
		}
		add(new GLine(0, getHeight()-GRAPH_MARGIN_SIZE, 
				getWidth(), getHeight()-GRAPH_MARGIN_SIZE));
		add(new GLine(0, GRAPH_MARGIN_SIZE, 
				getWidth(), GRAPH_MARGIN_SIZE));
	}
	
	private void GraphDisplay() {
		double startX = 0;
		double startY = 0;
		double endX = 0;
		double endY = 0;
		GLine line = null;
		double xUnit = (double)getWidth()/NDECADES;
		double yUnit = ((double)getHeight()-(2*GRAPH_MARGIN_SIZE))/MAX_RANK;
		
		if(entryArr.isEmpty()) {
			removeAll();
			GridDisplay();
		}
		else{
			for(int i = 0; i<entryArr.size(); i++) {
				NameSurferEntry entry = entryArr.get(i);
				Color color = rgen.nextColor();
				for(int j = 0; j<NDECADES; j++) {
					if(j==0) {
						startX = 0;
						startY = (entry.getRank(j)*yUnit)+GRAPH_MARGIN_SIZE;
					}
					else {
						line = new GLine(0, 0, 0, 0);
						line.setColor(color);
						endX = startX+xUnit;
						endY = (entry.getRank(j)*yUnit)+GRAPH_MARGIN_SIZE;
						line.setStartPoint(startX, startY);
						line.setEndPoint(endX, endY);
						add(line);
						startX = endX;
						startY = endY;
					}
				}
			}
		}
	}
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
