package trouble;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Trouble {

	public static Player[] players;
	public static int playerTurn;	
	public static Scanner scanner = new Scanner(System.in);
	
	public static void main(String[] args) {
		printRules();
		initGame();
		
		while (true) {
			Board.draw();
			players[playerTurn].takeTurn();			
			if (checkWin(playerTurn)) break;
			playerTurn = (playerTurn + 1) % players.length;
		}
		
		Player winner = players[playerTurn];
		String winColor = winner.getEscapeSeq();
		
		System.out.println(winColor + "PLAYER " + winner.getIdentifier() + " WON THE GAME!");
		System.out.print(Color.getColorEnd());
		scanner.close();
	}
	
	private static boolean checkWin(int player) {
		boolean win = true;
		Peg[] finishLine = Board.finish[player];
		for (Peg peg : finishLine) {
			if (peg == null) win = false;
		}
		return win;
	}

	private static void initGame() {
		
		int numPlayers = numPlayersSelect();
		
		players = new Player[numPlayers];

		for (int i = 0; i < players.length; i++) {
			players[i] = new Player(i);
		}
		
		playerTurn = initRoll();
		
	}
	
	private static void printRules() {
		for (int i = 0; i < 30; i++) System.out.print("*");
		System.out.println();
		System.out.println("WELCOME TO THE GAME OF TROUBLE");
		System.out.println("The rules are simple");
		System.out.println("Players roll to see who goes first");
		System.out.println("All pegs start in HOME, in order to leave HOME the player must roll a 6");
		System.out.println("The game ends when a player has moved all of their pegs into their FINISH line");
		System.out.println("Players cannot move a peg onto a space occupied by another one of their pegs");
		System.out.println("If your peg lands on an opponent's peg, their peg is sent back HOME");
		for (int i = 0; i < 30; i++) System.out.print("*");
		System.out.println();
	}
	
	private static int numPlayersSelect() {
		int numPlayers = 0;
		while (numPlayers == 0) {
			System.out.println("How many players? (2-4)");			
			try {
				numPlayers = new Integer(scanner.nextLine());		
			} catch (NumberFormatException e) {
				//non-integer input	
			}
			if (numPlayers < 2 || numPlayers > 4) {
				System.out.println("Please enter a valid number of players.");
				
				numPlayers = 0;
			}
		}
		return numPlayers;
	}
	
	private static int initRoll() {
		
		System.out.println("Roll to see who goes first");
		List<Player> playersWithMaxRoll = new ArrayList<>();
		List<Player> playersToRemove = new ArrayList<>();
		ArrayList<Player> playersToRoll = new ArrayList<Player>(Arrays.asList(players));
		
		while (playersWithMaxRoll.size() != 1) {
			int maxRoll = 0;
			playersWithMaxRoll = new ArrayList<>();
			playersToRoll.removeAll(playersToRemove);
			for (Player player : playersToRoll) {
				int roll = player.roll();
				if (roll > maxRoll) {
					maxRoll = roll;
					playersToRemove.addAll(playersWithMaxRoll);
					playersWithMaxRoll = new ArrayList<Player>();
					playersWithMaxRoll.add(player);
				} else if(roll == maxRoll) {
					playersWithMaxRoll.add(player);
				} else {
					playersToRemove.add(player);
				}
			}
			if (playersWithMaxRoll.size() > 1) {
				System.out.println("There was a tie, lets roll some more!");
			}
		}
		
		int firstPlayer = playersWithMaxRoll.get(0).getIdentifier();
		System.out.println();
		System.out.println("Player " + firstPlayer + " goes first!");
		System.out.println();
		
		return firstPlayer - 1;
	}
}
