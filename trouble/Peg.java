package trouble;

public class Peg {
	
	
	private int player;	
	private String identifier;
	private int internalId;
	private int space;
	private boolean started;
	private boolean finish;

	public final static int HOME = -1;
	
	public int getPlayer() {
		return player;
	}
	public void setPlayer(int player) {
		this.player = player;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public int getSpace() {
		return space;
	}
	public void setSpace(int space) {
		this.space = space;
	}
	public int getInternalId() {
		return internalId;
	}
	public void setInternalId(int internalId) {
		this.internalId = internalId;
	}
	public boolean isStarted() {
		return started;
	}
	public void setStarted(boolean started) {
		this.started = started;
	}
	public boolean isFinish() {
		return finish;
	}
	public void setFinish(boolean finish) {
		this.finish = finish;
	}
	
}
