import acm.program.*;
import acm.graphics.*;
import java.awt.event.*;
import java.awt.*;

public class UFO extends GraphicsProgram {
	private static final int UFO_WIDTH = 50;
	private static final int UFO_HEIGHT = 20;
	private static int ufodx = 5;
	private static final int DELAY = 20;
	private static final int X_START = 0;
	private static final int Y_START = 0;
	private GRect ufo;
	private GOval bullet;
	private static final int BULLET_DIAMETER = 10;
	private static final int BULLET_SPEED = 10;
	boolean moveUfoRight=true;
	private GLabel label;
	
	
	public void run() {
		setupUFO();
		while (!gameOver()) {
			moveUFO();
			moveBullet();
			findCollisions();
			pause(DELAY);
		}
	}
	
	//game over
	private boolean gameOver() {
		return (ufo == null) ||
				(ufo.getY() >= getHeight() - UFO_HEIGHT);
	}
	
	//setup the ufo block at the initial position
	private void setupUFO() {
		ufo = new GRect(UFO_WIDTH, UFO_HEIGHT);
		add(ufo, X_START, Y_START);
		addMouseListeners();
	}
	
	//setup the bullet in the form of an oval
	private void setupBullet() {
		bullet = new GOval(BULLET_DIAMETER/2, BULLET_DIAMETER);
		add(bullet);
		bullet.setLocation((getWidth()-BULLET_DIAMETER)/2, (getHeight()-BULLET_DIAMETER));
	}
	
	//creates bullet when the mouse is clicked and if no bullet exists already
	public void mouseClicked(MouseEvent e) {
		while (bullet == null) {
			setupBullet();
		}
	}
	
	//moves bullet
	private void moveBullet() {
		if (bullet != null) {
			bullet.move(0, -BULLET_SPEED);
		}
	}
	
	//move ufo
	private void moveUFO() {
		if (moveUfoRight) {
			ufo.move(ufodx, 0);
			if (ufo.getX() >= getWidth() - UFO_WIDTH) {
				moveUfoRight=false;
				ufo.move(0, UFO_HEIGHT);
			}
			pause(DELAY);
		}
		else {
			ufo.move(-ufodx, 0);
			if (ufo.getX() <= 0) {
				moveUfoRight=true;
				ufo.move(0, UFO_HEIGHT);
			}
			pause(DELAY);
		}
		
	}
	
	//finds collisions
	private void findCollisions() {
		collidesWithUfo();
		moveOffScreen();
	}
	
	private void collidesWithUfo() {
 		if (bullet!=null) {
			GObject obj = getElementAt(bullet.getX(), bullet.getY());
			if (obj==ufo) {
				remove(ufo);
				remove(bullet);
				ufo=null;
				bullet=null;
				label = new GLabel ("YOU WIN!");
				label.setFont("Helvetica-50-bold");
				label.setColor(Color.red);
				add(label, (getWidth()-label.getWidth())/2, (getHeight()-label.getAscent())/2);
			}
		}
	}
	
	
	private void moveOffScreen() {
		if (bullet != null) {
		if (bullet.getY() <= -BULLET_DIAMETER) {
			remove(bullet);
			bullet = null;
			}
		}
	}
}









