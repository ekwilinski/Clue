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
	
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public void addAdj(BoardCell adj) {
		
	}

	public boolean isDoorway() {
		// TODO Auto-generated method stub
		return false;
	}

	public Object getDoorDirection() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isLabel() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isRoomCenter() {
		// TODO Auto-generated method stub
		return false;
	}

	public char getSecretPassage() {
		// TODO Auto-generated method stub
		return 'z';
	}
}
