package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;

class gameSetupTests {

	private static Board board;

	@BeforeAll
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("data/ClueLayout.csv", "data/ClueSetup.txt");		
		// Initialize will load config files 
		board.initialize();
	}
	
	@Test
	public void testRoomCards() {
		Set<Card> roomCards = board.getRoomCards();
		assertEquals(9, roomCards.size());
		Card kitchen = new Card("Kitchen", CardType.ROOM);
		assertTrue(kitchen.equals(board.getCard("Kitchen")));
		
		Card cubicle = new Card("Cubicle", CardType.ROOM);
		assertTrue(cubicle.equals(board.getCard("Cubicle")));
		
		Card manager = new Card("Regional Manager's Office", CardType.ROOM);
		assertTrue(manager.equals(board.getCard("Regional Manager's Office")));
	}
	
	@Test
	public void testPlayerCards() {
		Set<Card> playerCards = board.getPlayerCards();
		assertEquals(6, playerCards.size());
		
		Card jim = new Card("Jim Halpert", CardType.PERSON);
		assertTrue(jim.equals(board.getCard("Jim Halpert")));
		
		Card dwight = new Card("Dwight Schrute", CardType.PERSON);
		assertTrue(dwight.equals(board.getCard("Dwight Schrute")));
		
		Card angela = new Card("Angela Schrute", CardType.PERSON);
		assertTrue(angela.equals(board.getCard("Angela Schrute")));
		
	}

}
