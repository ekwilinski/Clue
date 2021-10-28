package clueGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import clueGame.BoardCell;
import experiment.TestBoardCell;
import clueGame.BadConfigFormatException;

import java.io.*;

public class Board {
	private BoardCell[][] grid; //= new BoardCell[][];
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private Set<BoardCell> roomCenters = new HashSet<BoardCell>();
	private Map<Character, BoardCell> passagewayCells = new HashMap<Character, BoardCell>();

	/*
	 * variable and methods used for singleton pattern
	 */
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {
		super();
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
			loadLayoutConfig();
		} catch(Exception e) {
			System.out.println(e);
		}
		generateAllAdjacencies();
	}

	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader f = new FileReader(setupConfigFile);
		Scanner in = new Scanner(f);
		String line;

		while(in.hasNextLine()) {
			line = in.nextLine();
			if(!line.contains("//")) {
				String[] lineData = line.split(", ");
				Room room_data = new Room(lineData[1]);
				roomMap.put(lineData[2].charAt(0), room_data);
			}
		}
		in.close();
	}

	public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException {
		FileReader f = new FileReader(layoutConfigFile);
		Scanner in = new Scanner(f);
		String line;

		ArrayList<String> temp = new ArrayList<String>();
		while(in.hasNextLine()) {
			line = in.nextLine();
			temp.add(line);
		}

		numRows = temp.size();
		String[] sizeCheck = temp.get(0).split(",");
		for(String c : sizeCheck) {
			String[] test =  c.split(",");
			for(String rooms : test) {
				if((rooms.length() < 1) || (rooms.length() > 2)) {
					throw new BadConfigFormatException();
				}
			}
			numColumns++;
		}
		grid = new BoardCell[numRows][numColumns];

		int i = 0;
		for(String lineData : temp) {
			String[] currentLine = lineData.split(",");
			int length = 0;
			for(String c : currentLine) {
				length++;
			}
			if(length != numColumns) {
				throw new BadConfigFormatException();
			}
			for(int j = 0; j < numColumns; j++) {
				grid[i][j] = new BoardCell(i, j);
				grid[i][j].setInitial(currentLine[j].charAt(0));
				if(currentLine[j].contains("^")) {
					grid[i][j].setIsDoor(true);
					grid[i][j].setDoorDirection(DoorDirection.UP);
				}
				if(currentLine[j].contains("v")) {
					grid[i][j].setIsDoor(true);
					grid[i][j].setDoorDirection(DoorDirection.DOWN);
				}
				if(currentLine[j].contains("<")) {
					grid[i][j].setIsDoor(true);
					grid[i][j].setDoorDirection(DoorDirection.LEFT);
				}
				if(currentLine[j].contains(">")) {
					grid[i][j].setIsDoor(true);
					grid[i][j].setDoorDirection(DoorDirection.RIGHT);
				}
				if(currentLine[j].contains("#")) {
					grid[i][j].setIsLabel();
					roomMap.get(grid[i][j].getInitial()).setLabelCell(grid[i][j]);
				}
				if(currentLine[j].contains("*")) {
					roomCenters.add(grid[i][j]);
					grid[i][j].setIsRoomCenter();
					roomMap.get(grid[i][j].getInitial()).setCenterCell(grid[i][j]);
				}
				if((currentLine[j].length() > 1) && roomMap.containsKey(currentLine[j].charAt(1))) {
					grid[i][j].setSecretPassage(currentLine[j].charAt(1));
					passagewayCells.put(grid[i][j].getInitial(), grid[i][j]);
				}
			}
			i++;
		}
		in.close();
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
		BoardCell tempCell = grid[row][column];
		return tempCell;
	}

	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial()); 
	}

	public void setConfigFiles(String csv, String txt) {
		layoutConfigFile = csv;
		setupConfigFile = txt;
	}

	public Set<BoardCell> getAdjList(int i, int j) {
		return grid[i][j].getAdjList();
	}

	public void calcTargets(BoardCell cell, int pathLength) {
		visited = new HashSet<BoardCell>();
		targets = new HashSet<BoardCell>();
		visited.add(cell);
		totalLength = pathLength;
		generateTargets(cell, pathLength);
	}

	private void generateTargets(BoardCell startCell, int pathLength) {
		for(BoardCell cell : startCell.getAdjList()) {
			if( startCell.isDoorway() && cell.isRoomCenter()) {
				if(!visited.contains(cell)) {
					targets.add(cell);
				}
			}
			else if( startCell.isRoomCenter() && cell.isRoomCenter()) {
				if(!visited.contains(cell)) {
					targets.add(cell);
				}
			}
			else if(!cell.isOccupied() || cell.isRoomCenter()) {
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
	}

	private void generateAllAdjacencies() {
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				if((grid[i][j].isDoorway()) || (grid[i][j].isRoomCenter()) || (grid[i][j].getInitial() == 'W') || (grid[i][j].getInitial() == 'H')) {
					generateAdjList(grid[i][j]);
				}
			}
		}
	}

	private void generateAdjList(BoardCell cell) {
		if( (cell.isDoorway()) || (cell.getInitial() == 'W') || (cell.getInitial() == 'H')) {
			//left square
			if( (cell.getRow() > 0) && (getCell(cell.getRow()-1, cell.getColumn()).getInitial() == 'W') || ((cell.getRow() > 0) && (getCell(cell.getRow()-1, cell.getColumn()).getInitial() == 'H')) ) {
				cell.addAdj(getCell(cell.getRow()-1, cell.getColumn()));
			}
			//right square
			if( (cell.getRow() < (numRows - 1)) && (getCell(cell.getRow()+1, cell.getColumn()).getInitial() == 'W') || ((cell.getRow() < (numRows - 1)) && (getCell(cell.getRow()+1, cell.getColumn()).getInitial() == 'H')) ) {
				cell.addAdj(getCell(cell.getRow()+1, cell.getColumn()));
			}
			//upper square
			if( (cell.getColumn() > 0) && (getCell(cell.getRow(), cell.getColumn()-1).getInitial() == 'W') || ((cell.getColumn() > 0) && (getCell(cell.getRow(), cell.getColumn()-1).getInitial() == 'H')) ) {
				cell.addAdj(getCell(cell.getRow(), cell.getColumn()-1));
			}
			//lower square
			if( (cell.getColumn() < (numColumns - 1)) && (getCell(cell.getRow(), cell.getColumn()+1).getInitial() == 'W') || ((cell.getColumn() < (numColumns - 1)) && (getCell(cell.getRow(), cell.getColumn()+1).getInitial() == 'H')) ) {
				cell.addAdj(getCell(cell.getRow(), cell.getColumn()+1));
			}
		}

		if(cell.isDoorway()) {
			char initial;
			if(cell.getDoorDirection() == DoorDirection.UP) {
				initial = grid[cell.getRow()-1][cell.getColumn()].getInitial();
				for(BoardCell centerCell : roomCenters) {
					if(centerCell.getInitial() == initial) {
						cell.addAdj(centerCell);
						centerCell.addAdj(cell);
					}
				}
			}
			if(cell.getDoorDirection() == DoorDirection.DOWN) {
				initial = grid[cell.getRow()+1][cell.getColumn()].getInitial();
				for(BoardCell centerCell : roomCenters) {
					if(centerCell.getInitial() == initial) {
						cell.addAdj(centerCell);
						centerCell.addAdj(cell);
					}
				}
			}
			if(cell.getDoorDirection() == DoorDirection.LEFT) {
				initial = grid[cell.getRow()][cell.getColumn()-1].getInitial();
				for(BoardCell centerCell : roomCenters) {
					if(centerCell.getInitial() == initial) {
						cell.addAdj(centerCell);
						centerCell.addAdj(cell);
					}
				}
			}
			if(cell.getDoorDirection() == DoorDirection.RIGHT) {
				initial = grid[cell.getRow()][cell.getColumn()+1].getInitial();
				for(BoardCell centerCell : roomCenters) {
					if(centerCell.getInitial() == initial) {
						cell.addAdj(centerCell);
						centerCell.addAdj(cell);
					}
				}
			}
		}
		if(cell.isRoomCenter()) {
			if(passagewayCells.containsKey(cell.getInitial())) {
				for(BoardCell centerCell : roomCenters) {
					if(centerCell.getInitial() == passagewayCells.get(cell.getInitial()).getSecretPassage()) {
						cell.addAdj(centerCell);

					}
				}
			}
		}
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}
}
