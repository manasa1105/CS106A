/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.util.ArrayList;
import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		scoreArr = new int [N_CATEGORIES][nPlayers];
		for (int i = 0; i < N_CATEGORIES-1; i++) {
			for(int j = 0; j < nPlayers; j++) {
				scoreArr[i][j] = -1;
			}
		}
		
		for(int round = 0; round < N_SCORING_CATEGORIES; round++) {
			playGame();
		}
		for (int player = 1; player <= nPlayers; player++) {
			updateUpperScore(player);
			updateLowerScore(player);
		}
		declareWinner();
	}

	private void playGame() {
		int[] dice = new int[N_DICE];
		for (int player = 1 ; player <= nPlayers; player++) {
			display.printMessage(playerNames[player-1]+"'s turn!. Click the \"Roll Dice\" button to roll the dice.");
			
				display.waitForPlayerToClickRoll(player);
				for(int j = 0; j < N_DICE; j++) {
					dice[j] = rgen.nextInt(MIN_DIE_ROLL, MAX_DIE_ROLL);
				}
				display.displayDice(dice);
				rerollDice(dice);
				checkingTheCategory(dice, player);
		}
	}
	
	private void rerollDice(int[] dice) {
		int REROLL_CHANCE = 2;
		for(int i = 0; i < REROLL_CHANCE; i++) {
			display.printMessage("Select the dice you wish to re-roll and press \"Roll Again\"");
			display.waitForPlayerToSelectDice();
			for(int j = 0; j < N_DICE; j++) {
				if (display.isDieSelected(j)) {
					dice[j] = rgen.nextInt(MIN_DIE_ROLL, MAX_DIE_ROLL);
				}
			}
			display.displayDice(dice);
		}
	}
	
	private void checkingTheCategory(int[] dice, int player) {
		display.printMessage("Select a category for this roll");
		int category = display.waitForPlayerToSelectCategory();
		while(scoreArr[category-1][player-1] != (-1)) {
			display.printMessage("You already picked the category. Please choose another category");
			category = display.waitForPlayerToSelectCategory();
		}
		if(checkCategory(dice, category)) {
			int score = getScore(dice, category, player);
			display.updateScorecard(category, player, score);
			displayTotal(score, player);
		}
		else{
			display.updateScorecard(category, player, 0);
			scoreArr[category-1][player-1] = 0;
		}
		
	}
		
/*	private boolean checkCategory(int[] dice, int category) {
		int count;
		ArrayList<Integer> diceArrList;
		switch(category) {
			case ONES: 
			case TWOS:
			case THREES:
			case FOURS:
			case FIVES:
			case SIXES:
			case CHANCE:
				return true;
			case THREE_OF_A_KIND:
				for(int j = 0; j < N_DICE-1; j++) {
					count = 1;
					for(int i = j+1; i < N_DICE; i++) {
						if(dice[j] == dice[i]) {
							count++;
							if(count >= (N_DICE-2)) return true;
						}
					}
				}
				break;
			case FOUR_OF_A_KIND:
				for(int j = 0; j < N_DICE-1; j++) {
					count = 1;
					for(int i = j+1; i < N_DICE; i++) {
						if(dice[j] == dice[i]) {
							count++;
							if(count >= (N_DICE-1)) return true;
						}
					}
				}
				break;
			case FULL_HOUSE:
				boolean count2 = false;
				boolean count3 = false;
				for(int i = 0; i < N_DICE; i++) {
					count = 1;
					for(int j = i+1; j < N_DICE; j++) {
						if(dice[i] == dice[j]) {
							count++;
						}
					}
					if (count == 2) count2 = true;
					else if(count == 3) count3 = true;
					
					if(count2 && count3) return true;
				}
				break;
			case SMALL_STRAIGHT:
				count = 0;
				diceArrList = new ArrayList<Integer>();
				for(int i = 0; i<N_DICE; i++) {
					diceArrList.add(dice[i]);
				}
				for(int i = 1; i < 5; i++) {
					if(diceArrList.contains(i)) {
						count++;
						if(count==4) return true;
					}
				}
				for(int i = 2; i < 6; i++) {
					if(diceArrList.contains(i)) {
						count++;
						if(count==4) return true;
					}
				}
				for(int i = 3; i < 7; i++) {
					if(diceArrList.contains(i)) {
						count++;
						if(count==4) return true;
					}
				}
				break;
			case LARGE_STRAIGHT:
				count = 0;
				diceArrList = new ArrayList<Integer>();
				for(int i = 0; i<N_DICE; i++) {
					diceArrList.add(dice[i]);
				}
				for(int i = 1; i < 6; i++) {
					if(diceArrList.contains(i)) {
						count++;
						if(count==5) return true;
					}
				}
				count = 0;
				for(int i = 2; i < 7; i++) {
					if(diceArrList.contains(i)) {
						count++;
						if(count==5) return true;
					}
				}
				break;
			case YAHTZEE: 
				count = 1;
				for(int i = 1; i < N_DICE; i++) {
					if(dice[i] == dice[0]) {
						count++;
					}
				}
				if (count==N_DICE) return true;
				break;
		}
		return false;
	} */
	
	private boolean checkCategory(int[] dice, int category) {
		int num1=0;
		int num2=0;
		int num3=0;
		int num4=0;
		int num5=0;
		int num6=0;
		for(int i = 0; i<N_DICE; i++) {
			if (dice[i]==1) num1++;
			else if(dice[i]==2) num2++;
			else if(dice[i]==3) num3++;
			else if(dice[i]==4) num4++;
			else if(dice[i]==5) num5++;
			else if(dice[i]==6) num6++;
		}
		switch(category) {
			case ONES: 
			case TWOS:
			case THREES:
			case FOURS:
			case FIVES:
			case SIXES:
			case CHANCE: return true;
			case THREE_OF_A_KIND:
				if ((num1>=3)||(num2>=3)||(num3>=3)||(num4>=3)||(num5>=3)||(num6>=3)) 
					return true;
			case FOUR_OF_A_KIND:
				if ((num1>=4)||(num2>=4)||(num3>=4)||(num4>=4)||(num5>=4)||(num6>=4)) 
					return true;
			case YAHTZEE:
				if ((num1==5)||(num2==5)||(num3==5)||(num4==5)||(num5==5)||(num6==5)) 
					return true;
			case FULL_HOUSE:
				if (((num1==2)||(num2==2)||(num3==2)||(num4==2)||(num5==2)||(num6==2)) &&
					((num1==3)||(num2==3)||(num3==3)||(num4==3)||(num5==3)||(num6==3))) 
					return true;
			case SMALL_STRAIGHT:
				if((num1>=1)&&(num2>=1)&&(num3>=1)&&(num4>=1) || 
					(num2>=1)&&(num3>=1)&&(num4>=1)&&(num5>=1) ||
					(num3>=1)&&(num4>=1)&&(num5>=1)&&(num6>=1)) return true;
			case LARGE_STRAIGHT:
				if((num1==1)&&(num2==1)&&(num3==1)&&(num4==1)&&(num5==1) || 
					(num2==1)&&(num3==1)&&(num4==1)&&(num5==1)&&(num6==1)) return true;
		}
		return false;
	}

	private int getScore(int[] dice, int category, int player) {
		int score = 0;
		switch(category) {
			case ONES: 
			case TWOS:
			case THREES:
			case FOURS:
			case FIVES:
			case SIXES: 
				for(int j = 0; j < N_DICE; j++) {
					if(dice[j] == category) {
						score += dice[j];
					}
				}
				break;
			case THREE_OF_A_KIND: 
			case FOUR_OF_A_KIND: 
				for(int j = 0; j < N_DICE; j++) {
				 	score += dice[j];
				}
			 	break;
			case FULL_HOUSE: score = 25; break;
			case SMALL_STRAIGHT: score = 30; break;
			case LARGE_STRAIGHT: score = 40; break;
			case YAHTZEE: score = 50; break;
			case CHANCE: 
				for(int j = 0; j < N_DICE; j++) {
					score += dice[j];
		 		}
		 		break;
		}
		scoreArr[category-1][player-1] = score;
		return score;
	}
	
	private void displayTotal(int score, int player) {
		scoreArr[TOTAL-1][player-1] += score;
		display.updateScorecard(TOTAL, player, scoreArr[TOTAL-1][player-1]);
	}
	
	private void updateUpperScore(int player) {
		scoreArr[UPPER_SCORE-1][player-1] = 0;  //initializing to zero
		for (int i = 0; i < SIXES; i++) {
			scoreArr[UPPER_SCORE-1][player-1] += scoreArr[i][player-1];
		}
		display.updateScorecard(UPPER_SCORE, player, scoreArr[UPPER_SCORE-1][player-1]);
		updateUpperBonus(scoreArr[UPPER_SCORE-1][player-1], player);
	}
	
	private void updateUpperBonus(int upperScore, int player) {
		if(upperScore > 63) {
			scoreArr[UPPER_BONUS-1][player-1] = 35;
			display.updateScorecard(UPPER_BONUS, player, scoreArr[UPPER_BONUS-1][player-1]);
		}
		else {
			display.updateScorecard(UPPER_BONUS, 1, 0);
		}
		displayTotal(scoreArr[UPPER_BONUS-1][player-1], player);
	}
	
	private void updateLowerScore(int player) {
		scoreArr[LOWER_SCORE-1][player-1] = 0;
		for (int i = THREE_OF_A_KIND-1; i < CHANCE; i++) { 
			scoreArr[LOWER_SCORE-1][player-1] += scoreArr[i][player-1];
		}
		display.updateScorecard(LOWER_SCORE, player, scoreArr[LOWER_SCORE-1][player-1]);
	}
	
	private void declareWinner() {
		int winner = 0;
		int max = 0;
		for(int player = 1; player <= nPlayers; player++) {
			if(max < scoreArr[TOTAL-1][player-1]) {
				max = scoreArr[TOTAL-1][player-1];
				winner = player;
			}
		}
		display.printMessage("Congratulations, "+playerNames[winner-1]+"! "
				+ "You're the winner with a total score of "+scoreArr[TOTAL-1][winner-1]);
	}

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();
	private int MIN_DIE_ROLL = 1;
	private int MAX_DIE_ROLL = 6;
	int[][] scoreArr;

}

