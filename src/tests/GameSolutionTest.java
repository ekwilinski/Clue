package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

class GameSolutionTest {
	
	private static Board board;
	private static Card jim, bob, steve, aretha, elena, elliot, sword, knife, ak47, glock19, cal50bmg, thumbs, culina, kitchen, bathroom, dungeon, library, dining, bedroom, poolhouse, traphouse; 

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
		
		//create cards to test
		//people
		jim = new Card("Jim", CardType.PERSON);
		bob = new Card("Bob", CardType.PERSON);
		steve = new Card("Steve", CardType.PERSON);
		aretha = new Card("Aretha", CardType.PERSON);
		elena = new Card("Elena", CardType.PERSON);
		elliot = new Card("Elliot", CardType.PERSON);
		//weapons
		sword = new Card("Sword", CardType.WEAPON);
		knife = new Card("Knife", CardType.WEAPON);
		ak47 = new Card("AK47", CardType.WEAPON);
		cal50bmg = new Card("50 BMG", CardType.WEAPON);
		glock19 = new Card("Glock 19", CardType.WEAPON);
		thumbs = new Card("Thumbs", CardType.WEAPON);
		//rooms
		culina = new Card("Culina", CardType.ROOM);
		kitchen = new Card("Kitchen", CardType.ROOM);
		bathroom = new Card("Bathroom", CardType.ROOM);
		dungeon = new Card("Dungeon", CardType.ROOM);
		library = new Card("Library", CardType.ROOM);
		dining = new Card("Dining", CardType.ROOM);
		bedroom = new Card("Bedroom", CardType.ROOM);
		poolhouse = new Card("Poolhouse", CardType.ROOM);
		traphouse = new Card("Traphouse", CardType.ROOM);
	}
	

	@Test
	void testCheckAccusation() {
		board.setSolution(jim, kitchen, glock19);
		Solution testAccusation = new Solution(jim, kitchen, glock19);
		assertTrue(board.checkAccusation(testAccusation));
		//checking the 3 false tests to make sure
		testAccusation.addWeapon(ak47);
		assertFalse(board.checkAccusation(testAccusation));
		
		testAccusation.addWeapon(glock19);
		testAccusation.addRoom(bedroom);
		assertFalse(board.checkAccusation(testAccusation));
		
		testAccusation.addRoom(kitchen);
		testAccusation.addPlayer(elena);
		assertFalse(board.checkAccusation(testAccusation));
	}
	
	@Test 
	void testDisproveSuggestion() {
		Player humanPlayer = new HumanPlayer("player", "red", 0, 0, 0);
		humanPlayer.updateHand(ak47);
		humanPlayer.updateHand(aretha);
		humanPlayer.updateHand(bathroom);
		Solution suggestion = new Solution(aretha, kitchen, glock19);
		
		assertEquals(humanPlayer.disproveSuggestion(suggestion), aretha);
	}
	
	@Test
	void testHandleSuggestions() {
		
		Set<ComputerPlayer> computerPlayers = new HashSet<ComputerPlayer>();
		//no players can disprove
		HumanPlayer humanPlayer = new HumanPlayer("HP", "blue", 0, 0, 0);
		Solution suggestion = new Solution(aretha, bathroom, ak47);
		humanPlayer.updateHand(jim);
		humanPlayer.updateHand(bedroom);
		humanPlayer.updateHand(thumbs);
		ComputerPlayer computerPlayer1 = new ComputerPlayer("Computer 1", "grey", 1, 1, 1);
		computerPlayers.add(computerPlayer1);
		computerPlayer1.updateHand(elena);
		computerPlayer1.updateHand(culina);
		computerPlayer1.updateHand(sword);
		ComputerPlayer computerPlayer2 = new ComputerPlayer("Computer 2", "tan", 1, 1, 2);
		computerPlayers.add(computerPlayer2);
		computerPlayer2.updateHand(elliot);
		computerPlayer2.updateHand(kitchen);
		computerPlayer2.updateHand(knife);
		
		board.setComputerPlayer(computerPlayers);
		board.setHumanPlayer(humanPlayer);
		assertEquals(null, board.handleSuggestion(humanPlayer, suggestion));
		
		//accuser can disprove
		ComputerPlayer computerPlayer3 = new ComputerPlayer("Computer 3", "purple", 1, 1, 3);
		computerPlayer3.updateHand(aretha);
		computerPlayer3.updateHand(poolhouse);
		computerPlayer3.updateHand(ak47);
		computerPlayers.add(computerPlayer3);
		board.setComputerPlayer(computerPlayers);
		assertEquals(null, board.handleSuggestion(computerPlayer3, suggestion));
		
		//humanPlayer accuser computerPlayer1 and computerPlayer2 can disprove
		computerPlayer3.removeCard(aretha);
		computerPlayer3.removeCard(ak47);
		computerPlayer1.updateHand(aretha);
		computerPlayer2.updateHand(bathroom);
		assertEquals(aretha, board.handleSuggestion(humanPlayer, suggestion));
		
		//humanPlayer accuser and computerplayer3 can disprove
		computerPlayer1.removeCard(aretha);
		computerPlayer2.removeCard(bathroom);
		computerPlayer3.updateHand(aretha);
		assertEquals(aretha, board.handleSuggestion(humanPlayer, suggestion));
	}

}
