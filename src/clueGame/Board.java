package clueGame;

import java.util.Map;
import clueGame.BoardCell;
import java.io.*;

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
		try {
			loadSetupConfig();
		} catch(FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadSetupConfig() throws IOException {
			BufferedReader f = new BufferedReader(new FileReader(setupConfigFile));
			String line;
			while((line = f.readLine()) != null) {
				if(!line.contains("//")) {
					String[] lineData = line.split(", ");
					Room paul = new Room(lineData[1]);
					roomMap.put(lineData[2].charAt(0), paul);
				}
			f.close();
			}
	}

	public void loadLayoutConfig() {

	}

	public Room getRoom(char roomName) {
		Room blank = roomMap.get(roomName);
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
		Room blank = new Room("");
		return blank;
	}

	public void setConfigFiles(String csv, String txt) {
		layoutConfigFile = csv;
		setupConfigFile = txt;
	}
}
