/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

/** Resets the display so that only the scaffold appears */
	public void reset() {
		x1 = getWidth()/8;
		y1 = (getHeight()*3)/4;
		GLine scaffold = new GLine(x1, y1,
				x1, y1-SCAFFOLD_HEIGHT);
		add(scaffold);
		
		GLine beam = new GLine(x1 , y1-SCAFFOLD_HEIGHT,
				x1+BEAM_LENGTH , y1-SCAFFOLD_HEIGHT);
		add(beam);
		
		GLine rope = new GLine(x1+BEAM_LENGTH, y1-SCAFFOLD_HEIGHT,
				x1+BEAM_LENGTH, (y1-SCAFFOLD_HEIGHT)+ROPE_LENGTH);
		add(rope);
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		if (label!=null) {
			remove(label);
		}
		label = new GLabel(word);
		label.setFont("Times-20");
		add (label, (getWidth()-label.getWidth())/2, 
				SCAFFOLD_HEIGHT+50);
	}


/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char ch) {
		count++;
		if (incorrectGuess!=null) {
			remove(incorrectGuess);
		}
		
		wrongGuessesList += ch;
		incorrectGuess = new GLabel(wrongGuessesList);
		add(incorrectGuess, (getWidth()-label.getWidth())/2, 
				SCAFFOLD_HEIGHT+100);
		bodyParts(count);
	}
	
	private void bodyParts(int i) {
		switch (i) {
		case 1: GOval head = new GOval((x1+BEAM_LENGTH)-HEAD_RADIUS, 
				(y1-SCAFFOLD_HEIGHT)+ROPE_LENGTH,
				2*HEAD_RADIUS, 2*HEAD_RADIUS);
			add(head);
			break;
			
		case 2: GLine body = new GLine(x1+BEAM_LENGTH,
				(y1-SCAFFOLD_HEIGHT)+ROPE_LENGTH+(2*HEAD_RADIUS),
				(x1+BEAM_LENGTH), 
				y1-SCAFFOLD_HEIGHT+ROPE_LENGTH+(2*HEAD_RADIUS)+BODY_LENGTH);
			add(body);
			break;
			
		case 3: GLine leftArm = new GLine(x1+BEAM_LENGTH,
				(y1-SCAFFOLD_HEIGHT)+ARM_OFFSET_FROM_HEAD+(2*HEAD_RADIUS),
				x1+BEAM_LENGTH-UPPER_ARM_LENGTH,
				(y1-SCAFFOLD_HEIGHT)+ARM_OFFSET_FROM_HEAD+(2*HEAD_RADIUS));
			
				GLine leftPalm = new GLine(x1+BEAM_LENGTH-UPPER_ARM_LENGTH,
				(y1-SCAFFOLD_HEIGHT)+ARM_OFFSET_FROM_HEAD+(2*HEAD_RADIUS),
				x1+BEAM_LENGTH-UPPER_ARM_LENGTH,
				(y1-SCAFFOLD_HEIGHT)+ARM_OFFSET_FROM_HEAD+(2*HEAD_RADIUS)+LOWER_ARM_LENGTH);
			add(leftArm);
			add(leftPalm);
			break;
			
		case 4: GLine rightArm = new GLine(x1+BEAM_LENGTH,
				(y1-SCAFFOLD_HEIGHT)+ARM_OFFSET_FROM_HEAD+(2*HEAD_RADIUS),
				x1+BEAM_LENGTH+UPPER_ARM_LENGTH,
				(y1-SCAFFOLD_HEIGHT)+ARM_OFFSET_FROM_HEAD+(2*HEAD_RADIUS));
		
				GLine rightPalm = new GLine(x1+BEAM_LENGTH+UPPER_ARM_LENGTH,
				(y1-SCAFFOLD_HEIGHT)+ARM_OFFSET_FROM_HEAD+(2*HEAD_RADIUS),
				x1+BEAM_LENGTH+UPPER_ARM_LENGTH,
				(y1-SCAFFOLD_HEIGHT)+ARM_OFFSET_FROM_HEAD+(2*HEAD_RADIUS)+LOWER_ARM_LENGTH);
			add(rightPalm);
			add(rightArm);
			break;
			
		case 5: GLine leftHip = new GLine((x1+BEAM_LENGTH), 
				y1-SCAFFOLD_HEIGHT+ROPE_LENGTH+(2*HEAD_RADIUS)+BODY_LENGTH,
				(x1+BEAM_LENGTH)-(HIP_WIDTH/2),
				y1-SCAFFOLD_HEIGHT+ROPE_LENGTH+(2*HEAD_RADIUS)+BODY_LENGTH);
				
				GLine leftLeg = new GLine((x1+BEAM_LENGTH)-(HIP_WIDTH/2),
				y1-SCAFFOLD_HEIGHT+ROPE_LENGTH+(2*HEAD_RADIUS)+BODY_LENGTH,
				(x1+BEAM_LENGTH)-(HIP_WIDTH/2),
				y1-SCAFFOLD_HEIGHT+ROPE_LENGTH+(2*HEAD_RADIUS)+BODY_LENGTH+LEG_LENGTH);
			add(leftLeg);
			add(leftHip);
			break;
			
		case 6: GLine rightHip = new GLine((x1+BEAM_LENGTH), 
				y1-SCAFFOLD_HEIGHT+ROPE_LENGTH+(2*HEAD_RADIUS)+BODY_LENGTH,
				(x1+BEAM_LENGTH)+(HIP_WIDTH/2),
				y1-SCAFFOLD_HEIGHT+ROPE_LENGTH+(2*HEAD_RADIUS)+BODY_LENGTH);
		
				GLine rightLeg = new GLine((x1+BEAM_LENGTH)+(HIP_WIDTH/2),
				y1-SCAFFOLD_HEIGHT+ROPE_LENGTH+(2*HEAD_RADIUS)+BODY_LENGTH,
				(x1+BEAM_LENGTH)+(HIP_WIDTH/2),
				y1-SCAFFOLD_HEIGHT+ROPE_LENGTH+(2*HEAD_RADIUS)+BODY_LENGTH+LEG_LENGTH);
			add(rightLeg);
			add(rightHip);
			break;
			
		case 7: GLine leftFoot = new GLine((x1+BEAM_LENGTH)-(HIP_WIDTH/2),
				y1-SCAFFOLD_HEIGHT+ROPE_LENGTH+(2*HEAD_RADIUS)+BODY_LENGTH+LEG_LENGTH,
				(x1+BEAM_LENGTH)-(HIP_WIDTH/2)-FOOT_LENGTH,
				y1-SCAFFOLD_HEIGHT+ROPE_LENGTH+(2*HEAD_RADIUS)+BODY_LENGTH+LEG_LENGTH);
			add(leftFoot);
			break;
			
		case 8: GLine rightFoot = new GLine((x1+BEAM_LENGTH)+(HIP_WIDTH/2),
				y1-SCAFFOLD_HEIGHT+ROPE_LENGTH+(2*HEAD_RADIUS)+BODY_LENGTH+LEG_LENGTH,
				(x1+BEAM_LENGTH)+(HIP_WIDTH/2)+FOOT_LENGTH,
				y1-SCAFFOLD_HEIGHT+ROPE_LENGTH+(2*HEAD_RADIUS)+BODY_LENGTH+LEG_LENGTH);
			add(rightFoot);
			break;
			
		default: reset();
		}
	}
	

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	double x1;
	double y1;
	int guesses = 0;
	GLabel label;
	GLabel incorrectGuess;
	int count = 0;
	String wrongGuessesList = "";

}
