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
		assertEquals(board.getCard("Kitchen"), kitchen);
		
		Card cubicle = new Card("Cubicle", CardType.ROOM);
		assertEquals(board.getCard("Cubicle"), cubicle);
		
		Card manager = new Card("Regional Manager's Office", CardType.ROOM);
		assertEquals(board.getCard("Regional Manager's Office"), manager);
	}

}
