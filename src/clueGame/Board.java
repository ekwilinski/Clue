package clueGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import gui.CardPanel;
import gui.GameControlPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;

@SuppressWarnings("serial")
public class Board extends JPanel{
	
	public static Boolean DEBUG_01 = false;
	
	private int position;
	private Boolean playerFinished = false;
	private int numRows;				// instance variables
	private int numColumns;				//
	private String layoutConfigFile;	//
	private String setupConfigFile;		// map of all the initials w/ their room
	private Solution solution;
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
	private Player currentPlayer;
	private Set<ComputerPlayer> computerPlayers;
	private Set<Card> solutionCards;
	private Set<Card> allCards;

	private CardPanel cardPanel;

	private GameControlPanel controlPanel;

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
			boardListener tom = new boardListener();
			addMouseListener(tom);
			
			if(DEBUG_01) {
				currentPlayer = humanPlayer;
			}
			
		} catch(Exception e) {
			System.out.println(e);
		}
	}

	public void loadSetupConfig() throws BadConfigFormatException, FileNotFoundException {
		//allocate memory
		position = 0;
		solution = new Solution();
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
		givePlayersAllCard();
		in.close();		//closing the input file
	}

	private void givePlayersAllCard() {
		humanPlayer.giveCards(roomCards, weaponCards, playerCards);
		for( Player player : computerPlayers) {
			player.giveCards(roomCards, weaponCards, playerCards);
		}
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
			humanPlayer = new HumanPlayer(lineData[1], lineData[2], Integer.parseInt(lineData[3]), Integer.parseInt(lineData[4]), position);
			playerCards.add(new Card(lineData[1], CardType.PERSON));
			position = position + 1;
			currentPlayer = humanPlayer;
		}
		else if(type.equals("Computer")) {
			ComputerPlayer computerPlaya = new ComputerPlayer(lineData[1], lineData[2], Integer.parseInt(lineData[3]), Integer.parseInt(lineData[4]), position);
			computerPlayers.add(computerPlaya);
			playerCards.add(new Card(lineData[1], CardType.PERSON));
			position = position + 1;
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
					cell.setTarget(currentPlayer);
				}
			}
			else if( startCell.isRoomCenter() && cell.isRoomCenter()) {
				if(!visited.contains(cell)) {
					targets.add(cell);
					cell.setTarget(currentPlayer);
				}
			}
			else if(!cell.isOccupied() || cell.isRoomCenter()) {
				if( !visited.contains(cell)) {
					visited.add(cell);
					if(pathLength == 1) {
						targets.add(cell);
						cell.setTarget(currentPlayer);
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
		
		allCards = new HashSet<Card>(playingCards);
		allCards.add(solution.getPlayer());
		allCards.add(solution.getRoom());
		allCards.add(solution.getWeapon());
		
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

	public Solution getSolutionType() {
		return solution;
	}

	public void setSolution(Card person, Card room, Card weapon) {
		solution.addPlayer(person);
		solution.addRoom(room);
		solution.addWeapon(weapon);
	}

	public Boolean checkAccusation(Solution testAnswer) {
		// TODO Auto-generated method stub
		if(testAnswer.getRoom().equals(solution.getRoom()) && testAnswer.getWeapon().equals(solution.getWeapon()) && testAnswer.getPlayer().equals(solution.getPlayer()) )
			return true;
		return false;
	}

	public Card handleSuggestion(Player accuser, Solution suggestion) {
		//loop through players by position
		Card refutal;
		for(int i = 0; i < 6; i++) {
			if((humanPlayer.getPosition() == i) && (!accuser.getColor().equals(humanPlayer.getColor()))) {
				refutal = humanPlayer.disproveSuggestion(suggestion);
				if(refutal != null) {
					return refutal;
				}
			}
			else {
				for(ComputerPlayer computerPlayer : computerPlayers) {
					if((computerPlayer.getPosition() == i) && (!accuser.getColor().equals(computerPlayer.getColor()))) {
						refutal = computerPlayer.disproveSuggestion(suggestion);
						if(refutal != null) {
							return refutal;
						}
					}
				}
			}
		}
		return null;
	}

	public void setHumanPlayer(HumanPlayer humanPlayer) {
		this.humanPlayer = humanPlayer;
	}
	public void setComputerPlayer(Set<ComputerPlayer> computerPlayers) {
		this.computerPlayers = computerPlayers;
	}

	public void addToRoomMap(char c, Card living, int i, int j) {
		Room newRoom = new Room(living.getName());
		newRoom.setCenterCell(grid[i][j]);
		roomMap.put('l', newRoom);
	}

	public void addToRoomCards(Card room) {
		roomCards.add(room);
	}

	//GUI Methods

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int panelWidth = getWidth();
		int panelHeight = getHeight();

		int boardCellWidth = panelWidth / numColumns;
		int boardCellHeight = panelHeight / numRows;

		for(int row = 0; row < numRows; row++) {
			for(int columns = 0; columns < numColumns; columns++) {
				grid[row][columns].draw(boardCellWidth, boardCellHeight, g);
			}
		}
		
		for(int row = 0; row < numRows; row++) {
			for(int columns = 0; columns < numColumns; columns++) {
				if(grid[row][columns].isLabel()) {
					String name = getRoom(grid[row][columns]).getName();
					g.setColor(Color.BLACK);
					g.drawString(name, columns * boardCellWidth, (row+1) * boardCellHeight);
				}
			}
		}
		
		humanPlayer.draw(boardCellWidth, boardCellHeight, g);
		for(ComputerPlayer cpu : computerPlayers) {
			cpu.draw(boardCellWidth, boardCellHeight, g);
		}		
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		Board board = Board.getInstance();
		frame.add(board);
		frame.setSize(500, 500);
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		
		frame.setVisible(true);
	}
	
	public Boolean getPlayerFinished() {
		return playerFinished;
	}

	public void updateCurrentPlayer() {
		int currentID = currentPlayer.getPosition(); 
		
		if(currentPlayer instanceof HumanPlayer) {
			for(ComputerPlayer cpu : computerPlayers) {
				if(cpu.getPosition() == (currentID+1)) {
					currentPlayer = cpu;
					break;
				}
			}
		} 
		else {
			currentPlayer = humanPlayer;
			
			for(ComputerPlayer cpu : computerPlayers) {
				if(cpu.getPosition() == currentID+1) {
					currentPlayer = cpu;
					break;
				}
			}
		}
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public class boardListener implements MouseListener {
		public void mouseClicked(MouseEvent jerry) {
			Point click = jerry.getLocationOnScreen();
			int mouseX = click.x;
			int mouseY = click.y;
			
			Point boardLoc = getLocationOnScreen();
			int boardX = boardLoc.x;
			int boardY = boardLoc.y;
			
			mouseX = mouseX - boardX;
			mouseY = mouseY - boardY;
			
			Point offsetClick = new Point(mouseX, mouseY);
			
			int panelWidth = getWidth();
			int panelHeight = getHeight();
			int boardCellWidth = panelWidth / numColumns;
			int boardCellHeight = panelHeight / numRows;
			
			if(currentPlayer instanceof HumanPlayer) {
				for(int row = 0; row < numRows; row++) {
					for(int columns = 0; columns < numColumns; columns++) {
						Rectangle rect = new Rectangle((columns)*boardCellWidth,(row)*boardCellHeight, boardCellWidth, boardCellHeight);
						if (rect.contains(offsetClick)) {
							if(grid[row][columns].isTarget()) {
								movePlayer(row, columns);
								for(BoardCell tCell : targets) {
									tCell.removeTarget();
								}
								if(grid[row][columns].isRoomCenter()) {
									Solution s = createSuggestion();
									Card result = handleSuggestion(currentPlayer, s);
									updateResult(result);
									movePlayerToRoom(s.getPlayer(), s.getRoom());
								}
								repaint();
								playerFinished = true;
								break;
							}
							else if(targets.size() == 0) {
								JOptionPane.showMessageDialog(Board.getInstance(), "You can't move", "oopsies...", JOptionPane.WARNING_MESSAGE);
								playerFinished = true;
							}
							else {
								JOptionPane.showMessageDialog(Board.getInstance(), "Not a Target!", "oopsies...", JOptionPane.WARNING_MESSAGE);
							}
						}	
					}
				}
			}
			else {
				JOptionPane.showMessageDialog(Board.getInstance(), "Not your turn!", "oopsies...", JOptionPane.WARNING_MESSAGE);
			}
		}


		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}

	public void movePlayer(int row, int columns) {
		// TODO Auto-generated method stub
		BoardCell removeOccupied = grid[currentPlayer.startRow][currentPlayer.startColumn];
		removeOccupied.setOccupied(false);
		currentPlayer.setLocation(row, columns);
		BoardCell setOccupied = grid[row][columns];
		setOccupied.setOccupied(true);
	}
	
	public void movePlayerToRoom(Card player, Card room) {
		Player toMove = null;
		for(Player cpu : computerPlayers) {
			if(cpu.getName() == player.getName()) {
				toMove = cpu;
			}
		}
		BoardCell removeOccupied = grid[toMove.getRow()][toMove.getColumn()];
		removeOccupied.setOccupied(false);
		BoardCell moveLoc = getRoom(room.getName().charAt(0)).getCenterCell();
		moveLoc.setOccupied(true);
		toMove.setLocation(moveLoc.getRow(), moveLoc.getColumn());
		
	}
	
	public void setPlayerNotFinished() {
		// TODO Auto-generated method stub
		playerFinished = false;
	}
	
	private Solution createSuggestion() {
		
		String[] weaponCardList = new String[6];
		String[] personCardList = new String[6];
		
		int i = 0;
		int j = 0;
		for(Card cardInHand : allCards) {
			if(cardInHand.getType() == CardType.WEAPON) {
				weaponCardList[i] = cardInHand.getName();
				i++;
			}
			else if(cardInHand.getType() == CardType.PERSON) {
				personCardList[j] = cardInHand.getName();
				j++;
			}
		}
		
		JComboBox weaponCards = new JComboBox(weaponCardList);
		JComboBox personCards = new JComboBox(personCardList);
		
		JPanel suggestions = new JPanel();
		suggestions.add(weaponCards, BorderLayout.NORTH);
		suggestions.add(personCards, BorderLayout.SOUTH);
		
		Object[] options = { "Submit", "Cancel" };
		 JOptionPane.showOptionDialog(null, suggestions, null, 
		 JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, 
		 options[0]);
		 
		 int weaponIDX = weaponCards.getSelectedIndex();
		 String weaponName = weaponCardList[weaponIDX];
		 int personIDX = personCards.getSelectedIndex();
		 String personName = personCardList[personIDX];
		 
		 Room roomSugg = getRoom(grid[currentPlayer.getRow()][currentPlayer.getColumn()]);
		 Card room = getCard(roomSugg.getName());
		 
		 Card weapon = null;
		 Card person = null;
		 
		 for(Card cards : allCards) {
			 if(weaponName == cards.getName()) {
				 weapon = cards;
			 }
			 else if(personName == cards.getName()) {
				 person = cards;
			 }
		 }
		 
		 Solution sol = new Solution(person, room, weapon);
		 
		 controlPanel.setGuess(person.getName() + ", " + weapon.getName() + ", " + room.getName());
		 controlPanel.revalidate();
		 
		 return sol;
	}
	
	private void updateResult(Card result) {
		if(result == null) {
			controlPanel.setGuessResult("No player could disprove the suggestion...");
		}
		else if(currentPlayer instanceof HumanPlayer){
			String color = getOwnerColor(result);
			currentPlayer.updateSeen(result, color);
			cardPanel.createCardPanel(currentPlayer);
			cardPanel.revalidate();
			controlPanel.setGuessResult(result.getName() + " disproved the suggestion.");
		}
		controlPanel.revalidate();
	}

	private String getOwnerColor(Card result) {
		for(ComputerPlayer cpu : computerPlayers) {
			for(Card card : cpu.getHand()) {
				if(card.getName() == result.getName()) {
					return cpu.getColor();
				}
			}
		}
		return null;
	}

	public void removeCurrentPlayer() {
		computerPlayers.remove(currentPlayer);
	}

	public Room getCurrentRoom() {
		// TODO Auto-generated method stub
		return getRoom(grid[currentPlayer.getRow()][currentPlayer.getColumn()]);
	}

	public void setCP(CardPanel cardPanel) {
		this.cardPanel = cardPanel;
	}
	public void setControlPanel(GameControlPanel controlPanel) {
		this.controlPanel = controlPanel;
	}

	public Set<Card> getAllCards() {
		// TODO Auto-generated method stub
		return allCards;
	}

}
