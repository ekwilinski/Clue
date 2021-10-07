package experiment;
import java.util.*;

public class TestBoard {
	final static int COLS = 4;
	final static int ROWS = 4;

	private BoardCell[][] grid = new BoardCell[ROWS][COLS];
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;


	public TestBoard() {

		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				grid[i][j] = new BoardCell(i,j);
			}
		}
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				generateAdjList(grid[i][j]);
			}
		}
	}

	private void generateAdjList(BoardCell cell) {
		//left square
		if(cell.getRow() > 0) {
			cell.addAdjacency(getCell(cell.getRow()-1, cell.getColumn()));
		}
		//right square
		if(cell.getRow() < (ROWS - 1)) {
			cell.addAdjacency(getCell(cell.getRow()+1, cell.getColumn()));
		}
		//upper square
		if(cell.getColumn() > 0) {
			cell.addAdjacency(getCell(cell.getRow(), cell.getColumn()-1));
		}
		//lower square
		if(cell.getColumn() < (COLS - 1)) {
			cell.addAdjacency(getCell(cell.getRow(), cell.getColumn()+1));
		}

	}

	public void calcTargets( BoardCell startCell, int pathLength) {

		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(startCell);
		generateTargets(startCell, pathLength);

	}

	public void generateTargets(BoardCell startCell, int pathLength) {

		for(BoardCell cell : startCell.getAdjList()) {
			if( !visited.contains(cell)) {
				visited.add(cell);
				if(pathLength == 1) {
					targets.add(cell);
				}
				else {
					generateTargets(cell, pathLength-1);
				}
				visited.remove(cell);
			}
		}
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public BoardCell getCell( int row, int col) {

		BoardCell e = grid[row][col];
		return e;

	}
}
