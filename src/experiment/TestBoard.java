package experiment;
import java.util.*;

public class TestBoard {
	final static int COLS = 4;
	final static int ROWS = 4;

	private TestBoardCell[][] grid = new TestBoardCell[ROWS][COLS];
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;


	public TestBoard() {

		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				grid[i][j] = new TestBoardCell(i,j);
			}
		}
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				generateAdjList(grid[i][j]);
			}
		}


	}

	private void generateAdjList(TestBoardCell cell) {
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

	public void calcTargets( TestBoardCell startCell, int pathlength) {

	}

	public Set<TestBoardCell> getTargets() {
		Set<TestBoardCell> emptyList = new HashSet<TestBoardCell>();
		return emptyList;
	}

	public TestBoardCell getCell( int row, int col) {

		TestBoardCell e = grid[row][col];
		return e;

	}

}
