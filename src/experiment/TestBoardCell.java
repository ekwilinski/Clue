package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	
	private int row, column;
	private Boolean isRoom, isOccupied ;
	private Set<TestBoardCell> adjacencyList = new HashSet<TestBoardCell>();
	
	public TestBoardCell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public void addAdjacency( TestBoardCell cell ) {
		adjacencyList.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList() {
		return adjacencyList;
	}
	
	public void setRoom(boolean roomOrNot) {
		
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean isOccupied) {
		
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
