package trouble;

import java.util.Random;

public class Player {	
	
	private Peg pegs[] = new Peg[4]; 
	private int identifier;
	private int internalId;
	private String playerStr; 		
	
	Player(int iter) {
		this.identifier = iter + 1;
		this.internalId = iter;
		for (int j = 0; j < 4; j++) {
			Peg peg = new Peg();
			peg.setPlayer(iter);			
			peg.setIdentifier(new Integer(j+1).toString());
			peg.setInternalId(j);
			peg.setSpace(Peg.HOME);
			Board.home[iter][j] = peg;
			pegs[j] = peg;					
		}
		playerStr = this.getEscapeSeq() + "Player " + this.identifier + Color.getColorEnd();
	}
	
	public int roll() {	
		System.out.println(playerStr + " press enter to roll");
		Trouble.scanner.nextLine();
		Random random = new Random();
		int roll = random.nextInt(6) + 1;
		System.out.println(playerStr + " rolls a " + roll);
		
		return roll;
	}
		
	public void takeTurn() {
		System.out.println(playerStr + " it is your turn");
		int roll = roll();
		Move move = new Move(this, roll);
		if (move.noValidMoves()) {
			System.out.println(playerStr + " doesn't have any valid moves.");
		} else {
			boolean validPeg = false;
			int pegId = -1;
			while (!validPeg) {
				System.out.println(playerStr + " which peg would you like to move");				
				while (pegId == -1) {
					try {						
						String str = Trouble.scanner.nextLine();
						pegId = Integer.parseInt(str);
					} catch (NumberFormatException e) {
						//non-integer input
					}
					if (pegId < 1 || pegId > 4) {
						System.out.println("Please enter a valid peg id.");
						pegId = -1;
					}
				}
				Peg peg = pegs[pegId-1];
				move.setPeg(peg);
				if (move.validMove()) {
					move.move();
					validPeg = true;
				}
				else {
					System.out.println(playerStr + " you cannot move this peg");
					pegId = -1;
				}
			}
			
		}
		System.out.println(playerStr + "'s turn is over");
	}
	
	
	public Peg[] getPegs() {
		return pegs;
	}
	public void setPegs(Peg[] pegs) {
		this.pegs = pegs;
	}
	public int getIdentifier() {
		return identifier;
	}
	public void setIdentifier(int identifier) {
		this.identifier = identifier;
	}
	public int getInternalId() {
		return internalId;
	}
	public void setInternalId(int internalId) {
		this.internalId = internalId;
	}
	public String getPlayerStr() {
		return playerStr;
	}

	public void setPlayerStr(String playerStr) {
		this.playerStr = playerStr;
	}
	public String getEscapeSeq() {
		return Color.colorMap.get(this.getInternalId());
	}
}
