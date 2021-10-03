package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoardCell {
	
	private int row;
	private int column;
	
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
		return false;
	}
	
	public void setOccupied(boolean isOccupied) {
		
	}
	
	public boolean getOccupied() {
		return false;
	}
	
	public int getRow() {
		return row;
	}
	public int getColumn() {
		return column;
	}
	
}
