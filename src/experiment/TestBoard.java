package experiment;
import java.util.*;

public class TestBoard {
	
	private Set<TestBoardCell> board;

	public TestBoard() {
		board = new HashSet<TestBoardCell>();
		
		/*
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				TestBoardCell e = new TestBoardCell(i,j);
				board.add(e);

			}
		}
		*/
		
	}
	
	void calcTargets( TestBoardCell startCell, int pathlength) {
		
	}
	
	public Set<TestBoardCell> getTargets() {
		Set<TestBoardCell> emptyList = new HashSet<TestBoardCell>();
		return emptyList;
	}
	
	public TestBoardCell getCell( int row, int col) {
		
		TestBoardCell e = new TestBoardCell(row, col);
		return e;
		
		/*
		TestBoardCell toReturn = null;
		for(TestBoardCell currentCell : board) {
			if((currentCell.getRow() == row) && (currentCell.getColumn() == col)) {
				toReturn = currentCell;
			}
		}
		return toReturn;
		*/

	}

}
