package clueGame;

public enum DoorDirection {
	 UP("up"),
	 DOWN("down"),
	 LEFT("left"),
	 RIGHT("right"),
	 NONE("none");

	private String direction;
	
	private DoorDirection(String direction) {
		this.direction= direction;
	}
	
	public String toString() {
		return direction;
	}
}
