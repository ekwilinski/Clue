package clueGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.*;

public class Board {
	private int numRows;				// instance variables
	private int numColumns;				//
	private String layoutConfigFile;	//
	private String setupConfigFile;		// 		// map of all the initials w/ their room
	private Solution solution = new Solution();
	private static final Set<Character> VALID_SYMBOLS = new HashSet<Character>(Arrays.asList('<','>','^','v','#','*'));		// valid characters to use
	
	private Set<BoardCell> targets;		//all target cells
	private Set<BoardCell> visited;		// all visited cells
	private BoardCell[][] grid; 		// creation of grid
	private Map<Character, Room> roomMap;
	private Set<BoardCell> roomCenters;				// set of roomCenters
	private Map<Character, BoardCell> passagewayCells;
	private Set<Card> roomCards;
	private Set<Card> playerCards;
	private Set<Card> weaponCards;
	private HumanPlayer humanPlayer;
	private Set<ComputerPlayer> computerPlayers;
	private Set<Card> solutionCards;
	
	//pull out instance declarations to load setup config
	
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

	public void initialize() {		// initializing the game
		try {
			loadSetupConfig();		// loading in the file
			loadLayoutConfig();		// loading the layout
			generateAllAdjacencies();
			deal();
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		
		//allocate memory
		roomMap = new HashMap<Character, Room>();
		roomCenters = new HashSet<BoardCell>();				// set of roomCenters
		passagewayCells = new HashMap<Character, BoardCell>();
		roomCards = new HashSet<Card>();
		playerCards = new HashSet<Card>();
		weaponCards = new HashSet<Card>();
		computerPlayers = new HashSet<ComputerPlayer>();
		solutionCards = new HashSet<Card>();
		
		// if the file being read in is invalid this will throw an error
		// we read in the file using scanner
		FileReader f = new FileReader(setupConfigFile);
		Scanner in = new Scanner(f);
		String line;

		while(in.hasNextLine()) {
			line = in.nextLine();
			if(!line.contains("//")) {
				String[] lineData = line.split(", ");	// we use this as a delimeter
				
				readCards(lineData);
			}
		}
		in.close();		//closing the input file
	}
	
	public void readCards(String[] lineData) throws BadConfigFormatException {
		String type = lineData[0];
		
		if(type.equals("Room")) {
			Room room_data = new Room(lineData[1]);
			roomMap.put(lineData[2].charAt(0), room_data);
			roomCards.add(new Card(lineData[1], CardType.ROOM));
		}
		else if(type.equals("Space")) {
			Room room_data = new Room(lineData[1]);
			roomMap.put(lineData[2].charAt(0), room_data);
		}
		else if(type.equals("Human")) {
			humanPlayer = new HumanPlayer(lineData[1], lineData[2], Integer.parseInt(lineData[3]), Integer.parseInt(lineData[4]));
			playerCards.add(new Card(lineData[1], CardType.PERSON));
		}
		else if(type.equals("Computer")) {
			ComputerPlayer computerPlaya = new ComputerPlayer(lineData[1], lineData[2], Integer.parseInt(lineData[3]), Integer.parseInt(lineData[4]));
			computerPlayers.add(computerPlaya);
			playerCards.add(new Card(lineData[1], CardType.PERSON));
		}
		else if(type.equals("Weapon")) {
			weaponCards.add(new Card(lineData[1], CardType.WEAPON));
		}
		else {
			throw new BadConfigFormatException();
		}
		
	}

	public void loadLayoutConfig() throws BadConfigFormatException, FileNotFoundException {
		
		
		// if the file being read in is invalid this will throw an error
		// we read in the file using scanner
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
			for(int j = 0; j < numColumns; j++) {		// checking if the character is to the right of the initial
				grid[i][j] = new BoardCell(i, j);
				grid[i][j].setInitial(currentLine[j].charAt(0));
				
				if(currentLine[j].length() == 2) {
					if(!VALID_SYMBOLS.contains(currentLine[j].charAt(1)) && !roomMap.containsKey(currentLine[j].charAt(1))) {
						System.out.println(currentLine[j].charAt(1));
					}
				}
				
				// these if statements check the door direction when reading in the board
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
		in.close();		// closing input file
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
		generateTargets(cell, pathLength);
	}

	private void generateTargets(BoardCell startCell, int pathLength) {
		// has checks for doorway(), roomCenter() and isOccupied()
		// then add depending on which conditions are true
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
		// need to figure out how to undo hardcode* 
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numColumns; j++) {
				if((grid[i][j].isDoorway()) || (grid[i][j].isRoomCenter()) || (grid[i][j].getInitial() == 'W') || (grid[i][j].getInitial() == 'H')) {
					generateAdjList(grid[i][j]);
				}
			}
		}
	}

	private void generateAdjList(BoardCell cell) {

		int row = cell.getRow();
		int column = cell.getColumn();
		if( (cell.isDoorway()) || (cell.getInitial() == 'W') || (cell.getInitial() == 'H')) {
			//left square
			if( (row > 0) && (getCell(row-1, column).getInitial() == 'W') || ((row > 0) && (getCell(row-1, column).getInitial() == 'H')) ) {
				cell.addAdj(getCell(row-1, column));
			}
			//right square
			if( (row < (numRows - 1)) && (getCell(row+1, column).getInitial() == 'W') || (row < (numRows - 1)) && (getCell(row+1, column).getInitial() == 'H')) {
				cell.addAdj(getCell(row+1, column));
			}
			//upper square
			if( (column > 0) && (getCell(row, column-1).getInitial() == 'W') || ((column > 0) && (getCell(row, column-1).getInitial() == 'H')) ) {
				cell.addAdj(getCell(row, column-1));
			}
			//lower square
			if( (column < (numColumns - 1)) && (getCell(row, column+1).getInitial() == 'W') || ((column < (numColumns - 1)) && (getCell(row, column+1).getInitial() == 'H')) ) {
				cell.addAdj(getCell(row, column+1));
			}
		}

		if(cell.isDoorway()) {
			char initial;
			// checks each possible door direction and then adds to adjList
			if(cell.getDoorDirection() == DoorDirection.UP) {
				initial = grid[cell.getRow()-1][cell.getColumn()].getInitial();
				BoardCell centerCell = roomMap.get(initial).getCenterCell();
				cell.addAdj(centerCell);
				centerCell.addAdj(cell);
			}
			if(cell.getDoorDirection() == DoorDirection.DOWN) {
				initial = grid[cell.getRow()+1][cell.getColumn()].getInitial();
				BoardCell centerCell = roomMap.get(initial).getCenterCell();
				cell.addAdj(centerCell);
				centerCell.addAdj(cell);
			}
			if(cell.getDoorDirection() == DoorDirection.LEFT) {
				initial = grid[cell.getRow()][cell.getColumn()-1].getInitial();
				BoardCell centerCell = roomMap.get(initial).getCenterCell();
				cell.addAdj(centerCell);
				centerCell.addAdj(cell);
			}
			if(cell.getDoorDirection() == DoorDirection.RIGHT) {
				initial = grid[cell.getRow()][cell.getColumn()+1].getInitial();
				BoardCell centerCell = roomMap.get(initial).getCenterCell();
				cell.addAdj(centerCell);
				centerCell.addAdj(cell);
			}
		}
		// checks if roomCenter then adds to adjList
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
	
	public void deal() {
		dealSolution();
		dealToPlayers();
	}
	
	private void dealSolution() {
		//get room
		int rand = (int)(Math.random() * (3 - 1) + 1);
		int i = 1;
		for(Card card : roomCards) {
			if(i == rand) {
				solutionCards.add(card);
				solution.addRoom(card);
			}
			i++;
		}
		
		//get weapon
		rand = (int)(Math.random() * (3 - 1) + 1);
		i = 1;
		for(Card card : weaponCards) {
			if(i == rand) {
				solutionCards.add(card);
				solution.addWeapon(card);
			}
			i++;
		}
		
		//get player
		rand = (int)(Math.random() * (3 - 1) + 1);
		i = 1;
		for(Card card : playerCards) {
			if(i == rand) {
				solutionCards.add(card);
				solution.addPlayer(card);
			}
			i++;
		}
		
	}

	private void dealToPlayers() {
		Set<Card> playingCards = new HashSet<Card>();
		
		for(Card card : roomCards) {
			if(!solutionCards.contains(card)) {
				playingCards.add(card);
			}
		}
		for(Card card : weaponCards) {
			if(!solutionCards.contains(card)) {
				playingCards.add(card);
			}
		}
		for(Card card : playerCards) {
			if(!solutionCards.contains(card)) {
				playingCards.add(card);
			}
		}
		
		while(!playingCards.isEmpty()) {
			//get random card
			for(ComputerPlayer player : computerPlayers) {
				int rand = (int)(Math.random() * (playingCards.size() - 1) + 1);
				int i = 1;
				for(Card card : playingCards) {
					if(i == rand) {
						player.updateHand(card);
						playingCards.remove(card);
						break;
					}
					i++;
				}
			}
			int rand2 = (int)(Math.random() * (playingCards.size() - 1) + 1);
			int j = 1;
			for(Card card : playingCards) {
				if(j == rand2) {
					humanPlayer.updateHand(card);
					playingCards.remove(card);
					break;
				}
				j++;
			}
				
		}
	}


	public Set<Card> getRoomCards() {
		return roomCards;
	}

	public Card getCard(String name) {
		for(Card card : roomCards) {
			if(card.getName().equals(name)) {
				return card;
			}
		}
		for(Card card : playerCards) {
			if(card.getName().equals(name)) {
				return card;
			}
		}
		for(Card card : weaponCards) {
			if(card.getName().equals(name)) {
				return card;
			}
		}
		return null;
	}

	public Set<Card> getPlayerCards() {
		return playerCards;
	}

	public Set<Card> getWeaponCards() {
		return weaponCards;
	}

	public HumanPlayer getHumanPlayer() {
		return humanPlayer;
	}

	public ComputerPlayer getComputerPlayer(String name) {
		for(ComputerPlayer cp : computerPlayers) {
			if(cp.getName().equals(name)) {
				return cp;
			}
		}
		return null;
	}
	public Set<ComputerPlayer> getComputerPlayers() {
		return computerPlayers;
	}

	public Set<Card> getSolution() {
		return solutionCards;
	}

	public void setSolution(Card person, Card room, Card weapon) {
		// TODO Auto-generated method stu
		
	}

	public Boolean checkAccusation(Solution testAnswer) {
		// TODO Auto-generated method stub
		return false;
	}

	
}
