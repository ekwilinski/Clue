package clueGame;

import java.util.Set;

public class BoardCell {
	private int row;
	private int column;
	private char initial;
	private DoorDirection doorDirection;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;
	private boolean isDoorway;
	
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public void addAdj(BoardCell adj) {
		
	}
	
	public void setIsDoor(boolean isDoor) {
		isDoorway = isDoor;
	}

	public boolean isDoorway() {
		return isDoorway;
	}
	
	public void setDoorDirection(DoorDirection direction) {
		doorDirection = direction;
	}

	public DoorDirection getDoorDirection() {
		// TODO Auto-generated method stub
		return doorDirection;
	}

	public boolean isLabel() {
		return roomLabel;
	}

	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return false;
	}

	public char getSecretPassage() {
		// TODO Auto-generated method stub
		return 'z';
	}
	
	public void setInitial(char initial) {
		this.initial = initial;
	}
	
	public char getInitial() {
		return initial;
	}
}
