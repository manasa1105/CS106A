/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import acm.util.*;

public class HangmanLexicon {
	private static final int NUMBER_OF_WORDS = 121806;
	String[] arr = new String[NUMBER_OF_WORDS];
	
/** constructor for hangmanLexicon class*/
		public HangmanLexicon() {
			int i=(-1);
			BufferedReader rd = openFileReader("HangmanLexicon.txt");
	    	while(true) {
				try {
					String line = rd.readLine();
					if(line==null) break;
					i++;
					arr[i]=line;
				} 
				catch (IOException ex) {
					throw new ErrorException(ex);
				}
				
	    	}
		}

/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return NUMBER_OF_WORDS;
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		return arr[index];
	}
	
	private BufferedReader openFileReader(String fileName) {
		BufferedReader rd;
		try{
			String name = fileName;
			rd = new BufferedReader(new FileReader(name));
		}
		catch(IOException ex){
			throw new ErrorException(ex);
		}
		return rd;
	}
}
