package trouble;

public class Board {
	public static Peg[] track = new Peg[28];
	public static Peg[][] home = new Peg[4][4];
	public static Peg[][] finish = new Peg[4][4];
	
	public static void draw() {
		printTopHome();
		printTopRow();
		printSmallDiagonalTop();
		printBigDiagonalTop();
		printColumns();
		printBigDiagonalBottom();
		printSmallDiagonalBottom();
		printBottomRow();
		printBottomHome();
	}
	
	public static void printTopRow() {
		for (int i = 0; i < 5; i++) System.out.print(" ");
		for (int i = 0; i < 5; i++) {
			printPeg(i);
			System.out.print(" ");		
		}
		System.out.println();
	}
	
	public static void printBottomRow() {
		for (int i = 0; i < 5; i++) System.out.print(" ");
		for (int i = 0; i < 5; i++) {
			printPeg(18-i);
			System.out.print(" ");		
		}	
		System.out.println();
	}
	
	public static void printColumns() {
		for (int i = 0; i < 5; i++) {
			if (i == 1 || i == 3) {
				printPegHome(3, i-1);
				System.out.print(" ");
				printPeg(25-i);
				for (int j = 0; j < 6; j++) System.out.print(" ");
				printPegFinish(i-1,3);
				for (int j = 0; j < 6; j++) System.out.print(" ");
				printPeg(7+i);		
				System.out.print(" ");
				printPegHome(1, i-1);
			} else if (i == 2) {
				//HOME 3
				printPegHome(3, 1);
				printPegHome(3, 3);
				printPeg(25-i);
				for (int j = 0; j < 4; j++) printPegFinish(3,j);
				for (int j = 0; j < 5; j++) System.out.print(" ");
				for (int j = 4; j > 0; j--) printPegFinish(1,j-1);
				printPeg(7+i);
				//HOME 1
				printPegHome(1, 3);
				printPegHome(1, 1);
			} else if (i == 0 || i == 4) {
				for (int j = 0; j < 2; j++) System.out.print(" ");	
				printPeg(25-i);
				for (int j = 0; j < 6; j++) System.out.print(" ");				
				if (i == 0) printPegFinish(0,2);
				else printPegFinish(2,2);
				for (int j = 0; j < 6; j++) System.out.print(" ");
				printPeg(7+i);				
			}			
			System.out.println();
		}
	}
	
	public static void printSmallDiagonalTop() {
		for (int i = 0; i < 4; i++) System.out.print(" ");
		printPeg(27);
		for (int i = 0; i < 4; i++) System.out.print(" ");
		printPegFinish(0,0);
		for (int i = 0; i < 4; i++) System.out.print(" ");
		printPeg(5);
		System.out.println();
	}
	
	public static void printSmallDiagonalBottom() {
		for (int i = 0; i < 4; i++) System.out.print(" ");
		printPeg(19);
		for (int i = 0; i < 4; i++) System.out.print(" ");
		printPegFinish(2,0);
		for (int i = 0; i < 4; i++) System.out.print(" ");
		printPeg(13);
		System.out.println();
	}
	
	public static void printBigDiagonalTop() {
		for (int i = 0; i < 3; i++) System.out.print(" ");
		printPeg(26);
		for (int i = 0; i < 5; i++) System.out.print(" ");
		printPegFinish(0,1);
		for (int i = 0; i < 5; i++) System.out.print(" ");
		printPeg(6);
		System.out.println();
	}
	
	public static void printBigDiagonalBottom() {
		for (int i = 0; i < 3; i++) System.out.print(" ");
		printPeg(20);
		for (int i = 0; i < 5; i++) System.out.print(" ");
		printPegFinish(2,1);
		for (int i = 0; i < 5; i++) System.out.print(" ");
		printPeg(12);
		System.out.println();
	}
	
	//HOME 0
	public static void printTopHome() {
		for (int i = 0; i < 8; i++) System.out.print(" ");
		for (int i = 0; i < 3; i++) printPegHome(0, i);
		System.out.println();
		for (int i = 0; i < 9; i++) System.out.print(" ");
		printPegHome(0, 3);
		System.out.println();
	}
	
	//HOME 2
	public static void printBottomHome() {		
		for (int i = 0; i < 9; i++) System.out.print(" ");
		printPegHome(2, 3);	
		System.out.println();
		for (int i = 0; i < 8; i++) System.out.print(" ");
		for (int i = 0; i < 3; i++) printPegHome(2, i);
		System.out.println();
	}
	
	public static void printPeg(int space) {
		Peg peg = track[space];
		if (peg == null) {
			System.out.print("X");
		} else {		
			String escapeSeq = Trouble.players[peg.getPlayer()].getEscapeSeq();
			//String escapeSeq = "";
			System.out.print(escapeSeq + peg.getIdentifier());
			System.out.print(Color.getColorEnd());
		}
	}
	
	public static void printPegHome(int homeLoc, int space) {
		Peg peg = home[homeLoc][space];
		String escapeSeq = "";
		try {
			escapeSeq = Trouble.players[homeLoc].getEscapeSeq();
		} catch (ArrayIndexOutOfBoundsException e) {
			//player DNE
		}
		if (peg == null) {
			System.out.print(escapeSeq + "+");
		} else {		
					
			System.out.print(escapeSeq + peg.getIdentifier());			
		}
		System.out.print(Color.getColorEnd());
	}
	
	public static void printPegFinish(int finishLoc, int space) {
		Peg peg = finish[finishLoc][space];
		String escapeSeq= "";
		try {
			escapeSeq = Trouble.players[finishLoc].getEscapeSeq();
		} catch (ArrayIndexOutOfBoundsException e) {
			//player DNE
		}
		if (peg == null) {
			System.out.print(escapeSeq + "O");
		} else {			
			System.out.print(escapeSeq + peg.getIdentifier());			
		}
		System.out.print(Color.getColorEnd());
	}
	
}
