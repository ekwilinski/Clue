package experiment;

import java.util.HashSet;
import java.util.Set;

public class BoardCell {
	
	private int row, column;
	private Boolean isRoom, isOccupied ;
	private Set<BoardCell> adjacencyList = new HashSet<BoardCell>();
	
	public BoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public void addAdjacency( BoardCell cell ) {
		adjacencyList.add(cell);
	}
	
	public Set<BoardCell> getAdjList() {
		return adjacencyList;
	}
	
	public void setRoom(boolean roomOrNot) {
		isRoom = roomOrNot;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean isOccupied) {
		this.isOccupied = isOccupied;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}
	
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
}
