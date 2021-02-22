package trouble;

public class Move {
	
	private static int[] startEndSpace = {1,8,15,22};
	
	private Player player;
	private Peg peg;
	private int roll;
	
	public Move(Player player, int roll) {
		this.player = player;
		this.roll = roll;		
	}
	
	public boolean validMove() {
		
		int startSpace = peg.getSpace();
		int finalSpace = getFinalSpace(startSpace, roll);
		int startEnd = startEndSpace[player.getInternalId()];
		
		//LEAVING HOME
		if (startSpace == Peg.HOME) {
			if (roll == 6) {
				Peg otherPeg = Board.track[startEnd + 1];
				return otherPegLogic(peg, otherPeg);
			} else
				return false;
		}
		
		int finishLine = finalSpace - (startEnd + 2);
		
		//IN FINISH LINE
		if (peg.isFinish()) {
			if (startSpace + roll <= startEnd + 5) {				
				Peg otherPeg = Board.finish[player.getInternalId()][finishLine];
				if (otherPeg == null) return true;
				else return false;
			} else {
				return false;
			}
		}
		
		//GOING INTO FINISH LINE
		if (isGoingIntoFinish(peg, startSpace, finalSpace, startEnd)) {
			try {
				Peg otherPeg = Board.finish[player.getInternalId()][finishLine];
				return otherPegLogic(peg, otherPeg);
			} catch (ArrayIndexOutOfBoundsException e) {
				//peg overshot finish line
				return false;
			}
		//ON TRACK
		} else {
			Peg otherPeg = Board.track[finalSpace];
			return otherPegLogic(peg, otherPeg);
		
			
		}	
	}
	
	private boolean isGoingIntoFinish(Peg peg, int startSpace, int finalSpace, int startEnd) {
		boolean started = peg.isStarted();
		
		//edge case for player 3 looping back around
		boolean player3 = peg.getPlayer() == 3;
		boolean betweenStartAndZero = started && (startSpace == startEnd || startSpace == startEnd + 1);
		boolean looped = finalSpace == 0 || finalSpace == 1;
		if (player3 && betweenStartAndZero && looped) finalSpace += 27;
		
		boolean pastStartEnd = finalSpace > startEnd + 1;
		boolean finishing = (startSpace + roll) % 28 <= startEnd + roll + 1;
		return started && pastStartEnd && finishing;
	}
	
	private boolean otherPegLogic(Peg peg, Peg otherPeg) {
		if (otherPeg != null) {
			if (otherPeg.getPlayer() != peg.getPlayer())
				return true;
			else 
				return false;					
		}
			return true;
	}
	
	public boolean noValidMoves() {		
		int invalidMoves = 0;		
		
		int startEnd = startEndSpace[player.getInternalId()];
			
		for (Peg peg : player.getPegs()) {
			int startSpace = peg.getSpace();			
			int finalSpace = getFinalSpace(startSpace, roll);
			
			//home and not a 6
			if (startSpace == Peg.HOME && roll != 6) {
				invalidMoves++;
				continue;
			}
			
			//past finish (in finish)
			if (peg.isFinish()) {
				if (startSpace + roll > startEnd + 5) {
					invalidMoves++;
					continue;
				}
			}
			
			boolean goingIntoFinish = isGoingIntoFinish(peg, startSpace, finalSpace, startEnd);
			
			//past finish (going into finish)				
			if (goingIntoFinish) {
				if (finalSpace > startEnd + 5) {
					invalidMoves++;
					continue;
				} 				
			}
			
			//land on own peg			
			Peg otherPeg;			
			
			if (startSpace == Peg.HOME) {
				otherPeg = Board.track[startEnd + 1];
			} else if (goingIntoFinish || peg.isFinish()) {
				int finishLine = finalSpace - (startEnd + 2);
				if (finishLine > 3) {
					invalidMoves++;
					continue;
				}
				try {
					otherPeg = Board.finish[peg.getPlayer()][finishLine];					
				} catch (ArrayIndexOutOfBoundsException e) {
					//goes beyond finish line
					invalidMoves++;
					continue;
				}
			} else {
				otherPeg = Board.track[finalSpace % 28];
			}
			
			if (otherPeg != null && peg.getPlayer() == otherPeg.getPlayer()) {
				invalidMoves++;
				continue;
			}
		
		}
		
		return invalidMoves == 4;
	}
	
	private int getFinalSpace(int startSpace, int roll) {
		int finalSpace = (startSpace + roll) % 28;
		int startEnd = startEndSpace[player.getInternalId()];
		
		if (startSpace == Peg.HOME) {
			finalSpace = startEnd + 1;
		}
		
		return finalSpace;
	}
	
	public void move() {
		int startSpace = peg.getSpace();
		int finalSpace = getFinalSpace(startSpace, roll);
		int startEnd = startEndSpace[player.getInternalId()];		
		
		if (startSpace == Peg.HOME) {
			//HOME BOARD
			Board.home[player.getInternalId()][peg.getInternalId()] = null;
			Peg oppPeg = Board.track[startEnd + 1];			
			Board.track[startEnd + 1] = peg;
			peg.setSpace(startEnd + 1);			
			stomp(oppPeg);
		} else if (peg.isFinish()) {
			Board.finish[player.getInternalId()][startSpace - (startEnd + 2)] = null;
			Board.finish[player.getInternalId()][finalSpace - (startEnd + 2)] = peg;
			peg.setSpace(finalSpace);
			
		} else if (isGoingIntoFinish(peg, startSpace, finalSpace, startEnd)) {
			//FINISH BOARD
			try {
				Board.finish[player.getInternalId()][finalSpace - (startEnd + 2)] = peg;
				peg.setFinish(true);
				peg.setSpace(finalSpace);
				Board.track[startSpace] = null;				
			} catch (ArrayIndexOutOfBoundsException e) {
				e.printStackTrace();
				//this should be prevented by validMove check
			}
		} else {
			//TRACK BOARD
			Board.track[startSpace] = null;	
			if (startSpace == startEnd + 1) peg.setStarted(true);
			Peg oppPeg = Board.track[finalSpace % 28];
			stomp(oppPeg);
			Board.track[finalSpace % 28] = peg;
			peg.setSpace(finalSpace % 28);
		}		
	}
	
	private void stomp(Peg oppPeg) {
		if (oppPeg != null) {
			oppPeg.setSpace(-1);
			oppPeg.setStarted(false);
			Board.home[oppPeg.getPlayer()][oppPeg.getInternalId()] = oppPeg;
			String escapeSeq = Trouble.players[oppPeg.getPlayer()].getEscapeSeq();
			String colorName = Color.colorNameMap.get(oppPeg.getPlayer());
			String oppPegStr = escapeSeq + colorName + "-" + oppPeg.getIdentifier() + Color.getColorEnd();
			System.out.println(oppPegStr + " was sent back to HOME!");			
		}
	}

	public Peg getPeg() {
		return peg;
	}

	public void setPeg(Peg peg) {
		this.peg = peg;
	}
	
}
