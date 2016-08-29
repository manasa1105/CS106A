/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Hangman extends ConsoleProgram {
	HangmanLexicon lex;
	RandomGenerator rgen;
	String hiddenWord;
	String guessWord = "";
	int guesses = 8;
	private HangmanCanvas canvas;
	
	public void init() {
        canvas = new HangmanCanvas();
        add(canvas);
	}

    public void run() {
    	println("Welcome to Hangman.");
    	lex = new HangmanLexicon();
    	rgen = new RandomGenerator();
    	generateRandomWord();
    	canvas.reset();
    	canvas.displayWord(guessWord);
    	while (!gameOver()) {
    		numberOfGuessesLeft();
        	playGame();
    	}
    	
	}
    /*Chooses a random word from the HangmanLexicon file and 
     * creates a slot pattern available for guessing*/
    private void generateRandomWord() {
       	int index = rgen.nextInt(0, lex.getWordCount() - 1);
    	hiddenWord = lex.getWord(index);
    	for (int i=0; i<hiddenWord.length(); i++) {
    		guessWord += '-';
    	}
    	println("The word now looks like this: "+guessWord);
    	canvas.displayWord(guessWord);
//    	println("hiddenWord: "+hiddenWord);
    }
	
	
	/*takes the guess character and plays the game*/
	private void playGame() {
			String character = readLine("Your guess: ");
			char ch1 = character.charAt(0);
			char ch = Character.toUpperCase(ch1);
			if (character.length()>1) {
				println("Illegal guess");
				wrongGuess(ch);
			}
			searchGuess(ch);
	}
	
	/*searches for the availability of the guessed character in the word*/
	private void searchGuess(char ch) {
		boolean matchFound = false;
		for (int i=0; i<hiddenWord.length(); i++) {
			if (ch==hiddenWord.charAt(i)) {
				rightGuess(i, ch);
				matchFound=true;
			}
		}
		if (!matchFound) {
			wrongGuess(ch);
		}
		else if (matchFound) {
			println("The guess is correct.");
			println("The word now looks like this: "+guessWord);
			canvas.displayWord(guessWord);
		}
	}
	
	
	/*determines the number of right guesses*/
	private void rightGuess(int i, char ch) {
		guessWord = guessWord.substring(0, i) 
				+ ch + guessWord.substring(i+1);
	}
	
	
	/*determines the number of wrong guesses*/
	private void wrongGuess(char ch) {
		println("There are no "+ch+"'s in the word.");
		guesses--;
		numberOfGuessesLeft();
		canvas.noteIncorrectGuess(ch);
		if (guesses>0) {
			println("The word now looks like this: "+guessWord);
			playGame();
		}
	}
	
	
    /*Calculates the number of guesses available for the player*/
	private void numberOfGuessesLeft() {
		if (guesses>0) {
			println("You have "+guesses+" guesses left");
		}
		else {
			println("You are completely hung.");
			println("The word was: "+hiddenWord);
		}	
	}
	
	
	/*Determines when the game is over*/
	private boolean gameOver() {
		if (guesses==0) {
			println("You loose");
			return true;
		}
		else if (guessWord.equals(hiddenWord)==true) {
			println("You Win");
			return true;
		}
		else return false;
	}
	
	
}




