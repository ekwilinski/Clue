package clueGame;

import java.util.Map;
import clueGame.BoardCell;

public class Board {
	private BoardCell[][] grid; //= new BoardCell[][];
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	
	/*
	 * variable and methods used for singleton pattern
	 */
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {
		super() ;
	}
	
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}
	
	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	
	public void initialize() {
		
	}

	public void loadSetupConfig() {

	}

	public void loadLayoutConfig() {

	}

	public Room getRoom(char roomName) {
		Room blank = new Room();
		return blank;
	}

	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	
	public BoardCell getCell(int row, int column) {
		BoardCell emptyCell = new BoardCell(row, column);
		return emptyCell;
	}
	
	public Room getRoom(BoardCell cell) {
		Room blank = new Room();
		return blank;
	}

	public void setConfigFiles(String string, String string2) {
		// TODO Auto-generated method stub
		
	}
}
