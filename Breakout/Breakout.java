/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels.  On some platforms 
  * these may NOT actually be the dimensions of the graphics canvas. */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board.  On some platforms these may NOT actually
  * be the dimensions of the graphics canvas. */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;
/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

/** Number of turns */
	private static final int NTURNS = 3;
	
	private static final int BRICKS_TO_GAMEOVER = 100;
	
	private GRect bricks;
	private GRect paddle;
	private GOval ball;
	private double vy = 3.0;
	private double vx;
	private RandomGenerator rgen = new RandomGenerator();
	private static final int delay = 10;
	AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
	int paddleHitCount = 0;
	int brickHitCount = 0;
	int ballBelowPaddleCount;
	private GLabel label;
	private int score = 0;

/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		setupGame();
		addMouseListeners();
		playGame();
		remove(ball);
	}
	
	private void setupGame() {
		setupBricks();
		setupPaddle();
	}
	
	private void setupBricks() {
		int x_start = (getWidth() -((NBRICKS_PER_ROW*BRICK_WIDTH)+(NBRICKS_PER_ROW*BRICK_SEP)))/2;
		int y_start = BRICK_Y_OFFSET;
		
		for(int j=0; j < NBRICK_ROWS; j++) {
			bricksInRow(x_start, y_start, getColor(j));
			y_start = y_start+(BRICK_HEIGHT)+(BRICK_SEP);
		}
	}
	
	private void bricksInRow(int x_start, int y_start, Color brick_color) {
		for(int j=0; j < NBRICKS_PER_ROW; j++) {
			bricks = new GRect(x_start+(j*BRICK_WIDTH)+(j*BRICK_SEP), y_start, BRICK_WIDTH, BRICK_HEIGHT);
			bricks.setFilled(true);
			bricks.setColor(brick_color);
			add(bricks);
		}
	}
	
	private Color getColor(int row) {
		switch (row%NBRICK_ROWS) {
		case 0:
		case 1:return Color.RED;
		case 2:
		case 3:return Color.ORANGE;
		case 4:
		case 5:return Color.YELLOW;
		case 6:
		case 7:return Color.GREEN;
		case 8:
		case 9:return Color.CYAN;
		default:return null;
		}
	}
	
	private void setupPaddle() {
		int x_start = (getWidth()-PADDLE_WIDTH)/2;
		int y_start = getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT;
		paddle = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		paddle.setFilled(true);
		add(paddle, x_start, y_start);
	}
	
	public void mouseMoved(MouseEvent e) {
		if (e.getX()<=(getWidth()-PADDLE_WIDTH)) {
			paddle.setLocation(e.getX(), getHeight() - PADDLE_Y_OFFSET - PADDLE_HEIGHT);
		}
	}
	
	private void playGame() {
	if (ballBelowPaddleCount!=3) {
		setupBall();
		waitForClick();
		moveBall();
		}
	}
	
	private void setupBall() {
		ball = new GOval(BALL_RADIUS, BALL_RADIUS);
		ball.setFilled(true);
		add(ball, (getWidth()-(2*BALL_RADIUS))/2, (getHeight()-(2*BALL_RADIUS))/2);
	}
	
	private void moveBall() {
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5)) {
			vx = -vx;
		}
		while (!gameOver()) {
			ball.move(vx, vy);
			bounce();
			collisions();
			pause(delay);
		}
	}
	
/*	private boolean ballInCanvas() {
		return (ball.getX()>=0 && (ball.getX()+(2*BALL_RADIUS))<=getWidth() && 
				ball.getY()>=0 && (ball.getY()+(2*BALL_RADIUS))<=getHeight());
	}*/
	
	private void bounce() {
		if (ball.getY()<=0) {
			ball.move(0, -(ball.getY()));
			vy = -vy;
		}
		
		if ((ball.getY()+(2*BALL_RADIUS))>=getHeight()) {
			ballBelowPaddleCount++;
			remove(ball);
			if (ballBelowPaddleCount<3) {
				printLabel("TRY AGAIN");
				waitForClick();
				remove(label);
				playGame();
			}
		}

		else if ((ball.getX()+(2*BALL_RADIUS))>=getWidth()) {
			ball.move(-(ball.getX() - getWidth() + (2*BALL_RADIUS)), 0);
			vx = -vx;
		}
		else if (ball.getX()<=0) {
			ball.move(-(ball.getX()), 0);
			vx = -vx;
		}
	}
	
	private void collisions() {
		GObject collider = getCollidingObject();
		if (collider==paddle) {
			colloidPaddle();
		}
		else if (collider!=null){
			colloidBricks(collider);
		}
	}
	
	private void colloidPaddle() {
		vy = -vy;
		paddleHitCount++;
		if (paddleHitCount==7) {
			vx = 2*vx;
		}
	}
	
	private void colloidBricks(GObject brick) {
		bounceClip.play();
		remove(brick);
		brickHitCount++;
		if (brickHitCount==BRICKS_TO_GAMEOVER) {
			gameOver();
		}
		vy = -vy;
	}
	
	private GObject getCollidingObject() {
		if (getElementAt(ball.getX(), ball.getY())!=null) {
			return getElementAt(ball.getX(), ball.getY());
		}
		else if (getElementAt(ball.getX()+(2*BALL_RADIUS), ball.getY())!=null){
			return getElementAt(ball.getX()+(2*BALL_RADIUS), ball.getY());
		}
		else if (getElementAt(ball.getX()+(2*BALL_RADIUS), ball.getY()+(2*BALL_RADIUS))!=null){
			return getElementAt(ball.getX()+(2*BALL_RADIUS), ball.getY());
		}
		else if (getElementAt(ball.getX(), ball.getY()+(2*BALL_RADIUS))!=null){
			return getElementAt(ball.getX()+(2*BALL_RADIUS), ball.getY());
		}
		else {
			return null;
		}
	}
	
	private boolean gameOver() {
		if (ballBelowPaddleCount==3) {
			printLabel("YOU LOOSE!");
			return true;
		}
		else if (brickHitCount==100) {
			printLabel("YOU WIN!");
			return true;
		}
		else {
			return false;
		}
	}
	
	private void printLabel(String labelvalue) {
		label = new GLabel (labelvalue);
		label.setFont("SansSerif-50-BOLD");
		add(label, (getWidth()-label.getWidth())/2, (getHeight()-label.getAscent())/2);
	}
}






