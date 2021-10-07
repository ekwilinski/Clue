package clueGame;

import java.util.Map;
import experiment.BoardCell;

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

	public static Board getInstance() {
		Board empty = new Board();
		return empty;
	}
}
