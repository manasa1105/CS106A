import acm.program.*;
import acm.util.*;

public class Craps extends ConsoleProgram {
	private RandomGenerator rgen = new RandomGenerator();
	
	public void run() {
		int total = rollTwoDice();
		if (total==7 || total==11) {
			println("That's a natural. You win!");
		}
		else if (total==2 || total==3 || total==12) {
			println("That's a Crap. You lose!");
		}
		println("Your point is: "+total);
		int point = total;
		while (true) {
			total = rollTwoDice();
			if (total==point) {
				println("You made your point. You Win!");
				break;
			}
			else if (total==7) {
				println("That's a 7. You lose");
				break;
			}
		}
	}
	
	private int rollTwoDice() {
		int dice1 = rgen.nextInt(1,6);
		int dice2 = rgen.nextInt(1,6);
		println("Rolling Dice = "+dice1+" + "+dice2+" = "+(dice1+dice2));
		return dice1 + dice2;
	}

}
